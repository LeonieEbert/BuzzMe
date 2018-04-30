package com.example.buzzme;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
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

        View view = LayoutInflater.from(mCtx).inflate(R.layout.active_project_list, parent,false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        final Project project = projectList.get(position);
        holder.textViewTitle.setText(project.getProjectName());

        final Button btnbuzzme = holder.btnBuzzme;
        btnbuzzme.setId(btnbuzzme.getId()+position);
        final GradientDrawable gd = new GradientDrawable();
        gd.setColor(project.getProjectColor());
        btnbuzzme.setBackground(gd);
        btnbuzzme.setTextColor(getTextColorBasedOnBackgroundBrightness(project.getProjectColor()));
        holder.switchStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (isChecked) {
                    FirebaseDatabase.getInstance().getReference().child(user.getUid()).child(project.getProjectId()).child("projectStatus").setValue("aktiv");
                } else {
                    FirebaseDatabase.getInstance().getReference().child(user.getUid()).child(project.getProjectId()).child("projectStatus").setValue("inaktiv");
                }

            }
        });

        btnbuzzme.setOnClickListener(new View.OnClickListener() {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            @Override
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();
                if (!ActiveActivity.getTimerFlag()) {
                    String id = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child(project.getProjectId()).child("timestamps").push().getKey();
                    Timestamp timestamp = new Timestamp(id, currentTime);
                    FirebaseDatabase.getInstance().getReference().child(user.getUid()).child(project.getProjectId()).child("timestamps").child(timestamp.getTimestampId()).setValue(timestamp);
                    ActiveActivity.setTimerFlag(true);
                    ActiveActivity.setcurrentTimestamp(timestamp);
                    ActiveActivity.setProjectId(project.getProjectId());
                    gd.setStroke(10,Color.GREEN, 20,4);
                    btnbuzzme.setBackground(gd);
                    ActiveActivity.setBtnId(btnbuzzme.getId());
                } else {
                    Timestamp timestamp = ActiveActivity.getcurrentTimestamp();
                    timestamp.setStop(currentTime);
                    //Es wird immer das vorher gestartete Project beendet
                    FirebaseDatabase.getInstance().getReference().child(user.getUid()).child(ActiveActivity.getProjectId()).child("timestamps").child(timestamp.getTimestampId()).setValue(timestamp);
                    ActiveActivity.setTimerFlag(false);
                    Button btn = ((ActiveActivity)mCtx).findViewById(ActiveActivity.getBtnId());
                    GradientDrawable gd = (GradientDrawable) btn.getBackground();
                    gd.setStroke(0,0);
                    btn.setBackground(gd);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }


    class ProjectViewHolder extends RecyclerView.ViewHolder{

        private Button btnBuzzme;
        private TextView textViewTitle;
        private Switch switchStatus;

        public ProjectViewHolder(View itemView) {
            super(itemView);
            btnBuzzme = itemView.findViewById(R.id.btnBuzzme);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            switchStatus = itemView.findViewById(R.id.toggleStatus);
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
