package com.fges.storage;


import java.io.IOException;
import java.util.List;

import com.fges.grocerydata.GroceryItem;

// Defines the contract for loading and saving grocery items.
public interface GroceryListStorage {
    List<GroceryItem> load();
    void save(List<GroceryItem> groceryItems) throws IOException;
}