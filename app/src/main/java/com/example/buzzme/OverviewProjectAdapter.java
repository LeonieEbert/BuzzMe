package com.example.buzzme;

import android.app.Activity;
import android.arch.lifecycle.HolderFragment;
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




    public OverviewProjectAdapter(Context mCtx, List<Project> projectList, List<Long> timeList) {
        this.mCtx = mCtx;
        this.projectList = projectList;
        this.timeList= timeList;

    }


    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.overview_project_list, parent, false);
        return new ProjectViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        final Project project = projectList.get(position);
        Long time= timeList.get(position);



        holder.textViewTitle.setText(project.getProjectName());
        holder.textViewTime.setText(time.toString());

        holder.btnEdit.setBackgroundColor(project.getProjectColor());


        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( mCtx ,AddTimeActivity.class);
                i.putExtra("projectId",project.getProjectId());

                 mCtx.startActivity(i);
                ((Activity)mCtx).finish();

            }
        });
    }




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
