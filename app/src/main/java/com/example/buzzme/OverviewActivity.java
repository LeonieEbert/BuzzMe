package com.example.buzzme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

public class OverviewActivity extends BaseActivity {
    private OverviewProjectAdapter adapter;
    private List<Project> projectsList;
    private List<Long> timestampList;
    private List<Long> timeList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.overviewRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        projectsList = new ArrayList<>();
        timeList = new ArrayList<>();
        timestampList = new ArrayList<>();

        adapter = new OverviewProjectAdapter(this, projectsList, timeList);
        recyclerView.setAdapter(adapter);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mDatabase.addListenerForSingleValueEvent(valueEventListener);
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
                    for (DataSnapshot timestampSnapshot : dataSnapshot.child(project.getProjectId()).child("timestamps").getChildren()) {
                        Timestamp timestamp = timestampSnapshot.getValue(Timestamp.class);
                        Long timedif = timestamp.getStop().getTime() - timestamp.getStart().getTime();
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

    //start ActiveAcivity if back button is pressed
    @Override
    public void onBackPressed() {
        startActivity(new Intent(OverviewActivity.this, ActiveActivity.class));
        finish();
    }

    private void calculateProjecttime() {
        Long projectTime = 0L;
        for (Long time : timestampList) {
            projectTime = projectTime + time;
        }
        timeList.add(projectTime);
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_overview;
    }

    @Override
    int getNavigationMenuItemId() {
        return 2;
    }

}