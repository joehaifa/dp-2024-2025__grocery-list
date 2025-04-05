package com.fges.stockage;


import com.fges.donnees.Item;
import java.io.IOException;
import java.util.List;

// Interface définissant les méthodes de chargement et sauvegarde de la liste de courses.
public interface GroceryListStorage {
    List<Item> load();
    void save(List<Item> items) throws IOException;
}