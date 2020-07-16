package com.example.passed.dao;

import android.util.Log;

import com.example.passed.bean.CollectionSet;
import com.example.passed.bean.Exercise;
import com.example.passed.bean.User;
import com.example.passed.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CollectionDao {
    private String TAG = "CollectionDao";
    private JdbcUtil jdbcUtil = JdbcUtil.getInstance();
    private Connection conn = jdbcUtil.getConnection();

    public List<CollectionSet> selectCollection(String user_id){
        List<CollectionSet> collectionList = new ArrayList<>();

        if (conn==null){
            Log.i(TAG,"selectCollection:conn is null");
        }else {
            ResultSet rs = null;
            String sqlSelect = "select * from collectionSet where user_id = ?";
            PreparedStatement pst = null;
            try {
                pst = conn.prepareStatement(sqlSelect);
                pst.setString(1,user_id);
                rs = pst.executeQuery();
                while (rs.next()){
                    CollectionSet collectionSet = new CollectionSet(rs.getString(1),rs.getLong(2));
                    collectionList.add(collectionSet);
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

        return collectionList;
    }

    public List<Exercise> showCollectionExercise(List<CollectionSet> collectionSetList){
        ExerciseDao exerciseDao = new ExerciseDao();
        List<Exercise> exerciseList = new ArrayList<>();
        for(CollectionSet collectionSet : collectionSetList){
            long exercise_id = collectionSet.getExerciseId();
            Exercise exercise = exerciseDao.selectExerciseById(exercise_id);
            if(exercise != null){
                exerciseList.add(exercise);
            }
        }
        return exerciseList;
    }

    public boolean addCollection(Exercise exercise, User user){
        boolean result = false;

        ResultSet rs = null;
        String sql = "insert into collectionSet(exercise_id,user_id) values(?,?)";
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

        return result;
    }

    public boolean deleteCollection(String userName, long exercise_id) {
        boolean result = false;

        ResultSet rs = null;
        String sql = "delete from collectionSet where exercise_id = ? and user_id = ?";
        try {

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setLong(1,exercise_id);
            pst.setString(2,userName);
            result = pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
