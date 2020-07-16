package com.example.passed.bean;

public class Test {
    private long test_id;
    private String user_id;
    private long test_finish;
    private long test_true;
    private String test_type;

    public long getTest_id() {
        return test_id;
    }

    public void setTest_id(long test_id) {
        this.test_id = test_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public long getTest_finish() {
        return test_finish;
    }

    public void setTest_finish(long test_finish) {
        this.test_finish = test_finish;
    }

    public long getTest_true() {
        return test_true;
    }

    public void setTest_true(long test_true) {
        this.test_true = test_true;
    }

    public String getTest_type() {
        return test_type;
    }

    public void setTest_type(String test_type) {
        this.test_type = test_type;
    }

    public Test(long test_id, String user_id, long test_finish, long test_true, String test_type) {
        this.test_id = test_id;
        this.user_id = user_id;
        this.test_finish = test_finish;
        this.test_true = test_true;
        this.test_type = test_type;
    }

    @Override
    public String toString() {
        return "Test{" +
                "test_id=" + test_id +
                ", user_id='" + user_id + '\'' +
                ", test_error=" + test_finish +
                ", test_true=" + test_true +
                ", test_type='" + test_type + '\'' +
                '}';
    }
}
