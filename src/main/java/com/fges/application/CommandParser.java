package com.fges.application;

import org.apache.commons.cli.*;

// Gère l'analyse des arguments passés en ligne de commande.
public class CommandParser {
    public static CommandLine parseArgs(String[] args) {
        Options cliOptions = new Options();
        cliOptions.addRequiredOption("s", "source", true, "File containing the grocery list");
        cliOptions.addOption("f", "format", true, "Format of the grocery list file (json or csv)");
        cliOptions.addOption("c", "category", true, "Category of the item (optional)");

        CommandLineParser parser = new DefaultParser();
        try {
            return parser.parse(cliOptions, args);
        } catch (ParseException ex) {
            System.err.println("Failed to parse arguments: " + ex.getMessage());
            return null;
        }
    }
}
