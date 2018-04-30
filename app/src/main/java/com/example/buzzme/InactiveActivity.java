package com.example.buzzme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
 * Created by User on 22.03.2018.
 */

public class InactiveActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    InactiveProjectAdapter adapter;
    List<Project> projectsList;

    /* @Override
     int getContentViewId() {
         return R.layout.activity_inactive;
     }

     @Override
     int getNavigationMenuItemId() {
         return R.id.action_inactive_project;
     }*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inactive);

        firebaseAuth= FirebaseAuth.getInstance();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.action_active_project:
                        startActivity(new Intent(InactiveActivity.this, ActiveActivity.class));
                        finish();
                        break;
                    case R.id.action_inactive_project:
                        break;
                    case R.id.action_overview_project:
                        startActivity(new Intent(InactiveActivity.this, OverviewActivity.class));
                        finish();
                        break;
                }
                return true;
            }
        });

        // Main window-Layout

        recyclerView = (RecyclerView) findViewById(R.id.inactiveRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Query-Bestandteile

        projectsList = new ArrayList<>();
        adapter = new InactiveProjectAdapter(this, projectsList);
        recyclerView.setAdapter(adapter);

        DatabaseReference inactiveRef = FirebaseDatabase.getInstance().getReference();
        Query inactiveQuery = inactiveRef.child(firebaseAuth.getCurrentUser().getUid()).orderByChild("projectStatus").equalTo("inaktiv");

        inactiveQuery.addListenerForSingleValueEvent(valueEventListener);
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

        Intent i = new Intent(InactiveActivity.this, ActiveActivity.class);
        startActivity(i);
        finish();
    }
}
