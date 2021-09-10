package com.example.guiltypleasures;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Movie API Key
    private static String JSON_Parse = "https://api.themoviedb.org/3/movie/upcoming?api_key=8ef07998664a58a01082bc2ab507fcb8";

    //Firebase Variables
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    //User Interface Variables
    private TextView register;
    private TextView forgotten;
    private EditText editTextEmail, editTextPassword;
    private Button login;
    private CheckBox light;

    //Misc
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;


    //creation of Main Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //User Interface Variables Setting
        //that require On Click
        register = findViewById(R.id.newUser);
        register.setOnClickListener(this);

        forgotten = findViewById(R.id.forgotPass);
        forgotten.setOnClickListener(this);

        login = findViewById(R.id.logIn);
        login.setOnClickListener(this);

        //that don't need on Click
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);

        //Light Mode Variables
        mPreferences = getSharedPreferences("com.example.Prefs_Guilty_Pleasures", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
        light = findViewById(R.id.lightSwitch);

        //FireBase Setting
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        //checks whenever light mode is turn on or off
        CheckSharedPrefs();

        light.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (light.isChecked()){
                mEditor.putString(getString(R.string.LightMode), "true");
                mEditor.commit();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else{
                mEditor.putString(getString(R.string.LightMode), "false");
                mEditor.commit();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
    }


    private void CheckSharedPrefs(){
        String LightMode = mPreferences.getString(getString(R.string.LightMode), "false");

        if (LightMode.equals("true")){
            light.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }


    //All OnClick Events
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
                //Bring User to Reset Password Link
                startActivity(new Intent(this, ForgotPassword.class));
                break;
        }
    }


    //Verify information and Log User in
    private void userLogin() {
        //Variables set to User Input in Text Fields
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //checks if Email info is entered Correctly
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

        //checks if Password info is entered Correctly
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

        //attempts to Sign User if Previous Info Passes
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                //create User Instance Variable
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                assert user != null;

                //if Email is verified
                if(user.isEmailVerified()){
                    //Take to Home Page
                    startActivity(new Intent(MainActivity.this, HomeScreen.class));
                }
                //if not
                else {
                    //send Verification Email
                    user.sendEmailVerification();
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Email verification sent, please confirm email", Toast.LENGTH_LONG).show();
                }
            }
            //if attempt fails
            else{
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this,"That's not the right info yo", Toast.LENGTH_LONG).show();
            }
        });
    }
}