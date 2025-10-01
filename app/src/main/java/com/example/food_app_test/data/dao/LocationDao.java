package com.example.food_app_test.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.food_app_test.data.entities.LocationEntity;

import java.util.List;

@Dao
public interface LocationDao {
    
    @Query("SELECT * FROM locations")
    LiveData<List<LocationEntity>> getAllLocations();
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLocations(List<LocationEntity> locations);
}