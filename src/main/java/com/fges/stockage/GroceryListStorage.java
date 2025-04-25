package com.fges.stockage;


import com.fges.donnees.GroceryItem;

import java.io.IOException;
import java.util.List;

// Interface définissant les méthodes de chargement et sauvegarde de la liste de courses.
public interface GroceryListStorage {
    List<GroceryItem> load();
    void save(List<GroceryItem> groceryItems) throws IOException;
}