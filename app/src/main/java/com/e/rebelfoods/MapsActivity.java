package com.e.rebelfoods;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.e.rebelfoods.model.Address;
import com.e.rebelfoods.model.Geo;
import com.e.rebelfoods.support.Common;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Intent intent = getIntent();
        if (intent!=null){
            address = intent.getParcelableExtra(Common.ADDRESS_OBJ);
            Log.d(TAG, "onCreate: "+address);
            // Add a marker in Sydney and move the camera
        }
        TextView addressTextView = findViewById(R.id.address);
        addressTextView.setText("address: "+address.getStreet()+", "+address.getSuite()+", "+address.getCity()+", "+address.getZipcode());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


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
        Geo geo = address.getGeo();

        LatLng location = new LatLng(Float.parseFloat(geo.getLat()), Float.parseFloat(geo.getLng()));
        Log.d(TAG, "onMapReady: "+geo+"  "+location.latitude+" "+location.longitude);
        mMap.addMarker(new MarkerOptions().position(location).title(address.getCity()));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,8.0f));
    }
}
