package com.example.bikroy.Users;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bikroy.ItemClickListener;
import com.example.bikroy.R;


import androidx.recyclerview.widget.RecyclerView;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtProductDescription, txtProductPrice,txtProductSeller;
    public ImageView imageView;
    public Button txtProductButton;
    public ItemClickListener listner;


    public ProductViewHolder(View itemView)
    {
        super(itemView);


        imageView =  itemView.findViewById(R.id.product_image);
        txtProductName =  itemView.findViewById(R.id.product_name);
        txtProductDescription = itemView.findViewById(R.id.product_address);
        txtProductPrice =  itemView.findViewById(R.id.product_price);
        txtProductSeller = itemView.findViewById(R.id.product_seller);
        txtProductButton = itemView.findViewById(R.id.product_buy_button);
    }

    public void setItemClickListner(ItemClickListener listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view)
    {
        listner.onClick(view, getAdapterPosition(), false);
    }
}