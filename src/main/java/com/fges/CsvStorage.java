package com.fges;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

class CsvStorage implements GroceryListStorage {
    private final String filePath;

    public CsvStorage(String filePath) {
        this.filePath = filePath;
    }

    public List<Item> load() {
        try {
            return java.nio.file.Files.lines(java.nio.file.Paths.get(filePath))
                    .skip(1)
                    .map(line -> {
                        String[] parts = line.split(",");
                        return new Item(parts[0].trim(), Integer.parseInt(parts[1].trim()), parts.length > 2 ? parts[2].trim() : "default");
                    }).collect(Collectors.toList());
        } catch (IOException e) {
            return new java.util.ArrayList<>();
        }
    }

    public void save(List<Item> items) throws IOException {
        try (java.io.BufferedWriter writer = java.nio.file.Files.newBufferedWriter(java.nio.file.Paths.get(filePath))) {
            writer.write("Item,Quantity,Category\n");
            for (Item item : items) {
                writer.write(item.getName() + "," + item.getQuantity() + "," + item.getCategory() + "\n");
            }
        }
    }
}

