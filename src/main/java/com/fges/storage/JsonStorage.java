package com.fges.storage;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fges.grocerydata.GroceryItem;

import java.io.IOException;
import java.util.List;

// Handles loading and saving grocery items to a JSON file.
public class JsonStorage implements GroceryListStorage {
    private final ObjectMapper objectMapper;
    private final String filePath;

    public JsonStorage(String filePath, ObjectMapper objectMapper) {
        this.filePath = filePath;
        this.objectMapper = objectMapper;
    }

    public List<GroceryItem> load() {
        try {
            return objectMapper.readValue(new java.io.File(filePath), new com.fasterxml.jackson.core.type.TypeReference<>() {});
        } catch (IOException e) {
            return new java.util.ArrayList<>();
        }
    }

    public void save(List<GroceryItem> groceryItems) throws IOException {
        objectMapper.writeValue(new java.io.File(filePath), groceryItems);
    }
}