package com.example.buzzme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by User on 26.03.2018.
 */

public class ActiveActivity extends AppCompatActivity {
    private FirebaseDatabase buzzbase = FirebaseDatabase.getInstance(); // Verbindung aufbauen über json-Datei
    private DatabaseReference buzzdataref = buzzbase.getReference();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        final TextView showFirebaseData = (TextView) findViewById(R.id.showFirebaseData);

        buzzdataref.addValueEventListener(new ValueEventListener() {
            @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               /* String textupdate = dataSnapshot.getValue(String.class);
                showFirebaseData.setText(textupdate);*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.action_active_project:
                        Toast.makeText(ActiveActivity.this, "Action Active Project", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_inactive_project:
                        Toast.makeText(ActiveActivity.this, "Action Inactive Project", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ActiveActivity.this, InactiveActivity.class));
                        break;
                    case R.id.action_overview_project:
                        Toast.makeText(ActiveActivity.this, "Action Overview Project", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ActiveActivity.this, OverviewActivity.class));
                        break;
                }
                return true;
            }
        });
    }

    public void btnAddProject_Click(View v ){

        Intent i = new Intent(ActiveActivity.this, AddProjectActivity.class);
        startActivity(i);
    }


}
