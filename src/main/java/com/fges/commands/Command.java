package com.fges.commands;

import com.fges.application.CommandContext;

import java.io.IOException;

// Defines a command that can be executed with contextual CLI arguments.
public interface Command {
    int execute(CommandContext context) throws IOException;
}
