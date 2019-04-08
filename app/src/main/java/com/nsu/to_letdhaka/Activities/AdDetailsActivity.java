package com.nsu.to_letdhaka.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.nsu.to_letdhaka.Service.AdService;

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
    @SuppressWarnings("FieldCanBeLocal")
    private FirebaseStorage firebaseStorage;
    private Ad ad;
    private String prevActivity;
    private Toolbar toolbar;

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
        if(getIntent().getExtras() !=null){
            Object activity = getIntent().getExtras().get("activity");
            if (activity != null) {
                prevActivity = (String) activity;
            }
        }
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ad_details_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.action_delete);
        if(prevActivity==null){
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_delete){
            showDialog();

        }
        return true;
    }

    private void showDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.delete_card_confirmation_title)
                .setMessage(R.string.delete_card_confirmation_message)
                .setPositiveButton(R.string.delete,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ProgressDialog progressDialog = new ProgressDialog(AdDetailsActivity.this);
                                progressDialog.setTitle("Deleting ad...");
                                progressDialog.show();
                                AdService adService = new AdService();
                                adService.deleteAd(ad);
                                progressDialog.dismiss();
                                finish();
                            }
                        })
                .setNegativeButton(R.string.cancel, null)
                .create()
                .show();
    }
}
