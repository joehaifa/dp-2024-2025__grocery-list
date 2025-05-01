package com.fges.commands;

import java.time.LocalDate;
import java.util.List;

// Handles the "info" command to display the current date, operating system, and Java version.
public class HandleInfoCommand implements Command {

    @Override
    public int execute(List<String> args) {
        String date = LocalDate.now().toString();
        String os = System.getProperty("os.name");
        String javaVersion = System.getProperty("java.version");

        System.out.println("Today's date: " + date);
        System.out.println("Operating System: " + os);
        System.out.println("Java version: " + javaVersion);

        return 0;
    }
}
