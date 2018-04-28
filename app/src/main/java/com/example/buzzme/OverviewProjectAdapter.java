package com.example.buzzme;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OverviewProjectAdapter extends RecyclerView.Adapter<OverviewProjectAdapter.ProjectViewHolder> {

    private Context mCtx;
    private List<Project> projectList;
    private List<Long> timeList;
    private Long projectTime = 0L;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;

    public OverviewProjectAdapter(Context mCtx, List<Project> projectList) {
        this.mCtx = mCtx;
        this.projectList = projectList;

    }


    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        timeList = new ArrayList<>();

        View view = LayoutInflater.from(mCtx).inflate(R.layout.overview_project_list, parent, false);
        return new ProjectViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        Project project = projectList.get(position);
        //calculateProjecttime();
        //Für Berechnung der Zeit
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference(firebaseAuth.getCurrentUser().getUid().toString());
        mDatabase= mDatabase.child(project.getProjectId()).child("timestamp");
        mDatabase.addListenerForSingleValueEvent(valueEventListener);


        holder.textViewTitle.setText(project.getProjectName());
        holder.textViewTime.setText(projectTime.toString());

        holder.btnEdit.setBackgroundColor(project.getProjectColor());


        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCtx.startActivity(new Intent(mCtx, AddTimeActivity.class));

            }
        });
    }
    //Für Berechnung der Zeit
    private void calculateProjecttime() {
        // projectTime= projectTime+1234L;// Funktioniert !!
      //  timeList.clear();
        projectTime=0L;

        //timeList.add(1234L);
        //timeList.add(1234L);
        for (Long time : timeList) {
            projectTime = projectTime + time;

        }
       System.out.print("k: "+projectTime);

    }

    //Für Berechnung der Zeit
    ValueEventListener valueEventListener = new ValueEventListener() {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            timeList.clear();
            //timeList.add(1234L);
            if (dataSnapshot.exists()) {
                for (DataSnapshot timestampSnapshot : dataSnapshot.getChildren()) {
                    Timestamp timestamp = timestampSnapshot.getValue(Timestamp.class);
                    Long timedif = timestamp.getStop().getTime()-timestamp.getStart().getTime();
                    timeList.add(timedif);
                    System.out.println("JAAA ein Datensatz");

                }
                //adapter.notifyDataSetChanged();
                calculateProjecttime();
            }
            else {System.out.println("Nein kein Datensatz");}

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }

    };

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder {

        Button btnEdit;
        TextView textViewTitle, textViewTime;

        public ProjectViewHolder(View itemView) {
            super(itemView);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewTime = itemView.findViewById(R.id.textViewTime);
        }
    }
}
