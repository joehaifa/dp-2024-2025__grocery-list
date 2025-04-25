package com.fges.commandes;

import com.fges.donnees.GroceryItem;
import com.fges.donnees.GroceryListDAO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HandleListCommand implements Command {
    private final GroceryListDAO dao;

    public HandleListCommand(GroceryListDAO dao) {
        this.dao = dao;
    }

    @Override
    public int execute(List<String> args) {
        Map<String, List<GroceryItem>> groupedByCategory = dao.getItems().stream()
                .collect(Collectors.groupingBy(GroceryItem::getCategory));

        groupedByCategory.forEach((category, items) -> {
            System.out.println("#" + category + ":");
            items.forEach(item -> System.out.println("  " + item));
        });
        return 0;
    }
}
