package com.fges.commandes;

import com.fges.donnees.GroceryListDAO;
import com.fges.donnees.Item;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class HandleListCommandTest {

    @Test
    public void test_execute_should_return_0_and_call_getItems() {
        // Arrange
        GroceryListDAO dao = mock(GroceryListDAO.class);
        when(dao.getItems()).thenReturn(List.of(
                new Item("pomme", 2, "fruit"),
                new Item("carotte", 3, "legume")
        ));

        HandleListCommand command = new HandleListCommand();
        List<String> args = List.of("list");

        // Act
        int result = command.execute(args, dao);

        // Assert
        assertThat(result).isEqualTo(0);
        verify(dao).getItems();
    }
}
