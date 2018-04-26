package com.example.buzzme;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 26.03.2018.
 * Altered by ATraulsen on 21.04.2018 - inserted query layout and stuff
 */

public class ActiveActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    ActiveProjectAdapter adapter;
    List<Project> projectsList;

  /*  @Override
    int getContentViewId() {
        return R.layout.activity_active;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_active_project;
    }*/

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active);

        firebaseAuth= FirebaseAuth.getInstance();

       BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_active_project:
                        Toast.makeText(ActiveActivity.this, "Action Active Project", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_inactive_project:
                        Toast.makeText(ActiveActivity.this, "Action Inactive Project", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ActiveActivity.this, InactiveActivity.class));
                        finish();
                        break;
                    case R.id.action_overview_project:
                        Toast.makeText(ActiveActivity.this, "Action Overview Project", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ActiveActivity.this, OverviewActivity.class));
                        finish();
                        break;
                }
                return true;
            }
        });

        // Main window-Layout

        recyclerView = (RecyclerView) findViewById(R.id.activeRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Query-Bestandteile

        projectsList = new ArrayList<>();
        adapter = new ActiveProjectAdapter(this, projectsList);
        recyclerView.setAdapter(adapter);

        DatabaseReference activeRef = FirebaseDatabase.getInstance().getReference();
        Query activeQuery = activeRef.child(firebaseAuth.getCurrentUser().getUid()).orderByChild("projectStatus").equalTo("aktiv");

        activeQuery.addListenerForSingleValueEvent(valueEventListener);
    }

    // Query
    ValueEventListener valueEventListener = new ValueEventListener() {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            projectsList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot projectSnapshot : dataSnapshot.getChildren()) {
                    Project project = projectSnapshot.getValue(Project.class);
                    projectsList.add(project);
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
      
        startActivity(new Intent(this, AddProjectActivity.class));
        finish();
        return true;
    }



    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("BuzzMe verlassen")
                .setMessage("Bist du sicher, dass du BuzzMe verlassen möchtest?")
                .setPositiveButton("Ja", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                        System.exit(0);
                    }

                })
                .setNegativeButton("Nein", null)
                .show();

}

    }
