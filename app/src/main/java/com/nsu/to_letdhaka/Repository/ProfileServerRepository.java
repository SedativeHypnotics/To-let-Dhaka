package com.nsu.to_letdhaka.Repository;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nsu.to_letdhaka.Domain.Profile;

import java.util.Objects;

public class ProfileServerRepository implements ProfileRepository{
    private static final String DB_COLLECTION_NAME = "User";
    private static final String LOG_TAG = "To-let Dhaka";

    private FirebaseFirestore db;

    public ProfileServerRepository() {
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void getProfile(String email, final TextView username) {
        DocumentReference documentReference = db.collection(DB_COLLECTION_NAME).document(email);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {
                        Profile profile = document.toObject(Profile.class);
                        if(profile == null){
                            profile = new Profile("null","null","null");
                        }
                        username.setText(profile.getUsername());
                    } else {
                        Log.e("error","no document");
                    }
                } else {
                    Log.e("error", Objects.requireNonNull(task.getException()).getMessage());
                }
            }
        });
    }

    @Override
    public void addProfile(Profile profile) {

    }

    @Override
    public void deleteProfile(Profile profile) {

    }

    public void setUserName(String email, final SharedPreferences sharedPreferences){
        DocumentReference documentReference = db.collection(DB_COLLECTION_NAME).document(email);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("ApplySharedPref")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {
                        Profile profile = document.toObject(Profile.class);
                        if(profile == null){
                            profile = new Profile("null","null","null");
                        }
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username",profile.getUsername());
                        editor.commit();
                    } else {
                        Log.e("error","no document");
                    }
                } else {
                    Log.e("error", Objects.requireNonNull(task.getException()).getMessage());
                }
            }
        });
    }
}
