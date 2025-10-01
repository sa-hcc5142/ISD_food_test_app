package com.example.food_app_test.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.food_app_test.data.entities.CategoryEntity;

import java.util.List;

@Dao
public interface CategoryDao {
    
    @Query("SELECT * FROM categories")
    LiveData<List<CategoryEntity>> getAllCategories();
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategories(List<CategoryEntity> categories);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategory(CategoryEntity category);
    
    @Query("DELETE FROM categories")
    void deleteAllCategories();
}