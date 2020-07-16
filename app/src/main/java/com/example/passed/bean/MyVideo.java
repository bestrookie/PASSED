package com.example.passed.bean;

import android.net.Uri;

public class MyVideo {
    private String videoName;
    private Uri video;

    public MyVideo(String videoName, Uri video) {
        this.videoName = videoName;
        this.video = video;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public Uri getVideo() {
        return video;
    }

    public void setVideo(Uri video) {
        this.video = video;
    }
}
