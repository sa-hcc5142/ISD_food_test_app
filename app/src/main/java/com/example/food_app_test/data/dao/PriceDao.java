package com.example.food_app_test.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.food_app_test.data.entities.PriceEntity;

import java.util.List;

@Dao
public interface PriceDao {
    
    @Query("SELECT * FROM prices")
    LiveData<List<PriceEntity>> getAllPrices();
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPrices(List<PriceEntity> prices);
}