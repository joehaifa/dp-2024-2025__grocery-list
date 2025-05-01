package com.fges.commands;

import java.io.IOException;
import java.util.List;

// Defines a generic executable command with a list of arguments.
public interface Command {
    int execute(List<String> args) throws IOException;
}
