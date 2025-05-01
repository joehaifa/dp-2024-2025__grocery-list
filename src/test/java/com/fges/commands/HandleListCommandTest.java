package com.fges.commands;

import org.junit.jupiter.api.Test;

import com.fges.grocerydata.GroceryItem;
import com.fges.grocerydata.GroceryListDAO;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class HandleListCommandTest {

    @Test
    public void test_execute_should_return_0_and_call_getItems() {
        // Arrange
        GroceryListDAO dao = mock(GroceryListDAO.class);
        when(dao.getItems()).thenReturn(List.of(
                new GroceryItem("pomme", 2, "fruit"),
                new GroceryItem("carotte", 3, "legume")
        ));

        HandleListCommand command = new HandleListCommand(dao);
        List<String> args = List.of("list");

        // Act
        int result = command.execute(args);

        // Assert
        assertThat(result).isEqualTo(0);
        verify(dao).getItems();
    }
}
