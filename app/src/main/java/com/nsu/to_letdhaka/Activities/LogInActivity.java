package com.nsu.to_letdhaka.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nsu.to_letdhaka.R;
import com.nsu.to_letdhaka.Service.ProfileService;

public class LogInActivity extends AppCompatActivity {
    private EditText email,password;
    private Button loginButton;
    private ProgressDialog progressDialog;
    private TextView signUp,recoverPassword;
    private SharedPreferences sharedPreferences;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        setTitle("Log In");
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
        bindListeners();
    }

    private void bindWidgets() {
        email = findViewById(R.id.email_value);
        password = findViewById(R.id.password_value);
        loginButton = findViewById(R.id.email_sign_in);
        signUp = findViewById(R.id.signUp);
        recoverPassword = findViewById(R.id.recover_password);
        progressDialog = new ProgressDialog(this);
        sharedPreferences = getSharedPreferences("shared_preference",MODE_PRIVATE);
    }

    private void bindListeners() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate(email,password)) {
                    progressDialog.setTitle("Logging in...");
                    progressDialog.show();
                    logIn();
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogInActivity.this,SignUpActivity.class));
            }
        });

        recoverPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogInActivity.this,RetrievePassword.class));
            }
        });
    }

    private void logIn() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            ProfileService profileService = new ProfileService();
                            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            assert firebaseUser != null;
                            profileService.setUserName(firebaseUser.getEmail(),sharedPreferences);
                            //Toast.makeText(LogInActivity.this, firebaseUser.getEmail(), Toast.LENGTH_SHORT).show();
                            String route = sharedPreferences.getString("route",null);
                            SharedPreferences.Editor editor;
                            editor = sharedPreferences.edit();
                            editor.putString("current_activity","login");
                            editor.commit();
                            assert route != null;
                            if(route.equals("post")) {
                                startActivity(new Intent(LogInActivity.this, MyAdsActivity.class));
                            }
                            else if(route.equals("post_ad")){
                                startActivity(new Intent(LogInActivity.this, PostAdActivity.class));
                            }
                            else{
                                Toast.makeText(LogInActivity.this, firebaseUser.getEmail(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LogInActivity.this, MyProfileActivity.class).putExtra("email",firebaseUser.getEmail()));
                            }
                        }
                        else{
                            Toast.makeText(LogInActivity.this,getString(R.string.error),Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });

    }
    private boolean validate(EditText email, EditText password){
        boolean value = true;

        if(TextUtils.isEmpty(email.getText().toString())){
            value = false;
            email.setError("Email can't be empty!");
        }

        if(TextUtils.isEmpty(password.getText().toString())){
            value = false;
            if(TextUtils.isEmpty(password.getText().toString())) password.setError("Password can't be empty!");
        }
        return value;
    }
}
