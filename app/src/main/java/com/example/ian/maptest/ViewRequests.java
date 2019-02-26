package com.example.ian.maptest;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class ViewRequests extends AppCompatActivity
{

    String IDkey;
    String DriverIDkey;
    Trip trip = new Trip();

    FirebaseUser user;

    String CurrentLat;
    String CurrentLong;

    String destLat;
    String destLong;

    DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Trips");
    DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("In Progress Trips");


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requests);

        final Geocoder geocoder;
        geocoder = new Geocoder(this, Locale.getDefault());

        final TextView IDTextB = findViewById(R.id.KeyValue);
        final TextView CurrentAddressB = findViewById(R.id.CurrentAddressBox);
        final TextView DestAddressB = findViewById(R.id.DestinationTextBox);


        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren())
                {
                    IDkey = objSnapshot.getKey();


                    user = FirebaseAuth.getInstance().getCurrentUser();

                    DriverIDkey = user.getUid();

                    CurrentLat = objSnapshot.child("CurrentLatitude").getValue().toString();
                    CurrentLong = objSnapshot.child("CurrentLongitude").getValue().toString();

                    destLat = objSnapshot.child("DestLatitude").getValue().toString();
                    destLong = objSnapshot.child("DestLongitude").getValue().toString();


                    //GETTING ADDRESSES

                    Float CurrLat = Float.valueOf(CurrentLat);
                    Float CurrLong = Float.valueOf(CurrentLong);

                    Float DestLat = Float.valueOf(destLat);
                    Float DestLong = Float.valueOf(destLong);

                    //CURRENT
                    trip.CurrentLatitude = Float.valueOf(CurrentLat);
                    trip.CurrentLongitude = Float.valueOf(CurrentLong);
                    //DEST
                    trip.DestLongitude = Float.valueOf(destLong);
                    trip.DestLatitude = Float.valueOf(destLat);
                    //STATUS
                    trip.Available = false;
                    trip.Completed = false;
                    //PRICE
                    trip.Price = 0.0;
                    trip.Driver = DriverIDkey;


                    List<Address> Current = new List<Address>() {
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

                    try {
                        Current = geocoder.getFromLocation(CurrLat, CurrLong, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String CurrentAddress = Current.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()


                    List<Address> Dest = new List<Address>() {
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

                    try {
                        Current = geocoder.getFromLocation(DestLat, DestLong, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String DestAddress = Current.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

                    //SETTING TEXT BOXES
                    IDTextB.setText(IDkey);
                    CurrentAddressB.setText(CurrentAddress);
                    DestAddressB.setText(DestAddress);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(ViewRequests.this, "An Error occured", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void open_AcceptFare(View view)
    {
        //MOVE FARE DETAILS FROM TRIPS TO ACTIVE TRIPS
        ref2.child(IDkey).setValue(trip);
        ref1.child(IDkey).removeValue();

        Toast.makeText(this, "Fare sent to active fares", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(ViewRequests.this,DriverMap.class);
        i.putExtra("IDkey", IDkey);
        i.putExtra("CurrentLat", CurrentLat);
        i.putExtra("CurrentLong", CurrentLong);
        i.putExtra("DestLat", destLat);
        i.putExtra("DestLong", destLong);
        startActivity(i);

    }

}