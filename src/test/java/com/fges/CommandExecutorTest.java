package com.fges;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.CommandLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;
import org.mockito.MockedConstruction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CommandExecutorTest {

    private ObjectMapper objectMapper;
    private CommandExecutor commandExecutor;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        commandExecutor = new CommandExecutor(objectMapper);
    }

    @Test
    void testRunWithNullCommandLine() throws IOException {
        try (MockedStatic<CommandParser> mockedParser = mockStatic(CommandParser.class)) {
            mockedParser.when(() -> CommandParser.parseArgs(any())).thenReturn(null);
            int result = commandExecutor.run(new String[]{});
            assertEquals(1, result);
        }
    }

    @Test
    void testRunMissingCommand() throws IOException {
        CommandLine cmd = mock(CommandLine.class);
        when(cmd.getOptionValue("s")).thenReturn("test.json");
        when(cmd.getOptionValue("f", "json")).thenReturn("json");
        when(cmd.getArgList()).thenReturn(new ArrayList<>());

        try (MockedStatic<CommandParser> mockedParser = mockStatic(CommandParser.class)) {
            mockedParser.when(() -> CommandParser.parseArgs(any())).thenReturn(cmd);
            int result = commandExecutor.run(new String[]{});
            assertEquals(1, result);
        }
    }

    @Test
    void testRunUnknownCommand() throws IOException {
        CommandLine cmd = mock(CommandLine.class);
        when(cmd.getOptionValue("s")).thenReturn("test.json");
        when(cmd.getOptionValue("f", "json")).thenReturn("json");
        when(cmd.getArgList()).thenReturn(List.of("unknown"));

        try (MockedStatic<CommandParser> mockedParser = mockStatic(CommandParser.class)) {
            mockedParser.when(() -> CommandParser.parseArgs(any())).thenReturn(cmd);
            int result = commandExecutor.run(new String[]{});
            assertEquals(1, result);
        }
    }

    @Test
    void testHandleAddSuccess() throws IOException {
        File testFile = tempDir.resolve("test.json").toFile();
        CommandLine cmd = mock(CommandLine.class);
        when(cmd.getOptionValue("s")).thenReturn(testFile.getAbsolutePath());
        when(cmd.getOptionValue("f", "json")).thenReturn("json");
        when(cmd.getArgList()).thenReturn(List.of("add", "apple", "3"));

        try (MockedStatic<CommandParser> mockedParser = mockStatic(CommandParser.class);
             MockedConstruction<GroceryListManager> mockedManager = mockConstruction(GroceryListManager.class,
                     (mock, context) -> {
                         when(mock.loadGroceryList()).thenReturn(new ArrayList<>());
                     })) {

            mockedParser.when(() -> CommandParser.parseArgs(any())).thenReturn(cmd);
            int result = commandExecutor.run(new String[]{});
            assertEquals(0, result);

            List<GroceryListManager> managers = mockedManager.constructed();
            assertEquals(1, managers.size());
            verify(managers.get(0)).saveGroceryList(anyList());
        }
    }

    @Test
    void testHandleAddMissingArguments() throws IOException {
        CommandLine cmd = mock(CommandLine.class);
        when(cmd.getOptionValue("s")).thenReturn("test.json");
        when(cmd.getOptionValue("f", "json")).thenReturn("json");
        when(cmd.getArgList()).thenReturn(List.of("add", "apple"));

        try (MockedStatic<CommandParser> mockedParser = mockStatic(CommandParser.class)) {
            mockedParser.when(() -> CommandParser.parseArgs(any())).thenReturn(cmd);
            int result = commandExecutor.run(new String[]{});
            assertEquals(1, result);
        }
    }

    @Test
    void testHandleRemoveSuccess() throws IOException {
        File testFile = tempDir.resolve("test.json").toFile();
        List<Item> initialList = new ArrayList<>();
        initialList.add(new Item("apple", 3));

        CommandLine cmd = mock(CommandLine.class);
        when(cmd.getOptionValue("s")).thenReturn(testFile.getAbsolutePath());
        when(cmd.getOptionValue("f", "json")).thenReturn("json");
        when(cmd.getArgList()).thenReturn(List.of("remove", "apple"));

        try (MockedStatic<CommandParser> mockedParser = mockStatic(CommandParser.class);
             MockedConstruction<GroceryListManager> mockedManager = mockConstruction(GroceryListManager.class,
                     (mock, context) -> {
                         when(mock.loadGroceryList()).thenReturn(initialList);
                     })) {

            mockedParser.when(() -> CommandParser.parseArgs(any())).thenReturn(cmd);
            int result = commandExecutor.run(new String[]{});
            assertEquals(0, result);

            List<GroceryListManager> managers = mockedManager.constructed();
            assertEquals(1, managers.size());
            verify(managers.get(0)).saveGroceryList(anyList());
        }
    }

    @Test
    void testHandleRemoveItemNotFound() throws IOException {
        File testFile = tempDir.resolve("test.json").toFile();
        List<Item> initialList = new ArrayList<>();
        initialList.add(new Item("banana", 2));

        CommandLine cmd = mock(CommandLine.class);
        when(cmd.getOptionValue("s")).thenReturn(testFile.getAbsolutePath());
        when(cmd.getOptionValue("f", "json")).thenReturn("json");
        when(cmd.getArgList()).thenReturn(List.of("remove", "apple"));

        try (MockedStatic<CommandParser> mockedParser = mockStatic(CommandParser.class);
             MockedConstruction<GroceryListManager> mockedManager = mockConstruction(GroceryListManager.class,
                     (mock, context) -> {
                         when(mock.loadGroceryList()).thenReturn(initialList);
                     })) {

            mockedParser.when(() -> CommandParser.parseArgs(any())).thenReturn(cmd);
            int result = commandExecutor.run(new String[]{});
            assertEquals(1, result);

            List<GroceryListManager> managers = mockedManager.constructed();
            assertEquals(1, managers.size());
            verify(managers.get(0), never()).saveGroceryList(anyList());
        }
    }

    @Test
    void testHandleListEmpty() throws IOException {
        File testFile = tempDir.resolve("test.json").toFile();

        CommandLine cmd = mock(CommandLine.class);
        when(cmd.getOptionValue("s")).thenReturn(testFile.getAbsolutePath());
        when(cmd.getOptionValue("f", "json")).thenReturn("json");
        when(cmd.getArgList()).thenReturn(List.of("list"));

        try (MockedStatic<CommandParser> mockedParser = mockStatic(CommandParser.class);
             MockedConstruction<GroceryListManager> mockedManager = mockConstruction(GroceryListManager.class,
                     (mock, context) -> {
                         when(mock.loadGroceryList()).thenReturn(new ArrayList<>());
                     })) {

            mockedParser.when(() -> CommandParser.parseArgs(any())).thenReturn(cmd);
            int result = commandExecutor.run(new String[]{});
            assertEquals(0, result);
        }
    }

    @Test
    void testHandleListWithItems() throws IOException {
        File testFile = tempDir.resolve("test.json").toFile();
        List<Item> items = List.of(new Item("apple", 3), new Item("banana", 2));

        CommandLine cmd = mock(CommandLine.class);
        when(cmd.getOptionValue("s")).thenReturn(testFile.getAbsolutePath());
        when(cmd.getOptionValue("f", "json")).thenReturn("json");
        when(cmd.getArgList()).thenReturn(List.of("list"));

        try (MockedStatic<CommandParser> mockedParser = mockStatic(CommandParser.class);
             MockedConstruction<GroceryListManager> mockedManager = mockConstruction(GroceryListManager.class,
                     (mock, context) -> {
                         when(mock.loadGroceryList()).thenReturn(items);
                     })) {

            mockedParser.when(() -> CommandParser.parseArgs(any())).thenReturn(cmd);
            int result = commandExecutor.run(new String[]{});
            assertEquals(0, result);
        }
    }
}