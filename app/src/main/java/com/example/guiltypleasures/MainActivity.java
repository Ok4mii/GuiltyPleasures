package com.example.guiltypleasures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String JSON_Parse = "https://api.themoviedb.org/3/movie/upcoming?api_key=8ef07998664a58a01082bc2ab507fcb8";


    private TextView register;
    private TextView forgotten;
    private EditText editTextEmail, editTextPassword;
    private Button login;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (TextView) findViewById(R.id.newUser);
        register.setOnClickListener(this);

        forgotten = (TextView) findViewById(R.id.forgotPass);
        forgotten.setOnClickListener(this);

        login = (Button) findViewById(R.id.logIn);
        login.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.newUser:
                //Brings user to registration
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.logIn:
                //Attempts to log in user
                userLogin();
                break;
            case R.id.forgotPass:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //checks if login info is entered
        if (email.isEmpty()){
            editTextEmail.setError("Gotta give an email yo");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("That's not a valid email yo");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("I don't see a password yo");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextPassword.setError("Passwords are at least 6 characters yo");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    //Check if verified, send registration email if not
                    if(user.isEmailVerified()){
                        //Take to Home Page
                        startActivity(new Intent(MainActivity.this, HomeScreen.class));
                    }else {
                        user.sendEmailVerification();
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Email verification sent, please confirm email", Toast.LENGTH_LONG).show();
                    }

                }
                //login failed
                else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this,"That's not the right info yo", Toast.LENGTH_LONG).show();
                }
            }

        });

    }
}