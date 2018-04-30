package com.example.buzzme;

import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
        holder.textViewTime.setText(calculateTimeComponents(time));
        holder.btnEdit.setBackgroundColor(project.getProjectColor());

        holder.btnEdit.setTextColor(getTextColorBasedOnBackgroundBrightness(project.getProjectColor()));
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( mCtx ,EditProjectActivity.class);
                i.putExtra("projectId",project.getProjectId());

                 mCtx.startActivity(i);
                ((Activity)mCtx).finish();

            }
        });
    }


    private String calculateTimeComponents(Long time) {
        Long timeDD = 0L;
        Long timeHH = 0L;
        Long timeMM = 0L;

        time = time / (1000 * 60); // Millisekunden > Minuten

        if (time >= 0L) {
            timeMM = time % 60;             // Restliche Minuten nach Abzug voller Stunden
            timeHH = (time / 60) % 24;      // Restliche Stunden unter Abzug voller Tage
            timeDD = (time / (60 * 24));    // Volle Tage
        } else {
        }
        return timeDD + "T " + timeHH + "S " + timeMM + "M";
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder{

        Button btnEdit;
        TextView textViewTitle,textViewTime;

        public ProjectViewHolder(View itemView) {
            super(itemView);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewTime = itemView.findViewById(R.id.textViewTime);
        }
    }

    private static int getBrightness(int color) {
        int[] rgb = {Color.red(color), Color.green(color), Color.blue(color)};
        return (int) Math.sqrt(rgb[0] * rgb[0] * .241 + rgb[1]
                * rgb[1] * .691 + rgb[2] * rgb[2] * .068);
    }

    public static int getTextColorBasedOnBackgroundBrightness(int color) {
        if (getBrightness(color) < 130)
            return Color.WHITE;
        else
            return Color.BLACK;
    }
}
