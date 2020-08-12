package com.example.bikroy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bikroy.Users.ProductViewHolder;
import com.example.bikroy.Users.Products;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Shop extends AppCompatActivity {


    private DatabaseReference productsRef;
    private RecyclerView recyclerView;
    public TextView productName,productPrice,productAdress;
    public ImageView imageView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        productsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        productName = findViewById(R.id.product_name);
        productAdress = findViewById(R.id.product_address);
        productPrice = findViewById(R.id.product_price);
        imageView = findViewById(R.id.product_image);
        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager( layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>().setQuery(productsRef,Products.class).build();
        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull final Products products) {

                    productViewHolder.txtProductName.setText(products.getProduct());
                    productViewHolder.txtProductSeller.setText("Seller : " + products.getUserName());
                    productViewHolder.txtProductDescription.setText("Location : " +products.getAddress());
                    productViewHolder.txtProductPrice.setText("Price : "+products.getPrice()+"৳");
                    Picasso.get().load(products.getImage()).into(productViewHolder.imageView);
                    if(products.getUserID().equals(CurrentUser.userID)) {
                    productViewHolder.txtProductButton.setEnabled(false);
                    productViewHolder.txtProductButton.setText("Own Item");

                }


               /* productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Shop.this,ProductDetails.class);
                        intent.putExtra("product",products.getProduct());
                        intent.putExtra("userID",products.getUserID());
                        startActivity(intent);

                    }
                }); */


               productViewHolder.txtProductButton.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                       CartItem cartItem = new CartItem(products.getProduct(),products.getPrice(),products.getImage());
                       boolean b = true;
                       if(!Cart.cartItemList.isEmpty()){
                           for(int i=0 ; i<Cart.cartItemList.size() ; i++) {
                                CartItem c = Cart.cartItemList.get(i);
                                if((c.getProductName().equals(products.getProduct())))
                                {
                                    showToast("Already Added to Cart");
                                    b=false;
                                    break;
                                }

                              // System.out.println("List Product" + Cart.cartItemList.get(i).getProductName());
                             //  System.out.println("Current Product" + cartItem.getProductName());
                           }
                           if(b==true) {
                               Cart.cartItemList.add(cartItem);
                               showToast("Added to Cart");
                           }


                       }
                       else {
                           Cart.cartItemList.add(cartItem);
                           showToast("Added to Cart");
                       }

                       System.out.println("Size" + Cart.cartItemList.size());


                      // showToast(products.getProduct());

                      /* Intent intent = new Intent(Shop.this,Cart.class);
                       intent.putExtra("product",products.getProduct());
                       intent.putExtra("userID",products.getUserID());
                       intent.putExtra("Image",products.getImage());
                       startActivity(intent); */

                   }
               });
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_layout,parent,false);
               ProductViewHolder holder = new ProductViewHolder(view);
               return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}
