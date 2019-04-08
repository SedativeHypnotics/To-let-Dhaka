package com.nsu.to_letdhaka.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nsu.to_letdhaka.Domain.Ad;
import com.nsu.to_letdhaka.R;
import com.nsu.to_letdhaka.Service.AdService;
import com.nsu.to_letdhaka.Service.ProfileService;
import com.nsu.to_letdhaka.Utils.FileChooserLibrary.FileUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class PostAdActivity extends AppCompatActivity {
    private AppCompatSpinner month, roomFor, category;
    private EditText rent, location, phoneNumber;
    private StorageReference storageReference;
    private SharedPreferences sharedPreferences;
    private Button submitButton;
    private ImageView imageView;
    private String filename;
    private Uri fileUri;
    private ProgressDialog progressDialog;

    private static final String TAG = "PostAdActivity";

    private static final int REQUEST_CODE = 6384;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ad);
        setTitle("Post Ad");
        bindWidgets();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        ProfileService profileService = new ProfileService();
        assert firebaseUser != null;
        profileService.setUserName(firebaseUser.getEmail(),sharedPreferences);
        bindListeners();
    }

    private void bindListeners() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate(new EditText[]{rent,location,phoneNumber})){
                    uploadAd();
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChooser();
            }
        });

    }

    private void uploadAd() {
        progressDialog.setTitle("Uploading Ad...");
        progressDialog.show();
        Ad ad = new Ad();
        ad.setAddress(location.getText().toString());
        ad.setNumber("1");
        ad.setContactNo(phoneNumber.getText().toString());
        ad.setRent(rent.getText().toString());
        ad.setMonth(month.getSelectedItem().toString());
        ad.setRoomFor(roomFor.getSelectedItem().toString());
        ad.setCategory(category.getSelectedItem().toString());
        ad.setUser(sharedPreferences.getString("username",null));
        ad.setImage("gs://toletdhaka-c9c35.appspot.com/"+filename);
        Date date = new Date();
        String strDateFormat = "dd MMM,yyyy";
        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);
        ad.setDate(formattedDate);
        Toast.makeText(PostAdActivity.this,ad.toString(),Toast.LENGTH_LONG).show();

        AdService adService = new AdService();
        adService.addAd(ad);
        uploadImage();
    }

    private void uploadImage() {
        StorageReference imageReference = storageReference.child(filename);
        UploadTask uploadTask = imageReference.putFile(fileUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PostAdActivity.this,"Failed Uploading image",Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
            }
        });
    }

    private void bindWidgets() {
        month = findViewById(R.id.select_month);
        roomFor = findViewById(R.id.select_room_for);
        category = findViewById(R.id.select_category);
        storageReference = FirebaseStorage.getInstance().getReference();
        rent = findViewById(R.id.input_rent);
        location = findViewById(R.id.input_address);
        phoneNumber = findViewById(R.id.input_contact_no);
        sharedPreferences = getSharedPreferences("shared_preference",MODE_PRIVATE);
        submitButton = findViewById(R.id.submit);
        imageView = findViewById(R.id.input_image);
        progressDialog = new ProgressDialog(this);
    }

    private boolean validate(EditText[] editTexts){
        boolean validate = true;
        for (EditText editText : editTexts){
            if(TextUtils.isEmpty(editText.getText().toString())){
                editText.setError("Field cannot be empty!");
                validate = false;
            }
        }
        if(filename == null){
            Toast.makeText(PostAdActivity.this,"Filename empty",Toast.LENGTH_LONG).show();
            validate = false;
        }
        return validate;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PostAdActivity.this,MainActivity.class));
    }

    private void showChooser() {
        // Use the GET_CONTENT intent from the utility class
        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(
                target, getString(R.string.chooser_title));
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the URI of the selected file
                        final Uri uri = data.getData();
                        assert uri != null;
                        Log.i(TAG, "Uri = " + uri.toString());
                        try {
                            // Get the file path from the URI
                            filename = uri.getLastPathSegment();
                            fileUri =uri;
                            imageView.setImageURI(null);
                            imageView.setImageURI(uri);
                        } catch (Exception e) {
                            Log.e("FileSelectorTest", "File select error", e);
                        }
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
