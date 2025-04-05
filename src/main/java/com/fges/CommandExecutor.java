package com.fges;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.CommandLine;

import java.io.IOException;
import java.util.ArrayList;
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

        // Get the category from command line option if it exists
        String categoryOption = cmd.getOptionValue("c");

        return switch (positionalArgs.get(0)) {
            case "add" -> {
                // If category was provided as option, add it to the positional args
                if (categoryOption != null) {
                    positionalArgs.add(categoryOption);
                }
                yield new HandleAddCommand().execute(positionalArgs, dao);
            }
            case "remove" -> new HandleRemoveCommand().execute(positionalArgs, dao);
            case "list" -> new HandleListCommand().execute(positionalArgs, dao);
            default -> { System.err.println("Unknown command"); yield 1; }
        };
    }
}
