package com.example.food_app_test.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_app_test.Adapter.BestFoodsAdapter;
import com.example.food_app_test.Adapter.CategoryAdapter;
import com.example.food_app_test.Domain.Category;
import com.example.food_app_test.Domain.Foods;
import com.example.food_app_test.Domain.Location;
import com.example.food_app_test.Domain.Price;
import com.example.food_app_test.Domain.Time;
import com.example.food_app_test.R;
import com.example.food_app_test.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    //private RecyclerView categoryView;
    //private RecyclerView bestFoodView;
    //RecyclerView  = findViewById(R.id.categoryView);

    private RecyclerView.Adapter<CategoryAdapter.viewholder> categoryAdapter;
    private ActivityMainBinding binding;
    private BestFoodsAdapter bestFoodsAdapter;
    //private CategoryAdapter categoryAdapter;

    private final ArrayList<Foods> bestFoodList = new ArrayList<>();
    private final ArrayList<Category> categoryList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setContentView(binding.getRoot());

        initRecyclerView();

        categoryAdapter = new CategoryAdapter(categoryList);
        binding.categoryView.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
        binding.categoryView.setHasFixedSize(true);
        binding.categoryView.setAdapter(categoryAdapter);


        setVariable();
        initBestFood();
        initCategory();

        initLocation();
        initTime();
        initPrice();


        Log.d("MainActivity", "onCreate: Activity initialized");
    }

    private void initRecyclerView() {

        Log.d("MainActivity", "Initializing RecyclerView");

        // Initialize adapters
        bestFoodsAdapter = new BestFoodsAdapter(bestFoodList);
        //categoryAdapter = new CategoryAdapter(categoryList);

        // Set layout managers and adapters
        binding.bestFoodView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        binding.bestFoodView.setAdapter(bestFoodsAdapter);
        Log.d("MainActivity", "initRecyclerView: BestFoodsAdapter attached");

        //binding.categoryView.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
        //binding.categoryView.setAdapter(categoryAdapter);
        Log.d("MainActivity", "initRecyclerView: CategoryAdapter attached");
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

        binding.cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initBestFood() {
        DatabaseReference myRef = database.getReference("Foods");
        binding.progressBarBestFood.setVisibility(View.VISIBLE);

        //ArrayList<Foods> list = new ArrayList<>();
        //ArrayList<Foods> bestFoodList = new ArrayList<>();

        Query query = myRef.orderByChild("BestFood").equalTo(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bestFoodList.clear();
                if(snapshot.exists()){
                    for(DataSnapshot issue : snapshot.getChildren()){
                        bestFoodList.add(issue.getValue(Foods.class));
                    }

                    bestFoodsAdapter.notifyDataSetChanged();

                    if(!bestFoodList.isEmpty()){
                        binding.bestFoodView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL,false));
                        RecyclerView.Adapter<BestFoodsAdapter.viewholder> adapter = new BestFoodsAdapter(bestFoodList);
                        binding.bestFoodView.setAdapter(adapter);
                        Log.d("MainActivity", "initBestFood: Best foods loaded and adapter notified");
                    }
                    binding.progressBarBestFood.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivity", "initBestFood: DatabaseError - " + error.getMessage());
                binding.progressBarBestFood.setVisibility(View.GONE);

            }
        });
    }

    private void initCategory() {
        DatabaseReference myRef = database.getReference("Category");
        binding.progressBarcategory.setVisibility(View.VISIBLE);

        //ArrayList<Category> list = new ArrayList<>();
        //ArrayList<Category> categoryList = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryList.clear();

                if(snapshot.exists()){
                    for(DataSnapshot issue : snapshot.getChildren()){
                        Category category = issue.getValue(Category.class);

                        Log.d("MainActivity", "Category List Size: " + categoryList.size());

                        if (category != null) {
                            categoryList.add(category);
                            Log.d("MainActivity", "Category ID: " + category.getCategoryId() + ", Name: " + category.getName());
                        }
                    }

                    categoryAdapter.notifyDataSetChanged();
                    binding.progressBarcategory.setVisibility(View.GONE);

                    Log.d("MainActivity", "initCategory: Categories loaded and adapter notified");

                    //if(!categoryList.isEmpty()){
                        //RecyclerView categoryView = findViewById(R.id.categoryView);
                        //RecyclerView.Adapter<CategoryAdapter.viewholder> adapter = new CategoryAdapter(categoryList);

                        //binding.categoryView.setLayoutManager(new GridLayoutManager(MainActivity.this,4));
                        //categoryView.setHasFixedSize(true);
                        //binding.categoryView.setAdapter(adapter);

                        //categoryView.setAdapter(adapter);

                        //Log.d("MainActivity", "initCategory: Categories loaded and adapter notified");
                    //}

                    //binding.progressBarcategory.setVisibility(View.GONE);
                }

                else {
                    Log.d("MainActivity", "No data found in Category node.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivity", "initCategory: DatabaseError - " + error.getMessage());
                binding.progressBarcategory.setVisibility(View.GONE);

            }
        });
    }

    private void initLocation() {

        DatabaseReference myRef = database.getReference("Location");
        ArrayList<Location> list = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue : snapshot.getChildren()){
                        list.add(issue.getValue(Location.class));
                    }

                    ArrayAdapter<Location> adapter = new ArrayAdapter<>(MainActivity.this,R.layout.sp_item,list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.locationSp.setAdapter(adapter);
                    Log.d("MainActivity", "initLocation: LocationAdapter set");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivity", "initLocation: DatabaseError - " + error.getMessage());
            }
        });
    }

    private void initTime() {

        DatabaseReference myRef = database.getReference("Time");
        ArrayList<Time> list = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue : snapshot.getChildren()){
                        list.add(issue.getValue(Time.class));
                    }

                    ArrayAdapter<Time> adapter = new ArrayAdapter<>(MainActivity.this,R.layout.sp_item,list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.timeSp.setAdapter(adapter);
                    Log.d("MainActivity", "initTime: TimeAdapter set");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivity", "initTime: DatabaseError - " + error.getMessage());

            }
        });
    }

    private void initPrice() {

        DatabaseReference myRef = database.getReference("Price");
        ArrayList<Price> list = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue : snapshot.getChildren()){
                        list.add(issue.getValue(Price.class));
                    }
                    ArrayAdapter<Price> adapter = new ArrayAdapter<>(MainActivity.this,R.layout.sp_item,list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.dollarSp.setAdapter(adapter);
                    Log.d("MainActivity", "initPrice: PriceAdapter set");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivity", "initPrice: DatabaseError - " + error.getMessage());


            }
        });
    }


}