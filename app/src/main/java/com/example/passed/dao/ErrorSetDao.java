package com.example.passed.dao;

import android.util.Log;

import com.example.passed.bean.ErrorSet;
import com.example.passed.bean.Exercise;
import com.example.passed.bean.User;
import com.example.passed.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ErrorSetDao {
    private String TAG = "ErrorSetDao";
    private JdbcUtil jdbcUtil = JdbcUtil.getInstance();
    private Connection conn = jdbcUtil.getConnection();

    public List<ErrorSet> selectErrorSet(String user_id){
        List<ErrorSet> errorSetList = new ArrayList<>();

        if (conn==null){
            Log.i(TAG,"selectErrorSet:conn is null");
        }else {
            ResultSet rs = null;
            String sqlSelect = "select * from errorSet where user_id = ?";
            PreparedStatement pst = null;
            try {
                pst = conn.prepareStatement(sqlSelect);
                pst.setString(1,user_id);
                rs = pst.executeQuery();
                while (rs.next()){
                    ErrorSet errorSet = new ErrorSet(rs.getString(1),rs.getLong(2));
                    errorSetList.add(errorSet);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return errorSetList;
    }

    public List<Exercise> showErrorExercise(List<ErrorSet> errorSetList){
        ExerciseDao exerciseDao = new ExerciseDao();
        List<Exercise> exerciseList = new ArrayList<>();
        for(ErrorSet errorSet : errorSetList){
            long exercise_id = errorSet.getExerciseId();
            Exercise exercise = exerciseDao.selectExerciseById(exercise_id);
            if(exercise != null){
                exerciseList.add(exercise);
            }
        }
        return exerciseList;
    }

    public boolean addError(Exercise exercise, User user){
        boolean result = false;
        if (conn==null){
            Log.i(TAG,"addError:conn is null");
        }else {
            ResultSet rs = null;
            String sql = "insert into errorSet(exercise_id,user_id) values(?,?) ";
            try {

                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setLong(1,exercise.getExercise_id());
                pst.setString(2,user.getUser_id());
                pst.execute();

                result = true;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

        return result;
    }
}
