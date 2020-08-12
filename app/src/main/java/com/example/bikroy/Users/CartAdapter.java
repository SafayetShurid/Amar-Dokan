package com.example.bikroy.Users;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import  android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bikroy.CartItem;
import com.example.bikroy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {


    public CartAdapter(List<CartItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    private List<CartItem> listItems;
    private Context context;



    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout,parent,false);
       return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            CartItem cartItem = listItems.get(position);
            holder.cartName.setText(cartItem.getProductName());
            holder.cartPrice.setText(cartItem.getProductPrice()+"৳");
            Picasso.get().load(cartItem.getImageUrl()).into(holder.cartImage);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

            public TextView cartPrice;
            public TextView cartName;
            public ImageView cartImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartPrice = itemView.findViewById(R.id.cart_product_price);
            cartName = itemView.findViewById(R.id.cart_product_name);
            cartImage = itemView.findViewById(R.id.cart_image);
        }
    }



}
