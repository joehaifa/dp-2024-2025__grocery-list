package com.fges.commands;

import org.junit.jupiter.api.Test;

import com.fges.grocerydata.GroceryListManager;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class HandleRemoveCommandTest {

    @Test
    public void test_execute_should_return_1_when_arguments_missing() throws IOException {
        // Arrange
        GroceryListManager dao = mock(GroceryListManager.class);
        HandleRemoveCommand command = new HandleRemoveCommand(dao);
        List<String> args = List.of("remove");

        // Act
        int result = command.execute(args);

        // Assert
        assertThat(result).isEqualTo(1);
        verifyNoInteractions(dao);
    }

    @Test
    public void test_execute_should_return_0_when_item_removed() throws IOException {
        // Arrange
        GroceryListManager dao = mock(GroceryListManager.class);
        when(dao.removeItem("banane")).thenReturn(true);

        HandleRemoveCommand command = new HandleRemoveCommand(dao);
        List<String> args = List.of("remove", "banane");

        // Act
        int result = command.execute(args);

        // Assert
        assertThat(result).isEqualTo(0);
        verify(dao).removeItem("banane");
    }

    @Test
    public void test_execute_should_return_1_when_item_not_found() throws IOException {
        // Arrange
        GroceryListManager dao = mock(GroceryListManager.class);
        when(dao.removeItem("banane")).thenReturn(false);

        HandleRemoveCommand command = new HandleRemoveCommand(dao);
        List<String> args = List.of("remove", "banane");

        // Act
        int result = command.execute(args);

        // Assert
        assertThat(result).isEqualTo(1);
        verify(dao).removeItem("banane");
    }
}
