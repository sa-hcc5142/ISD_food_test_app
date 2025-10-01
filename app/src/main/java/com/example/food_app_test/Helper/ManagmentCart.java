package com.example.food_app_test.Helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.food_app_test.Domain.Foods;

import java.util.ArrayList;


public class ManagmentCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagmentCart(Context context) {
        this.context = context;
        this.tinyDB=new TinyDB(context);
    }

    public void insertFood(Foods item) {
        Log.d("CART_SAVE_DEBUG", "=== ADDING ITEM TO CART ===");
        Log.d("CART_SAVE_DEBUG", "Item title: " + item.getTitle());
        Log.d("CART_SAVE_DEBUG", "Item imagePath: " + item.getImagePath());
        Log.d("CART_SAVE_DEBUG", "Item ID: " + item.getId());
        
        ArrayList<Foods> listpop = getListCart();
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listpop.size(); i++) {
            if (listpop.get(i).getTitle().equals(item.getTitle())) {
                existAlready = true;
                n = i;
                break;
            }
        }
        if(existAlready){
            listpop.get(n).setNumberInCart(item.getNumberInCart());
            Log.d("CART_SAVE_DEBUG", "Updated existing item quantity: " + item.getNumberInCart());
        }else{
            listpop.add(item);
            Log.d("CART_SAVE_DEBUG", "Added new item to cart");
        }

        Log.d("CART_DEBUG", "InsertFood -> " + item.getTitle()
                + ", imagePath: " + item.getImagePath());

        tinyDB.putListObject("CartList",listpop);

        Log.d("CART_DEBUG", "Restored from TinyDB: " + listpop.toString());

        Log.d("CART_SAVE_DEBUG", "Saved cart with " + listpop.size() + " items");
        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Foods> getListCart() {
        ArrayList<Foods> cartList = tinyDB.getListObject("CartList");
        Log.d("CART_LOAD_DEBUG", "=== LOADING CART ===");
        Log.d("CART_LOAD_DEBUG", "Cart size: " + cartList.size());
        for (int i = 0; i < cartList.size(); i++) {
            Foods item = cartList.get(i);
            Log.d("CART_LOAD_DEBUG", "Item " + i + ": " + item.getTitle() + ", imagePath: " + item.getImagePath());
        }
        return cartList;
    }

    public Double getTotalFee(){
        ArrayList<Foods> listItem=getListCart();
        double fee=0;
        for (int i = 0; i < listItem.size(); i++) {
            fee=fee+(listItem.get(i).getPrice()*listItem.get(i).getNumberInCart());
        }
        return fee;
    }
    public void minusNumberItem(ArrayList<Foods> listItem,int position,ChangeNumberItemsListener changeNumberItemsListener){
        if(listItem.get(position).getNumberInCart()==1){
            listItem.remove(position);
        }else{
            listItem.get(position).setNumberInCart(listItem.get(position).getNumberInCart()-1);
        }
        tinyDB.putListObject("CartList",listItem);
        changeNumberItemsListener.change();
    }
    public  void plusNumberItem(ArrayList<Foods> listItem,int position,ChangeNumberItemsListener changeNumberItemsListener){
        listItem.get(position).setNumberInCart(listItem.get(position).getNumberInCart()+1);
        tinyDB.putListObject("CartList",listItem);
        changeNumberItemsListener.change();
    }
}
