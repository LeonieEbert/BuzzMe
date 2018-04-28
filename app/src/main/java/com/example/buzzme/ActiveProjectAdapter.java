package com.example.buzzme;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import java.util.Calendar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.List;

public class ActiveProjectAdapter extends RecyclerView.Adapter<ActiveProjectAdapter.ProjectViewHolder>{

    private Context mCtx;
    private List<Project> projectList;

    public ActiveProjectAdapter(Context mCtx, List<Project> projectList) {
        this.mCtx = mCtx;
        this.projectList = projectList;
    }


    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCtx).inflate(R.layout.active_project_list,parent,false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        final Project project = projectList.get(position);

        holder.textViewTitle.setText(project.getProjectName());

        holder.btnBuzzme.setBackgroundColor(project.getProjectColor());
        holder.switchStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (isChecked) {

                }
                else {
                    project.setProjectStatus("inaktiv");
                    FirebaseDatabase.getInstance().getReference().child(user.getUid()).child(project.getProjectId()).setValue(project);
                }

            }
        });

        holder.btnBuzzme.setOnClickListener(new View.OnClickListener() {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            @Override
            public void onClick(View v) {

                Date currentTime = Calendar.getInstance().getTime();
                if (ActiveActivity.getTimerFlag() == false) {
                    String id = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child(project.getProjectId()).child("timestamp").push().getKey();
                    Timestamp timestamp = new Timestamp(id, currentTime);
                    FirebaseDatabase.getInstance().getReference().child(user.getUid()).child(project.getProjectId()).child("timestamp").child(timestamp.getTimestampId()).setValue(timestamp);
                    ActiveActivity.setTimerFlag(true);
                    ActiveActivity.setcurrentTimestamp(timestamp);
                    ActiveActivity.setProjectId(project.getProjectId());
                }
                else {
                    Timestamp timestamp =ActiveActivity.getcurrentTimestamp();
                    timestamp.setStop(currentTime);
                    //Es wird immer das vorher gestartete Project beendet
                    FirebaseDatabase.getInstance().getReference().child(user.getUid()).child(ActiveActivity.getProjectId()).child("timestamp").child(timestamp.getTimestampId()).setValue(timestamp);
                    ActiveActivity.setTimerFlag(false);
                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }


    class ProjectViewHolder extends RecyclerView.ViewHolder{

        Button btnBuzzme;
        TextView textViewTitle;
        private Switch switchStatus;

        public ProjectViewHolder(View itemView) {
            super(itemView);
            btnBuzzme = itemView.findViewById(R.id.btnBuzzme);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            switchStatus = itemView.findViewById(R.id.toggleStatus);

        }
    }

}
