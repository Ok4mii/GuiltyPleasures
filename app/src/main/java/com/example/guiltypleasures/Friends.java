package com.example.guiltypleasures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Friends extends AppCompatActivity {

    TextView FindFriends;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        toolbar = getSupportActionBar();
        FindFriends = findViewById(R.id.SearchUsers);

        FindFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Friends.this, "The button was clicked", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Friends.this, UserSearch.class));
                Friends.this.finish();
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.profile);
        bottomNav.setOnNavigationItemSelectedListener(NavigationListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener NavigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId())
                    {
                        case R.id.homepage:
                            startActivity(new Intent(Friends.this, HomeScreen.class));
                            Friends.this.finish();
                            break;
                        case R.id.database:
                            startActivity(new Intent(Friends.this, Database.class));
                            Friends.this.finish();
                            break;
                        case R.id.list:
                            startActivity(new Intent(Friends.this, List.class));
                            Friends.this.finish();
                            break;
                        case R.id.profile:
                            startActivity(new Intent(Friends.this, UserProfile.class));
                            Friends.this.finish();
                            break;
                    }

                    return true;
                }
            };
}