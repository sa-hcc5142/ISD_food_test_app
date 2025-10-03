package com.example.food_app_test.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_app_test.Adapter.FoodListAdapter;
import com.example.food_app_test.Domain.Foods;
import com.example.food_app_test.R;
import com.example.food_app_test.data.FoodRepository;
import com.example.food_app_test.data.entities.FoodEntity;
import com.example.food_app_test.databinding.ActivityListFoodBinding;

import java.util.ArrayList;
import java.util.List;

public class ListFoodActivity extends BaseActivity {

    ActivityListFoodBinding binding;
    private RecyclerView.Adapter adapterListFood;
    private FoodRepository foodRepository;
    private int categoryId;
    private String searchText,categoryName;
    private boolean isSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityListFoodBinding.inflate(getLayoutInflater());     //layout inflater

        EdgeToEdge.enable(this);

        setContentView(binding.getRoot());          //get root

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Room database repository
        foodRepository = new FoodRepository(getApplication());
        
        getIntentExtra();
        
        initList();
        
        setVariable();
    }

    private void setVariable() {
    }

    private void initList() {
        binding.progressBar.setVisibility(View.VISIBLE);

        ArrayList<Foods> list = new ArrayList<>();

        //Initialize empty recycler view
        adapterListFood = new FoodListAdapter(list);
        binding.foodListView.setLayoutManager(new GridLayoutManager(ListFoodActivity.this,2));
        binding.foodListView.setAdapter(adapterListFood);

        // Use Room database instead of Firebase
        if(isSearch){
            // Search by title in Room database
            foodRepository.searchFoodsByTitle(searchText).observe(this, new Observer<List<FoodEntity>>() {
                @Override
                public void onChanged(List<FoodEntity> foodEntities) {
                    Log.d("LISTFOOD_DEBUG", "Search results: " + (foodEntities != null ? foodEntities.size() : 0) + " items");
                    
                    list.clear();
                    if(foodEntities != null) {
                        for(FoodEntity entity : foodEntities) {
                            list.add(convertToFoods(entity));
                        }
                    }
                    
                    adapterListFood.notifyDataSetChanged();
                    binding.progressBar.setVisibility(View.GONE);
                    updateEmptyState(list.isEmpty());
                }
            });
        } else {
            // Filter by category in Room database
            foodRepository.getFoodsByCategory(categoryId).observe(this, new Observer<List<FoodEntity>>() {
                @Override
                public void onChanged(List<FoodEntity> foodEntities) {
                    Log.d("LISTFOOD_DEBUG", "Category " + categoryId + " results: " + (foodEntities != null ? foodEntities.size() : 0) + " items");
                    
                    list.clear();
                    if(foodEntities != null) {
                        for(FoodEntity entity : foodEntities) {
                            Foods food = convertToFoods(entity);
                            list.add(food);
                            Log.d("LISTFOOD_DEBUG", "Added food: " + food.getTitle() + ", Image: " + food.getImagePath());
                        }
                    }
                    
                    adapterListFood.notifyDataSetChanged();
                    binding.progressBar.setVisibility(View.GONE);
                    updateEmptyState(list.isEmpty());
                }
            });
        }
    }
    
    // Convert FoodEntity to Foods domain model
    private Foods convertToFoods(FoodEntity entity) {
        Foods food = new Foods();
        food.setCategoryId(entity.getCategoryId());
        food.setDescription(entity.getDescription());
        food.setBestFood(entity.isBestFood());
        food.setId(entity.getId());
        food.setLocation(entity.getLocationId());  // Use setLocation() not setLocationId()
        food.setPrice(entity.getPrice());
        food.setPriceId(entity.getPriceId());
        food.setStar(entity.getStar());
        food.setTimeId(entity.getTimeId());
        food.setTimeValue(entity.getTimeValue());
        food.setTitle(entity.getTitle());
        food.setImagePath(entity.getImagePath()); // This should match the asset filename
        return food;
    }

    private void updateEmptyState(boolean isEmpty) {
        if (isEmpty) {
            binding.foodListView.setVisibility(View.GONE);
        } else {
            binding.foodListView.setVisibility(View.VISIBLE);
        }
    }

    private void getIntentExtra() {
        categoryId = getIntent().getIntExtra("CategoryId",0);
        categoryName = getIntent().getStringExtra("CategoryName");
        searchText = getIntent().getStringExtra("text");
        isSearch = getIntent().getBooleanExtra("isSearch",false);

        binding.titleTxt.setText(categoryName);

        binding.backBtn.setOnClickListener(v -> finish());

        
    }
}