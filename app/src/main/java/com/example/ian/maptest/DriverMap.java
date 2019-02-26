package com.example.ian.maptest;

import android.content.Intent;
import android.location.Address;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.sql.Driver;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DriverMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("In Progress Trips");

    String IDkey;
    Trip trip = new Trip();

    String CurrentLat;
    String CurrentLong;

    String DestLat;
    String DestLong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren())
                {
                    IDkey = objSnapshot.getKey();

                    CurrentLat =  objSnapshot.child("CurrentLatitude").getValue().toString();
                    CurrentLong = objSnapshot.child("CurrentLongitude").getValue().toString();

                    DestLat = objSnapshot.child("DestLatitude").getValue().toString();
                    DestLong = objSnapshot.child("DestLongitude").getValue().toString();

                    //CURRENT
                    trip.CurrentLatitude = Float.valueOf(CurrentLat);
                    trip.CurrentLongitude = Float.valueOf(CurrentLong);
                    //DEST
                    trip.DestLongitude = Float.valueOf(DestLong);
                    trip.DestLatitude = Float.valueOf(DestLat);
                    //STATUS
                    trip.Available = false;
                    trip.Completed = false;
                    //PRICE
                    trip.Price = 0.0;

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(DriverMap.this, "An Error occured", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren())
                {

                    float CurLat = Float.valueOf(String.valueOf(objSnapshot.child("CurrentLatitude").getValue()));
                    float CurLong = Float.valueOf(String.valueOf(objSnapshot.child("CurrentLongitude").getValue()));

                    float DeLat = Float.valueOf(String.valueOf(objSnapshot.child("DestLatitude").getValue()));
                    float DeLong = Float.valueOf(String.valueOf(objSnapshot.child("DestLongitude").getValue()));

                    LatLng current = new LatLng(CurLat,CurLong);
                    LatLng dest = new LatLng(DeLat,DeLong);

                    MarkerOptions Current = new MarkerOptions();
                    Current.position(current);
                    Current.title("Customer is Currently here");

                    mMap.addMarker(Current);

                    MarkerOptions Dest = new MarkerOptions();
                    Dest.position(dest);
                    Dest.title("Customer wants to go here");

                    mMap.addMarker(Dest);

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 17.0f));

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(DriverMap.this, "An Error occured", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void open_CompleteFare(View view)
    {
        Intent i = new Intent(DriverMap.this,CompleteFare.class);
        i.putExtra("IDkey", IDkey);
        i.putExtra("CurrentLat", CurrentLat);
        i.putExtra("CurrentLong", CurrentLong);
        i.putExtra("DestLat", DestLat);
        i.putExtra("DestLong", DestLong);
        startActivity(i);
    }
}
