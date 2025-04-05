package com.fges;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class HandleListCommand implements Command {
    public int execute(List<String> args, GroceryListDAO dao) {
        Map<String, List<Item>> groupedByCategory = dao.getItems().stream()
                .collect(Collectors.groupingBy(Item::getCategory));

        groupedByCategory.forEach((category, items) -> {
            System.out.println("#" + category + ":");
            items.forEach(item -> System.out.println("  " + item));
        });
        return 0;
    }
}
