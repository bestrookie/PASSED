package com.example.passed.dao;

import android.util.Log;

import com.example.passed.bean.Exercise;
import com.example.passed.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDao {
    private String TAG = "ExerciseDao";
    private JdbcUtil jdbcUtil = JdbcUtil.getInstance();
    private Connection conn = jdbcUtil.getConnection();


    public Exercise selectExerciseById(long exercise_id) {
        Exercise exercise = null;
        if (conn==null){
            Log.i(TAG,"selectExerciseById:conn is null");
        }else {
            try {
                ResultSet rs = null;
                String sqlSelect = "select * from exercise where exercise_id = ?";
                PreparedStatement pst = conn.prepareStatement(sqlSelect);
                pst.setLong(1,exercise_id);
                rs = pst.executeQuery();
                if(rs.next()){
                    //将答案的字符串转换成List
                    String answerStr = rs.getString(8);
                    String[] answerStrArr = answerStr.split(",");
                    List<Integer> answerList = new ArrayList<>();
                    for(int i=0; i<answerStrArr.length; i++){
                        answerList.add(Integer.parseInt(answerStrArr[i]));
                    }

                    /*2:exercise_describe
                      3:type_name
                      4:option_one
                      5:option_two
                      6:option_three
                      7:option_four
                      9:exercise_explain
                    */
                    exercise = new Exercise(exercise_id,rs.getString(2),rs.getString(3),
                            rs.getString(4),rs.getString(5),rs.getString(6),
                            rs.getString(7),answerList,rs.getString(9));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return exercise;
    }

    /**
     * 查询所有的练习题
     * @return
     */
    public List<Exercise> selectAllExercise() throws SQLException {
        List<Exercise> exerciseList = new ArrayList<Exercise>();
        if (conn == null){
            Log.i(TAG,"register:conn is null");
        }else {
            ResultSet rs = null;
            String sql = "select * from exercise";
            Statement statement = (Statement) conn.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()){
                Exercise exercise = new Exercise();
                exercise.setExercise_id(rs.getLong(1));
                exercise.setExercise_describe(rs.getString(2));
                exercise.setType_name(rs.getString(3));
                exercise.setOption_one(rs.getString(4));
                exercise.setOption_two(rs.getString(5));
                exercise.setOption_three(rs.getString(6));
                exercise.setOption_four(rs.getString(7));
                String answer = rs.getString(8);
                String[] answerStrArr = answer.split(",");
                List<Integer> an = new ArrayList<Integer>();
                for (String s : answerStrArr) {
                    an.add(Integer.parseInt(s));
                }
                exercise.setOption_answer(an);
                exercise.setExercise_explain(rs.getString(9));
                exerciseList.add(exercise);
            }

        }
        return exerciseList;
    }

    /**
     *根据类型查询题目
     * @param type
     * @return
     * @throws SQLException
     */
    public List<Exercise> selectExerciseByType(String type) throws SQLException {
        List<Exercise> exerciseList = new ArrayList<Exercise>();
        ResultSet rs = null;
        PreparedStatement pst = conn.prepareStatement("select * from exercise where type_name = ?");
        pst.setString(1,type);
        rs = pst.executeQuery();
        while (rs.next()){
            Exercise exercise = new Exercise();
            exercise.setExercise_id(rs.getLong(1));
            exercise.setExercise_describe(rs.getString(2));
            exercise.setType_name(rs.getString(3));
            exercise.setOption_one(rs.getString(4));
            exercise.setOption_two(rs.getString(5));
            exercise.setOption_three(rs.getString(6));
            exercise.setOption_four(rs.getString(7));
            String answer = rs.getString(8);
            String[] answerStrArr = answer.split(",");
            List<Integer> an = new ArrayList<Integer>();
            for (String s : answerStrArr) {
                an.add(Integer.parseInt(s));
            }
            exercise.setOption_answer(an);
            exercise.setExercise_explain(rs.getString(9));
            exerciseList.add(exercise);
        }
        return exerciseList;
    }
}
