package com.example.passed.bean;

public class Practice{
    private long practiceId;
    private String userId;
    private int practiceFinOne;
    private int practiceTrOne;
    private int practiceFinTwo;
    private int practiceTrTwo;
    private int practiceFinThree;
    private int practiceTrThree;
    private int practiceExam;
    private int practicePass;

    public Practice() {
    }

    public Practice(String userId, int practiceFinOne, int practiceTrOne, int practiceFinTwo
            , int practiceTrTwo, int practiceFinThree, int practiceTrThree, int practiceExam, int practicePass) {
        this.userId = userId;
        this.practiceFinOne = practiceFinOne;
        this.practiceTrOne = practiceTrOne;
        this.practiceFinTwo = practiceFinTwo;
        this.practiceTrTwo = practiceTrTwo;
        this.practiceFinThree = practiceFinThree;
        this.practiceTrThree = practiceTrThree;
        this.practiceExam = practiceExam;
        this.practicePass = practicePass;
    }

    public long getPracticeId() {
        return practiceId;
    }

    public void setPracticeId(long practiceId) {
        this.practiceId = practiceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPracticeFinOne() {
        return practiceFinOne;
    }

    public void setPracticeFinOne(int practiceFinOne) {
        this.practiceFinOne = practiceFinOne;
    }

    public int getPracticeTrOne() {
        return practiceTrOne;
    }

    public void setPracticeTrOne(int practiceTrOne) {
        this.practiceTrOne = practiceTrOne;
    }

    public int getPracticeFinTwo() {
        return practiceFinTwo;
    }

    public void setPracticeFinTwo(int practiceFinTwo) {
        this.practiceFinTwo = practiceFinTwo;
    }

    public int getPracticeTrTwo() {
        return practiceTrTwo;
    }

    public void setPracticeTrTwo(int practiceTrTwo) {
        this.practiceTrTwo = practiceTrTwo;
    }

    public int getPracticeFinThree() {
        return practiceFinThree;
    }

    public void setPracticeFinThree(int practiceFinThree) {
        this.practiceFinThree = practiceFinThree;
    }

    public int getPracticeTrThree() {
        return practiceTrThree;
    }

    public void setPracticeTrThree(int practiceTrThree) {
        this.practiceTrThree = practiceTrThree;
    }

    public int getPracticeExam() {
        return practiceExam;
    }

    public void setPracticeExam(int practiceExam) {
        this.practiceExam = practiceExam;
    }

    public int getPracticePass() {
        return practicePass;
    }

    public void setPracticePass(int practicePass) {
        this.practicePass = practicePass;
    }

    @Override
    public String toString() {
        return "Practice{" +
                "practiceId=" + practiceId +
                ", userId='" + userId + '\'' +
                ", practiceFinOne=" + practiceFinOne +
                ", practiceTrOne=" + practiceTrOne +
                ", practiceFinTwo=" + practiceFinTwo +
                ", practiceTrTwo=" + practiceTrTwo +
                ", practiceFinThree=" + practiceFinThree +
                ", practiceTrThree=" + practiceTrThree +
                ", practiceExam=" + practiceExam +
                ", practicePass=" + practicePass +
                '}';
    }
}
