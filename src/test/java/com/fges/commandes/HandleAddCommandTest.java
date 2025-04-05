package com.fges.commandes;

import com.fges.donnees.GroceryListDAO;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class HandleAddCommandTest {

    @Test
    public void test_execute_should_return_1_when_args_are_missing() throws IOException {
        // Arrange
        GroceryListDAO dao = mock(GroceryListDAO.class);
        HandleAddCommand command = new HandleAddCommand();

        List<String> args = List.of("add");

        // Act
        int result = command.execute(args, dao);

        // Assert
        assertThat(result).isEqualTo(1);
        verify(dao, never()).addItem(any());
    }

    @Test
    public void test_execute_should_call_addItem_with_correct_values() throws IOException {
        // Arrange
        GroceryListDAO dao = mock(GroceryListDAO.class);
        HandleAddCommand command = new HandleAddCommand();

        List<String> args = List.of("add", "pomme", "2", "fruit");

        // Act
        int result = command.execute(args, dao);

        // Assert
        assertThat(result).isEqualTo(0);
        verify(dao).addItem(argThat(item ->
                item.getName().equals("pomme") &&
                item.getQuantity() == 2 &&
                item.getCategory().equals("fruit")
        ));
    }

    @Test
    public void test_execute_should_use_default_category_if_not_provided() throws IOException {
        // Arrange
        GroceryListDAO dao = mock(GroceryListDAO.class);
        HandleAddCommand command = new HandleAddCommand();

        List<String> args = List.of("add", "pomme", "2");

        // Act
        int result = command.execute(args, dao);

        // Assert
        assertThat(result).isEqualTo(0);
        verify(dao).addItem(argThat(item ->
                item.getName().equals("pomme") &&
                item.getQuantity() == 2 &&
                item.getCategory().equals("default")
        ));
    }
}
