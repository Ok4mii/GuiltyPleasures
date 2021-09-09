package com.example.guiltypleasures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Friends extends AppCompatActivity {

    TextView FindFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        FindFriends = findViewById(R.id.SearchUsers);

        FindFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Friends.this, "The button was clicked", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Friends.this, UserSearch.class));
                Friends.this.finish();
            }
        });
    }
}