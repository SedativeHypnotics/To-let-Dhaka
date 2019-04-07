package com.nsu.to_letdhaka.Service;

import android.content.Context;
import android.widget.TextView;

import com.nsu.to_letdhaka.Domain.Profile;
import com.nsu.to_letdhaka.Repository.ProfileRepository;
import com.nsu.to_letdhaka.Repository.ProfileServerRepository;

public class ProfileService {
    private static final boolean USE_SQLITE_REPOSITORY = false;

    private ProfileRepository repository;

    public ProfileService(Context context) {
        repository = USE_SQLITE_REPOSITORY ? new ProfileServerRepository() : new ProfileServerRepository();
    }

    public ProfileService() {
        repository = new ProfileServerRepository();
    }

    public void addProfile(Profile profile) {
        repository.addProfile(profile);
    }

    public void getProfile(String email, TextView username){
        repository.getProfile(email,username);
    }

    public void deleteProfile(Profile profile) {
        repository.deleteProfile(profile);
    }
}
