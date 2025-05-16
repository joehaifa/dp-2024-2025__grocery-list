package com.fges.grocerydata;

import com.fges.storage.GroceryListStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GroceryListManagerTest {

    private GroceryListStorage storage;
    private GroceryListManager manager;

    @BeforeEach
    void setUp() {
        // Arrange common mocks
        storage = mock(GroceryListStorage.class);
        when(storage.load()).thenReturn(new ArrayList<>());
        manager = new GroceryListManager(storage);
    }

    @Test
    void getItems_shouldReturnListProvidedByStorage() {
        // Arrange
        List<GroceryItem> stub = Arrays.asList(new GroceryItem("eau", 6, "boisson"));
        when(storage.load()).thenReturn(stub);

        // Act
        List<GroceryItem> out = manager.getItems();

        // Assert
        assertSame(stub, out, "Manager must directly return storage list");
        verify(storage).load();
    }

    @Test
    void addItem_shouldAppendNewItem_andPersistFullList() throws IOException {
        // Arrange
        GroceryItem bread    = new GroceryItem("pain", 1, "boulangerie");
        GroceryItem toInsert = new GroceryItem("oeufs", 12, "frais");
        when(storage.load()).thenReturn(new ArrayList<>(Arrays.asList(bread)));

        // Act
        manager.addItem(toInsert);

       // Assert
        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<GroceryItem>> captor =
                (ArgumentCaptor<List<GroceryItem>>) (ArgumentCaptor<?>) ArgumentCaptor.forClass(List.class);

        verify(storage).save(captor.capture());
        List<GroceryItem> saved = captor.getValue();

        assertEquals(2, saved.size(), "Original item + newly added");
        assertTrue(saved.contains(toInsert));

    }

    @Test
    void removeItem_shouldReturnTrue_andPersist_whenNameMatchesCaseInsensitive() throws IOException {
        // Arrange
        GroceryItem sugar = new GroceryItem("Sucre", 1, "épicerie");
        when(storage.load()).thenReturn(new ArrayList<>(Arrays.asList(sugar)));

        // Act
        boolean result = manager.removeItem("sucre");

        //Assert
        assertTrue(result);
        verify(storage).save(new ArrayList<>());
    }

    @Test
    void removeItem_shouldReturnFalse_andNotPersist_whenItemAbsent() throws IOException {
        // Act
        boolean result = manager.removeItem("chocolat");

        // Assert
        assertFalse(result);
        verify(storage, never()).save(any());
    }
}
