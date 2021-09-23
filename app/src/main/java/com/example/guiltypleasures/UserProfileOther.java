package com.example.guiltypleasures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileOther extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private ImageView profileimage;
    private TextView lists;
    private TextView reviews;
    private TextView comments;
    private ActionBar toolbar;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_other);

        toolbar = getSupportActionBar();
        profileimage = findViewById(R.id.ProfileImage);
        lists =  findViewById(R.id.lists);
        reviews = findViewById(R.id.userReviews);
        comments = findViewById(R.id.profileComments);
        database = FirebaseDatabase.getInstance().getReference("Users");
        final TextView UserName = findViewById(R.id.Username);
        mPreferences = getSharedPreferences("com.example.Prefs_Guilty_Pleasures", Context.MODE_PRIVATE);

        //set user info
        String userid = mPreferences.getString(getString(R.string.userID), "false");

        database.child(userid).child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username =  snapshot.getValue().toString();
                UserName.setText(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.child(userid).child("profilepicture").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String profilepic =  snapshot.getValue().toString();
                Glide.with(UserProfileOther.this).load(profilepic).into(profileimage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                            startActivity(new Intent(UserProfileOther.this, HomeScreen.class));
                            UserProfileOther.this.finish();
                            break;
                        case R.id.database:
                            startActivity(new Intent(UserProfileOther.this, Database.class));
                            UserProfileOther.this.finish();
                            break;
                        case R.id.list:
                            startActivity(new Intent(UserProfileOther.this, List.class));
                            UserProfileOther.this.finish();
                            break;
                        case R.id.profile:
                            startActivity(new Intent(UserProfileOther.this, UserProfile.class));
                            UserProfileOther.this.finish();
                            break;
                    }

                    return true;
                }
            };
}