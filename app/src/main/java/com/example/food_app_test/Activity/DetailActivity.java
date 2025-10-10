package com.example.food_app_test.Activity;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.food_app_test.Domain.Foods;
import com.example.food_app_test.Helper.ManagmentCart;
import com.example.food_app_test.R;
import com.example.food_app_test.databinding.ActivityDetailBinding;

public class DetailActivity extends BaseActivity {

    ActivityDetailBinding binding;
    private Foods object;
    private int num = 1;
    private ManagmentCart managmentCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getIntentExtra();
        setVariable();



    }

    private void setVariable() {

        managmentCart = new ManagmentCart(this);

        binding.backBtn.setOnClickListener(v -> finish());

        // Load image from assets (no URL encoding needed with underscores)
        String imagePath = object.getImagePath();
        String assetPath = "file:///android_asset/" + imagePath;
        Log.d("DETAIL_DEBUG", "Food title: " + object.getTitle());
        Log.d("DETAIL_DEBUG", "Raw imagePath from DB: '" + imagePath + "'");
        Log.d("DETAIL_DEBUG", "Final asset path: " + assetPath);
        
        Glide.with(this)
                .load(assetPath)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.pic);


        binding.priceTxt.setText("৳"+ object.getPrice());
        binding.titleTxt.setText(object.getTitle());
        binding.description.setText(object.getDescription());
        binding.ratingTxt.setText(object.getStar() + " Rating");
        binding.ratingBar.setRating((float) object.getStar());
        binding.totalTxt.setText(num + object.getPrice() + "৳");

        binding.plusBtn.setOnClickListener(v -> {
            num ++;
            binding.numTxt.setText(num+" ");
            binding.totalTxt.setText("৳"+(num *object.getPrice()));
        });

        binding.minusBtn.setOnClickListener(v -> {
            num--;
            binding.numTxt.setText(num+" ");
            binding.totalTxt.setText("৳"+ (num *object.getPrice()));
        });

        binding.addBtn.setOnClickListener(v -> {
            object.setNumberInCart(num);

            // ✅ Normalize imagePath before saving
            String imgPath = object.getImagePath();
            if (imgPath != null && !imgPath.contains("images/foods/")) {
                // add prefix + extension if missing
                object.setImagePath("images/foods/" + imgPath.replace(" ", "_") + ".jpg");
            }

            Log.d("CART_DEBUG", "Saving to cart -> Title: " + object.getTitle()
                    + ", imagePath: " + object.getImagePath());

            managmentCart.insertFood(object);
        });

    }

    private void getIntentExtra() {
        object = (Foods) getIntent().getSerializableExtra("object");
    }
}