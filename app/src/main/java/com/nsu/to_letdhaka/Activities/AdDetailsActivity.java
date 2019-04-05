package com.nsu.to_letdhaka.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nsu.to_letdhaka.Domain.Ad;
import com.nsu.to_letdhaka.R;

public class AdDetailsActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView month;
    private TextView rent;
    private TextView category;
    private TextView contactNo;
    private TextView address;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;
    private Ad ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_details);
        if(getIntent().getExtras() !=null){
            Object ad = getIntent().getExtras().get("ad");
            if (ad != null) {
                this.ad = (Ad) ad;
            }
        }
        bindWidgets();
        prepareScreen();
    }

    private void prepareScreen() {
        Glide.with(this)
                .load(storageReference)
                .into(imageView);
        month.setText(ad.getMonth());
        rent.setText(ad.getRent());
        category.setText(ad.getCategory());
        contactNo.setText(ad.getContactNo());
        address.setText(ad.getAddress());
    }

    private void bindWidgets() {
        imageView = findViewById(R.id.image_view);
        month = findViewById(R.id.month_view);
        rent = findViewById(R.id.rent_view);
        category = findViewById(R.id.category_view);
        contactNo = findViewById(R.id.contact_no_view);
        address = findViewById(R.id.address_view);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReferenceFromUrl(ad.getImage());
    }
}
