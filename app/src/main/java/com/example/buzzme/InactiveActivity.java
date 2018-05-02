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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 22.03.2018.
 *  Funktion:
 * Darstellung der inaktiven Projekte.
 * Übermittlung der Daten aus Firebase an den Adapter(InactiveProjectAdapter)
 */

public class InactiveActivity extends BaseActivity {
    private InactiveProjectAdapter adapter;
    private List<Project> projectsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Main window-Layout

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.inactiveRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Query-Bestandteile

        projectsList = new ArrayList<>();
        adapter = new InactiveProjectAdapter(this, projectsList);
        recyclerView.setAdapter(adapter);

        DatabaseReference inactiveRef = FirebaseDatabase.getInstance().getReference();
        Query inactiveQuery = inactiveRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).orderByChild("projectStatus").equalTo("inaktiv");

        inactiveQuery.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        //Snapshot: Die Projektliste des Users wird  an den Adapter übergeben. Vermutlich auch auslagerbar
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

    //start ActiceActivity if back button is pressed
    @Override
    public void onBackPressed() {
        startActivity(new Intent(InactiveActivity.this, ActiveActivity.class));
        finish();
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_inactive;
    }
    @Override
    int getNavigationMenuItemId() {
        return 1;
    }
}
