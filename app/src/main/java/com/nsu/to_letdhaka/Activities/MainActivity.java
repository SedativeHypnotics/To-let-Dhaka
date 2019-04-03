package com.nsu.to_letdhaka.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.nsu.to_letdhaka.R;
import com.nsu.to_letdhaka.Utils.FileChooserLibrary.FileUtils;

public class MainActivity extends AppCompatActivity {
    private ImageButton messButton;
    private ImageButton hostelButton;
    private ImageButton flatButton;
    private ImageButton subletButton;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindWidgets();
        bindListeners();
    }

    private void bindListeners() {
        messButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor = sharedPreferences.edit();
                editor.putString("filter","mess");
                editor.commit();
                startActivity(new Intent(MainActivity.this,AdListActivity.class));
            }
        });

        hostelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor = sharedPreferences.edit();
                editor.putString("filter","hostel");
                editor.commit();
                startActivity(new Intent(MainActivity.this,AdListActivity.class));
            }
        });

        flatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor = sharedPreferences.edit();
                editor.putString("filter","flat");
                editor.commit();
                startActivity(new Intent(MainActivity.this,AdListActivity.class));
            }
        });

        subletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor = sharedPreferences.edit();
                editor.putString("filter","sublet");
                editor.commit();
                startActivity(new Intent(MainActivity.this,AdListActivity.class));
            }
        });
    }

    private void bindWidgets() {
        messButton = findViewById(R.id.mess);
        hostelButton = findViewById(R.id.hostel);
        flatButton = findViewById(R.id.flat);
        subletButton = findViewById(R.id.sub_let);
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
    }

}
