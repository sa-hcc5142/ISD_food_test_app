package com.example.food_app_test.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_app_test.Adapter.CartAdapter;
import com.example.food_app_test.Helper.ChangeNumberItemsListener;
import com.example.food_app_test.Helper.ManagmentCart;
import com.example.food_app_test.R;
import com.example.food_app_test.databinding.ActivityCartBinding;

public class CartActivity extends BaseActivity {

    private ActivityCartBinding binding;
    private RecyclerView.Adapter adapter;
    private ManagmentCart managmentCart;
    private double tax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCartBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        managmentCart = new ManagmentCart(this);

        initList();
        setVariable();
        calculateCart();
    }

    private void initList() {
        if(managmentCart.getListCart().isEmpty()){
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scrollViewCart.setVisibility(View.GONE);
        }
        else {
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scrollViewCart.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.cartView.setLayoutManager(linearLayoutManager);
        adapter = new CartAdapter(managmentCart.getListCart(), this, () -> calculateCart());
        binding.cartView.setAdapter(adapter);

        binding.placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CartActivity.this, "Order Placed, Thank you for chosing our app. \nPlease wait 30 minutes.", Toast.LENGTH_SHORT).show();
            }
        });

        String coupon = binding.couponCode.toString();

        binding.couponBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(coupon.isEmpty()){
                    Toast.makeText(CartActivity.this, "No code entered yet", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(CartActivity.this, "This food app is not real \nSo the coupon code won't work ...!!!\n", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void calculateCart() {
        double percentTaxes = 0.02; //percent 2% tax
        double delivery = 10; //10 dollar

        tax = Math.round(managmentCart.getTotalFee() * percentTaxes * 100.0)/100;

        double total = Math.round((managmentCart.getTotalFee() + tax + delivery) * 100) / 100;
        double itemTotal = Math.round(managmentCart.getTotalFee() * 100)/100;

        binding.totalFeeTxt.setText("$ "+ itemTotal);
        binding.taxTxt.setText("$ "+ tax);
        binding.deliveryTxt.setText("$ "+ delivery);
        binding.totalTxt.setText("$ "+ total);
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(v -> finish());
    }
}