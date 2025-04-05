package com.fges.commandes;

import com.fges.donnees.GroceryListDAO;
import java.io.IOException;
import java.util.List;

// Interface représentant une commande exécutable.
public interface Command {
    int execute(List<String> args, GroceryListDAO dao) throws IOException;
}