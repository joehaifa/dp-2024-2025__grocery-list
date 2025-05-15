package com.fges.application;

import java.util.List;
import java.util.Optional;

// A context object to carry all necessary data for executing a command.
public class CommandContext {
    private final List<String> positionalArgs;
    private final String category;
    private final String sourceFile;

    public CommandContext(List<String> positionalArgs, String category, String sourceFile) {
        this.positionalArgs = positionalArgs;
        this.category = category;
        this.sourceFile = sourceFile;
    }

    public List<String> getPositionalArgs() {
        return positionalArgs;
    }

    public String getCategory() {
        return category;
    }

    public Optional<String> getSourceFile() {
        return Optional.ofNullable(sourceFile);
    }
}
