package com.fges.donnees;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// Représente un article de la liste de courses avec son nom, sa quantité et sa catégorie.
public class Item {
    private String name;
    private int quantity;
    private String category;

    public Item() {
        this.category = "default";
    }
    // Constructeur avec annotation JSON pour que Jackson puisse l'utiliser
    @JsonCreator
    public Item(@JsonProperty("name") String name, @JsonProperty("quantity") int quantity,@JsonProperty("category") String category) {
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
