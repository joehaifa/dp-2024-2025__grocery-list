package com.fges.commands;

import com.fges.application.CommandContext;
import com.fges.grocerydata.GroceryItem;
import com.fges.grocerydata.GroceryListManager;

import java.io.IOException;
import java.util.List;

public class HandleAddCommand implements Command {
    private final GroceryListManager dao;

    public HandleAddCommand(GroceryListManager dao) {
        this.dao = dao;
    }

    @Override
    public int execute(CommandContext context) throws IOException {
        if (context.getSourceFile().isEmpty()) {
            System.err.println("Error: -s (source) option is required for this command.");
            return 1;
        }

        List<String> args = context.getPositionalArgs();

        if (args.size() < 3) {
            System.err.println("Usage: add <item> <quantity> [category]");
            return 1;
        }

        String itemName = args.get(1);
        int quantity = Integer.parseInt(args.get(2));
        String category = context.getCategory() != null ? context.getCategory() : "default";

        dao.addItem(new GroceryItem(itemName, quantity, category));
        System.out.println("Added: " + itemName + " (" + quantity + ") in category " + category);
        return 0;
    }
}
