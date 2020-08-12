package com.example.bikroy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Random;

public class AddProduct extends AppCompatActivity implements View.OnClickListener {


    public String address="",price="",product="",downloadImageUrl="",productKey="";
    public EditText productName,productPrice,productAdress;
    public Button addProductButton;
    public ImageView viewImage,uploadButton;
    private static final int galleryPick = 1;
    private StorageReference productImageRef;
    private Uri imageUri;

    public String currentUserID="";

    private DatabaseReference productRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        currentUserID = getIntent().getStringExtra("UserID");
        productImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productName = findViewById(R.id.product_name_text);
        productPrice = findViewById(R.id.product_price_text);
        productAdress = findViewById(R.id.product_adress_text);
        addProductButton = findViewById(R.id.add_product_button);
        uploadButton = findViewById(R.id.image_upload_button);
        viewImage = findViewById(R.id.view_mage);
        addProductButton.setOnClickListener(this);
        uploadButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){


            case R.id.add_product_button:

                    Toast.makeText(this,"Add Product",Toast.LENGTH_SHORT).show();
                    Validate();
                    break;

            case R.id.image_upload_button:
                   Toast.makeText(this,"Image Upload",Toast.LENGTH_SHORT).show();
                    openGallery();
                   break;
        }
    }


    public void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,galleryPick);
    }

    public void Validate() {
            product = productName.getText().toString();
            price = productPrice.getText().toString();
            address = productAdress.getText().toString();

            if(imageUri==null){
                Toast.makeText(this,"You Must Upload a Product Image",Toast.LENGTH_LONG);
            }
            else if(!(ValueCheck.checkEmpty(product) && ValueCheck.checkEmpty(price) && ValueCheck.checkEmpty(address))) {
                Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_LONG);
        }
            else {
                StorageInfromation();
            }
    }


    public void StorageInfromation() {

        Random rand = new Random();
        int a = rand.nextInt(10000);
        productKey = a+"";
        final StorageReference filepath = productImageRef.child(imageUri.getLastPathSegment() + productKey + ".jpg");
        final UploadTask uploadTask = filepath.putFile(imageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                ShowToast(message);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ShowToast("Image Uploaded Successfully");
                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                       if(!(task.isSuccessful())) {
                           throw  task.getException();
                       }
                       downloadImageUrl = filepath.getDownloadUrl().toString();
                       return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();
                            ShowToast("Product Image url done");
                            SaveImageToDatabase();
                        }

                    }
                });
            }
        });
    }


    public void SaveImageToDatabase() {
        HashMap<String,Object> productmap = new HashMap<>();
        productmap.put("productKey",productKey);
        productmap.put("product",product);
        productmap.put("address",address);
        productmap.put("price",price);
        productmap.put("image",downloadImageUrl);
        productmap.put("userID",currentUserID);
        productmap.put("userName",CurrentUser.name);
        productRef.child(product).updateChildren(productmap);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==galleryPick && resultCode==RESULT_OK && data!=null) {
            imageUri = data.getData();
            viewImage.setImageURI(imageUri);
        }

    }

    public void ShowToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}
