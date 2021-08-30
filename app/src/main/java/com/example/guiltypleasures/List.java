package com.example.guiltypleasures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class List extends AppCompatActivity {

    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        toolbar = getSupportActionBar();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.list);
        bottomNav.setOnNavigationItemSelectedListener(NavigationListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener NavigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId())
                    {
                        case R.id.homepage:
                            startActivity(new Intent(List.this, HomeScreen.class));
                            break;
                        case R.id.database:
                            startActivity(new Intent(List.this, Database.class));
                            break;
                        case R.id.list:
                            startActivity(new Intent(List.this, List.class));
                            break;
                        case R.id.profile:
                            startActivity(new Intent(List.this, UserProfile.class));
                            break;
                    }

                    return true;
                }
            };
}