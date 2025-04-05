package com.fges.commandes;

import com.fges.donnees.GroceryListDAO;
import com.fges.donnees.Item;

import java.io.IOException;
import java.util.List;

// Implémente la commande "add" pour ajouter un article à la liste.
public class HandleAddCommand implements Command {
    public int execute(List<String> args, GroceryListDAO dao) throws IOException {
        if (args.size() < 3) {
            System.err.println("Usage: add <item> <quantity> [category]");
            return 1;
        }
        String itemName = args.get(1);
        int quantity = Integer.parseInt(args.get(2));
        String category = args.size() > 3 ? args.get(3) : "default";

        dao.addItem(new Item(itemName, quantity, category));
        System.out.println("Added: " + itemName + " (" + quantity + ") in category " + category);
        return 0;
    }
}