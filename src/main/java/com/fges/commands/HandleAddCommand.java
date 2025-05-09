package com.fges.commands;

import java.io.IOException;
import java.util.List;

import com.fges.grocerydata.GroceryItem;
import com.fges.grocerydata.GroceryListDAO;

// Handles the "add" command to add a new grocery item to the list.
public class HandleAddCommand implements Command {
    private final GroceryListDAO dao;

    public HandleAddCommand(GroceryListDAO dao) {
        this.dao = dao;
    }

    @Override
    public int execute(List<String> args) throws IOException {
        if (args.size() < 3) {
            System.err.println("Usage: add <item> <quantity> [category]");
            return 1;
        }
        String itemName = args.get(1);
        int quantity = Integer.parseInt(args.get(2));
        String category = args.size() > 3 ? args.get(3) : "default";

        dao.addItem(new GroceryItem(itemName, quantity, category));
        System.out.println("Added: " + itemName + " (" + quantity + ") in category " + category);
        return 0;
    }
}
