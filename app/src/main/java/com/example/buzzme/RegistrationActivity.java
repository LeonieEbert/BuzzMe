package com.example.buzzme;

import android.content.Intent;
import android.content.ServiceConnection;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText txtEmailAddress;
    private EditText  txtPassword;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        txtEmailAddress= findViewById(R.id.txtEmailRegistration);
        txtPassword= findViewById(R.id.txtPasswordRegistration);
        firebaseAuth= FirebaseAuth.getInstance();
    }

    public void btnRegistrationUser_Click (View v ) {

        (firebaseAuth.createUserWithEmailAndPassword(txtEmailAddress.getText().toString(),txtPassword.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
                    Intent i = new Intent (RegistrationActivity.this, EMailLoginActivity.class);
                    startActivity(i);

                }
                else {
                    Log.e("ERROR", task.getException().toString() );
                    Toast.makeText(RegistrationActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
