package com.example.food_app_test.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.example.food_app_test.Domain.Foods;
import com.example.food_app_test.Helper.ChangeNumberItemsListener;
import com.example.food_app_test.Helper.ManagmentCart;
import com.example.food_app_test.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.viewholder> {

    ArrayList<Foods> list;
    private ManagmentCart managmentCart;
    ChangeNumberItemsListener changeNumberItemsListener;

    public CartAdapter(ArrayList<Foods> list, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.list = list;
        managmentCart = new ManagmentCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public CartAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new viewholder(inflate);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartAdapter.viewholder holder, int position) {
        Foods item = list.get(position);

        holder.title.setText(item.getTitle());
        holder.feeEachItem.setText("$" + (item.getNumberInCart() * item.getPrice()));
        holder.totalEachItem.setText(item.getNumberInCart() + " x $" + item.getPrice());
        holder.num.setText(String.valueOf(item.getNumberInCart()));

        // Enhanced debug logging for cart items
        String imagePath = item.getImagePath();
        String assetPath = "file:///android_asset/" + imagePath;
        Log.d("CART_DEBUG", "=== CART ITEM " + position + " ===");
        Log.d("CART_DEBUG", "Food title: " + item.getTitle());
        Log.d("CART_DEBUG", "Food ID: " + item.getId());
        Log.d("CART_DEBUG", "Food price: " + item.getPrice());
        Log.d("CART_DEBUG", "Raw imagePath from DB: '" + imagePath + "'");
        Log.d("CART_DEBUG", "Final asset path: " + assetPath);
        
        if (imagePath == null || imagePath.isEmpty()) {
            Log.e("CART_DEBUG", "ERROR: imagePath is null or empty!");
            // Try to show a default image or placeholder
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.ic_launcher_foreground)
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(holder.pic);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(assetPath)
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .error(R.drawable.ic_launcher_foreground) // Error fallback
                    .into(holder.pic);
        }



        // handle + button
        holder.plusBtn.setOnClickListener(v -> managmentCart.plusNumberItem(list, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.change();
        }));

        // handle - button
        holder.minusBtn.setOnClickListener(v -> managmentCart.minusNumberItem(list, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.change();
        }));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView title, feeEachItem, plusBtn, minusBtn, totalEachItem, num;
        ImageView pic;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titleTxt);
            pic = itemView.findViewById(R.id.pic);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            plusBtn = itemView.findViewById(R.id.plusCartBtn);
            minusBtn = itemView.findViewById(R.id.minusCartBtn);
            totalEachItem = itemView.findViewById(R.id.totalEachTime);
            num = itemView.findViewById(R.id.numItemTxt);
        }
    }
}
