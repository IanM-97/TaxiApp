package com.example.ian.maptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class UserLoginSignup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_signup);
    }

    public void open_UserLogin(View view)
    {
        Intent i = new Intent(UserLoginSignup.this, UserLogin.class);
        startActivity(i);
    }

    public void open_UserSignup(View view)
    {
        Intent i = new Intent(UserLoginSignup.this, UserSignup.class);
        startActivity(i);
    }
}
