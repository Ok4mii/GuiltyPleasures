package com.example.guiltypleasures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ForgotPassword extends AppCompatActivity  implements View.OnClickListener {
    private EditText editTextEmail;

    private Button submit;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        submit = (Button) findViewById(R.id.Submit);
        submit.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.email);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Submit:
                ResetLink();
                break;
        }

    }

    //send reset link
    private void ResetLink(){
        //if valid link
        if(EmailCheck()) {
            //send a password reset and return to previous screen
            mAuth.sendPasswordResetEmail(editTextEmail.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPassword.this, "Password Reset Sent", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ForgotPassword.this, "Failure", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    //Checks for valid email
    private boolean EmailCheck() {
        String email = editTextEmail.getText().toString().trim();

        //email empty?
        if (email.isEmpty()) {
            editTextEmail.setError("Gotta give an email yo");
            editTextEmail.requestFocus();
            return false;
        }

        //valid email
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("That's not a valid email yo");
            editTextEmail.requestFocus();
            return false;
        }

        //if all pass
        return  true;
    }
}