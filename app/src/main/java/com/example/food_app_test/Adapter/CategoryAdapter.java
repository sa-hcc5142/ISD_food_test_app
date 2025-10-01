package com.example.food_app_test.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_app_test.Activity.ListFoodActivity;
import com.example.food_app_test.Domain.Category;
import com.example.food_app_test.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewholder> {

    private ArrayList<Category> items;
    private Context context;

    public CategoryAdapter(ArrayList<Category> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CategoryAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);

        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.viewholder holder, int position) {

        //Category category = items.get(position);

        holder.titleTxt.setText(items.get(position).getName());

        // Load category image from assets (no URL encoding needed with underscores)
        String imagePath = items.get(position).getImagePath();
        String assetPath = "file:///android_asset/" + imagePath;
        Log.d("CATEGORY_DEBUG", "Category name: " + items.get(position).getName());
        Log.d("CATEGORY_DEBUG", "Raw imagePath from DB: '" + imagePath + "'");
        Log.d("CATEGORY_DEBUG", "Final asset path: " + assetPath);
        
        Glide.with(context)
                .load(assetPath)
                .error(R.drawable.ic_launcher_foreground) // Error fallback
                .into(holder.pic);


        switch (position){
            case 0 : {
                holder.pic.setBackgroundResource(R.drawable.cat_0_background);
                break;
            }
            case 1 : {
                holder.pic.setBackgroundResource(R.drawable.cat_1_background);
                break;
            }
            case 2 : {
                holder.pic.setBackgroundResource(R.drawable.cat_2_background);
                break;
            }
            case 3 : {
                holder.pic.setBackgroundResource(R.drawable.cat_3_background);
                break;
            }
            case 4 : {
                holder.pic.setBackgroundResource(R.drawable.cat_4_background);
                break;
            }
            case 5 : {
                holder.pic.setBackgroundResource(R.drawable.cat_5_background);
                break;
            }
            case 6 : {
                holder.pic.setBackgroundResource(R.drawable.cat_6_background);
                break;
            }
            case 7 : {
                holder.pic.setBackgroundResource(R.drawable.cat_7_background);
                break;
            }
        }




        //holder.bind(category);



        holder.itemView.setOnClickListener(v -> {

            //int categoryId = items.get(position).getId();
            //String categoryName = items.get(position).getName();

            int categoryId = items.get(position).getCategoryId();
            String categoryName = items.get(position).getName();

            Log.d("CategoryAdapter", "Clicked category ID: " + categoryId + ", Name: " + categoryName);

            Intent intent = new Intent(context, ListFoodActivity.class);
            intent.putExtra("CategoryId", categoryId);
            intent.putExtra("CategoryName",categoryName);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder{

        TextView titleTxt;
        ImageView pic;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.catNameTxt);
            pic = itemView.findViewById(R.id.imgCat);
        }

//        public void bind(Category category) {
//            titleTxt.setText(category.getName());
//        }
    }
}
