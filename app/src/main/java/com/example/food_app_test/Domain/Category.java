package com.example.food_app_test.Domain;

import com.google.firebase.database.PropertyName;

public class Category {
    @PropertyName("Id")
    private int CategoryId;
    private String ImagePath, Name;

    public Category() {
    }

    @PropertyName("Id")
    public int getCategoryId() {
        return CategoryId;
    }

    @PropertyName("Id")
    public void setCategoryId(int categoryId) {
        this.CategoryId = categoryId;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
