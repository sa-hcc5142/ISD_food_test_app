package com.example.food_app_test.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

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

@Database(
        entities = {
                FoodEntity.class,
                CategoryEntity.class,
                LocationEntity.class,
                TimeEntity.class,
                PriceEntity.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FoodDao foodDao();
    public abstract CategoryDao categoryDao();
    public abstract LocationDao locationDao();
    public abstract TimeDao timeDao();
    public abstract PriceDao priceDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "food_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}