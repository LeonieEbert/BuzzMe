package com.example.buzzme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import yuku.ambilwarna.AmbilWarnaDialog;

/**
 * Created by User on 22.03.2018.
 */

public class AddProjectActivity extends AppCompatActivity {
    Button btn;
    int defaultColor;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        setTitle("Projekt anlegen");
        defaultColor = ContextCompat.getColor(AddProjectActivity.this, R.color.colorPrimary);
        btn = (Button) findViewById(R.id.button_color);
        btn.setBackgroundColor(defaultColor);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPickerDialog(false);
            }
        });

    }
    private void openColorPickerDialog(boolean alphaSupport) {

        AmbilWarnaDialog colorPickerDialog = new AmbilWarnaDialog(AddProjectActivity.this, defaultColor, alphaSupport, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog colorPickerDialog, int color) {
                defaultColor = color;
                btn.setBackgroundColor(color);
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }
        });
        colorPickerDialog.show();


    }

}
