package com.example.food_app_test.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.food_app_test.data.entities.FoodEntity;

import java.util.List;

@Dao
public interface FoodDao {
    
    @Query("SELECT * FROM foods")
    LiveData<List<FoodEntity>> getAllFoods();
    
    @Query("SELECT * FROM foods")
    List<FoodEntity> getAllFoodsSync();  // Synchronous version for checking
    
    @Query("SELECT * FROM foods WHERE bestFood = 1")
    LiveData<List<FoodEntity>> getBestFoods();
    
    @Query("SELECT * FROM foods WHERE categoryId = :categoryId")
    LiveData<List<FoodEntity>> getFoodsByCategory(int categoryId);
    
    @Query("SELECT * FROM foods WHERE title LIKE '%' || :searchText || '%'")
    LiveData<List<FoodEntity>> searchFoods(String searchText);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFoods(List<FoodEntity> foods);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFood(FoodEntity food);
    
    @Query("DELETE FROM foods")
    void deleteAllFoods();
}