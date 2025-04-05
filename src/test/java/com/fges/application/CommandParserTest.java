package com.fges.application;

import org.apache.commons.cli.CommandLine;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandParserTest {

    @Test
    public void test_parseArgs_should_return_CommandLine_when_source_is_present() {
        // Arrange
        String[] args = {"--source", "liste.json", "add", "pommes", "2"};

        // Act
        CommandLine result = CommandParser.parseArgs(args);

        // Assert
        assertNotNull(result);
        assertEquals("liste.json", result.getOptionValue("source"));
    }

    @Test
    public void test_parseArgs_should_return_null_when_source_is_missing() {
        // Arrange
        String[] args = {"add", "pommes", "2"};

        // Act
        CommandLine result = CommandParser.parseArgs(args);

        // Assert
        assertNull(result);
    }
}
