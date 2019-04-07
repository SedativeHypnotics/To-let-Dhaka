package com.nsu.to_letdhaka.Activities;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nsu.to_letdhaka.R;
import com.nsu.to_letdhaka.Service.ProfileService;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ImageButton messButton;
    private ImageButton hostelButton;
    private ImageButton flatButton;
    private ImageButton subletButton;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @SuppressWarnings("FieldCanBeLocal")
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout mDrawer;
    @SuppressWarnings("FieldCanBeLocal")
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private FirebaseUser firebaseUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindWidgets();
        bindListeners();
        setupDrawerContent(nvDrawer);
    }

    private void bindListeners() {
        messButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ApplySharedPref")
            @Override
            public void onClick(View view) {
                editor = sharedPreferences.edit();
                editor.putString("filter","mess");
                editor.commit();
                startActivity(new Intent(MainActivity.this,AdListActivity.class));
            }
        });

        hostelButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ApplySharedPref")
            @Override
            public void onClick(View view) {
                editor = sharedPreferences.edit();
                editor.putString("filter","hostel");
                editor.commit();
                startActivity(new Intent(MainActivity.this,AdListActivity.class));
            }
        });

        flatButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ApplySharedPref")
            @Override
            public void onClick(View view) {
                editor = sharedPreferences.edit();
                editor.putString("filter","flat");
                editor.commit();
                startActivity(new Intent(MainActivity.this,AdListActivity.class));
            }
        });

        subletButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ApplySharedPref")
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
    	toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawer = findViewById(R.id.drawer_layout);
        nvDrawer = findViewById(R.id.nvView);
        drawerToggle = new ActionBarDrawerToggle(this, mDrawer,toolbar ,  R.string.drawer_open, R.string.drawer_close) {
	        /** Called when a drawer has settled in a completely closed state. */
	        public void onDrawerClosed(View view) {
	        super.onDrawerClosed(view);
	    } 
	        /** Called when a drawer has settled in a completely open state. */
	        public void onDrawerOpened(View drawerView) {
	        super.onDrawerOpened(drawerView);
	    } 
        };
        //noinspection deprecation
        mDrawer.setDrawerListener(drawerToggle);
	    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true); 
	    drawerToggle.syncState();
        messButton = findViewById(R.id.mess);
        hostelButton = findViewById(R.id.hostel);
        flatButton = findViewById(R.id.flat);
        subletButton = findViewById(R.id.sub_let);
        sharedPreferences = getSharedPreferences("shared_preference",Context.MODE_PRIVATE);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        assert searchManager != null;
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }
    
    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        switch(menuItem.getItemId()) {
            case R.id.profile:
                if(firebaseUser != null) {
                    startActivity(new Intent(MainActivity.this, MyProfileActivity.class).putExtra("email",firebaseUser.getEmail()));
                }
                else{
                    Toast.makeText(MainActivity.this,"You are not logged in!",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.posts:
                ProfileService profileService = new ProfileService();
                profileService.setUserName("example@gmail.com",sharedPreferences);
                startActivity(new Intent(MainActivity.this,MyAdsActivity.class));
//                if(firebaseUser != null){
//                    ProfileService profileService = new ProfileService();
//                    profileService.setUserName(firebaseUser.getEmail(),sharedPreferences);
//                    startActivity(new Intent(MainActivity.this, MyAdsActivity.class));
//                }
//                else{
//
//                    startActivity(new Intent(MainActivity.this,LogInActivity.class));
//                }
                break;
            case R.id.post_ad:
                if(firebaseUser != null){
                    startActivity(new Intent(MainActivity.this, PostAdActivity.class));
                }
                else{
                    startActivity(new Intent(MainActivity.this,LogInActivity.class).putExtra("activity","post_ad"));
                }
                break;
            case R.id.log_out:
                if(firebaseUser != null) {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(MainActivity.this, "log out", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"You are not logged in!",Toast.LENGTH_LONG).show();
                }
                break;
            default:
        }
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
