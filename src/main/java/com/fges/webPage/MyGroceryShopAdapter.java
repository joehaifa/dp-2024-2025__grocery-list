package com.fges.webPage;

import com.fges.grocerydata.GroceryItem;
import com.fges.grocerydata.GroceryListManager;
import fr.anthonyquere.MyGroceryShop;

import java.time.LocalDate;
import java.util.List;

// Adapts the GroceryListManager to the MyGroceryShop interface for web integration.
public class MyGroceryShopAdapter implements MyGroceryShop {
    private final GroceryListManager dao;

    public MyGroceryShopAdapter(GroceryListManager dao) {
        this.dao = dao;
    }

    @Override
    public List<WebGroceryItem> getGroceries() {
        return dao.getItems().stream()
                .map(item -> new WebGroceryItem(item.getName(), item.getQuantity(), item.getCategory()))
                .toList();
    }

    @Override
    public void addGroceryItem(String name, int quantity, String category) {
        try {
            dao.addItem(new GroceryItem(name, quantity, category));
        } catch (Exception e) {
            throw new RuntimeException("Failed to add item", e);
        }
    }

    @Override
    public void removeGroceryItem(String name) {
        try {
            dao.removeItem(name);
        } catch (Exception e) {
            throw new RuntimeException("Failed to remove item", e);
        }
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
