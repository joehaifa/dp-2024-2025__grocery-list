package com.fges.donnees;

import com.fges.stockage.GroceryListStorage;
import java.io.IOException;
import java.util.List;

// Gère l'accès aux données de la liste de courses.
public class GroceryListDAO {
    private final GroceryListStorage storage;

    public GroceryListDAO(GroceryListStorage storage) {
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