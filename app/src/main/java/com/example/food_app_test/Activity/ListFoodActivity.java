package com.example.food_app_test.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_app_test.Adapter.FoodListAdapter;
import com.example.food_app_test.Domain.Foods;
import com.example.food_app_test.R;
import com.example.food_app_test.databinding.ActivityListFoodBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListFoodActivity extends BaseActivity {

    ActivityListFoodBinding binding;
    private RecyclerView.Adapter adapterListFood;
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

        getIntentExtra();
        
        initList();
        
        setVariable();
    }

    private void setVariable() {
    }

    private void initList() {
        DatabaseReference myRef = database.getReference("Foods");
        binding.progressBar.setVisibility(View.VISIBLE);

        ArrayList<Foods> list = new ArrayList<>();

        //CNG : Initialize empty recycler view
        adapterListFood = new FoodListAdapter(list);
        binding.foodListView.setLayoutManager(new GridLayoutManager(ListFoodActivity.this,2));

        binding.foodListView.setAdapter(adapterListFood);

        Query query;
        if(isSearch){
            query = myRef.orderByChild("Title").startAt(searchText).endAt(searchText + '\uf8ff');
        }
        else {
            query = myRef.orderByChild("CategoryId").equalTo(categoryId);
        }

        query.addListenerForSingleValueEvent((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    //CNG
                    list.clear();

                    for(DataSnapshot issue : snapshot.getChildren()){
                        list.add(issue.getValue(Foods.class));
                    }

                    //CNG
                    adapterListFood.notifyDataSetChanged();

                    if(!list.isEmpty()) {

                        // CNG : Update data in adapter

//                        binding.foodListView.setLayoutManager(new GridLayoutManager(ListFoodActivity.this, 2));
//                        adapterListFood = new FoodListAdapter(list);
//                        binding.foodListView.setAdapter(adapterListFood);

                        ((FoodListAdapter) adapterListFood).updateData(list);

                    }
                    binding.progressBar.setVisibility(View.GONE);
                }

                else {
                    Log.d("initList", "No data found for categoryId: " + categoryId);
                    binding.progressBar.setVisibility(View.GONE);
                }

                updateEmptyState(list.isEmpty());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("initList", "Database error: " + error.getMessage());
                binding.progressBar.setVisibility(View.GONE);

            }
        }));
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