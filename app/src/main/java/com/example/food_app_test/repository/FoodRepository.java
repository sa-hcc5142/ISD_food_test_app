package com.example.food_app_test.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.food_app_test.data.AppDatabase;
import com.example.food_app_test.data.dao.CategoryDao;
import com.example.food_app_test.data.dao.FoodDao;
import com.example.food_app_test.data.dao.LocationDao;
import com.example.food_app_test.data.dao.TimeDao;
import com.example.food_app_test.data.dao.PriceDao;
import com.example.food_app_test.data.entities.CategoryEntity;
import com.example.food_app_test.data.entities.FoodEntity;
import com.example.food_app_test.data.entities.LocationEntity;
import com.example.food_app_test.data.entities.TimeEntity;
import com.example.food_app_test.data.entities.PriceEntity;

import java.util.List;

public class FoodRepository {

    private FoodDao foodDao;
    private CategoryDao categoryDao;
    private LocationDao locationDao;
    private TimeDao timeDao;
    private PriceDao priceDao;

    private LiveData<List<FoodEntity>> allFoods;
    private LiveData<List<FoodEntity>> bestFoods;
    private LiveData<List<CategoryEntity>> allCategories;
    private LiveData<List<LocationEntity>> allLocations;
    private LiveData<List<TimeEntity>> allTimes;
    private LiveData<List<PriceEntity>> allPrices;

    public FoodRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);

        foodDao = database.foodDao();
        categoryDao = database.categoryDao();
        locationDao = database.locationDao();
        timeDao = database.timeDao();
        priceDao = database.priceDao();

        allFoods = foodDao.getAllFoods();
        bestFoods = foodDao.getBestFoods();
        allCategories = categoryDao.getAllCategories();
        allLocations = locationDao.getAllLocations();
        allTimes = timeDao.getAllTimes();
        allPrices = priceDao.getAllPrices();
    }

    // Getters for LiveData
    public LiveData<List<FoodEntity>> getAllFoods() {
        return allFoods;
    }

    public LiveData<List<FoodEntity>> getBestFoods() {
        return bestFoods;
    }

    public LiveData<List<CategoryEntity>> getAllCategories() {
        return allCategories;
    }

    public LiveData<List<LocationEntity>> getAllLocations() {
        return allLocations;
    }

    public LiveData<List<TimeEntity>> getAllTimes() {
        return allTimes;
    }

    public LiveData<List<PriceEntity>> getAllPrices() {
        return allPrices;
    }

    public LiveData<List<FoodEntity>> getFoodsByCategory(int categoryId) {
        return foodDao.getFoodsByCategory(categoryId);
    }

    public LiveData<List<FoodEntity>> searchFoods(String searchText) {
        return foodDao.searchFoods(searchText);
    }
}