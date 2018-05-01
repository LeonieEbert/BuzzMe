package com.example.buzzme.Utils;

import com.example.buzzme.ActiveActivity;
import com.example.buzzme.Project;
import com.example.buzzme.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

/**
 * Created by User on 01.05.2018.
 */

public class TimerUtil {
    private FirebaseUser user;

    public TimerUtil() {
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void startingTimer(Project project) {
        String id = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child(project.getProjectId()).child("timestamps").push().getKey();
        Timestamp timestamp = new Timestamp(id, Calendar.getInstance().getTime());
        FirebaseDatabase.getInstance().getReference().child(user.getUid()).child(project.getProjectId()).child("timestamps").child(timestamp.getTimestampId()).setValue(timestamp);
        ActiveActivity.setTimerFlag(true);
        ActiveActivity.setcurrentTimestamp(timestamp);
        ActiveActivity.setProjectId(project.getProjectId());
    }

    public void finishingTimer() {
        Timestamp currentTimestamp = ActiveActivity.getcurrentTimestamp();
        currentTimestamp.setStop(Calendar.getInstance().getTime());
        FirebaseDatabase.getInstance().getReference().child(user.getUid()).child(ActiveActivity.getProjectId()).child("timestamps").child(currentTimestamp.getTimestampId()).setValue(currentTimestamp);
        ActiveActivity.setTimerFlag(false);
    }
}
