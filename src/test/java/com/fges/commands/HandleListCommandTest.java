package com.fges.commands;

import com.fges.application.CommandContext;
import com.fges.grocerydata.GroceryItem;
import com.fges.grocerydata.GroceryListManager;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HandleListCommandTest {

    @Test
    void execute_shouldPrintItemsGroupedByCategory_andReturn0() {
        // Arrange
        GroceryListManager dao = mock(GroceryListManager.class);
        when(dao.getItems()).thenReturn(Arrays.asList(
                new GroceryItem("banane", 6, "fruit"),
                new GroceryItem("tomate", 4, "légume")
        ));
        CommandContext ctx = new CommandContext(
                Arrays.asList("list"),
                null,
                "list.json"
        );
        HandleListCommand cmd = new HandleListCommand(dao);

        // Capture System.out
        PrintStream originalOut = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));

        try {
            // Act
            int status = cmd.execute(ctx);
            String output = buffer.toString();

            // Assert
            assertEquals(0, status, "Return code should be 0");
            assertTrue(output.contains("#fruit:"),   "Fruit header must be printed");
            assertTrue(output.contains("#légume:"),  "Légume header must be printed");
            assertTrue(output.contains("banane: 6"), "Item lines must be printed");
            assertTrue(output.contains("tomate: 4"));
            verify(dao).getItems();
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void execute_shouldPrintNothingButHeader_whenListIsEmpty() {
        // Arrange
        GroceryListManager dao = mock(GroceryListManager.class);
        when(dao.getItems()).thenReturn(Collections.emptyList());
        CommandContext ctx = new CommandContext(
                Arrays.asList("list"),
                null,
                "list.json"
        );
        HandleListCommand cmd = new HandleListCommand(dao);

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
            assertTrue(output.trim().isEmpty(), "No items -> no output expected");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void execute_shouldReturn1_andNotCallDao_whenSourceOptionMissing() {
        // Arrange
        GroceryListManager dao = mock(GroceryListManager.class);
        CommandContext ctx = new CommandContext(
                Arrays.asList("list"),
                null,
                null
        );

        //Act
        int status = new HandleListCommand(dao).execute(ctx);

        //Assert
        assertEquals(1, status, "Missing -s should produce error code 1");
        verifyNoInteractions(dao);
    }
}
