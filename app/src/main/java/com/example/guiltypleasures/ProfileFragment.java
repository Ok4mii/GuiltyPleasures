package com.example.guiltypleasures;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.util.UUID;

public class ProfileFragment extends Fragment {

    private TextView logout;
    private FirebaseUser user;
    private FirebaseStorage storage;
    private StorageReference storagereference;
    private DatabaseReference reference;
    private String userID;
    private ImageView profileimage;
    public Uri imageuri;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile_fragment,container,false);

        profileimage = v.findViewById(R.id.ProfileImage);
        logout = (Button) v.findViewById(R.id.logout);

        //get user info
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        storage = FirebaseStorage.getInstance();
        storagereference = storage.getReference();

        //change details on page to that of user (may need to add more info)
        final TextView UserName = (TextView) v.findViewById(R.id.Username);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);
                if (userprofile != null){
                    String username = userprofile.username;
                    UserName.setText(username);
                    Glide.with(getActivity()).load(userprofile.profilepicture).into(profileimage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Woah, something went wrong!", Toast.LENGTH_LONG).show();
            }
        });

        //logout user
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        //profile picture
        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseimage();
            }
        });

        return v;
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
        if(requestCode == 1 && resultCode == -1 && data.getData() != null){
            imageuri = data.getData();
            profileimage.setImageURI(imageuri);
            uploadPicture();
        }
    }

    private void uploadPicture() {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setTitle("uploading...");
        pd.show();
        StorageReference imageref = storagereference.child("Users/" + userID + "/ProfilePic");

        imageref.putFile(imageuri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Toast.makeText(getActivity(), "Image uploaded successfully!", Toast.LENGTH_LONG).show();
                        imageref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                reference.child(userID).child("profilepicture").setValue(uri.toString());
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getActivity(), "Image upload failed!", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progresspercent = (100.00 + snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("percentage: " + (int) progresspercent);
                    }
                });
    }
}
