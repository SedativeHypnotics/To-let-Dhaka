package com.nsu.to_letdhaka.Activities;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nsu.to_letdhaka.Domain.Ad;
import com.nsu.to_letdhaka.R;

import java.io.File;
import java.io.IOException;

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
        try {
            prepareScreen();
        } catch (Exception e) {e.printStackTrace();}
    }

    @SuppressLint("SetTextI18n")
    private void prepareScreen() throws IOException {
        final File localFile = File.createTempFile("images", "jpg");
        storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
                imageView.setImageBitmap(BitmapFactory.decodeFile(localFile.getPath()));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Toast.makeText(AdDetailsActivity.this,"Failed image",Toast.LENGTH_LONG).show();
            }
        });
        month.setText("Month\n" + ad.getMonth());
        rent.setText("Rent\n"+ad.getRent());
        category.setText("Category\n"+ad.getCategory());
        contactNo.setText(ad.getContactNo());
        address.setText(ad.getAddress());
        Toast.makeText(AdDetailsActivity.this, ad.getAddress(), Toast.LENGTH_LONG).show();
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
