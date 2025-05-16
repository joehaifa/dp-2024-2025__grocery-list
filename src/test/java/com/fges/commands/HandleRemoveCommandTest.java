package com.fges.commands;

import com.fges.application.CommandContext;
import com.fges.grocerydata.GroceryListManager;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HandleRemoveCommandTest {

    @Test
    void execute_shouldReturn0_andPrintConfirmation_whenItemRemoved() throws IOException {
        // Arrange
        GroceryListManager dao = mock(GroceryListManager.class);
        when(dao.removeItem("lait")).thenReturn(true);
        CommandContext ctx = new CommandContext(
                Arrays.asList("remove", "lait"),
                null,
                "list.json"
        );
        HandleRemoveCommand cmd = new HandleRemoveCommand(dao);

        // Capture System.out
        PrintStream originalOut = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));

        try {
            // Act
            int status = cmd.execute(ctx);
            String output = buffer.toString();

            // Assert
            assertEquals(0, status);
            assertTrue(output.contains("Removed: lait"));
            verify(dao).removeItem("lait");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void execute_shouldReturn1_andPrintError_whenItemNotFound() throws IOException {
        // Arrange
        GroceryListManager dao = mock(GroceryListManager.class);
        when(dao.removeItem("farine")).thenReturn(false);
        CommandContext ctx = new CommandContext(
                Arrays.asList("remove", "farine"),
                null,
                "list.json"
        );
        HandleRemoveCommand cmd = new HandleRemoveCommand(dao);

        // Capture System.err
        PrintStream originalErr = System.err;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buffer));

        try {
            // Act
            int status = cmd.execute(ctx);
            String err = buffer.toString();

            // Assert
            assertEquals(1, status);
            assertTrue(err.contains("GroceryItem not found"));
            verify(dao).removeItem("farine");
        } finally {
            System.setErr(originalErr);
        }
    }

    @Test
    void execute_shouldReturn1_andNotCallDao_whenSourceOptionMissing() throws IOException {
        //Arrange
        GroceryListManager dao = mock(GroceryListManager.class);
        CommandContext ctx = new CommandContext(
                Arrays.asList("remove", "eau"),
                null,
                null
        );

        // Act
        int status = new HandleRemoveCommand(dao).execute(ctx);

        // Assert
        assertEquals(1, status);
        verifyNoInteractions(dao);
    }

    @Test
    void execute_shouldReturn1_andNotCallDao_whenItemNameMissing() throws IOException {
        //Arrange
        GroceryListManager dao = mock(GroceryListManager.class);
        CommandContext ctx = new CommandContext(
                Arrays.asList("remove"),
                null,
                "list.json"
        );

        // Act
        int status = new HandleRemoveCommand(dao).execute(ctx);

        // Assert
        assertEquals(1, status);
        verifyNoInteractions(dao);
    }
}
