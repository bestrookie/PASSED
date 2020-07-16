package com.example.passed.bean;

import java.util.List;

public class Exercise{
    private long exercise_id;
    private String exercise_describe;
    private String type_name;
    private String option_one;
    private String option_two;
    private String option_three;
    private String option_four;
    private List<Integer> option_answer;
    private String exercise_explain;

    public Exercise(long exercise_id, String exercise_describe, String type_name, String option_one,
                    String option_two, String option_three, String option_four,
                    List<Integer> option_answer, String exercise_explain) {
        this.exercise_id = exercise_id;
        this.exercise_describe = exercise_describe;
        this.type_name = type_name;
        this.option_one = option_one;
        this.option_two = option_two;
        this.option_three = option_three;
        this.option_four = option_four;
        this.option_answer = option_answer;
        this.exercise_explain = exercise_explain;
    }

    public Exercise(){

    }

    public long getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(long exercise_id) {
        this.exercise_id = exercise_id;
    }

    public String getExercise_describe() {
        return exercise_describe;
    }

    public void setExercise_describe(String exercise_describe) {
        this.exercise_describe = exercise_describe;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getOption_one() {
        return option_one;
    }

    public void setOption_one(String option_one) {
        this.option_one = option_one;
    }

    public String getOption_two() {
        return option_two;
    }

    public void setOption_two(String option_two) {
        this.option_two = option_two;
    }

    public String getOption_three() {
        return option_three;
    }

    public void setOption_three(String option_three) {
        this.option_three = option_three;
    }

    public String getOption_four() {
        return option_four;
    }

    public void setOption_four(String option_four) {
        this.option_four = option_four;
    }

    public List<Integer> getOption_answer() {
        return option_answer;
    }

    public void setOption_answer(List<Integer> option_answer) {
        this.option_answer = option_answer;
    }

    public String getExercise_explain() {
        return exercise_explain;
    }

    public void setExercise_explain(String exercise_explain) {
        this.exercise_explain = exercise_explain;
    }
}
