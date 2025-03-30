package com.fges;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {

    @Test
    void parseArgs_WithRequiredSource_ShouldReturnCommandLine() {
        String[] args = {"-s", "grocery.json"};
        CommandLine cmd = CommandParser.parseArgs(args);

        assertNotNull(cmd);
        assertTrue(cmd.hasOption("s"));
        assertEquals("grocery.json", cmd.getOptionValue("s"));
        assertFalse(cmd.hasOption("f")); // format optionnel
    }

    @Test
    void parseArgs_WithSourceAndFormat_ShouldReturnCommandLine() {
        String[] args = {"-s", "grocery.csv", "-f", "csv"};
        CommandLine cmd = CommandParser.parseArgs(args);

        assertNotNull(cmd);
        assertTrue(cmd.hasOption("s"));
        assertEquals("grocery.csv", cmd.getOptionValue("s"));
        assertTrue(cmd.hasOption("f"));
        assertEquals("csv", cmd.getOptionValue("f"));
    }

    @Test
    void parseArgs_WithLongOptions_ShouldReturnCommandLine() {
        String[] args = {"--source", "grocery.json", "--format", "json"};
        CommandLine cmd = CommandParser.parseArgs(args);

        assertNotNull(cmd);
        assertTrue(cmd.hasOption("source"));
        assertEquals("grocery.json", cmd.getOptionValue("source"));
        assertTrue(cmd.hasOption("format"));
        assertEquals("json", cmd.getOptionValue("format"));
    }

    @Test
    void parseArgs_WithoutSource_ShouldReturnNull() {
        String[] args = {"-f", "json"};
        CommandLine cmd = CommandParser.parseArgs(args);
        assertNull(cmd);
    }

    @Test
    void parseArgs_WithEmptyArgs_ShouldReturnNull() {
        String[] args = {};
        CommandLine cmd = CommandParser.parseArgs(args);
        assertNull(cmd);
    }

    @ParameterizedTest
    @ValueSource(strings = {"json", "csv"})
    void parseArgs_WithDifferentFormats_ShouldReturnCommandLine(String format) {
        String[] args = {"-s", "grocery." + format, "-f", format};
        CommandLine cmd = CommandParser.parseArgs(args);

        assertNotNull(cmd);
        assertEquals(format, cmd.getOptionValue("f"));
    }

    @Test
    void parseArgs_WithInvalidOption_ShouldReturnNull() {
        String[] args = {"-s", "grocery.json", "-x", "invalid"};
        CommandLine cmd = CommandParser.parseArgs(args);
        assertNull(cmd);
    }
}