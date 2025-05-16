package com.fges.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fges.grocerydata.GroceryItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonStorageTest {

    @TempDir
    Path tempDir;

    @Test
    void saveAndLoadRoundTrip_shouldPersistAllItems() throws Exception {
        // Arrange
        Path file = tempDir.resolve("list.json");
        JsonStorage storage = new JsonStorage(file.toString(), new ObjectMapper());
        List<GroceryItem> items = Arrays.asList(
                new GroceryItem("lait", 2, "frais"),
                new GroceryItem("riz", 1, "épicerie")
        );

        //Act
        storage.save(items);
        List<GroceryItem> reloaded = storage.load();

        //Assert
        assertEquals(2, reloaded.size());
        assertEquals("lait", reloaded.get(0).getName());
        assertEquals("frais", reloaded.get(0).getCategory());
        assertEquals("épicerie", reloaded.get(1).getCategory());
    }

    @Test
    void load_shouldReturnEmptyList_whenFileDoesNotExist() {
        //Arrange
        Path file = tempDir.resolve("missing.json");
        JsonStorage storage = new JsonStorage(file.toString(), new ObjectMapper());

        //  Act
        List<GroceryItem> out = storage.load();

        //Assert
        assertTrue(out.isEmpty(), "Missing file → expect empty list, not exception");
    }

    @Test
    void load_shouldReturnEmptyList_whenFileContainsInvalidJson() throws Exception {
        // Arrange
        Path file = tempDir.resolve("corrupt.json");
        Files.write(file, Collections.singletonList("{ invalid json }"));
        JsonStorage storage = new JsonStorage(file.toString(), new ObjectMapper());

        // Act
        List<GroceryItem> out = storage.load();

        //Assert
        assertTrue(out.isEmpty(), "Corrupt JSON -> expect empty list");
    }
}
