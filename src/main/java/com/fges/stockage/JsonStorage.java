package com.fges.stockage;


import com.fges.donnees.GroceryItem;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

// Gère le chargement et la sauvegarde des données au format JSON.
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