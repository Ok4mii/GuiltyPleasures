package com.example.guiltypleasures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Database extends AppCompatActivity {

    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        toolbar = getSupportActionBar();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.database);
        bottomNav.setOnNavigationItemSelectedListener(NavigationListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener NavigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId())
                    {
                        case R.id.homepage:
                            startActivity(new Intent(Database.this, HomeScreen.class));
                            break;
                        case R.id.database:
                            startActivity(new Intent(Database.this, Database.class));
                            break;
                        case R.id.list:
                            startActivity(new Intent(Database.this, List.class));
                            break;
                        case R.id.profile:
                            startActivity(new Intent(Database.this, UserProfile.class));
                            break;
                    }

                    return true;
                }
            };
}