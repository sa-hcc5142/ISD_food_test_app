package com.example.food_app_test.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.food_app_test.R;

import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {
    
    /**
     * Load image from assets folder using Glide
     * @param context Application context
     * @param imageName Name of the image file (with or without extension)
     * @param folder Folder name inside assets/images/
     * @param imageView ImageView to load the image into
     */
    public static void loadImageFromAssets(Context context, String imageName, String folder, ImageView imageView) {
        try {
            // Clean the image name - remove extension if present
            String cleanImageName = imageName;
            if (imageName.endsWith(".jpg") || imageName.endsWith(".png")) {
                cleanImageName = imageName.substring(0, imageName.lastIndexOf("."));
            }
            
            // Try with .jpg extension first
            String assetPath = "file:///android_asset/images/" + folder + "/" + cleanImageName + ".jpg";
            
            Glide.with(context)
                    .load(assetPath)
                    .error(R.drawable.ic_launcher_foreground) // fallback image
                    .into(imageView);
                    
        } catch (Exception e) {
            try {
                // Clean the image name - remove extension if present
                String cleanImageName = imageName;
                if (imageName.endsWith(".jpg") || imageName.endsWith(".png")) {
                    cleanImageName = imageName.substring(0, imageName.lastIndexOf("."));
                }
                
                // Try with .png extension
                String assetPath = "file:///android_asset/images/" + folder + "/" + cleanImageName + ".png";
                
                Glide.with(context)
                        .load(assetPath)
                        .error(R.drawable.ic_launcher_foreground) // fallback image
                        .into(imageView);
                        
            } catch (Exception e2) {
                // Load default placeholder if image not found
                try {
                    Glide.with(context)
                            .load(R.drawable.ic_launcher_foreground)
                            .into(imageView);
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        }
    }
    
    /**
     * Load food image from assets
     */
    public static void loadFoodImage(Context context, String imageName, ImageView imageView) {
        loadImageFromAssets(context, imageName, "foods", imageView);
    }
    
    /**
     * Load category image from assets  
     */
    public static void loadCategoryImage(Context context, String imageName, ImageView imageView) {
        loadImageFromAssets(context, imageName, "categories", imageView);
    }
    
    /**
     * Get drawable from assets (alternative method)
     */
    public static Drawable getImageFromAssets(Context context, String imageName, String folder) {
        try {
            InputStream inputStream = context.getAssets().open("images/" + folder + "/" + imageName + ".jpg");
            return Drawable.createFromStream(inputStream, null);
        } catch (IOException e) {
            try {
                InputStream inputStream = context.getAssets().open("images/" + folder + "/" + imageName + ".png");
                return Drawable.createFromStream(inputStream, null);
            } catch (IOException e2) {
                return null;
            }
        }
    }
}