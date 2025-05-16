package com.fges.application;

import com.fges.commands.*;
import com.fges.grocerydata.GroceryListManager;
import com.fges.storage.CsvStorage;
import com.fges.storage.GroceryListStorage;
import com.fges.storage.JsonStorage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.CommandLine;

import java.io.IOException;
import java.util.List;

// Executes the appropriate command using CLI arguments wrapped in a context object.

class CommandExecutor {
    private final ObjectMapper objectMapper;

    public CommandExecutor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public int run(String[] args) throws IOException {
        CommandLine cmd = CommandParser.parseArgs(args);
        if (cmd == null) return 1;

        String fileName = cmd.getOptionValue("s");
        String format = cmd.getOptionValue("f", "json").toLowerCase();
        GroceryListStorage storage = format.equals("csv") ? new CsvStorage(fileName) : new JsonStorage(fileName, objectMapper);
        GroceryListManager dao = new GroceryListManager(storage);
        List<String> positionalArgs = cmd.getArgList();
        String categoryOption = cmd.getOptionValue("c");

        CommandContext context = new CommandContext(positionalArgs, categoryOption, fileName);

        Command command;
        switch (positionalArgs.get(0)) {
            case "add":
                command = new HandleAddCommand(dao);
                break;
            case "remove":
                command = new HandleRemoveCommand(dao);
                break;
            case "list":
                command = new HandleListCommand(dao);
                break;
            case "info":
                command = new HandleInfoCommand();
                break;
            case "web":
                command = new HandleWebCommand(dao);
                break;
            default:
                System.err.println("Unknown command");
                return 1;
        }

        return command.execute(context);
    }
}