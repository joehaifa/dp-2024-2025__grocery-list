package com.fges.donnees;

import com.fges.stockage.GroceryListStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class GroceryListDAOTest {

    GroceryListStorage storage;
    GroceryListDAO dao;

    @BeforeEach
    void setup() {
        storage = mock(GroceryListStorage.class);
        dao = new GroceryListDAO(storage);
    }

    @Test
    public void test_getItems_should_return_items_from_storage() {
        // Arrange
        List<Item> fakeItems = List.of(new Item("pomme", 3, "fruit"));
        when(storage.load()).thenReturn(fakeItems);

        // Act
        List<Item> result = dao.getItems();

        // Assert
        assertThat(result).containsExactlyElementsOf(fakeItems);
        verify(storage).load();
    }

    @Test
    public void test_addItem_should_add_and_save_item() throws IOException {
        // Arrange
        List<Item> items = new ArrayList<>();
        when(storage.load()).thenReturn(items);

        Item item = new Item("banane", 2, "fruit");

        // Act
        dao.addItem(item);

        // Assert
        assertThat(items).contains(item);
        verify(storage).save(items);
    }

    @Test
    public void test_removeItem_should_remove_and_save_when_item_exists() throws IOException {
        // Arrange
        List<Item> items = new ArrayList<>();
        Item item = new Item("carotte", 2, "légume");
        items.add(item);

        when(storage.load()).thenReturn(items);

        // Act
        boolean result = dao.removeItem("carotte");

        // Assert
        assertThat(result).isTrue();
        assertThat(items).doesNotContain(item);
        verify(storage).save(items);
    }

    @Test
    public void test_removeItem_should_return_false_when_item_not_found() throws IOException {
        // Arrange
        List<Item> items = new ArrayList<>();
        items.add(new Item("tomate", 1, "fruit"));
        when(storage.load()).thenReturn(items);

        // Act
        boolean result = dao.removeItem("banane");

        // Assert
        assertThat(result).isFalse();
        verify(storage, never()).save(any());
    }
}
