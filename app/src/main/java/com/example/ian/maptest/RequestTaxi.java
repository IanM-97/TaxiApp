package com.example.ian.maptest;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class RequestTaxi extends AppCompatActivity {

    String ClientIDkey;

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();

        /*TextView DestLat = (TextView) findViewById(R.id.DestLat);
        TextView DestLong = (TextView) findViewById(R.id.destLong);
        TextView currentLat = (TextView) findViewById(R.id.currentLat);
        TextView currentLong = (TextView) findViewById(R.id.currentLong);

        TextView CurrentAdd = (TextView) findViewById(R.id.currentAdd);
        TextView DestAdd = (TextView) findViewById(R.id.DestAdd);*/


        assert extras != null;

        float destLat = extras.getFloat("destLat");
        float destLong = extras.getFloat("destLong");
        float CurrentLat = extras.getFloat("CurrentLat");
        float CurrentLong = extras.getFloat("CurrentLong");
        String IDkey = extras.getString("IDkey");


        /*DestLat.setText(String.valueOf(destLat));
        DestLong.setText(String.valueOf(destLong));
        currentLat.setText(String.valueOf(CurrentLat));
        currentLong.setText(String.valueOf(CurrentLong));*/


        Geocoder geocoder;

        List<Address> addresses2 = new List<Address>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<Address> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] ts) {
                return null;
            }

            @Override
            public boolean add(Address address) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends Address> collection) {
                return false;
            }

            @Override
            public boolean addAll(int i, @NonNull Collection<? extends Address> collection) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Address get(int i) {
                return null;
            }

            @Override
            public Address set(int i, Address address) {
                return null;
            }

            @Override
            public void add(int i, Address address) {

            }

            @Override
            public Address remove(int i) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @NonNull
            @Override
            public ListIterator<Address> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<Address> listIterator(int i) {
                return null;
            }

            @NonNull
            @Override
            public List<Address> subList(int i, int i1) {
                return null;
            }
        };

        List<Address> addresses = new List<Address>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<Address> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] ts) {
                return null;
            }

            @Override
            public boolean add(Address address) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends Address> collection) {
                return false;
            }

            @Override
            public boolean addAll(int i, @NonNull Collection<? extends Address> collection) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Address get(int i) {
                return null;
            }

            @Override
            public Address set(int i, Address address) {
                return null;
            }

            @Override
            public void add(int i, Address address) {

            }

            @Override
            public Address remove(int i) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @NonNull
            @Override
            public ListIterator<Address> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<Address> listIterator(int i) {
                return null;
            }

            @NonNull
            @Override
            public List<Address> subList(int i, int i1) {
                return null;
            }
        };

        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(destLat, destLong, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

        try {
            addresses2 = geocoder.getFromLocation(CurrentLat, CurrentLong, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address1 = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()



       // DestAdd.setText(addresses.get(0).getAddressLine(0));
       // CurrentAdd.setText(addresses2.get(0).getAddressLine(0));

        //DATABASE STUFF
        Boolean Available = true;
        Boolean Completed = false;
        double price = 0;

        Trip trip = new Trip();

        user = FirebaseAuth.getInstance().getCurrentUser();

        ClientIDkey = user.getUid();

        trip.Client = ClientIDkey;
        trip.CurrentLongitude = CurrentLong;
        trip.CurrentLatitude = CurrentLat;
        trip.DestLatitude = destLat;
        trip.DestLongitude = destLong;
        trip.Completed = Completed;
        trip.Available = Available;
        trip.Price = price;

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Trips");

        String TripID = mDatabase.push().getKey();

        mDatabase.child(TripID).setValue(trip);


        Toast.makeText(this, "Your request has been accepted", Toast.LENGTH_LONG).show();

        Intent i = new Intent(RequestTaxi.this, MapsActivity.class);
        startActivity(i);

    }

   /* public void open_back(View view)
    {
        Intent i = new Intent(RequestTaxi.this, MapsActivity.class);
        startActivity(i);
    }*/
}