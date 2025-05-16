package com.fges.commands;

import com.fges.application.CommandContext;
import com.fges.grocerydata.GroceryItem;
import com.fges.grocerydata.GroceryListManager;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HandleAddCommandTest {

    @Test
    void execute_shouldAddItemAndReturn0_whenAllArgsProvided() throws IOException {
        // Arrange
        GroceryListManager dao = mock(GroceryListManager.class);
        CommandContext ctx = new CommandContext(
                Arrays.asList("add", "pâtes", "4"),
                "épicerie",
                "list.json"
        );

        // Act
        int status = new HandleAddCommand(dao).execute(ctx);

        // Assert
        assertEquals(0, status);
        ArgumentCaptor<GroceryItem> captor = ArgumentCaptor.forClass(GroceryItem.class);
        verify(dao).addItem(captor.capture());
        GroceryItem added = captor.getValue();
        assertEquals("pâtes",    added.getName());
        assertEquals(4,          added.getQuantity());
        assertEquals("épicerie", added.getCategory());
    }

    @Test
    void execute_shouldUseDefaultCategory_whenCategoryIsNull() throws IOException {
        //Arrange
        GroceryListManager dao = mock(GroceryListManager.class);
        CommandContext ctx = new CommandContext(
                Arrays.asList("add", "riz", "1"),
                null,
                "list.json"
        );

        // Act
        int status = new HandleAddCommand(dao).execute(ctx);

        // Assert
        assertEquals(0, status);
        ArgumentCaptor<GroceryItem> captor = ArgumentCaptor.forClass(GroceryItem.class);
        verify(dao).addItem(captor.capture());
        assertEquals("default", captor.getValue().getCategory());
    }

    @Test
    void execute_shouldReturn1_andNotCallDao_whenSourceOptionMissing() throws IOException {
        // Arrange
        GroceryListManager dao = mock(GroceryListManager.class);
        CommandContext ctx = new CommandContext(
                Arrays.asList("add", "pomme", "2"),
                "fruit",
                null
        );

        // Act
        int status = new HandleAddCommand(dao).execute(ctx);

        // Assert
        assertEquals(1, status);
        verifyNoInteractions(dao);
    }

    @Test
    void execute_shouldReturn1_andNotCallDao_whenNotEnoughArguments() throws IOException {
        // Arrange
        GroceryListManager dao = mock(GroceryListManager.class);
        CommandContext ctx = new CommandContext(
                Arrays.asList("add", "lait"),
                "frais",
                "list.json"
        );

        // Act
        int status = new HandleAddCommand(dao).execute(ctx);

        // Assert
        assertEquals(1, status);
        verifyNoInteractions(dao);
    }

    @Test
    void execute_shouldThrowNumberFormatException_whenQuantityNotNumeric() {
        // Arrange
        GroceryListManager dao = mock(GroceryListManager.class);
        CommandContext ctx = new CommandContext(
                Arrays.asList("add", "jus", "abc"),
                null,
                "list.json"
        );

        //Act and Assert
        assertThrows(NumberFormatException.class,
                () -> new HandleAddCommand(dao).execute(ctx));
        verifyNoInteractions(dao);
    }
}
