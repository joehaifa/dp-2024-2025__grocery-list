package com.fges;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class GroceryListManagerTest {

    @TempDir
    Path tempDir;
    private Path testJsonFile;
    private Path testCsvFile;
    private ObjectMapper realObjectMapper;
    private ObjectMapper mockObjectMapper;

    @BeforeEach
    void setUp() throws IOException {
        testJsonFile = tempDir.resolve("test.json");
        testCsvFile = tempDir.resolve("test.csv");
        realObjectMapper = new ObjectMapper();
        mockObjectMapper = Mockito.mock(ObjectMapper.class);
    }

    @Test
    void testLoadGroceryList_JsonFileNotExists() {
        GroceryListManager manager = new GroceryListManager("nonexistent.json", "json", realObjectMapper);
        assertDoesNotThrow(() -> {
            List<Item> result = manager.loadGroceryList();
            assertTrue(result.isEmpty());
        });
    }

    @Test
    void testLoadGroceryList_CsvFileNotExists() {
        GroceryListManager manager = new GroceryListManager("nonexistent.csv", "csv", realObjectMapper);
        assertDoesNotThrow(() -> {
            List<Item> result = manager.loadGroceryList();
            assertTrue(result.isEmpty());
        });
    }

    @Test
    void testLoadGroceryList_JsonFormat() throws IOException {
        // Créer un fichier JSON de test
        List<Item> expectedItems = List.of(
                new Item("Apple", 5),
                new Item("Banana", 3)
        );
        realObjectMapper.writeValue(testJsonFile.toFile(), expectedItems);

        GroceryListManager manager = new GroceryListManager(testJsonFile.toString(), "json", realObjectMapper);
        List<Item> result = manager.loadGroceryList();

        assertEquals(expectedItems.size(), result.size());
        assertEquals(expectedItems.get(0).getName(), result.get(0).getName());
        assertEquals(expectedItems.get(0).getQuantity(), result.get(0).getQuantity());
    }

    @Test
    void testLoadGroceryList_CsvFormat() throws IOException {
        // Créer un fichier CSV de test
        String csvContent = "Item,Quantity\nApple,5\nBanana,3";
        Files.write(testCsvFile, csvContent.getBytes());

        GroceryListManager manager = new GroceryListManager(testCsvFile.toString(), "csv", realObjectMapper);
        List<Item> result = manager.loadGroceryList();

        assertEquals(2, result.size());
        assertEquals("Apple", result.get(0).getName());
        assertEquals(5, result.get(0).getQuantity());
        assertEquals("Banana", result.get(1).getName());
        assertEquals(3, result.get(1).getQuantity());
    }

    @Test
    void testLoadGroceryList_JsonFormatWithMock() throws IOException {
        List<Item> expectedItems = List.of(new Item("Milk", 2));
        when(mockObjectMapper.readValue(any(String.class), any(TypeReference.class)))
                .thenReturn(expectedItems);

        GroceryListManager manager = new GroceryListManager(testJsonFile.toString(), "json", mockObjectMapper);
        // Créer le fichier vide pour que le test passe
        Files.createFile(testJsonFile);

        List<Item> result = manager.loadGroceryList();

        assertEquals(1, result.size());
        assertEquals("Milk", result.get(0).getName());
        assertEquals(2, result.get(0).getQuantity());
        verify(mockObjectMapper).readValue(any(String.class), any(TypeReference.class));
    }

    @Test
    void testSaveGroceryList_JsonFormat() throws IOException {
        List<Item> itemsToSave = List.of(
                new Item("Orange", 4),
                new Item("Bread", 1)
        );

        GroceryListManager manager = new GroceryListManager(testJsonFile.toString(), "json", realObjectMapper);
        manager.saveGroceryList(itemsToSave);

        assertTrue(Files.exists(testJsonFile));
        List<Item> loadedItems = realObjectMapper.readValue(testJsonFile.toFile(), new TypeReference<List<Item>>() {});
        assertEquals(itemsToSave.size(), loadedItems.size());
    }

    @Test
    void testSaveGroceryList_CsvFormat() throws IOException {
        List<Item> itemsToSave = List.of(
                new Item("Orange", 4),
                new Item("Bread", 1)
        );

        GroceryListManager manager = new GroceryListManager(testCsvFile.toString(), "csv", realObjectMapper);
        manager.saveGroceryList(itemsToSave);

        assertTrue(Files.exists(testCsvFile));
        List<String> lines = Files.readAllLines(testCsvFile);
        assertEquals(3, lines.size()); // Header + 2 items
        assertEquals("Item,Quantity", lines.get(0));
        assertTrue(lines.get(1).startsWith("Orange,4"));
        assertTrue(lines.get(2).startsWith("Bread,1"));
    }

    @Test
    void testSaveGroceryList_JsonFormatWithMock() throws IOException {
        List<Item> itemsToSave = List.of(new Item("Eggs", 12));

        GroceryListManager manager = new GroceryListManager(testJsonFile.toString(), "json", mockObjectMapper);
        manager.saveGroceryList(itemsToSave);

        verify(mockObjectMapper).writeValue(eq(testJsonFile.toFile()), eq(itemsToSave));
    }

    @Test
    void testConstructor_NullFileName_ThrowsException() {
        assertThrows(NullPointerException.class, () ->
                new GroceryListManager(null, "json", realObjectMapper));
    }

    @Test
    void testConstructor_NullFormat_ThrowsException() {
        assertThrows(NullPointerException.class, () ->
                new GroceryListManager("test.json", null, realObjectMapper));
    }



    @Test
    void testConstructor_InvalidFormat_DefaultsToJson() throws IOException {
        GroceryListManager manager = new GroceryListManager(testJsonFile.toString(), "invalid", realObjectMapper);

        List<Item> items = List.of(new Item("Test", 1));
        manager.saveGroceryList(items);

        // Vérifie que le fichier a été sauvegardé en JSON malgré le format invalide
        assertTrue(Files.exists(testJsonFile));
        List<Item> loadedItems = realObjectMapper.readValue(testJsonFile.toFile(), new TypeReference<List<Item>>() {});
        assertEquals(1, loadedItems.size());
    }
}