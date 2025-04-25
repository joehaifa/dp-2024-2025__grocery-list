package com.fges.commandes;

import com.fges.donnees.GroceryListDAO;

import java.time.LocalDate;
import java.util.List;

public class HandleInfoCommand implements Command {

    public int execute(List<String> args) {
        String date = java.time.LocalDate.now().toString();
        String os = System.getProperty("os.name");
        String javaVersion = System.getProperty("java.version");

        System.out.println("Today's date: " + date);
        System.out.println("Operating System: " + os);
        System.out.println("Java version: " + javaVersion);

        return 0;
    }
}
