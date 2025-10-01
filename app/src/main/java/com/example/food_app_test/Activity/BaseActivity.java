package com.example.food_app_test.Activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.food_app_test.R;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class BaseActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    public String TAG = "uilover";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Only Firebase Auth - no Database/Firestore
        mAuth = FirebaseAuth.getInstance();
        
        // Fix Firebase locale warning
        FirebaseAuth.getInstance().useAppLanguage();

        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
    }
}