package com.example.guiltypleasures;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UserProfile extends AppCompatActivity {

    private TextView logout;
    private TextView settings;
    private FirebaseUser user;
    private FirebaseStorage storage;
    private StorageReference storagereference;
    private DatabaseReference reference;
    private String userID;
    private ImageView profileimage;
    public Uri imageuri;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        profileimage = findViewById(R.id.ProfileImage);
        logout =  findViewById(R.id.logout);
        settings = findViewById(R.id.usersettings);
        toolbar = getSupportActionBar();

        //get user info
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        storage = FirebaseStorage.getInstance();
        storagereference = storage.getReference();

        //change details on page to that of user (may need to add more info)
        final TextView UserName = findViewById(R.id.Username);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);
                if (userprofile != null){
                    String username = userprofile.username;
                    UserName.setText(username);
                    Glide.with(UserProfile.this).load(userprofile.profilepicture).into(profileimage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this, "Woah, something went wrong!", Toast.LENGTH_LONG).show();
            }
        });

        //logout user
        logout.setOnClickListener(v1 -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(UserProfile.this, MainActivity.class));
        });

        //settings fragment change test
        settings.setOnClickListener(v12 -> {
            Toast.makeText(UserProfile.this, "The button was clicked", Toast.LENGTH_LONG).show();
            startActivity(new Intent(UserProfile.this, UserSettings.class));
        });

        //profile picture
        profileimage.setOnClickListener(v13 -> chooseimage());

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.profile);
        bottomNav.setOnNavigationItemSelectedListener(NavigationListener);

    }

    //choose image
    private void chooseimage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    //deprecated code again, but only method i could find to do this
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == -1) {
            assert data != null;
            if (data.getData() != null) {
                imageuri = data.getData();
                profileimage.setImageURI(imageuri);
                uploadPicture();
            }
        }
    }

    private void uploadPicture() {
        final ProgressDialog pd = new ProgressDialog(UserProfile.this);
        pd.setTitle("uploading...");
        pd.show();
        StorageReference imageref = storagereference.child("Users/" + userID + "/ProfilePic");

        imageref.putFile(imageuri)
                .addOnSuccessListener(taskSnapshot -> {
                    pd.dismiss();
                    Toast.makeText(UserProfile.this, "Image uploaded successfully!", Toast.LENGTH_LONG).show();
                    imageref.getDownloadUrl().addOnSuccessListener(uri -> reference.child(userID).child("profilepicture").setValue(uri.toString()));
                })
                .addOnFailureListener(e -> {
                    pd.dismiss();
                    Toast.makeText(UserProfile.this, "Image upload failed!", Toast.LENGTH_LONG).show();
                })
                .addOnProgressListener(snapshot -> {
                    double progresspercent = (100.00 + snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    pd.setMessage("percentage: " + (int) progresspercent);
                });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener NavigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId())
                    {
                        case R.id.homepage:
                            startActivity(new Intent(UserProfile.this, HomeScreen.class));
                            break;
                        case R.id.database:
                            startActivity(new Intent(UserProfile.this, Database.class));
                            break;
                        case R.id.list:
                            startActivity(new Intent(UserProfile.this, List.class));
                            break;
                        case R.id.profile:
                            startActivity(new Intent(UserProfile.this, UserProfile.class));
                            break;
                    }

                    return true;
                }
            };
}