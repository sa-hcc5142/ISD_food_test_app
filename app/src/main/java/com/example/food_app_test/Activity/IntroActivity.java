package com.example.food_app_test.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.food_app_test.R;
import com.example.food_app_test.databinding.ActivityIntroBinding;

public class IntroActivity extends BaseActivity {

    ActivityIntroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();
        getWindow().setStatusBarColor(Color.parseColor("#FFE485"));

    }

    private void setVariable() {
        binding.loginBtn.setOnClickListener(v -> {
            if(mAuth.getCurrentUser() != null){
                startActivity(new Intent(IntroActivity.this,MainActivity.class));
            }
            else{
                startActivity(new Intent(IntroActivity.this,LoginActivity.class));

            }

        });

        binding.signUpBtn.setOnClickListener(v -> startActivity(new Intent(IntroActivity.this,SignUpActivity.class)));
    }
}