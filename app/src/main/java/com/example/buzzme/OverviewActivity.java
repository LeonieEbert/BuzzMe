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
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 22.03.2018.
 */

public class OverviewActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    ProjectAdapter adapter;
    List<Project> projectsList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        firebaseAuth = FirebaseAuth.getInstance();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);




        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        projectsList = new ArrayList<>();
        adapter = new ProjectAdapter(this, projectsList);
        recyclerView.setAdapter(adapter);

        //1.SELECT *FROM Projects
        mDatabase = FirebaseDatabase.getInstance().getReference("vep6s5QEVrh9LYhMPev1ps7UBk72");

        mDatabase.addListenerForSingleValueEvent(valueEventListener);






        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_active_project:
                        Toast.makeText(OverviewActivity.this, "Action Active Project", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OverviewActivity.this, ActiveActivity.class));
                        break;
                    case R.id.action_inactive_project:
                        Toast.makeText(OverviewActivity.this, "Action Inactive Project", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OverviewActivity.this, InactiveActivity.class));
                        break;
                    case R.id.action_overview_project:
                        Toast.makeText(OverviewActivity.this, "Action Overview Project", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });


    }
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







    }

