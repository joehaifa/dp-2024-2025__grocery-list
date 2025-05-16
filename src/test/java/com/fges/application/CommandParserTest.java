package com.fges.application;

import org.apache.commons.cli.CommandLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CommandParser – parsing CLI arguments")
class CommandParserTest {

    @Test
    void parsesValidArguments_andReturnsPopulatedCommandLine() {
        // Arrange
        String[] args = { "add", "banane", "3", "-s", "list.json", "-f", "json" };

        // Act
        CommandLine cmd = CommandParser.parseArgs(args);

        // Assert
        assertNotNull(cmd, "Valid args must be parsed");
        assertEquals("list.json", cmd.getOptionValue("s"));
        assertEquals("json",      cmd.getOptionValue("f"));
        assertEquals(3,           cmd.getArgList().size());
        assertEquals("add",       cmd.getArgList().get(0));
    }

    @Test
    void parseArgs_shouldReturnNull_whenCommandMissing() {
        //Arrange
        String[] args = { "-s", "list.json" };

        // Act
        CommandLine cmd = CommandParser.parseArgs(args);

        //Assert
        assertNull(cmd, "No positional command → expect null");
    }

    @Test
    void parseArgs_returnsCommandLine_whenNoFormatFlag_defaultHandledLater() {
        //Arrange
        String[] args = { "list", "-s", "file.json" };

        //Act
        CommandLine cmd = CommandParser.parseArgs(args);

        //Assert
        assertNotNull(cmd);
        assertNull(cmd.getOptionValue("f"), "Missing -f should leave option null");
        assertEquals("file.json", cmd.getOptionValue("s"));
        assertEquals(1, cmd.getArgList().size());
        assertEquals("list", cmd.getArgList().get(0));
    }

    @Test
    void parseArgs_returnsNull_whenUnknownOptionProvided() {
        //Arrange
        String[] args = { "add", "-x" };

        //Act
        CommandLine cmd = CommandParser.parseArgs(args);

        //Assert
        assertNull(cmd, "Unknown option should trigger parse error → null");
    }
}
