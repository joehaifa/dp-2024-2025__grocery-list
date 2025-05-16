package com.fges.storage;

import com.fges.grocerydata.GroceryItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvStorageTest {

    @TempDir
    Path tempDir;

    @Test
    void saveAndLoadRoundTrip_shouldPersistItemsWithHeader() throws Exception {
        // Arrange
        Path file = tempDir.resolve("list.csv");
        CsvStorage storage = new CsvStorage(file.toString());
        List<GroceryItem> items = Arrays.asList(
                new GroceryItem("pomme", 5, "fruit"),
                new GroceryItem("pain", 1, "boulangerie")
        );

        // Act
        storage.save(items);
        List<GroceryItem> reloaded = storage.load(); 
        List<String> rawLines = Files.readAllLines(file); 

        // Assert
        assertEquals(2, reloaded.size());
        assertEquals("pomme",        reloaded.get(0).getName());
        assertEquals("fruit",        reloaded.get(0).getCategory());
        assertEquals("boulangerie",  reloaded.get(1).getCategory());
        assertEquals("GroceryItem,Quantity,Category", rawLines.get(0),
                "First line must be CSV header");
        assertEquals(3, rawLines.size(), "Header + 2 data lines expected");
    }

    @Test
    void load_shouldReturnEmptyList_whenCsvFileContainsOnlyHeader() throws Exception {
        // Arrange
        Path file = tempDir.resolve("empty.csv");
        Files.write(file, Collections.singletonList("GroceryItem,Quantity,Category"));
        CsvStorage storage = new CsvStorage(file.toString());

        // Act
        List<GroceryItem> out = storage.load();

        // Assert
        assertTrue(out.isEmpty(), "No data rows → expect empty list");
    }

    @Test
    void load_shouldReturnEmptyList_whenFileDoesNotExist() {
        // Arrange
        Path file = tempDir.resolve("missing.csv");
        CsvStorage storage = new CsvStorage(file.toString());

        //  Act
        List<GroceryItem> out = storage.load();

        // Assert
        assertTrue(out.isEmpty(), "Missing file should give empty list, not exception");
    }
}
