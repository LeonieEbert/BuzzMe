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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 22.03.2018.
 */

public class OverviewActivity extends AppCompatActivity{
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    OverviewProjectAdapter adapter;
    List<Project> projectsList;
    List<Long> timestampList;
    List<Long> timeList;


    /*    @Override
        int getContentViewId() {
            return R.layout.activity_overview;       }

    /*    @Override
        int getNavigationMenuItemId() {
            return R.id.action_overview_project;
        }*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        recyclerView = (RecyclerView) findViewById(R.id.overviewRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        projectsList = new ArrayList<>();
        timeList = new ArrayList<>();
        timestampList = new ArrayList<>();

        adapter = new OverviewProjectAdapter(this, projectsList,timeList);
        recyclerView.setAdapter(adapter);

        /*just for testing:
        timeList.add(1L);
        timeList.add(2L);
        timeList.add(3L);*/

        firebaseAuth = FirebaseAuth.getInstance();
        //just for testing

        mDatabase = FirebaseDatabase.getInstance().getReference(firebaseAuth.getCurrentUser().getUid().toString());

        mDatabase.addListenerForSingleValueEvent(valueEventListener);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_active_project:
                        Toast.makeText(OverviewActivity.this, "Action Active Project", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OverviewActivity.this, ActiveActivity.class));
                        finish();
                        break;
                    case R.id.action_inactive_project:
                        Toast.makeText(OverviewActivity.this, "Action Inactive Project", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OverviewActivity.this, InactiveActivity.class));
                        finish();
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
                    timestampList.clear();
                    for (DataSnapshot timestampSnapshot : dataSnapshot.child(project.getProjectId()).child("timestamp").getChildren()){
                        Timestamp timestamp = timestampSnapshot.getValue(Timestamp.class);
                        Long timedif = timestamp.getStop().getTime()-timestamp.getStart().getTime();
                        timestampList.add(timedif);

                    }
                    calculateProjecttime();


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

    public void onBackPressed() {
        Intent i = new Intent(OverviewActivity.this, ActiveActivity.class);
        startActivity(i);
        finish();
    }
    private void calculateProjecttime() {

        Long projectTime=0L;


        for (Long time : timestampList) {
            projectTime = projectTime + time;

        }
        timeList.add(projectTime);

    }

}