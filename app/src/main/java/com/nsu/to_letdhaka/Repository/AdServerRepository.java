package com.nsu.to_letdhaka.Repository;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nsu.to_letdhaka.Domain.Ad;

import java.util.ArrayList;
import java.util.List;

public class AdServerRepository implements AdRepository{
    private static final String DB_COLLECTION_NAME = "Ads";
    private static final String LOG_TAG = "To-let Dhaka";

    private FirebaseFirestore db;

    public AdServerRepository() {
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public List<Ad> listAds() {
        throw new RuntimeException("Method not implemented. Call the async variation of this method - listCardsAsync()");
    }

    @Override
    public void listAdsAsync(final OnResultListener<List<Ad>> resultListener) {
        db.collection(DB_COLLECTION_NAME)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<Ad> cards = new ArrayList<>();

                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            cards.add(document.toObject(Ad.class));
                        }

                        resultListener.onResult(cards);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(LOG_TAG, "Failed to retrieve cards.", e);
                    }
                });
    }

    @Override
    public void addAd(final Ad ad) {
        saveAd(ad);
    }

    @Override
    public void updateAd(Ad ad) {
        saveAd(ad);
    }

    @Override
    public void deleteAd(final Ad ad) {
        db.collection(DB_COLLECTION_NAME)
                .document(Integer.toString(ad.hashCode()))
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(LOG_TAG, "Ad deleted successfully from FireStore: " + ad);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(LOG_TAG, "Failed to delete Ad: " + ad, e);
                    }
                });
    }

    private void saveAd(final Ad ad) {
        db.collection(DB_COLLECTION_NAME)
                .document(Integer.toString(ad.hashCode()))
                .set(ad)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(LOG_TAG, "Ad saved successfully to FireStore: " + ad);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(LOG_TAG, "Failed to save Ad: " + ad, e);
                    }
                });
    }
}
