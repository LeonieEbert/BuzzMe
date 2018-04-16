package com.example.buzzme;

import android.content.Intent;
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

public class EMailLoginActivity extends AppCompatActivity {
    private EditText txtEmailAddress;
    private EditText txtPwd;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);
        txtEmailAddress = findViewById(R.id.txtEmailLogin);
        txtPwd = findViewById(R.id.txtPasswordLogin);
        firebaseAuth= FirebaseAuth.getInstance();
    }

    public void btnUserLogin_Click(View v){
        (firebaseAuth.signInWithEmailAndPassword(txtEmailAddress.getText().toString(),txtPwd.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

               if(task.isSuccessful()){
                   Toast.makeText(EMailLoginActivity.this, "Login succesful", Toast.LENGTH_LONG).show();
                   Intent i = new Intent(EMailLoginActivity.this, MainActivity.class);
                   startActivity(i);
               }
               else {
                   Log.e("Error",task.getException().toString());
                   Toast.makeText(EMailLoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
               }
            }
        });

    }
}
