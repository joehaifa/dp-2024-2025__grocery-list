package com.fges.grocerydata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// Represents an item in the grocery list with a name, quantity, and category.
public class GroceryItem {
    private String name;
    private int quantity;
    private String category;

    public GroceryItem() {
        this.category = "default";
    }

    @JsonCreator
    public GroceryItem(@JsonProperty("name") String name, @JsonProperty("quantity") int quantity, @JsonProperty("category") String category) {
        this.name = name;
        this.quantity = quantity;
        this.category = category != null ? category : "default";
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCategory(String category) {
        this.category=category;
    }

    public String getCategory() { return category; }


    @Override
    public String toString() {
        return name + ": " + quantity;
    }
    public String toCSV() {
        return name + "," + quantity;
    }
}
