package com.example.reecyclee_viewww;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SingleProductActivity extends AppCompatActivity {

    TextView singleHeadline,singlePrice,singleBrand,singleProductType,singleAboutProduct,singleOrigin;
    ImageView singleImage;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);

        singleHeadline= findViewById(R.id.singleHeadline);
        singlePrice= findViewById(R.id.singlePrice);
        singleBrand= findViewById(R.id.singleBrand);
        singleProductType= findViewById(R.id.singleProductType);
        singleAboutProduct= findViewById(R.id.singleAboutProduct);
        singleOrigin= findViewById(R.id.singleOrigin);

        singleImage= findViewById(R.id.singleImage);

        Picasso.get().load(getIntent().getStringExtra("singleImage"))
                .placeholder(R.drawable.photo)
                .into(singleImage);

        singleHeadline.setText(getIntent().getStringExtra("singleHeadline"));
        singlePrice.setText(getIntent().getStringExtra("singlePrice"));
        singleBrand.setText(getIntent().getStringExtra("singleBrand"));
        singleProductType.setText(getIntent().getStringExtra("singleProductType"));
        singleAboutProduct.setText(getIntent().getStringExtra("singleAboutProduct"));
        singleOrigin.setText(getIntent().getStringExtra("singleOrigin"));
    }
}