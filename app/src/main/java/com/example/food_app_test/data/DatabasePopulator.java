package com.example.food_app_test.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.food_app_test.data.entities.TimeEntity;

import java.util.List;

@Dao
public interface TimeDao {

    @Query("SELECT * FROM times")
    LiveData<List<TimeEntity>> getAllTimes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTimes(List<TimeEntity> times);
}