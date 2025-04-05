package com.fges.commandes;


import com.fges.donnees.GroceryListDAO;

import java.io.IOException;
import java.util.List;

// Implémente la commande "remove" pour supprimer un article de la liste.
public class HandleRemoveCommand implements Command {
    public int execute(List<String> args, GroceryListDAO dao) throws IOException {
        if (args.size() < 2) {
            System.err.println("Usage: remove <item>");
            return 1;
        }
        String itemName = args.get(1);
        boolean removed = dao.removeItem(itemName);
        if (removed) {
            System.out.println("Removed: " + itemName);
        } else {
            System.err.println("Item not found: " + itemName);
            return 1;
        }
        return 0;
    }
}