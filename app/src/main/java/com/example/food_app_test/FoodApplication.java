package com.example.food_app_test;

import android.app.Application;
import android.util.Log;

import com.example.food_app_test.data.DatabasePopulator;

public class FoodApplication extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        Log.d("DATABASE_DEBUG", "=== FOOD APPLICATION STARTING ===");
        
        // Initialize and populate database on first launch
        DatabasePopulator populator = new DatabasePopulator(this);
        populator.populateDatabase();
        
        Log.d("DATABASE_DEBUG", "=== DATABASE POPULATION TRIGGERED ===");
    }
}