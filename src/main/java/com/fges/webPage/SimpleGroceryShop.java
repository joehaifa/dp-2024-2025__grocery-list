package com.fges.webPage;

import fr.anthonyquere.MyGroceryShop;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Provides an in-memory implementation of MyGroceryShop for testing and demo purposes.
public class SimpleGroceryShop implements MyGroceryShop {
    private final List<WebGroceryItem> groceries = new ArrayList<>();

    @Override
    public List<WebGroceryItem> getGroceries() {
        return new ArrayList<>(groceries);
    }

    @Override
    public void addGroceryItem(String name, int quantity, String category) {
        groceries.add(new WebGroceryItem(name, quantity, category));
    }

    @Override
    public void removeGroceryItem(String name) {
        groceries.removeIf(item -> item.name().equals(name));
    }

    @Override
    public Runtime getRuntime() {
        return new Runtime(
                LocalDate.now(),
                System.getProperty("java.version"),
                System.getProperty("os.name")
        );
    }
}