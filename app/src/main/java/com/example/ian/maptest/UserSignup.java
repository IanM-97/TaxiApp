package com.example.ian.maptest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;


public class UserSignup extends AppCompatActivity {

    EditText e1_name, e2_email, e3_password;
    Spinner Type;

    String[] Types = new String[]{"Select Account type","User", "Driver"};


    FirebaseAuth auth;
    ProgressDialog dialog;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        e1_name = findViewById(R.id.e1_Name);
        e2_email = findViewById(R.id.e2_Email);
        e3_password = findViewById(R.id.e3_Password);
        Type = findViewById(R.id.Types);
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
    }

    public void open_UserSignup(View v) {
        dialog.setMessage("Registering. Please Wait.");
        dialog.show();

        final String name = e1_name.getText().toString();
        final String email = e2_email.getText().toString();
        final String password = e3_password.getText().toString();
        final String type = Type.getSelectedItem().toString();


        if(password.length() < 6)
        {
            Toast.makeText(this, "Please enter a password with 6 or more characters", Toast.LENGTH_SHORT).show();
        }
        else {
            if (type.equals("Select an Account type")) {
                Toast.makeText(this, "Please Select an account type", Toast.LENGTH_SHORT).show();
            } else {
                if (name.equals("") || email.equals("") || password.equals("")) {
                    dialog.hide();
                    Toast.makeText(getApplicationContext(), "Fields cannot be blank", Toast.LENGTH_SHORT).show();
                } else {
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        dialog.hide();
                                        Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();


                                        //INSERTING USER DETAILS INTO DATABASE
                                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
                                        User New_user = new User(name, email, password, type);
                                        final FirebaseUser firebaseUser = auth.getCurrentUser();

                                        assert firebaseUser != null;
                                        databaseReference.child(firebaseUser.getUid()).setValue(New_user)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        String IDkey = firebaseUser.getUid();
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(getApplicationContext(), "User data saved", Toast.LENGTH_LONG).show();
                                                            if (type.equals("User")) {
                                                                Intent myIntent = new Intent(UserSignup.this, MapsActivity.class);
                                                                myIntent.putExtra("IDkey", IDkey);
                                                                startActivity(myIntent);
                                                            } else if (type.equals("Driver")) {
                                                                Intent myIntent = new Intent(UserSignup.this, DriverCarSignup.class);
                                                                myIntent.putExtra("IDkey", IDkey);
                                                                startActivity(myIntent);
                                                            }
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "Data could not be saved", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        dialog.hide();
                                        Toast.makeText(getApplicationContext(), "User could not be registered", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        }
    }

    public void open_back(View view) {
        Intent i = new Intent(UserSignup.this, UserLoginSignup.class);
    }
}
