package com.example.food_app_test.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "prices")
public class PriceEntity {
    @PrimaryKey
    private int id;
    private String value;

    public PriceEntity() {}

    public PriceEntity(int id, String value) {
        this.id = id;
        this.value = value;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}