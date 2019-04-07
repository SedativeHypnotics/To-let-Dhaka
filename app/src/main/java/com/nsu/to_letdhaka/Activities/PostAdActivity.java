package com.nsu.to_letdhaka.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nsu.to_letdhaka.R;

public class PostAdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ad);
        setTitle("Post Ad");
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PostAdActivity.this,MainActivity.class));
    }
}
