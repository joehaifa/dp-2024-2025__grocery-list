package com.fges.grocerydata;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GroceryItemTest {

    @Test
    public void test_constructor_should_set_fields_correctly() {
        GroceryItem groceryItem = new GroceryItem("banane", 3, "fruit");

        assertThat(groceryItem.getName()).isEqualTo("banane");
        assertThat(groceryItem.getQuantity()).isEqualTo(3);
        assertThat(groceryItem.getCategory()).isEqualTo("fruit");
    }

    @Test
    public void test_constructor_should_use_default_category_when_null() {
        GroceryItem groceryItem = new GroceryItem("banane", 3, null);

        assertThat(groceryItem.getCategory()).isEqualTo("default");
    }

    @Test
    public void test_toString_should_return_correct_format() {
        GroceryItem groceryItem = new GroceryItem("banane", 3, "fruit");

        assertThat(groceryItem.toString()).isEqualTo("banane: 3");
    }

    @Test
    public void test_toCSV_should_return_correct_format() {
        GroceryItem groceryItem = new GroceryItem("banane", 3, "fruit");

        assertThat(groceryItem.toCSV()).isEqualTo("banane,3");
    }
}
