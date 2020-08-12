package com.example.bikroy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignUp extends AppCompatActivity implements View.OnClickListener{


    public Button registerButton;
    public EditText firstNameText,lastNameText,emailText,passText,cpassText;
    public String fname="",lname="",email="",pass="",cpass="";
    public String userID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstNameText = findViewById(R.id.name_text);
        lastNameText = findViewById(R.id.last_name_text);
        emailText = findViewById(R.id.email_text);
        passText = findViewById(R.id.passwod_text);
        cpassText = findViewById(R.id.confirm_pass_text);
        registerButton =findViewById(R.id.register_button);

        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){


            case R.id.register_button:

                fname = firstNameText.getText().toString();
                lname = lastNameText.getText().toString();
                email = emailText.getText().toString();
                pass = passText.getText().toString();
                cpass = cpassText.getText().toString();


                if(!(ValueCheck.checkEmpty(fname) && ValueCheck.checkEmpty(lname) && ValueCheck.checkEmpty(email) &&
                        ValueCheck.checkEmpty(pass) && ValueCheck.checkEmpty(cpass)) ) {
                    Toast.makeText(this,"empty",Toast.LENGTH_SHORT).show();
                }
                else if(!pass.equals(cpass)) {
                    Toast.makeText(this,"password_mismatch",Toast.LENGTH_SHORT).show();
                }

                else {
                    String arr[] = email.split("@",2);
                    userID =  arr[0];
                    Validate();

                }

                break;

        }
    }

    public void Validate() {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(userID).exists())) {
                    HashMap<String,Object> users = new HashMap<>();
                    users.put("first_name",fname);
                    users.put("last_name",lname);
                    users.put("email",email);
                    users.put("password",pass);
                    reference.child("Users").child(userID).updateChildren(users);
                    Toast.makeText(SignUp.this, "Registration Succesful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this,MainActivity.class);
                    startActivity(intent);


                }
                else {
                    Toast.makeText(SignUp.this, "Email Taken!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {



            }
        });
    }
}
