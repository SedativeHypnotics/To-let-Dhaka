package com.nsu.to_letdhaka.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nsu.to_letdhaka.Domain.Ad;
import com.nsu.to_letdhaka.R;
import com.nsu.to_letdhaka.Repository.AdRepository;
import com.nsu.to_letdhaka.Service.AdService;

import java.util.ArrayList;
import java.util.List;

public class MyAdsActivity extends AppCompatActivity {

    private List<Ad> ads = new ArrayList<>();

    private RecyclerView adsRecyclerView;
    private SharedPreferences sharedPreferences;
    private String filter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_list);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        bindWidgets();
        prepareListView();
    }

    private void bindWidgets() {
        sharedPreferences = getSharedPreferences("shared_preference", Context.MODE_PRIVATE);
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
                ads.clear();
                filter = sharedPreferences.getString("username",null);
                if(filter != null) {
                    for (Ad ad : data) {
                        if (ad.getUser().equals(filter)) {
                            ads.add(ad);
                        }
                    }
                }
                else{
                    ads = data;
                }
                MyAdsListAdapter adapter = (MyAdsListAdapter) adsRecyclerView.getAdapter();
                adapter.setAds(ads);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void prepareListView() {
        adsRecyclerView = findViewById(R.id.ad_list);
        adsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adsRecyclerView.setAdapter(new MyAdsListAdapter(ads));
    }

    private class MyAdsListAdapter extends RecyclerView.Adapter<MyAdsListItemViewHolder> {

        private List<Ad> ads;

        MyAdsListAdapter(List<Ad> ads) {
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
        public MyAdsListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MyAdsActivity.this).inflate(R.layout.row_ad, parent, false);

            return new MyAdsListItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdsListItemViewHolder holder, int position) {
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
                    startActivity(new Intent(MyAdsActivity.this, AdDetailsActivity.class).putExtra("ad", ad).putExtra("activity","myad"));
                }
            });
        }
    }


    private class MyAdsListItemViewHolder extends RecyclerView.ViewHolder {

        private TextView user;
        private TextView date;
        private TextView rent;
        private TextView month;
        private TextView roomFor;
        private TextView contactNo;
        private TextView address;

        MyAdsListItemViewHolder(@NonNull View view) {
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

    @Override
    public void onBackPressed() {
        String currentActivity = sharedPreferences.getString("current_activity",null);
        assert currentActivity != null;
        if(currentActivity.equals("login")){
            startActivity(new Intent(MyAdsActivity.this,MainActivity.class));
        }
        else{
            finish();
        }
    }
}
