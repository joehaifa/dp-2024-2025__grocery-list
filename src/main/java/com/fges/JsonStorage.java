package com.fges;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

class JsonStorage implements GroceryListStorage {
    private final ObjectMapper objectMapper;
    private final String filePath;

    public JsonStorage(String filePath, ObjectMapper objectMapper) {
        this.filePath = filePath;
        this.objectMapper = objectMapper;
    }

    public List<Item> load() {
        try {
            return objectMapper.readValue(new java.io.File(filePath), new com.fasterxml.jackson.core.type.TypeReference<>() {});
        } catch (IOException e) {
            return new java.util.ArrayList<>();
        }
    }

    public void save(List<Item> items) throws IOException {
        objectMapper.writeValue(new java.io.File(filePath), items);
    }
}