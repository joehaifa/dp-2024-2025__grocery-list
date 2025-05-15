package com.fges.commands;

import java.io.IOException;
import java.util.List;

import com.fges.application.CommandContext;
import com.fges.grocerydata.GroceryListManager;

// Handles the "remove" command to delete a grocery item from the list.
public class HandleRemoveCommand implements Command {
    private final GroceryListManager dao;

    public HandleRemoveCommand(GroceryListManager dao) {
        this.dao = dao;
    }

    @Override
    public int execute(CommandContext context) throws IOException {
        if (context.getSourceFile().isEmpty()) {
            System.err.println("Error: -s (source) option is required for this command.");
            return 1;
        }
        List<String> args = context.getPositionalArgs();
        if (args.size() < 2) {
            System.err.println("Usage: remove <item>");
            return 1;
        }
        String itemName = args.get(1);
        boolean removed = dao.removeItem(itemName);
        if (removed) {
            System.out.println("Removed: " + itemName);
        } else {
            System.err.println("GroceryItem not found: " + itemName);
            return 1;
        }
        return 0;
    }
}
