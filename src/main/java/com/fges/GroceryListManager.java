package com.fges;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GroceryListManager {

    private final Path filePath;
    private final ObjectMapper objectMapper;
    private final String format; // "json" ou "csv"

    public GroceryListManager(String fileName, String format, ObjectMapper objectMapper) {
        this.filePath = Paths.get(fileName);
        this.format = format.toLowerCase();
        this.objectMapper = objectMapper;
    }

    public List<Item> loadGroceryList() throws IOException {
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }

        if (format.equals("csv")) {
            return Files.lines(filePath)
                    .skip(1) // Ignorer la ligne d'en-tête
                    .map(line -> {
                        String[] parts = line.split(",");
                        String name = parts[0].trim();
                        int quantity = Integer.parseInt(parts[1].trim());
                        return new Item(name, quantity);
                    })
                    .collect(Collectors.toList());
        } else { // Par défaut, JSON
            String content = Files.readString(filePath);
            return objectMapper.readValue(content, new TypeReference<List<Item>>() {});
        }
    }

    public void saveGroceryList(List<Item> list) throws IOException {
        if (format.equals("csv")) {
            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                writer.write("Item,Quantity\n"); // Ajouter l'en-tête CSV
                for (Item item : list) {
                    writer.write(item.toCSV() + "\n"); // Sauvegarder en format CSV
                }
            }
        } else { // JSON
            objectMapper.writeValue(filePath.toFile(), list);
        }
    }


}
