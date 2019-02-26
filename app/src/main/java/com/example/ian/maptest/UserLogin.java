package com.example.ian.maptest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class UserLogin extends AppCompatActivity {

    EditText Email, Password;
    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

    }

    public void open_login(View view)
    {
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);

        Email = findViewById(R.id.e1_Email);
        Password = findViewById(R.id.e2_password);

        String email = Email.getText().toString();
        String password = Password.getText().toString();

        //email = email.trim();
        //password = password.trim();


        dialog.setMessage("Signing in, please wait");
        dialog.show();

        if(email.equals("") || password.equals(""))
        {
            Toast.makeText(getApplicationContext(),"Fields cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else
        {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                               dialog.hide();
                                Toast.makeText(getApplicationContext(), "Successfully Signed In", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(UserLogin.this,MapsActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else
                            {
                                dialog.hide();
                                Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    public void open_back(View view)
    {
        Intent i = new Intent(UserLogin.this,UserLoginSignup.class);
        startActivity(i);
    }
}
