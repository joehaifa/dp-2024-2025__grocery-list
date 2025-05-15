package com.fges.commands;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fges.application.CommandContext;
import com.fges.grocerydata.GroceryItem;
import com.fges.grocerydata.GroceryListManager;

// Handles the "list" command to display grocery items grouped by category.
public class HandleListCommand implements Command {
    private final GroceryListManager dao;

    public HandleListCommand(GroceryListManager dao) {
        this.dao = dao;
    }

    @Override
    public int execute(CommandContext context) {
        if (context.getSourceFile().isEmpty()) {
            System.err.println("Error: -s (source) option is required for this command.");
            return 1;
        }
        Map<String, List<GroceryItem>> groupedByCategory = dao.getItems().stream()
                .collect(Collectors.groupingBy(GroceryItem::getCategory));

        groupedByCategory.forEach((category, items) -> {
            System.out.println("#" + category + ":");
            items.forEach(item -> System.out.println("  " + item));
        });
        return 0;
    }

}
