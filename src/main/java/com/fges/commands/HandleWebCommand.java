package com.fges.commands;

import com.fges.application.CommandContext;
import com.fges.grocerydata.GroceryListManager;
import com.fges.webPage.MyGroceryShopAdapter;
import fr.anthonyquere.GroceryShopServer;

// Handles the "web" command to start a local HTTP server for grocery list management.
public class HandleWebCommand implements Command {
    private final GroceryListManager dao;

    public HandleWebCommand(GroceryListManager dao) {
        this.dao = dao;
    }

    @Override
    public int execute(CommandContext context) {
        if (context.getSourceFile().isEmpty()) {
            System.err.println("Error: -s (source) option is required for this command.");
            return 1;
        }
        try {
            int port = Integer.parseInt(context.getPositionalArgs().get(1));
            var adapter = new MyGroceryShopAdapter(dao);
            GroceryShopServer server = new GroceryShopServer(adapter);
            server.start(port);
            System.out.println("Web server started at http://localhost:" + port);
            Thread.currentThread().join(); // To keep it alive
        } catch (Exception e) {
            System.err.println("Error starting web server: " + e.getMessage());
            return 1;
        }
        return 0;
    }
}
