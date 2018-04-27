package com.example.buzzme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText txtEmailAddress;
    private EditText  txtPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressBar loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        txtEmailAddress= findViewById(R.id.txtEmailRegistration);
        txtPassword= findViewById(R.id.txtPasswordRegistration);
        firebaseAuth= FirebaseAuth.getInstance();
        loadingBar=findViewById(R.id.prbarRegistration);
        loadingBar.setVisibility(View.GONE);
        setupUI(findViewById(R.id.registration_layout));
    }

    public void btnRegistrationUser_Click (View v ) {

        if (!txtPassword.getText().toString().isEmpty() && !txtEmailAddress.getText().toString().isEmpty()) {
            loadingBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            (firebaseAuth.createUserWithEmailAndPassword(txtEmailAddress.getText().toString(), txtPassword.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                        startActivity(i);

                    } else {
                        Log.e("ERROR", task.getException().toString());
                        Toast.makeText(RegistrationActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                    loadingBar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                }
            });
        }
        else{
            Toast.makeText(RegistrationActivity.this, "Bitte Email Adresse und Passwort angeben", Toast.LENGTH_LONG).show();
        }
    }
    public void onBackPressed() {

        Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(i);
        finish();

    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(RegistrationActivity.this);
                    return false;
                }
            });
        }
        loadingBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

}
