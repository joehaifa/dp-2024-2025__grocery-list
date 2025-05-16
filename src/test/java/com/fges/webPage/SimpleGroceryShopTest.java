package com.fges.webPage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleGroceryShopTest {

    @Test
    void addAndRemoveItemsInMemory() {
        // Arrange
        SimpleGroceryShop shop = new SimpleGroceryShop();

        // Act 1-add
        shop.addGroceryItem("jus", 1, "boisson");

        // Assert 1
        assertEquals(1, shop.getGroceries().size());

        //  Act 2-remove
        shop.removeGroceryItem("jus");

        // Assert 2
        assertTrue(shop.getGroceries().isEmpty());
    }
}
