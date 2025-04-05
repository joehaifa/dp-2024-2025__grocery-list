package com.fges.donnees;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemTest {

    @Test
    public void test_constructor_should_set_fields_correctly() {
        Item item = new Item("banane", 3, "fruit");

        assertThat(item.getName()).isEqualTo("banane");
        assertThat(item.getQuantity()).isEqualTo(3);
        assertThat(item.getCategory()).isEqualTo("fruit");
    }

    @Test
    public void test_constructor_should_use_default_category_when_null() {
        Item item = new Item("banane", 3, null);

        assertThat(item.getCategory()).isEqualTo("default");
    }

    @Test
    public void test_toString_should_return_correct_format() {
        Item item = new Item("banane", 3, "fruit");

        assertThat(item.toString()).isEqualTo("banane: 3");
    }

    @Test
    public void test_toCSV_should_return_correct_format() {
        Item item = new Item("banane", 3, "fruit");

        assertThat(item.toCSV()).isEqualTo("banane,3");
    }
}
