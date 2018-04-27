package com.example.buzzme;

import java.util.List;

public class Project {
    private String projectId;
    private String projectName;
    private int projectColor;
    private String projectStatus;
    private List<Timestamp> projectTimes;

    public Project() {
        this.projectName = "NONAME";
        this.projectColor = -16540699;
        this.projectStatus = "aktiv";
    }

    public Project(String projectName, int projectColor) {
        this.projectName = projectName;
        this.projectColor = projectColor;
        this.projectStatus = "aktiv";
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getProjectColor() {
        return projectColor;
    }

    public void setProjectColor(int projectColor) {
        this.projectColor = projectColor;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }


}
