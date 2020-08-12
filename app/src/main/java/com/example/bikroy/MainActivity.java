package com.example.bikroy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bikroy.Users.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public EditText editTextEmail,editTextPassword;
    String email="",password="";
    public String userID="";


    public Button signUpButton,loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signUpButton = findViewById(R.id.signUp_button);
        loginButton = findViewById(R.id.login_button);
        editTextEmail = findViewById(R.id.email_text);
        editTextPassword = findViewById(R.id.passwod_text);



        loginButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }


    public void onClick(View view) {
        switch(view.getId()){


            case R.id.login_button:
                    email = editTextEmail.getText().toString();
                    password = editTextPassword.getText().toString();

                if( ValueCheck.checkEmpty(email) &&   ValueCheck.checkEmpty(password) ) {
                    Toast.makeText(this,"Login",Toast.LENGTH_SHORT).show();
                    String arr[] = email.split("@",2);
                    userID =  arr[0];
                    Validate();
                }
                else {
                    Toast.makeText(this,"Empty",Toast.LENGTH_SHORT).show();

                }


                break;
            case R.id.signUp_button:
                Intent intent = new Intent(MainActivity.this,SignUp.class);
                startActivity(intent);
                break;
        }
    }

    public void Validate() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(userID).exists()) {

                    User user = dataSnapshot.child("Users").child(userID).getValue(User.class);
                    if(user.getEmail().equals(email)) {
                        if(user.getPassword().equals(password)) {
                            showToast("Succesfully Logged In");
                            CurrentUser.userID=userID;
                            CurrentUser.name = user.getfirst_name() +" " +user.getLast_name();
                            Intent intent = new Intent(MainActivity.this,Profile.class);
                            intent.putExtra("currentUser",user.getfirst_name());
                            intent.putExtra("UserID",userID);
                            startActivity(intent);
                        }
                        else {
                            showToast("Wrong Password");
                        }
                    }
                }
                else {
                    showToast("User Doesn't Exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void showToast(String text) {

            Toast.makeText(this,text,Toast.LENGTH_LONG).show();



    }



}
