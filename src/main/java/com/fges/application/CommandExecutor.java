package com.fges.application;

import com.fges.commandes.*;
import com.fges.donnees.GroceryListDAO;
import com.fges.stockage.CsvStorage;
import com.fges.stockage.GroceryListStorage;
import com.fges.stockage.JsonStorage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.CommandLine;

import java.io.IOException;
import java.util.List;

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
        GroceryListDAO dao = new GroceryListDAO(storage);
        List<String> positionalArgs = cmd.getArgList();
        String categoryOption = cmd.getOptionValue("c");

        Command command;
        switch (positionalArgs.get(0)) {
            case "add":
                if (categoryOption != null) {
                    positionalArgs.add(categoryOption);
                }
                command = new HandleAddCommand(dao);
                break;
            case "remove":
                command = new HandleRemoveCommand(dao);
                break;
            case "list":
                command = new HandleListCommand(dao);
                break;

            case "info" :
                command = new HandleInfoCommand();
                break;
            default:
                System.err.println("Unknown command");
                return 1;
        }

        return command.execute(positionalArgs);
    }
}
