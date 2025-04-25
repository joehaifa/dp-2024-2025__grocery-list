package com.fges.stockage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fges.donnees.GroceryItem;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonStorageTest {

    @Test
    public void test_save_and_load_should_work_consistently() throws Exception {
        // Arrange
        File tempFile = File.createTempFile("grocery", ".json");
        tempFile.deleteOnExit();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonStorage storage = new JsonStorage(tempFile.getAbsolutePath(), objectMapper);

        List<GroceryItem> itemsToSave = List.of(
                new GroceryItem("pomme", 3, "fruit"),
                new GroceryItem("carotte", 1, "legume")
        );

        // Act
        storage.save(itemsToSave);
        List<GroceryItem> loadedGroceryItems = storage.load();

        // Assert
        assertThat(loadedGroceryItems).hasSize(2);
        assertThat(loadedGroceryItems.get(0).getName()).isEqualTo("pomme");
        assertThat(loadedGroceryItems.get(0).getQuantity()).isEqualTo(3);
        assertThat(loadedGroceryItems.get(0).getCategory()).isEqualTo("fruit");
    }

    @Test
    public void test_load_should_return_empty_list_if_file_invalid() throws Exception {
        // Arrange
        File tempFile = File.createTempFile("invalid", ".json");
        tempFile.deleteOnExit();

        Files.writeString(tempFile.toPath(), "not a json");

        JsonStorage storage = new JsonStorage(tempFile.getAbsolutePath(), new ObjectMapper());

        // Act
        List<GroceryItem> result = storage.load();

        // Assert
        assertThat(result).isEmpty();
    }
}
