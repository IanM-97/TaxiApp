package com.example.ian.maptest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverCarSignup extends AppCompatActivity {

DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Cars");

String Make, Model, Reg;

String IDkey;

FirebaseAuth.AuthStateListener mAuthListener;

@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_driver_car_signup);

}

public void open_Confirm(View view) {

mAuthListener = new FirebaseAuth.AuthStateListener() {
public static final String TAG = "";

@Override
public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
FirebaseUser user = firebaseAuth.getCurrentUser();
if (user != null) {
// User is signed in
IDkey = firebaseAuth.getUid();
} else {
// User is signed out
Toast.makeText(DriverCarSignup.this, "User not signed in", Toast.LENGTH_SHORT).show();
}

Bundle extras = getIntent().getExtras();

assert extras != null;
//IDkey = user.getUid();


Make = findViewById(R.id.Make).toString();
Model = findViewById(R.id.Model).toString();
Reg = findViewById(R.id.Registration).toString();

Car car = new Car();

car.CarMake = Make;
car.CarModel = Model;
car.Registration = Reg;

assert IDkey != null;
ref.child(IDkey).setValue(car);


}
};
Intent i = new Intent(DriverCarSignup.this, MapsActivity.class);
startActivity(i);
}

public void open_back(View view) {
Intent i = new Intent(DriverCarSignup.this, UserSignup.class);
startActivity(i);
}
}
