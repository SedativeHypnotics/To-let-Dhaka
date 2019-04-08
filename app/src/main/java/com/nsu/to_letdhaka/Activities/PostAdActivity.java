package com.nsu.to_letdhaka.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.widget.EditText;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nsu.to_letdhaka.R;

public class PostAdActivity extends AppCompatActivity {
    private AppCompatSpinner month, roomFor, category;
    private EditText rent, location, phoneNumber;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ad);
        setTitle("Post Ad");
        bindWidgets();
        bindListeners();
    }

    private void bindListeners() {
        if(validate(new EditText[]{rent,location,phoneNumber})){
            uploadAd();
        }
    }

    private void uploadAd() {

    }

    private void bindWidgets() {
        month = findViewById(R.id.select_month);
        roomFor = findViewById(R.id.select_room_for);
        category = findViewById(R.id.select_category);
        storageReference = FirebaseStorage.getInstance().getReference();
        rent = findViewById(R.id.input_rent);
        location = findViewById(R.id.input_address);
        phoneNumber = findViewById(R.id.input_contact_no);
    }

    private boolean validate(EditText[] editTexts){
        boolean validate = true;
        for (EditText editText : editTexts){
            if(TextUtils.isEmpty(editText.getText().toString())){
                editText.setError("Field cannot be empty!");
                validate = false;
            }
        }
        return validate;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PostAdActivity.this,MainActivity.class));
    }
}
