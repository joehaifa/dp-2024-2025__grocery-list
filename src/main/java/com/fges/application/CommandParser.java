package com.fges.application;

import org.apache.commons.cli.*;

import java.util.List;

// Parses CLI arguments and returns a CommandLine object with options and positional arguments.
public class CommandParser {
    public static CommandLine parseArgs(String[] args) {
        Options cliOptions = new Options();

        // "source" is now optional — we'll manually check if it's required later
        cliOptions.addOption("s", "source", true, "File containing the grocery list");
        cliOptions.addOption("f", "format", true, "Format of the grocery list file (json or csv)");
        cliOptions.addOption("c", "category", true, "Category of the item (optional)");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(cliOptions, args);

            List<String> positionalArgs = cmd.getArgList();

            if (positionalArgs.isEmpty()) {
                System.err.println("Error: No command provided.");
                return null;
            }

            return cmd;

        } catch (ParseException ex) {
            System.err.println("Failed to parse arguments: " + ex.getMessage());
            return null;
        }
    }

}
