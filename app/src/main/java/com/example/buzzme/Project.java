package com.example.buzzme;

public class Project {
    public String projectName;
    public int projectColor;

    public Project() {
        this.projectName = "JUHU";
        this.projectColor = -1658750;
    }
    public Project(String projectName, int projectColor) {
        this.projectName = projectName;
        this.projectColor = projectColor;
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
}
