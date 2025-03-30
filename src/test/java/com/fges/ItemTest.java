package com.fges;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class ItemTest {

    @Test
    void testDefaultConstructor() {
        Item item = new Item();

        assertNull(item.getName());
        assertEquals(0, item.getQuantity());
    }

    @Test
    void testJsonCreatorConstructor() {
        String expectedName = "Apple";
        int expectedQuantity = 5;

        Item item = new Item(expectedName, expectedQuantity);

        assertEquals(expectedName, item.getName());
        assertEquals(expectedQuantity, item.getQuantity());
    }

    @Test
    void testSettersAndGetters() {
        Item item = new Item();

        String expectedName = "Banana";
        int expectedQuantity = 3;

        item.setName(expectedName);
        item.setQuantity(expectedQuantity);

        assertEquals(expectedName, item.getName());
        assertEquals(expectedQuantity, item.getQuantity());
    }

    @ParameterizedTest
    @CsvSource({
            "Apple, 5, 'Apple: 5'",
            "Banana, 3, 'Banana: 3'",
            "'', 0, ': 0'"
    })
    void testToString(String name, int quantity, String expectedOutput) {
        Item item = new Item(name, quantity);
        assertEquals(expectedOutput, item.toString());
    }

    @ParameterizedTest
    @CsvSource({
            "Apple, 5, 'Apple,5'",
            "Banana, 3, 'Banana,3'",
            "'', 0, ',0'"
    })
    void testToCSV(String name, int quantity, String expectedOutput) {
        Item item = new Item(name, quantity);
        assertEquals(expectedOutput, item.toCSV());
    }



    @Test
    void testEqualsWithNull() {
        Item item = new Item("Apple", 5);
        assertNotEquals(null, item);
    }

    @Test
    void testEqualsWithDifferentClass() {
        Item item = new Item("Apple", 5);
        assertNotEquals("Apple: 5", item);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 5, 100, -1})
    void testQuantitySetterWithVariousValues(int quantity) {
        Item item = new Item();
        item.setQuantity(quantity);
        assertEquals(quantity, item.getQuantity());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "Apple", "Banana", "A very long item name with spaces"})
    void testNameSetterWithVariousValues(String name) {
        Item item = new Item();
        item.setName(name);
        assertEquals(name, item.getName());
    }
}