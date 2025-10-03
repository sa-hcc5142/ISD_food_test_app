package com.example.food_app_test.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.food_app_test.Adapter.BestFoodsAdapter;
import com.example.food_app_test.Adapter.CategoryAdapter;
import com.example.food_app_test.Domain.Category;
import com.example.food_app_test.Domain.Foods;
import com.example.food_app_test.Domain.Location;
import com.example.food_app_test.Domain.Price;
import com.example.food_app_test.Domain.Time;
import com.example.food_app_test.R;
import com.example.food_app_test.data.entities.CategoryEntity;
import com.example.food_app_test.data.entities.FoodEntity;
import com.example.food_app_test.data.entities.LocationEntity;
import com.example.food_app_test.data.entities.PriceEntity;
import com.example.food_app_test.data.entities.TimeEntity;
import com.example.food_app_test.databinding.ActivityMainBinding;
import com.example.food_app_test.repository.FoodRepository;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private BestFoodsAdapter bestFoodsAdapter;
    private CategoryAdapter categoryAdapter;
    private FoodRepository repository;

    private final ArrayList<Foods> bestFoodList = new ArrayList<>();
    private final ArrayList<Category> categoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize repository
        repository = new FoodRepository(getApplication());

        Log.e("MAIN_DEBUG", "=== MAIN ACTIVITY CREATED ===");
        Log.e("MAIN_DEBUG", "Repository initialized: " + (repository != null));
        
        initRecyclerView();
        setVariable();
        
        // Load data from local database
        Log.e("MAIN_DEBUG", "=== STARTING DATA LOADING ===");
        initBestFood();
        initCategory();
        initLocation();
        initTime();
        initPrice();

        Log.d("MainActivity", "onCreate: Activity initialized with local database");
    }

    private void initRecyclerView() {
        Log.d("MainActivity", "Initializing RecyclerView");

        // Initialize adapters
        bestFoodsAdapter = new BestFoodsAdapter(bestFoodList);
        categoryAdapter = new CategoryAdapter(categoryList);

        // Set layout managers and adapters
        binding.bestFoodView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        binding.bestFoodView.setAdapter(bestFoodsAdapter);
        
        binding.categoryView.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
        binding.categoryView.setHasFixedSize(true);
        binding.categoryView.setAdapter(categoryAdapter);
        
        Log.d("MainActivity", "initRecyclerView: Adapters attached");
    }

    private void setVariable() {
        binding.logOutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        });

        binding.searchBtn.setOnClickListener(v -> {
            String text = binding.searchEdt.getText().toString();

            if(!text.isEmpty()){
                Intent intent = new Intent(MainActivity.this, ListFoodActivity.class);
                intent.putExtra("text",text);
                intent.putExtra("isSearch",true);
                startActivity(intent);

            }
        });

        //binding.cartBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CartActivity.class)));

        binding.cartBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }

    private void initBestFood() {
        binding.progressBarBestFood.setVisibility(View.VISIBLE);
        Log.e("MAIN_DEBUG", "=== INIT BEST FOOD CALLED ===");

        // Observe best foods from local database
        repository.getBestFoods().observe(this, new Observer<List<FoodEntity>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<FoodEntity> foodEntities) {
                Log.e("MAIN_DEBUG", "=== DATABASE RESPONSE RECEIVED ===");
                Log.e("MAIN_DEBUG", "Food entities: " + (foodEntities != null ? foodEntities.size() : "NULL"));
                
                bestFoodList.clear();
                
                if (foodEntities != null && !foodEntities.isEmpty()) {
                    Log.d("DATABASE_DEBUG", "=== LOADING FROM LOCAL DATABASE ===");
                    Log.d("DATABASE_DEBUG", "Found " + foodEntities.size() + " food entities");
                    
                    // Convert FoodEntity to Foods (your existing domain model)
                    for (FoodEntity entity : foodEntities) {
                        Foods food = convertToFoods(entity);
                        Log.d("DATABASE_DEBUG", "Food: " + food.getTitle() + " | Image: '" + food.getImagePath() + "'");
                        bestFoodList.add(food);
                    }
                    
                    bestFoodsAdapter.notifyDataSetChanged();
                    Log.d("DATABASE_DEBUG", "=== DATABASE LOADING COMPLETE ===");
                    Log.d("MainActivity", "initBestFood: " + bestFoodList.size() + " best foods loaded");
                } else {
                    Log.e("DATABASE_DEBUG", "=== NO DATA IN LOCAL DATABASE ===");
                    Log.e("DATABASE_DEBUG", "Database might not be populated yet!");
                }
                
                binding.progressBarBestFood.setVisibility(View.GONE);
            }
        });
    }

    private void initCategory() {
        binding.progressBarcategory.setVisibility(View.VISIBLE);

        // Observe categories from local database
        repository.getAllCategories().observe(this, new Observer<List<CategoryEntity>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<CategoryEntity> categoryEntities) {
                categoryList.clear();
                
                if (categoryEntities != null && !categoryEntities.isEmpty()) {
                    // Convert CategoryEntity to Category (your existing domain model)
                    for (CategoryEntity entity : categoryEntities) {
                        Category category = convertToCategory(entity);
                        categoryList.add(category);
                    }
                    
                    categoryAdapter.notifyDataSetChanged();
                    Log.d("MainActivity", "initCategory: " + categoryList.size() + " categories loaded");
                }
                
                binding.progressBarcategory.setVisibility(View.GONE);
            }
        });
    }

    private void initLocation() {
        // Observe locations from local database
        repository.getAllLocations().observe(this, new Observer<List<LocationEntity>>() {
            @Override
            public void onChanged(List<LocationEntity> locationEntities) {
                if (locationEntities != null && !locationEntities.isEmpty()) {
                    ArrayList<Location> locationList = new ArrayList<>();
                    
                    for (LocationEntity entity : locationEntities) {
                        Location location = new Location();
                        location.setId(entity.getId());
                        location.setLoc(entity.getLocation());
                        locationList.add(location);
                    }
                    
                    ArrayAdapter<Location> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.sp_item, locationList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.locationSp.setAdapter(adapter);
                    Log.d("MainActivity", "initLocation: LocationAdapter set");
                }
            }
        });
    }

    private void initTime() {
        // Observe times from local database
        repository.getAllTimes().observe(this, new Observer<List<TimeEntity>>() {
            @Override
            public void onChanged(List<TimeEntity> timeEntities) {
                if (timeEntities != null && !timeEntities.isEmpty()) {
                    ArrayList<Time> timeList = new ArrayList<>();
                    
                    for (TimeEntity entity : timeEntities) {
                        Time time = new Time();
                        time.setId(entity.getId());
                        time.setValue(entity.getValue());
                        timeList.add(time);
                    }
                    
                    ArrayAdapter<Time> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.sp_item, timeList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.timeSp.setAdapter(adapter);
                    Log.d("MainActivity", "initTime: TimeAdapter set");
                }
            }
        });
    }

    private void initPrice() {
        // Observe prices from local database
        repository.getAllPrices().observe(this, new Observer<List<PriceEntity>>() {
            @Override
            public void onChanged(List<PriceEntity> priceEntities) {
                if (priceEntities != null && !priceEntities.isEmpty()) {
                    ArrayList<Price> priceList = new ArrayList<>();
                    
                    for (PriceEntity entity : priceEntities) {
                        Price price = new Price();
                        price.setId(entity.getId());
                        price.setValue(entity.getValue());
                        priceList.add(price);
                    }
                    
                    ArrayAdapter<Price> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.sp_item, priceList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.dollarSp.setAdapter(adapter);
                    Log.d("MainActivity", "initPrice: PriceAdapter set");
                }
            }
        });
    }

    // Helper methods to convert entities to domain models
    private Foods convertToFoods(FoodEntity entity) {
        Foods food = new Foods();
        food.setId(entity.getId());
        food.setBestFood(entity.isBestFood());
        food.setCategoryId(entity.getCategoryId());
        food.setDescription(entity.getDescription());
        food.setImagePath(entity.getImagePath());
        food.setLocation(entity.getLocationId());
        food.setPrice(entity.getPrice());
        food.setPriceId(entity.getPriceId());
        food.setStar(entity.getStar());
        food.setTimeId(entity.getTimeId());
        food.setTimeValue(entity.getTimeValue());
        food.setTitle(entity.getTitle());
        return food;
    }

    private Category convertToCategory(CategoryEntity entity) {
        Category category = new Category();
        category.setCategoryId(entity.getId());
        category.setImagePath(entity.getImagePath());
        category.setName(entity.getName());
        return category;
    }
}