package com.example.passed.bean;

import java.io.Serializable;

public class TestIntro implements Serializable {
    private int imageId;
    private String title;
    private String content;

    public TestIntro(int imageId, String title) {
        this.imageId = imageId;
        this.title = title;
    }

    public TestIntro(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
