package com.example.food_app_test.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "foods")
public class FoodEntity {
    @PrimaryKey
    private int id;
    private boolean bestFood;
    private int categoryId;
    private String description;
    private String imagePath;
    private int locationId;
    private double price;
    private int priceId;
    private double star;
    private int timeId;
    private int timeValue;
    private String title;

    public FoodEntity() {}

    public FoodEntity(int id, boolean bestFood, int categoryId, String description, 
                      String imagePath, int locationId, double price, int priceId, 
                      double star, int timeId, int timeValue, String title) {
        this.id = id;
        this.bestFood = bestFood;
        this.categoryId = categoryId;
        this.description = description;
        this.imagePath = imagePath;
        this.locationId = locationId;
        this.price = price;
        this.priceId = priceId;
        this.star = star;
        this.timeId = timeId;
        this.timeValue = timeValue;
        this.title = title;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public boolean isBestFood() { return bestFood; }
    public void setBestFood(boolean bestFood) { this.bestFood = bestFood; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public int getLocationId() { return locationId; }
    public void setLocationId(int locationId) { this.locationId = locationId; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getPriceId() { return priceId; }
    public void setPriceId(int priceId) { this.priceId = priceId; }

    public double getStar() { return star; }
    public void setStar(double star) { this.star = star; }

    public int getTimeId() { return timeId; }
    public void setTimeId(int timeId) { this.timeId = timeId; }

    public int getTimeValue() { return timeValue; }
    public void setTimeValue(int timeValue) { this.timeValue = timeValue; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}