package com.example.buzzme;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>{

    private Context mCtx;
    private List<Project> projectList;

    public ProjectAdapter(Context mCtx, List<Project> projectList) {
        this.mCtx = mCtx;
        this.projectList = projectList;
    }


    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCtx).inflate(R.layout.project_list,parent,false);
        return new ProjectViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
    Project project = projectList.get(position);
    holder.textViewTitle.setText(project.getProjectName());
    holder.textViewDesc.setText("TEST");
    holder.textViewRating.setText("TEST");
    holder.textViewPrice.setText("TEST");

    holder.btnEdit.setBackgroundColor(project.getProjectColor());

    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder{

        Button btnEdit;
        TextView textViewTitle,textViewDesc,textViewRating,textViewPrice;

        public ProjectViewHolder(View itemView) {
            super(itemView);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDesc= itemView.findViewById(R.id.textViewShortDesc);
            textViewRating= itemView.findViewById(R.id.textViewRating);
            textViewPrice= itemView.findViewById(R.id.textViewPrice);
        }
    }
}
