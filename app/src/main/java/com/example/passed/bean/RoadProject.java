package com.example.passed.bean;

import java.io.Serializable;

public class RoadProject implements Serializable {
    private int projectId;
    private boolean property;
    private MyVideo myVideo;
    private String subject;

    public RoadProject(int projectId, boolean property, MyVideo myVideo,String subject) {
        this.projectId = projectId;
        this.property = property;
        this.myVideo = myVideo;
        this.subject = subject;
    }

    public RoadProject(boolean property, MyVideo myVideo,String subject) {
        this.property = property;
        this.myVideo = myVideo;
        this.subject = subject;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public boolean isProperty() {
        return property;
    }

    public void setProperty(boolean property) {
        this.property = property;
    }

    public MyVideo getMyVideo() {
        return myVideo;
    }

    public void setMyVideo(MyVideo myVideo) {
        this.myVideo = myVideo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
