package com.fges;

import org.apache.commons.cli.*;

public class CommandParser {

    public static CommandLine parseArgs(String[] args) {
        Options cliOptions = new Options();
        cliOptions.addRequiredOption("s", "source", true, "File containing the grocery list");
        cliOptions.addOption("f", "format", true, "Format of the grocery list file (json or csv)");

        CommandLineParser parser = new DefaultParser();
        try {
            return parser.parse(cliOptions, args);
        } catch (ParseException ex) {
            System.err.println("Failed to parse arguments: " + ex.getMessage());
            return null;
        }
    }
}
