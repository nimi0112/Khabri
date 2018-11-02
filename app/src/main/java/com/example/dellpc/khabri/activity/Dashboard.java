package com.example.dellpc.khabri.activity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.dellpc.khabri.CustomViewPager;
import com.example.dellpc.khabri.R;
import com.example.dellpc.khabri.ViewPagerAdapter;
import com.example.dellpc.khabri.fragment.BollywoodFragment;
import com.example.dellpc.khabri.fragment.CommonFragment;
import com.example.dellpc.khabri.fragment.HomeFragment;
import com.example.dellpc.khabri.fragment.TechnologyFragment;
import com.example.dellpc.khabri.fragment.TrendingFragment;
import com.example.dellpc.khabri.model.NewsApiModel;
import com.example.dellpc.khabri.utils.Preference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnFragmentInteractionListener{


    private DrawerLayout drawer;
    private CustomViewPager viewPager;
    private FrameLayout content_frame;
    private Preference mPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BindViews();

         viewPager =  findViewById(R.id.viewpager1);
        content_frame=findViewById(R.id.content_frame);
        ViewPagerAdapter adapter = new ViewPagerAdapter(Dashboard.this.getSupportFragmentManager());
        adapter.addFragment(new  HomeFragment(), "Home");
        adapter.addFragment(new TrendingFragment(), "Trending");
        adapter.addFragment(new TechnologyFragment(), "Technology");
        adapter.addFragment(new BollywoodFragment(), "Bollywood");
        viewPager.setAdapter(adapter);

        viewPager.setOffscreenPageLimit(5);
        mPref=new Preference(Dashboard.this);
    }

    private void BindViews() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        drawer=findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        BottomNavigationView navigation = findViewById(R.id.navigation);


        navigationView.setNavigationItemSelectedListener(this);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (viewPager.getVisibility()==View.GONE){
                        viewPager.setVisibility(View.VISIBLE);
                        content_frame.setVisibility(View.GONE);
                    }
                    viewPager.setCurrentItem(0);

                    return true;
                case R.id.navigation_trending:
                    if (viewPager.getVisibility()==View.GONE){
                        viewPager.setVisibility(View.VISIBLE);
                        content_frame.setVisibility(View.GONE);
                    }
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_technology:
                    if (viewPager.getVisibility()==View.GONE){
                        viewPager.setVisibility(View.VISIBLE);
                        content_frame.setVisibility(View.GONE);
                    }
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_bollywood:
                    if (viewPager.getVisibility()==View.GONE){
                        viewPager.setVisibility(View.VISIBLE);
                        content_frame.setVisibility(View.GONE);
                    }
                    viewPager.setCurrentItem(3);


                    return true;
            }
            return false;
        }
    };


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            startActivity(new Intent(this,WebviewActivity.class).putExtra("urltoload","https://github.com/nimi0112/Khabrihttps://github.com/nimi0112/Khabri").putExtra("title","nimi0112"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_local) {
            try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = null;

                addresses = geocoder.getFromLocation(Double.parseDouble(mPref.getLatitude()), Double.parseDouble(mPref.getLongitude()), 1);

                String stateName;
                if (addresses.size() > 0){
                    stateName = addresses.get(0).getAdminArea();
                    Log.e("cityName",stateName+"nul");
                } else {
                    stateName="india";
                }

            loadCommonFragment(stateName);
            } catch (IOException e) {
                e.printStackTrace();
                loadCommonFragment("india");
            }

        } else if (id == R.id.nav_latest) {
            loadCommonFragment("latest");

        } else if (id == R.id.nav_gadgets) {
            loadCommonFragment("gadgets");

        } else if (id == R.id.nav_entertainment) {
            loadCommonFragment("entertainment");

        } else if (id == R.id.nav_india) {
            loadCommonFragment("india");

        } else if (id == R.id.nav_cricket) {
            loadCommonFragment("cricket");

        }else if (id == R.id.nav_football) {
            loadCommonFragment("football");

        }else if (id == R.id.nav_auto) {
            loadCommonFragment("automobiles");

        }else if (id == R.id.nav_science) {
            loadCommonFragment("science");

        }else if (id == R.id.nav_trave) {
            loadCommonFragment("travel");

        }else if (id == R.id.nav_food) {
            loadCommonFragment("food");

        }else if (id == R.id.nav_disease) {
            loadCommonFragment("diseases");
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    void loadCommonFragment(String query){

        if (viewPager.getVisibility()==View.VISIBLE){
            viewPager.setVisibility(View.GONE);
            content_frame.setVisibility(View.VISIBLE);
        }
        Fragment fragment=new CommonFragment();
        Bundle args = new Bundle();
        args.putString("topic",query);
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(List<NewsApiModel> modelLis) {

        if (viewPager.getVisibility()==View.VISIBLE){
            viewPager.setVisibility(View.GONE);
            content_frame.setVisibility(View.VISIBLE);
        }

        ArrayList<NewsApiModel> listwa= new ArrayList<>(modelLis.size());
        listwa.addAll(modelLis);
        Fragment fragment=new CommonFragment();
        Bundle args = new Bundle();
        args.putSerializable("NewsApiModel",listwa);
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
