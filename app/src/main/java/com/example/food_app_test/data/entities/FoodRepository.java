package com.example.food_app_test.data.entities;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.food_app_test.data.dao.CategoryDao;
import com.example.food_app_test.data.dao.FoodDao;
import com.example.food_app_test.data.entities.CategoryEntity;
import com.example.food_app_test.data.entities.FoodEntity;

import java.util.List;

public class FoodRepository {
    private FoodDao foodDao;
    private CategoryDao categoryDao;
    private LiveData<List<FoodEntity>> allFoods;
    private LiveData<List<CategoryEntity>> allCategories;

    public FoodRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        foodDao = database.foodDao();
        categoryDao = database.categoryDao();
        allFoods = foodDao.getAllFoods();
        allCategories = categoryDao.getAllCategories();
    }

    // Get all foods
    public LiveData<List<FoodEntity>> getAllFoods() {
        return allFoods;
    }

    // Get best foods
    public LiveData<List<FoodEntity>> getBestFoods() {
        return foodDao.getBestFoods();
    }

    // Get foods by category
    public LiveData<List<FoodEntity>> getFoodsByCategory(int categoryId) {
        return foodDao.getFoodsByCategory(categoryId);
    }

    // Search foods by title
    public LiveData<List<FoodEntity>> searchFoodsByTitle(String title) {
        return foodDao.searchFoods(title);
    }

    // Get all categories
    public LiveData<List<CategoryEntity>> getAllCategories() {
        return allCategories;
    }
}