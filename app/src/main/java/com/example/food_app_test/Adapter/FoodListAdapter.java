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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.food_app_test.Activity.DetailActivity;
import com.example.food_app_test.Domain.Foods;
import com.example.food_app_test.R;

import java.util.ArrayList;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.viewholder> {

    ArrayList<Foods> items;
    Context context;

    public FoodListAdapter(ArrayList<Foods> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public FoodListAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_list_food,parent,false);

        return new viewholder(inflate);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FoodListAdapter.viewholder holder, int position) {

        holder.titleTxt.setText(items.get(position).getTitle());

        //Foods foods = items.get(position);
        // String foodName = foods.getTitle();

        //holder.timeTxt.setText(items.get(position).getTimeValue());
        holder.timeTxt.setText(String.valueOf(items.get(position).getTimeValue()));

        holder.priceTxt.setText("$" + items.get(position).getPrice());

        //holder.rateTxt.setText(""+items.get(position).getStar());
        holder.rateTxt.setText(String.valueOf(items.get(position).getStar()));

        // Load image from assets (no URL encoding needed with underscores)
        String imagePath = items.get(position).getImagePath();
        String assetPath = "file:///android_asset/" + imagePath;
        Log.d("FOODLIST_DEBUG", "Food title: " + items.get(position).getTitle());
        Log.d("FOODLIST_DEBUG", "Raw imagePath from DB: '" + imagePath + "'");
        Log.d("FOODLIST_DEBUG", "Final asset path: " + assetPath);
        
        Glide.with(context)
                .load(assetPath)
                .transform(new CenterCrop(), new RoundedCorners(30))
                .error(R.drawable.ic_launcher_foreground) // Error fallback
                .into(holder.pic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("object",items.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateData(ArrayList<Foods> newItems) {

        this.items = newItems;
        notifyDataSetChanged();


    }


    public static class viewholder extends RecyclerView.ViewHolder{

        TextView titleTxt,priceTxt,rateTxt,timeTxt;
        ImageView pic;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            titleTxt = itemView.findViewById(R.id.titleTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
            rateTxt = itemView.findViewById(R.id.rateTxt);
            timeTxt = itemView.findViewById(R.id.timeTxt);
            pic = itemView.findViewById(R.id.img);
        }
    }
}
