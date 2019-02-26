
package com.example.ian.maptest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {


    FirebaseUser User;
    FirebaseUser firebaseUser;
    FirebaseAuth auth;

    DatabaseReference reference;
    DatabaseReference databaseReference;

    float CurrentLat;
    float CurrentLong;
    float destLat = 0f;
    float destLong;

    LatLng LastLocation;
    Marker mrkr;

    GoogleMap map;
    LocationManager locationManager;

    PlaceAutocompleteFragment placeAutoComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        User = FirebaseAuth.getInstance().getCurrentUser();
        if (User == null) {
            Intent myintent = new Intent(MapsActivity.this, UserLoginSignup.class);
            startActivity(myintent);
            finish();
        } else {


            AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(Place.TYPE_COUNTRY)
                    .setCountry("ie")
                    .build();
            placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete);
            placeAutoComplete.setFilter(autocompleteFilter);
            placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {

                    Log.d("Maps", "Place selected: " + place.getName());
                    Toast.makeText(MapsActivity.this, place.getName() + " was selected", Toast.LENGTH_SHORT).show();


                    LastLocation = place.getLatLng();

                    Marker mrkr;
                    MarkerOptions EndMarker = new MarkerOptions()
                            .position(LastLocation);
                    mrkr = map.addMarker(EndMarker);
                    mrkr.setPosition(LastLocation);
                    mrkr.setTitle("Your Destination");


                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(LastLocation, 17.0f));

                    destLat = (float) LastLocation.latitude;
                    destLong = (float) LastLocation.longitude;
                }

                @Override
                public void onError(Status status) {
                    Log.d("Maps", "An error occurred: " + status);
                    Toast.makeText(MapsActivity.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
                }
            });

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);


            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1, this);


        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;


    }

    @Override
    public void onLocationChanged(Location Currentlocation)
    {

        LatLng currentLocation = new LatLng(Currentlocation.getLatitude(), Currentlocation.getLongitude());

        MarkerOptions Current = new MarkerOptions();
        Current.position(currentLocation);
        Current.title("You're here");

        map.addMarker(Current);

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 17.0f));

        CurrentLat = (float)Currentlocation.getLatitude();
        CurrentLong = (float)Currentlocation.getLongitude();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void open_Requests(View view)
    {

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");


        final String IDkey = User.getUid();

        final String[] Type = new String[1];

        final ProgressDialog dialog;
        dialog = new ProgressDialog(this);




        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(IDkey).child("Type");


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                Type[0] = String.valueOf(dataSnapshot.getValue(String.class));
                //do what you want with the email
                if(Type[0].equals("Driver"))
                {
                    Intent i = new Intent(MapsActivity.this,ViewRequests.class);
                    i.putExtra("IDkey", IDkey);
                    startActivity(i);
                }
                else if(Type[0].equals("User"))
                {
                    if(destLat == 0f)
                    {
                        Toast.makeText(MapsActivity.this, "Please Select a destination", Toast.LENGTH_SHORT).show();
                    }
                    else
                        {
                        Intent i = new Intent(MapsActivity.this, RequestTaxi.class);
                        i.putExtra("IDkey", IDkey);
                        i.putExtra("CurrentLat", CurrentLat);
                        i.putExtra("CurrentLong", CurrentLong);
                        i.putExtra("destLat", destLat);
                        i.putExtra("destLong", destLong);
                        startActivity(i);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}