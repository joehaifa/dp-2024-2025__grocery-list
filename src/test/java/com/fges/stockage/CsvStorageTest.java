package com.fges.stockage;

import com.fges.donnees.GroceryItem;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CsvStorageTest {

    @Test
    public void test_load_should_return_items_from_csv_file() throws Exception {
        // Arrange
        File tempFile = File.createTempFile("grocery", ".csv");
        tempFile.deleteOnExit();

        try (FileWriter fw = new FileWriter(tempFile)) {
            fw.write("GroceryItem,Quantity,Category\n");
            fw.write("pomme,3,fruit\n");
            fw.write("carotte,2,legume\n");
        }

        CsvStorage storage = new CsvStorage(tempFile.getAbsolutePath());

        // Act
        List<GroceryItem> groceryItems = storage.load();

        // Assert
        assertThat(groceryItems).hasSize(2);
        assertThat(groceryItems.get(0).getName()).isEqualTo("pomme");
        assertThat(groceryItems.get(0).getQuantity()).isEqualTo(3);
        assertThat(groceryItems.get(0).getCategory()).isEqualTo("fruit");
    }

    @Test
    public void test_save_should_write_items_to_csv_file() throws Exception {
        // Arrange
        File tempFile = File.createTempFile("grocery", ".csv");
        tempFile.deleteOnExit();

        CsvStorage storage = new CsvStorage(tempFile.getAbsolutePath());
        List<GroceryItem> groceryItems = List.of(
                new GroceryItem("banane", 5, "fruit"),
                new GroceryItem("courgette", 1, "legume")
        );

        // Act
        storage.save(groceryItems);

        // Assert
        List<String> lines = Files.readAllLines(tempFile.toPath());
        assertThat(lines).hasSize(3);
        assertThat(lines.get(0)).isEqualTo("GroceryItem,Quantity,Category");
        assertThat(lines.get(1)).isEqualTo("banane,5,fruit");
        assertThat(lines.get(2)).isEqualTo("courgette,1,legume");
    }
}
