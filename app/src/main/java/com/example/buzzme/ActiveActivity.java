package com.example.buzzme;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.buzzme.Utils.TimerUtil;
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

public class ActiveActivity extends BaseActivity {
    private ActiveProjectAdapter adapter;
    private List<Project> projectsList;
    private static Boolean timerFlag = false;
    private static Timestamp currentTimestamp;
    private static String projectId;
    private static int btnId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Main window-Layout

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activeRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Query-Bestandteile

        projectsList = new ArrayList<>();
        adapter = new ActiveProjectAdapter(this, projectsList);
        recyclerView.setAdapter(adapter);

        DatabaseReference activeRef = FirebaseDatabase.getInstance().getReference();
        Query activeQuery = activeRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).orderByChild("projectStatus").equalTo("aktiv");

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

    //dialog if back button is pressed
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("BuzzMe verlassen")
                .setMessage("Bist du sicher, dass du BuzzMe verlassen möchtest?")
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ActiveActivity.getTimerFlag())
                            new TimerUtil().finishingTimer();
                        finish();
                        System.exit(0);
                    }

                })
                .setNegativeButton("Nein", null)
                .show();
    }

    //getter and setter for static variables
    public static boolean getTimerFlag() {
        return timerFlag;
    }

    public static void setTimerFlag(boolean b) {
        timerFlag = b;
    }

    public static Timestamp getcurrentTimestamp() {
        return currentTimestamp;
    }

    public static void setcurrentTimestamp(Timestamp t) {
        currentTimestamp = t;
    }

    public static String getProjectId() {
        return projectId;
    }

    public static void setProjectId(String s) {
        projectId = s;
    }

    public static void setBtnId(int id) {
        btnId = id;
    }

    public static int getBtnId() {
        return btnId;
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_active;
    }

    @Override
    int getNavigationMenuItemId() {
        return 0;
    }
}
