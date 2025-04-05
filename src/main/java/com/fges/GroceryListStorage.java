package com.fges;



import java.io.IOException;
import java.util.List;

interface GroceryListStorage {
    List<Item> load();
    void save(List<Item> items) throws IOException;
}