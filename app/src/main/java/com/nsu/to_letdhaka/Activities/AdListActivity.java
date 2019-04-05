package com.nsu.to_letdhaka.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nsu.to_letdhaka.Domain.Ad;
import com.nsu.to_letdhaka.R;
import com.nsu.to_letdhaka.Repository.AdRepository;
import com.nsu.to_letdhaka.Repository.AdServerRepository;
import com.nsu.to_letdhaka.Service.AdService;

import java.util.ArrayList;
import java.util.List;

public class AdListActivity extends AppCompatActivity {
    private List<Ad> ads = new ArrayList<>();

    private RecyclerView adsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_list);
        prepareListView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        retrieveAds();
    }


    @SuppressWarnings("ConstantConditions")
    private void retrieveAds() {

        AdService adService = new AdService();
        adService.listAdsAsync(new AdRepository.OnResultListener<List<Ad>>() {
            @Override
            public void onResult(List<Ad> data) {
                ads = data;
                AdsListAdapter adapter = (AdsListAdapter) adsRecyclerView.getAdapter();
                adapter.setAds(ads);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void prepareListView() {
        adsRecyclerView = findViewById(R.id.ad_list);
        adsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adsRecyclerView.setAdapter(new AdsListAdapter(ads));
    }

    private class AdsListAdapter extends RecyclerView.Adapter<AdsListItemViewHolder> {

        private List<Ad> ads;

        AdsListAdapter(List<Ad> ads) {
            this.ads = ads;
        }

        public void setAds(List<Ad> ads) {
            this.ads = ads;
        }

        @Override
        public int getItemCount() {
            return ads.size();
        }

        @NonNull
        @Override
        public AdsListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(AdListActivity.this).inflate(R.layout.row_ad, parent, false);

            return new AdsListItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AdsListItemViewHolder holder, int position) {
            final Ad ad = ads.get(position);

            holder.user.setText(ad.getUser());
            holder.date.setText(ad.getDate());
            holder.rent.setText(ad.getRent());
            holder.month.setText(ad.getMonth());
            holder.roomFor.setText(ad.getRoomFor());
            holder.contactNo.setText(ad.getContactNo());
            holder.address.setText(ad.getAddress());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(AdListActivity.this, AdDetailsActivity.class).putExtra("ad", ad));
                }
            });
        }
    }


    private class AdsListItemViewHolder extends RecyclerView.ViewHolder {

        private TextView user;
        private TextView date;
        private TextView rent;
        private TextView month;
        private TextView roomFor;
        private TextView contactNo;
        private TextView address;

        AdsListItemViewHolder(@NonNull View view) {
            super(view);

            user = view.findViewById(R.id.user);
            date = view.findViewById(R.id.date);
            rent = view.findViewById(R.id.rent);
            month = view.findViewById(R.id.month);
            roomFor = view.findViewById(R.id.room_for);
            contactNo = view.findViewById(R.id.contact_no);
            address = view.findViewById(R.id.address);
        }
    }
}
