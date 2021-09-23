package com.example.guiltypleasures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserSearch extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    userAdapter adapter;
    ArrayList<User> list;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);

        toolbar = getSupportActionBar();
        recyclerView = findViewById(R.id.userList);
        database = FirebaseDatabase.getInstance().getReference("Users");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new userAdapter(this, list);
        recyclerView.setAdapter(adapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    list.add(user);

                }
                adapter.notifyDataSetChanged();
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
                            startActivity(new Intent(UserSearch.this, HomeScreen.class));
                            UserSearch.this.finish();
                            break;
                        case R.id.database:
                            startActivity(new Intent(UserSearch.this, Database.class));
                            UserSearch.this.finish();
                            break;
                        case R.id.list:
                            startActivity(new Intent(UserSearch.this, List.class));
                            UserSearch.this.finish();
                            break;
                        case R.id.profile:
                            startActivity(new Intent(UserSearch.this, UserProfile.class));
                            UserSearch.this.finish();
                            break;
                    }

                    return true;
                }
            };
}