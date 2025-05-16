package com.fges.storage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.fges.grocerydata.GroceryItem;

// Loads and saves grocery items from and to a CSV file using line-based parsing.
public class CsvStorage implements GroceryListStorage {
    private final String filePath;

    public CsvStorage(String filePath) {
        this.filePath = filePath;
    }

    public List<GroceryItem> load() {
        try {
            return java.nio.file.Files.lines(java.nio.file.Paths.get(filePath))
                    .skip(1)
                    .map(line -> {
                        String[] parts = line.split(",");
                        return new GroceryItem(parts[0].trim(), Integer.parseInt(parts[1].trim()), parts.length > 2 ? parts[2].trim() : "default");
                    }).collect(Collectors.toList());
        } catch (IOException e) {
            return new java.util.ArrayList<>();
        }
    }

    public void save(List<GroceryItem> groceryItems) throws IOException {
        try (java.io.BufferedWriter writer = java.nio.file.Files.newBufferedWriter(java.nio.file.Paths.get(filePath))) {
            writer.write("GroceryItem,Quantity,Category\n");
            for (GroceryItem groceryItem : groceryItems) {
                writer.write(groceryItem.getName() + "," + groceryItem.getQuantity() + "," + groceryItem.getCategory() + "\n");
            }
        }
    }
}

