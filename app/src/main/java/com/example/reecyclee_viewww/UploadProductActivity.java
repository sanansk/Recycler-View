package com.example.reecyclee_viewww;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class UploadProductActivity extends AppCompatActivity {

    TextView headline,description,price,brand,productType,aboutProduct,origin;
    ImageView uploadbtn,productImage;
    Button submit;
    Uri ImageUri;
    RelativeLayout relativeLayout;
    private FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;
    ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_product);

        headline= findViewById(R.id.headline);
        description= findViewById(R.id.description);
        price= findViewById(R.id.price);
        brand= findViewById(R.id.brand);
        productType= findViewById(R.id.productType);
        aboutProduct= findViewById(R.id.aboutProduct);
        origin= findViewById(R.id.origin);
        relativeLayout= findViewById(R.id.relative);

        uploadbtn= findViewById(R.id.uploadbtn);
        productImage= findViewById(R.id.productImage);

//
        database= FirebaseDatabase.getInstance();
        firebaseStorage= FirebaseStorage.getInstance();

        progressDialog= new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait...");
        progressDialog.setTitle("Product Uploading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
//

        submit= findViewById(R.id.submit);


        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
                relativeLayout.setVisibility(View.VISIBLE);
                uploadbtn.setVisibility(View.GONE);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                progressDialog.show();

                final StorageReference reference= firebaseStorage.getReference().child("product")
                        .child(System.currentTimeMillis()+"");
                reference.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                ProjectModel model= new ProjectModel();
                                model.setProductImage(uri.toString());

                                model.setHeadline(headline.getText().toString());
                                model.setDescription(description.getText().toString());
                                model.setPrice(price.getText().toString());
                                model.setBrand(brand.getText().toString());
                                model.setProductType(productType.getText().toString());
                                model.setAboutProduct(aboutProduct.getText().toString());
                                model.setOrigin(origin.getText().toString());

                                database.getReference().child("product").push().setValue(model)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                                progressDialog.dismiss();

                                                Toast.makeText(UploadProductActivity.this, "Product Upload Successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                Toast.makeText(UploadProductActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
                    }
                });
//
            }
        });
    }
//    image Up load
    private void uploadImage(){
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent= new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, 101);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(UploadProductActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==101 && resultCode== RESULT_OK){
            ImageUri = data.getData();
            productImage.setImageURI(ImageUri);
        }
    }
    //    image upload
}