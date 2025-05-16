package com.fges.grocerydata;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroceryItemTest {

    @Test
    void defaultConstructor_shouldInitialiseCategoryToDefault() {
        // Arrange
        GroceryItem item = new GroceryItem();

        // Assert
        assertEquals("default", item.getCategory());
        assertNull(item.getName(),      "Name should be null initially");
        assertEquals(0,  item.getQuantity(), "Quantity should be zero initially");
    }

    @Test
    void allArgsConstructor_shouldStoreFields_andFallBackToDefaultCategory() {
        // Arrange
        GroceryItem item = new GroceryItem("pomme", 3, null);

        // Assert
        assertEquals("pomme",   item.getName());
        assertEquals(3,         item.getQuantity());
        assertEquals("default", item.getCategory(),
                     "Null category must fall back to 'default'");
    }

    @Test
    void setters_shouldMutateStateCorrectly() {
        // Arrange
        GroceryItem item = new GroceryItem();

        // Act
        item.setName("lait");
        item.setQuantity(2);
        item.setCategory("boisson");

        // Assert
        assertEquals("lait",    item.getName());
        assertEquals(2,         item.getQuantity());
        assertEquals("boisson", item.getCategory());
    }

    @Test
    void toString_shouldReturnNameColonQuantity() {
        // Arrange
        GroceryItem item = new GroceryItem("lait", 1, "boisson");

        // Act and Assert
        assertEquals("lait: 1", item.toString());
    }

    @Test
    void toCSV_shouldReturnNameCommaQuantity() {
        // Arrange
        GroceryItem item = new GroceryItem("riz", 2, "céréales");

        // # Act and Assert
        assertEquals("riz,2", item.toCSV());
    }
}
