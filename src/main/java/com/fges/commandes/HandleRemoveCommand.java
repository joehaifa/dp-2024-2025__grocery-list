package com.fges.commandes;

import com.fges.donnees.GroceryListDAO;

import java.io.IOException;
import java.util.List;

public class HandleRemoveCommand implements Command {
    private final GroceryListDAO dao;

    public HandleRemoveCommand(GroceryListDAO dao) {
        this.dao = dao;
    }

    @Override
    public int execute(List<String> args) throws IOException {
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
