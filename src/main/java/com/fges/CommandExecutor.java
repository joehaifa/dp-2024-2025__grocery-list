package com.fges;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.CommandLine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandExecutor {

    private final ObjectMapper objectMapper;

    public CommandExecutor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public int run(String[] args) throws IOException {
        CommandParser parser = new CommandParser();
        CommandLine cmd = parser.parseArgs(args);
        if (cmd == null) return 1;

        String fileName = cmd.getOptionValue("s");
        String format = cmd.getOptionValue("f", "json").toLowerCase(); // Default to JSON
        List<String> positionalArgs = cmd.getArgList();

        if (positionalArgs.isEmpty()) {
            System.err.println("Missing Command");
            return 1;
        }

        String command = positionalArgs.get(0);
        GroceryListManager manager = new GroceryListManager(fileName, format, objectMapper);
        List<Item> groceryList = manager.loadGroceryList();

        return switch (command) {
            case "add" -> handleAdd(positionalArgs, groceryList, manager);
            case "remove" -> handleRemove(positionalArgs, groceryList, manager);
            case "list" -> handleList(groceryList);
            default -> {
                System.err.println("Unknown command: " + command);
                yield 1;
            }
        };
    }


    private int handleAdd(List<String> args, List<Item> groceryList, GroceryListManager manager) throws IOException {
        if (args.size() < 3) {
            System.err.println("Usage: add <item> <quantity>");
            return 1;
        }
        String itemName = args.get(1);
        int quantity = Integer.parseInt(args.get(2)); // Assure-toi que la quantité est un nombre valide

        groceryList.add(new Item(itemName, quantity));
        manager.saveGroceryList(groceryList);
        System.out.println("Added: " + itemName + " (" + quantity + ")");
        return 0;
    }


    private int handleRemove(List<String> args, List<Item> groceryList, GroceryListManager manager) throws IOException {
        if (args.size() < 2) {
            System.err.println("Usage: remove <item>");
            return 1;
        }

        String itemName = args.get(1);
        boolean removed = groceryList.removeIf(item -> item.getName().equalsIgnoreCase(itemName));

        if (removed) {
            manager.saveGroceryList(groceryList);
            System.out.println("Removed: " + itemName);
        } else {
            System.err.println("Item not found: " + itemName);
            return 1;
        }
        return 0;
    }




    private int handleList(List<Item> groceryList) {
        if (groceryList.isEmpty()) {
            System.out.println("Grocery list is empty.");
        } else {
            System.out.println("Grocery List:");
            groceryList.forEach(item -> System.out.println(item));
        }
        return 0;
    }
}
