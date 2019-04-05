package com.nsu.to_letdhaka.Repository;

import com.nsu.to_letdhaka.Domain.Ad;

import java.util.List;

public interface AdRepository {
    List<Ad> listAds();

    void listAdsAsync(OnResultListener<List<Ad>> resultListener);

    void addAd(Ad ad);

    void updateAd(Ad ad);

    void deleteAd(Ad ad);


    interface OnResultListener<T> {

        void onResult(T data);

    }
}
