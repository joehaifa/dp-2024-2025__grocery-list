package com.fges.grocerydata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fges.storage.GroceryListStorage;

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
        List<GroceryItem> fakeGroceryItems = List.of(new GroceryItem("pomme", 3, "fruit"));
        when(storage.load()).thenReturn(fakeGroceryItems);

        // Act
        List<GroceryItem> result = dao.getItems();

        // Assert
        assertThat(result).containsExactlyElementsOf(fakeGroceryItems);
        verify(storage).load();
    }

    @Test
    public void test_addItem_should_add_and_save_item() throws IOException {
        // Arrange
        List<GroceryItem> groceryItems = new ArrayList<>();
        when(storage.load()).thenReturn(groceryItems);

        GroceryItem groceryItem = new GroceryItem("banane", 2, "fruit");

        // Act
        dao.addItem(groceryItem);

        // Assert
        assertThat(groceryItems).contains(groceryItem);
        verify(storage).save(groceryItems);
    }

    @Test
    public void test_removeItem_should_remove_and_save_when_item_exists() throws IOException {
        // Arrange
        List<GroceryItem> groceryItems = new ArrayList<>();
        GroceryItem groceryItem = new GroceryItem("carotte", 2, "légume");
        groceryItems.add(groceryItem);

        when(storage.load()).thenReturn(groceryItems);

        // Act
        boolean result = dao.removeItem("carotte");

        // Assert
        assertThat(result).isTrue();
        assertThat(groceryItems).doesNotContain(groceryItem);
        verify(storage).save(groceryItems);
    }

    @Test
    public void test_removeItem_should_return_false_when_item_not_found() throws IOException {
        // Arrange
        List<GroceryItem> groceryItems = new ArrayList<>();
        groceryItems.add(new GroceryItem("tomate", 1, "fruit"));
        when(storage.load()).thenReturn(groceryItems);

        // Act
        boolean result = dao.removeItem("banane");

        // Assert
        assertThat(result).isFalse();
        verify(storage, never()).save(any());
    }
}
