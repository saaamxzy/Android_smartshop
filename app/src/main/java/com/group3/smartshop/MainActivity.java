package com.group3.smartshop;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
//import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.group3.smartshop.MapsActivity.BASE_URL;
import static com.group3.smartshop.MapsActivity.YELP_TOKEN;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchFragment.OnFragmentInteractionListener{


    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private FirebaseAuth auth;
    private TextView name, email;
    private ArrayList<Business> recommendations;
    private List<Business> businesses;

    public final static String EXTRA_MESSAGE = "group3.CSE110smartshop.MESSAGE";


//    private ArrayList<Business> getRecommendations(List<Business> businesses, int num) {
//        ArrayList<Business> recommendations = new ArrayList<>(num);
//        for (int i = 0; i < num; ++i) {
//            recommendations.add(businesses.get(i));
//        }
//        return recommendations;
//    }

    @Override
    public void onFragmentInteraction(Uri uri){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        nvDrawer = (NavigationView) findViewById(R.id.nav_view);
        nvDrawer.setNavigationItemSelectedListener(this);

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        View header = nvDrawer.getHeaderView(0);
        name = (TextView)header.findViewById(R.id.nav_userName);
        email = (TextView)header.findViewById(R.id.nav_email);
        name.setText(user.getDisplayName());
        email.setText(user.getEmail());

//        //Get recommendations nearby
//
//        LatLng myLl = new LatLng(32.8673,-117.209);
//
//        Gson gson = new GsonBuilder()
//                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
//                .create();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//        YelpApiEndPointInterface apiService = retrofit.create(YelpApiEndPointInterface.class);
//        Call<YelpParser> call =
//                apiService.getBusinesses(YELP_TOKEN, "shop", 32.8672972, -117.209346);
//        call.enqueue(new Callback<YelpParser>() {
//            @Override
//            public void onResponse(Call<YelpParser> call, Response<YelpParser> response) {
//                if (response.body() == null) {
//                    System.out.println("body is null");
//                }
//                businesses = response.body().getBusinesses();
//                Collections.sort(businesses, new BusinessComparator());
//                Collections.reverse(businesses);
//                recommendations = getRecommendations(businesses, 4);
//
//
//                for (Business b : recommendations) {
//                    System.out.println(b.getName());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<YelpParser> call, Throwable t) {
//                System.out.println("businesses not grabbed");
//                System.out.println(t.toString());
//            }
//
//        });

    }

    public void searchNearby(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        if(!message.isEmpty()) {

            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra(EXTRA_MESSAGE, message);

            startActivity(intent);
        }
    }

    public void searchOnline (View view){
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        if (!message.isEmpty()) {
            Intent intent = new Intent(this, OnlineSearchActivity.class);
            intent.putExtra(EXTRA_MESSAGE, message);

            startActivity(intent);
        }
    }

    public void profileButton (View view){

        Intent intent = new Intent (this, myProfileActivity.class);
        startActivity(intent);
    }

    public void recButton (View view){

        Intent intent = new Intent (this, Recommendation.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            return true;
        }

//        // The action bar home/up action should open or close the drawer.
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                mDrawer.openDrawer(GravityCompat.START);
//                return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.search) {
            Fragment fragment = new SearchFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        } else if (id == R.id.recommendations) {
//            Fragment fragment = new SearchFragment();
//            FragmentManager fragmentManager = getFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.recommend_frame, fragment).commit();
        }else if (id == R.id.fav) {

        } else if (id == R.id.nav_profile) {
//            Fragment fragment = new SearchFragment();
//            FragmentManager fragmentManager = getFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.nav_profile, fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//
//    private void setupDrawerContent(NavigationView navigationView) {
//        navigationView.setNavigationItemSelectedListener(
//                new NavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(MenuItem menuItem) {
//                        selectDrawerItem(menuItem);
//                        return true;
//                    }
//                });
//    }
//
//    public void selectDrawerItem(MenuItem menuItem) {
//        // Create a new fragment and specify the fragment to show based on nav item clicked
//        Fragment fragment = null;
//        Class fragmentClass;
//        switch(menuItem.getItemId()) {
//            case R.id.login:
//                fragmentClass = GPlusFragment.class;
//                break;
////            case R.id.home:
////                fragmentClass = SecondFragment.class;
////                break;
////            case R.id.nav_third_fragment:
////                fragmentClass = ThirdFragment.class;
////                break;
//            default:
//                fragmentClass = GPlusFragment.class;
//        }
//
//        try {
//            fragment = (Fragment) fragmentClass.newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Insert the fragment by replacing any existing fragment
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
//
//        // Highlight the selected item has been done by NavigationView
//        menuItem.setChecked(true);
//        // Set action bar title
//        setTitle(menuItem.getTitle());
//        // Close the navigation drawer
//        mDrawer.closeDrawers();
//    }



}
