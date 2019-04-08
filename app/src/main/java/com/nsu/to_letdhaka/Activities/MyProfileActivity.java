package com.nsu.to_letdhaka.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.nsu.to_letdhaka.Domain.Profile;
import com.nsu.to_letdhaka.R;
import com.nsu.to_letdhaka.Service.ProfileService;

public class MyProfileActivity extends AppCompatActivity {
    private TextView username;
    private TextView emailView;
    private String email;
    private SharedPreferences sharedPreferences;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        setTitle("Profile");
        if(getIntent().getExtras() != null){
            Object email = getIntent().getExtras().get("email");
            if(email != null){
                this.email = (String) email;
            }
        }
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        bindWidgets();
        setProfile();
    }

    @SuppressLint("SetTextI18n")
    private void setProfile() {
        emailView.setText("Email: "+email);
        ProfileService profileService = new ProfileService();
        profileService.getProfile(email,username);
    }

    private void bindWidgets() {
        username = findViewById(R.id.username_view);
        emailView = findViewById(R.id.email_view);
        sharedPreferences = getSharedPreferences("shared_preference",MODE_PRIVATE);
    }

    @Override
    public void onBackPressed() {
        String currentActivity = sharedPreferences.getString("current_activity",null);
        assert currentActivity != null;
        if(currentActivity.equals("login")){
            startActivity(new Intent(MyProfileActivity.this,MainActivity.class));
        }
        else{
            finish();
        }
    }
}
