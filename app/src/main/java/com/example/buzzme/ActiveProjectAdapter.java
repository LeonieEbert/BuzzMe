package com.example.buzzme;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
        Project project = projectList.get(position);
        holder.textViewTitle.setText(project.getProjectName());

        holder.btnBackground.setBackgroundColor(project.getProjectColor());
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder{

        Button btnBackground;
        TextView textViewTitle;

        public ProjectViewHolder(View itemView) {
            super(itemView);
            btnBackground = itemView.findViewById(R.id.btnBackground);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
        }
    }
}
