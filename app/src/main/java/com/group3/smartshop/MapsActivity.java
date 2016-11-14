package com.group3.smartshop;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final int PERMISSIONS_REQUEST_FINE_LOCATION = 1;
    public static final String BASE_URL = "https://api.yelp.com/v3/";
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private double mLat, mLgn;
    private TextView mLatitudeText,mLongitudeText;
    private List<Business> businesses;

    public void getAllBusiness() {

    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAllBusiness();



        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

        } else {
            // Show rationale and request permission.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_FINE_LOCATION);

                // PERMISSIONS_REQUEST_FINE_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

        }
        // To check if the apiclient has been initialized
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                //mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
                //mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
                System.out.println("location info grabbed:");
                System.out.println(String.valueOf(mLastLocation.getLatitude()));
                System.out.println(String.valueOf(mLastLocation.getLongitude()));
                mLat = mLastLocation.getLatitude();
                mLgn = mLastLocation.getLongitude();
            }
        } else {
            // Show rationale and request permission.
        }

    }

    @Override
    public void onConnectionSuspended(int i){

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult){

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng myLl = new LatLng(mLat,mLgn);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
        /*
        Marker ucsdMarker = mMap.addMarker(new MarkerOptions()
                .position(ucsd)
                .title("Our Campus")
                .snippet("2016")
        );*/
        System.out.println("grabbing all businesses");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        YelpApiEndPointInterface apiService = retrofit.create(YelpApiEndPointInterface.class);
        Call<YelpParser> call = apiService.getTest("Bearer z-wkW_0ij8grCGOBsXW2jivBrYrsGThT4FyWcyQYtmcHh7sYTDMfXu_ypDbY0vFWDOZ7uIRZKzxYLTctx8cdkjIlBhBqoaiMKyF2n7quen_6BEG_pBgrnMfwi6YWWHYx");
        call.enqueue(new Callback<YelpParser>() {
            @Override
            public void onResponse(Call<YelpParser> call, Response<YelpParser> response) {
                if (response.body() == null) {
                    System.out.println("body is null");
                }
                businesses = response.body().getBusinesses();
                for (int i = 0; i < businesses.size(); i++) {
                    LatLng ll = new LatLng(businesses.get(i).getCoordinates().getLatitude(),
                            businesses.get(i).getCoordinates().getLongitude());
                    mMap.addMarker(new MarkerOptions()
                            .position(ll)
                            .title(businesses.get(i).
                                    getName()).
                                    snippet(businesses.get(i).
                                            getDistance().intValue() + " meters away from you")
                    );
                }
            }

            @Override
            public void onFailure(Call<YelpParser> call, Throwable t) {
                System.out.println("businesses not grabbed");
                System.out.println(t.toString());
            }
        });





        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLl));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_FINE_LOCATION) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                // Permission was denied. Display an error message.
            }
        }

    }





}
