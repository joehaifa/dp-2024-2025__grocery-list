package com.fges;

import java.io.IOException;
import java.util.List;

class GroceryListDAO {
    private final GroceryListStorage storage;

    public GroceryListDAO(GroceryListStorage storage) {
        this.storage = storage;
    }

    public List<Item> getItems() {
        return storage.load();
    }

    public void addItem(Item item) throws IOException {
        List<Item> items = getItems();
        items.add(item);
        storage.save(items);
    }

    public boolean removeItem(String name) throws IOException {
        List<Item> items = getItems();
        boolean removed = items.removeIf(item -> item.getName().equalsIgnoreCase(name));
        if (removed) storage.save(items);
        return removed;
    }
}