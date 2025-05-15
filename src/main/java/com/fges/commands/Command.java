package com.fges.commands;

import com.fges.application.CommandContext;

import java.io.IOException;

public interface Command {
    int execute(CommandContext context) throws IOException;
}
