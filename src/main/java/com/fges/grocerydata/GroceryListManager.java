package com.fges.grocerydata;

import java.io.IOException;
import java.util.List;

import com.fges.storage.GroceryListStorage;

// Manages grocery items by loading, adding, and removing them through the storage layer.
public class GroceryListManager {
    private final GroceryListStorage storage;

    public GroceryListManager(GroceryListStorage storage) {
        this.storage = storage;
    }

    public List<GroceryItem> getItems() {
        return storage.load();
    }

    public void addItem(GroceryItem groceryItem) throws IOException {
        List<GroceryItem> groceryItems = getItems();
        groceryItems.add(groceryItem);
        storage.save(groceryItems);
    }

    public boolean removeItem(String name) throws IOException {
        List<GroceryItem> groceryItems = getItems();
        boolean removed = groceryItems.removeIf(item -> item.getName().equalsIgnoreCase(name));
        if (removed) storage.save(groceryItems);
        return removed;
    }
}