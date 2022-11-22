package com.example.guiltypleasures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UserSettings extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private FirebaseUser user;
    private FirebaseStorage storage;
    private StorageReference storagereference;
    private DatabaseReference reference;
    private String userID;
    private TextView changeEmail;
    private TextView changeUserName;
    private CheckBox switchChildMode;
    private CheckBox switchLightMode;
    private CheckBox switchProfileComments;
    private EditText textChangeUserName;
    private EditText textChangeEmail;
    private ActionBar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        //user info
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        storage = FirebaseStorage.getInstance();
        storagereference = storage.getReference();

        textChangeUserName = findViewById((R.id.editTextUsername));
        textChangeEmail = findViewById(R.id.editTextEmail);
        switchChildMode = findViewById(R.id.boxChildMode);
        switchLightMode = findViewById(R.id.boxLightMode);
        switchProfileComments = findViewById(R.id.boxProfileComments);
        changeUserName = findViewById(R.id.buttonChangeUsername);
        changeEmail = findViewById(R.id.buttonChangeEmail);
        toolbar = getSupportActionBar();

        mPreferences = UserSettings.this.getSharedPreferences("com.example.Prefs_Guilty_Pleasures", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();

        CheckSharedPrefs();

        switchChildMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (switchChildMode.isChecked()){
                mEditor.putString(getString(R.string.ChildMode), "true");
                mEditor.commit();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else{
                mEditor.putString(getString(R.string.ChildMode), "false");
                mEditor.commit();
            }
        });

        switchProfileComments.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (switchProfileComments.isChecked()){
                mEditor.putString(getString(R.string.ProfileComments), "true");
                mEditor.commit();
            }else{
                mEditor.putString(getString(R.string.ProfileComments), "false");
                mEditor.commit();
            }
        });

        switchLightMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (switchLightMode.isChecked()){
                mEditor.putString(getString(R.string.LightMode), "true");
                mEditor.commit();
                //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else{
                mEditor.putString(getString(R.string.LightMode), "false");
                mEditor.commit();
                //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        changeUserName.setOnClickListener(v -> {
            String newusername = textChangeUserName.getText().toString().trim();
            if(newusername.isEmpty()) {
                textChangeUserName.setError("Give me a name yo");
                textChangeUserName.requestFocus();
            }
            else{
                reference.child(userID).child("username").setValue(newusername);
            }
            Toast.makeText(UserSettings.this, "Your username is now " + newusername, Toast.LENGTH_LONG).show();
        });

        changeEmail.setOnClickListener(v -> {
            String email = textChangeEmail.getText().toString().trim();
            if(email.isEmpty()){
                textChangeEmail.setError("Give me an email yo");
                textChangeEmail.requestFocus();
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                textChangeEmail.setError("that ain't a proper email yo");
                textChangeEmail.requestFocus();
            }
            else{
                user.updateEmail(email);
                reference.child(userID).child("email").setValue(email);
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.profile);
        bottomNav.setOnNavigationItemSelectedListener(NavigationListener);
    }
    private void CheckSharedPrefs(){
        String LightMode = mPreferences.getString(getString(R.string.LightMode), "false");
        String ChildMode = mPreferences.getString(getString(R.string.ChildMode), "false");
        String ProfileComments = mPreferences.getString(getString(R.string.ProfileComments), "false");

        if (LightMode.equals("true")){
            switchLightMode.setChecked(true);
        }
        if (ChildMode.equals("true")){
            switchChildMode.setChecked(true);
        }
        if (ProfileComments.equals("true")){
            switchProfileComments.setChecked(true);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener NavigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId())
                    {
                        case R.id.homepage:
                            startActivity(new Intent(UserSettings.this, HomeScreen.class));
                            UserSettings.this.finish();
                            break;
                        case R.id.database:
                            startActivity(new Intent(UserSettings.this, Database.class));
                            UserSettings.this.finish();
                            break;
                        case R.id.list:
                            startActivity(new Intent(UserSettings.this, List.class));
                            UserSettings.this.finish();
                            break;
                        case R.id.profile:
                            startActivity(new Intent(UserSettings.this, UserProfile.class));
                            UserSettings.this.finish();
                            break;
                    }

                    return true;
                }
            };
}