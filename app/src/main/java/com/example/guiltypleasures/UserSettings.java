package com.example.guiltypleasures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserSettings extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private TextView changeEmail;
    private TextView changeUserName;
    private CheckBox switchChildMode;
    private CheckBox switchLightMode;
    private CheckBox switchProfileComments;
    private ActionBar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

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
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else{
                mEditor.putString(getString(R.string.LightMode), "false");
                mEditor.commit();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
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
                            break;
                        case R.id.database:
                            startActivity(new Intent(UserSettings.this, Database.class));
                            break;
                        case R.id.list:
                            startActivity(new Intent(UserSettings.this, List.class));
                            break;
                        case R.id.profile:
                            startActivity(new Intent(UserSettings.this, UserProfile.class));
                            break;
                    }

                    return true;
                }
            };
}