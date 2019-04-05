package com.nsu.to_letdhaka.Service;

import android.content.Context;

import com.nsu.to_letdhaka.Domain.Ad;
import com.nsu.to_letdhaka.Repository.AdRepository;
import com.nsu.to_letdhaka.Repository.AdServerRepository;

import java.util.List;

public class AdService {
    private static final boolean USE_SQLITE_REPOSITORY = false;

    private AdRepository repository;

    public AdService(Context context) {
        repository = USE_SQLITE_REPOSITORY ? new AdServerRepository() : new AdServerRepository();
    }

    public AdService() {
        repository = new AdServerRepository();
    }

    public List<Ad> listAds() {
        return repository.listAds();
    }

    public void listAdsAsync(AdRepository.OnResultListener<List<Ad>> resultListener) {
        repository.listAdsAsync(resultListener);
    }

    public void addAd(Ad ad) {
        repository.addAd(ad);
    }

    public void updateAd(Ad ad) {
        repository.updateAd(ad);
    }

    public void deleteAd(Ad ad) {
        repository.deleteAd(ad);
    }
}
