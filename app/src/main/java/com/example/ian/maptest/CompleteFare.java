package com.example.ian.maptest;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class CompleteFare extends AppCompatActivity {


    DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("In Progress Trips");
    DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Completed Trips");

    String IDkey;
    String ClientIDKey;
    float CurrentLat;
    float CurrentLong;
    float DestLat;
    float DestLong;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_fare);

        Bundle extras = getIntent().getExtras();

    }

    public void open_Confirmed(View view)
    {

        ref1.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren())
                {

                    String IDkey = objSnapshot.getKey();

                    float CurrentLat = Float.valueOf(String.valueOf(objSnapshot.child("CurrentLatitude").getValue()));
                    CurrentLong = Float.valueOf(String.valueOf(objSnapshot.child("CurrentLongitude").getValue()));

                    DestLat = Float.valueOf(String.valueOf(objSnapshot.child("DestLatitude").getValue()));
                    DestLong = Float.valueOf(String.valueOf(objSnapshot.child("DestLongitude").getValue()));

                    EditText editText = (EditText) findViewById(R.id.editText);

                    String Price = editText.getText().toString();

                    Trip trip = new Trip();
                    trip.DestLatitude = DestLat;
                    trip.DestLongitude = DestLong;
                    trip.CurrentLongitude = CurrentLong;
                    trip.CurrentLatitude = DestLat;
                    trip.Completed = true;
                    trip.Available = false;
                    trip.Driver = IDkey;

                    trip.Price = Double.valueOf(Price);

                    ref2.child(IDkey).setValue(trip);

                    ref1.child(IDkey).removeValue();

                    Intent i = new Intent(CompleteFare.this, MapsActivity.class);
                    startActivity(i);

                    Toast.makeText(CompleteFare.this, "Fare Completed, returning to Map", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {
            }
        });
    }
}
