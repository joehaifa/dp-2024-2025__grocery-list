package com.fges;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
    private String name;
    private int quantity;

    // Constructeur par défaut
    public Item() {}

    // Constructeur avec annotation JSON pour que Jackson puisse l'utiliser
    @JsonCreator
    public Item(@JsonProperty("name") String name, @JsonProperty("quantity") int quantity) {
        this.name = name;
        this.quantity = quantity;
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

    @Override
    public String toString() {
        return name + ": " + quantity;
    }
    public String toCSV() {
        return name + "," + quantity;
    }
}
