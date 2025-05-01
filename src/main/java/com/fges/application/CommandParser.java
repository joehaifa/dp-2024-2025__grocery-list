package com.fges.application;

import org.apache.commons.cli.*;

import java.util.List;

// Gère l'analyse des arguments passés en ligne de commande.
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

            // Get the positional command like "add", "remove", "info" etc.
             List<String> positionalArgs = cmd.getArgList();

            if (!positionalArgs.isEmpty()) {
                String mainCommand = positionalArgs.get(0);

                // If the command is not "info", then -s must be present
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
