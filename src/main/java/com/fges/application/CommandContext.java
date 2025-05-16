package com.fges.application;

import java.util.List;
import java.util.Optional;

// Encapsulates CLI input data (arguments, category, source file) to pass to a command.
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
