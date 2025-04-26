package com.fges.application;

import org.apache.commons.cli.*;

import java.util.List;

// Parses and validates CLI arguments to extract commands and options.
public class CommandParser {
    public static CommandLine parseArgs(String[] args) {
        Options cliOptions = new Options();

        cliOptions.addOption("s", "source", true, "File containing the grocery list");
        cliOptions.addOption("f", "format", true, "Format of the grocery list file (json or csv)");
        cliOptions.addOption("c", "category", true, "Category of the item (optional)");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(cliOptions, args);

            List<String> positionalArgs = cmd.getArgList();

            if (!positionalArgs.isEmpty()) {
                String mainCommand = positionalArgs.get(0);

                if (!mainCommand.equals("info") && !cmd.hasOption("s")) {
                    System.err.println("Error: -s (source) option is required for this command.");
                    return null;
                }
            } else {
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
