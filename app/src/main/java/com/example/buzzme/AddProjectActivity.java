package com.example.buzzme;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import yuku.ambilwarna.AmbilWarnaDialog;

/**
 * Created by Melleoann on 22.03.2018.
 */

public class AddProjectActivity extends AppCompatActivity {
    Button btn;
    int projectColor;
    private DatabaseReference mDatabase;
    private EditText txtProjectName;
    private FirebaseAuth firebaseAuth;
    private ProgressBar loadingBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        projectColor = ContextCompat.getColor(AddProjectActivity.this, R.color.colorPrimary);
        btn = (Button) findViewById(R.id.button_color);
        btn.setBackgroundColor(projectColor);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPickerDialog(false);
            }
        });
        txtProjectName = findViewById(R.id.name_new_project);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        firebaseAuth= FirebaseAuth.getInstance();
        loadingBar = (ProgressBar)findViewById(R.id.prbarAddProject);
        loadingBar.setVisibility(View.GONE);
        setupUI(findViewById(R.id.addProjectLayout));

    }
    private void openColorPickerDialog(boolean alphaSupport) {

        AmbilWarnaDialog colorPickerDialog = new AmbilWarnaDialog(AddProjectActivity.this, projectColor, alphaSupport, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog colorPickerDialog, int color) {
                projectColor = color;
                btn.setBackgroundColor(color);
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }
        });
        colorPickerDialog.show();
    }

    public void btnCancel_Click (View v) {
        AlertDialog.Builder cancelAddProjekt = new AlertDialog.Builder(AddProjectActivity.this);
        cancelAddProjekt.setMessage("Willst du das Anlegen dieses Projektes wirklich abbrechen?");
        cancelAddProjekt.setCancelable(true);

        cancelAddProjekt.setPositiveButton(
                "JA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent i = new Intent(AddProjectActivity.this, ActiveActivity.class);
                        startActivity(i);
                        finish();
                    }
                });

        cancelAddProjekt.setNegativeButton(
                "NEIN",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = cancelAddProjekt.create();
        alert11.show();

    }

    public void btnSave_Click (View v) {
    saveProject();
    }

    public void saveProject(){
        loadingBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        String projectName = txtProjectName.getText().toString().trim();
        Project project = new Project(projectName, projectColor);
        FirebaseUser user = firebaseAuth.getCurrentUser();

        String id = FirebaseDatabase.getInstance().getReference().child(user.getUid()).push().getKey();
        project.setProjectId(id);
        FirebaseDatabase.getInstance().getReference().child(user.getUid()).child(id).setValue(project);

        Toast.makeText(this, "Projekt erstellt",Toast.LENGTH_LONG).show();
        startActivity(new Intent(AddProjectActivity.this, ActiveActivity.class));
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
                    hideSoftKeyboard(AddProjectActivity.this);
                    return false;
                }
            });
        }
        loadingBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Projekterstellung abbrechen")
                .setMessage("Bist du sicher, dass du das Erstellen des Projektes abbrechen möchtest?")
                .setPositiveButton("Ja", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(AddProjectActivity.this, ActiveActivity.class);
                        startActivity(i);
                        finish();
                    }

                })
                .setNegativeButton("Nein", null)
                .show();
    }

}
