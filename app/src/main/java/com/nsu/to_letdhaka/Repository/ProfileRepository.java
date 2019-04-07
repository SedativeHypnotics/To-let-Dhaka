package com.nsu.to_letdhaka.Repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.nsu.to_letdhaka.Domain.Profile;

public interface ProfileRepository {
    void getProfile(String email, TextView username);

    void addProfile(Profile profile);

    void deleteProfile(Profile profile);

    void setUserName(String email, SharedPreferences sharedPreferences);
}
