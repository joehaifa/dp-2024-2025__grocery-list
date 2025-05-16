package com.fges.webPage;

import fr.anthonyquere.GroceryShopServer;
import fr.anthonyquere.MyGroceryShop;

// Standalone entry point to run the grocery shop server on port 8080 for testing purposes.
public class Main {
    public static void main(String[] args) {
        MyGroceryShop groceryShop = new SimpleGroceryShop();
        GroceryShopServer server = new GroceryShopServer(groceryShop);
        server.start(8080);

        System.out.println("Grocery shop server started at http://localhost:8080");

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}