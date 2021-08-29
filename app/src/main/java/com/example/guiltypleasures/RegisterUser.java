package com.example.guiltypleasures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private ImageView banner;
    private TextView RegisterUser;
    private EditText editTextName, editTextUsername, editTextEmail, editTextPassword;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        banner = findViewById(R.id.logo3);
        banner.setOnClickListener(this);

        RegisterUser = findViewById(R.id.registerbutton);
        RegisterUser.setOnClickListener(this);

        editTextName = findViewById(R.id.createRealName);
        editTextUsername = findViewById(R.id.createUserName);
        editTextEmail = findViewById(R.id.createEmail);
        editTextPassword = findViewById(R.id.createpassword);

        progressBar = findViewById(R.id.progressBar2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logo3:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerbutton:
                registeruser();
                break;
        }

    }

    private void registeruser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();
        String defaultprofilepic = "https://firebasestorage.googleapis.com/v0/b/guilty-pleasures-b678a.appspot.com/o/images%2F25d154e9-8e98-446b-a265-6901760c3e94?alt=media&token=5cdce5a4-cb81-4232-a722-3cf2c34d932c";

        if(name.isEmpty()){
            editTextName.setError("Give me a name yo");
            editTextName.requestFocus();
            return;
        }
        if(username.isEmpty()){
            editTextUsername.setError("Give me a username yo");
            editTextUsername.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editTextEmail.setError("Give me an email yo");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("that ain't a proper email yo");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Give me a password yo");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editTextPassword.setError("Gotta be more than 6 characters yo");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(task -> {

                if(task.isSuccessful()) {
                    User user = new User(name, username, email, defaultprofilepic);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(task1 -> {

                                if (task1.isSuccessful()) {
                                    Toast.makeText(RegisterUser.this, "Account Created!", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    finish();
                                    //back to login!
                                } else {
                                    Toast.makeText(RegisterUser.this, "Register Failed. Please try again.", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility((View.GONE));
                                }

                            });

                    }else{
                    Toast.makeText(RegisterUser.this, "Registration Failed.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility((View.GONE));
                }
            });
    }
}