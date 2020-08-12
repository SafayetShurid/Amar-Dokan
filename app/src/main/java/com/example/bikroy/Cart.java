package com.example.bikroy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bikroy.Users.CartAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    String total="";


    public static ArrayList<CartItem> cartItemList = new ArrayList<CartItem>() {
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

       recyclerView = findViewById(R.id.cart_recyler_view);
       recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));
       Button checkoutButton = findViewById(R.id.checkout_button);
        total = getTotal();
       checkoutButton.setText("Checkout " + "(" +total + ")" );
       checkoutButton.setOnClickListener(this);

       adapter = new CartAdapter(cartItemList,this);
       recyclerView.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.checkout_button:
                Toast.makeText(this,"Purchased",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Cart.this,Profile.class);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                for(int i=0 ; i<cartItemList.size();i++) {
                   databaseReference.child("Products").child(cartItemList.get(i).getProductName()).removeValue();
                }
                cartItemList.clear();
                startActivity(intent);

        }
    }

    public String getTotal() {
        double price = 0;
        for(int i= 0 ;i<cartItemList.size() ; i++) {
            CartItem a = cartItemList.get(i);
             price = price + Double.valueOf(a.getProductPrice());
        }
        return price+"";
    }
}
