package com.example.buzzme;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.buzzme.Utils.UIUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import yuku.ambilwarna.AmbilWarnaDialog;

/**
 * Created by Melleoann on 22.03.2018.
 */

public class AddProjectActivity extends AppCompatActivity {
    private Button btnColor;
    private int projectColor;
    private EditText txtProjectName;
    private ProgressBar loadingBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        projectColor = ContextCompat.getColor(AddProjectActivity.this, R.color.colorPrimary);
        btnColor = (Button) findViewById(R.id.button_color);
        btnColor.setBackgroundColor(projectColor);
        btnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPickerDialog(false);
            }
        });
        txtProjectName = findViewById(R.id.name_new_project);
        loadingBar = (ProgressBar) findViewById(R.id.prbarAddProject);
        loadingBar.setVisibility(View.GONE);
        new UIUtil(this).setupUI(findViewById(R.id.add_project_layout));
    }

    private void openColorPickerDialog(boolean alphaSupport) {
        AmbilWarnaDialog colorPickerDialog = new AmbilWarnaDialog(AddProjectActivity.this, projectColor, alphaSupport, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog colorPickerDialog, int color) {
                projectColor = color;
                btnColor.setBackgroundColor(color);
            }
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }
        });
        colorPickerDialog.show();
    }

    public void btnCancel_Click(View v) {
        showAlertDialogCanelAddProject();
    }

    public void btnSave_Click(View v) {
        saveProject();
    }

    public void saveProject() {
        if (!txtProjectName.getText().toString().isEmpty()) {
            loadingBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            String projectName = txtProjectName.getText().toString().trim();
            Project project = new Project(projectName, projectColor);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            String id = FirebaseDatabase.getInstance().getReference().child(user.getUid()).push().getKey();
            project.setProjectId(id);
            FirebaseDatabase.getInstance().getReference().child(user.getUid()).child(id).setValue(project);

            Toast.makeText(this, "Projekt erstellt", Toast.LENGTH_LONG).show();
            startActivity(new Intent(AddProjectActivity.this, ActiveActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Bitte den Namen des Projektes angeben", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        showAlertDialogCanelAddProject();
    }

    public AlertDialog showAlertDialogCanelAddProject(){
        return new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Projekterstellung abbrechen")
                .setMessage("Bist du sicher, dass du das Erstellen des Projektes abbrechen möchtest?")
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
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
