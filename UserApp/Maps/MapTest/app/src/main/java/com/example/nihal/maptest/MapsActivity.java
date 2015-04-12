package com.example.nihal.maptest;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(12, 77)).title("Marker"));
        String [] cityList= {"Bangalore, Karnataka, India", "Chennai, Tamil Nadu, India"};
        putMarker("Bangalore, Karnataka, India");
        putMarker("Chennai, Tamil Nadu, India");
        putPolyLines(cityList);
    }

    public Address getLatitudeAndLongitudeFromGoogleMapForAddress(String searchedAddress, Address location){

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        try {

            address = coder.getFromLocationName(searchedAddress, 2);
            if (address == null) {
                Log.d("Map Error", "############Address not correct #########");
            }
            location = address.get(0);

            Log.d("Map Error", "Address Latitude : "+ location.getLatitude()+ "Address Longitude : "+ location.getLongitude());
            return location;

        }catch(Exception e){
            Log.d("Map Error", "MY_ERROR : ############Address Not Found");
            return location;
        }
    }

    private void putMarker(String cityName){
        Address location = new Address(new Locale("English"));
        try{
        location = getLatitudeAndLongitudeFromGoogleMapForAddress(cityName, location);
        mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title(cityName));

        }catch(Exception ex) {
            Toast.makeText(getApplicationContext(), (String) "Could not get Latitude and Longitude", Toast.LENGTH_SHORT).show();
        }
    }

    private void putPolyLines(String[] cityList){
        Address location1 = new Address(new Locale("English"));
        Address location2 = new Address(new Locale("English"));
        location1 = getLatitudeAndLongitudeFromGoogleMapForAddress(cityList[0], location1);
        location2 = getLatitudeAndLongitudeFromGoogleMapForAddress(cityList[1], location2);
        Polyline line = mMap.addPolyline(new PolylineOptions().add(new LatLng(location1.getLatitude(), location1.getLongitude()), new LatLng(location2.getLatitude(), location2.getLongitude())).width(5).color(Color.RED));
    }

}
