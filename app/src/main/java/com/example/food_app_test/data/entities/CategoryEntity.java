package com.example.food_app_test.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class CategoryEntity {
    @PrimaryKey
    private int id;
    private String imagePath;
    private String name;

    public CategoryEntity() {}

    public CategoryEntity(int id, String imagePath, String name) {
        this.id = id;
        this.imagePath = imagePath;
        this.name = name;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}