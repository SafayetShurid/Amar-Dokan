package com.example.bikroy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends AppCompatActivity implements View.OnClickListener {

    public TextView welcomeText;
    public String currentUserID="";
    public Button sellButton,buyButton,cartButton;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        welcomeText = findViewById(R.id.welcomeUser_text);
        sellButton = findViewById(R.id.sell_button);
        buyButton = findViewById(R.id.buy_button);
        cartButton = findViewById(R.id.cart_Button2);
        currentUserID = getIntent().getStringExtra("UserID");
        welcomeText.setText("Welcome " + CurrentUser.name);
        sellButton.setOnClickListener(this);
        buyButton.setOnClickListener(this);
        cartButton.setOnClickListener(this);
        context=this;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.sell_button:

                Toast.makeText(this,CurrentUser.name,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Profile.this,AddProduct.class);
                intent.putExtra("UserID",currentUserID);
                startActivity(intent);
                break;

            case R.id.buy_button:

                Intent intent2 = new Intent(Profile.this,Shop.class);
                intent2.putExtra("UserID",currentUserID);
                startActivity(intent2);
                break;

            case R.id.cart_Button2:


                Intent intent3 = new Intent(Profile.this,Cart.class);
                intent3.putExtra("UserID",currentUserID);
                startActivity(intent3);
                break;


        }
    }

    public void onBackPressed() {
        new AlertDialog.Builder(context)
                .setTitle("Exiting the App")
                .setMessage("Are you sure?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // The user wants to leave - so dismiss the dialog and exit
                        CurrentUser.name="";
                        CurrentUser.userID="";
                        Cart.cartItemList.clear();
                        Intent intent = new Intent(Profile.this,MainActivity.class);
                        startActivity(intent);
                       // finish();
                        dialog.dismiss();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // The user is not sure, so you can exit or just stay
                dialog.dismiss();
            }
        }).show();
    }
}
