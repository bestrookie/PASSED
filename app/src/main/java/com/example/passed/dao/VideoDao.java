package com.example.passed.dao;

import android.net.Uri;
import android.util.Log;

import com.example.passed.bean.MyVideo;
import com.example.passed.bean.RoadProject;
import com.example.passed.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VideoDao {
    private String TAG = "VideoDao";
    private JdbcUtil jdbcUtil;
    private Connection conn;

    public VideoDao(){
        jdbcUtil = JdbcUtil.getInstance();
        conn = jdbcUtil.getConnection();
    }

    //查询路考项目
    public List<RoadProject> selectRoadProject(String subject){
        List<RoadProject> roadProjectList = new ArrayList<>();
        if (conn==null){
            Log.i(TAG,"selectRoadProject:conn is null");
        }else {
            String sql = "select * from roadProject where subject=?";
            try {
                PreparedStatement pres = conn.prepareStatement(sql);
                pres.setString(1,subject);
                ResultSet res = pres.executeQuery();
                while (res.next()){
                    String videoPath = "F:\\Learn\\Android\\" + res.getString(4);
                    Uri uri = Uri.parse(videoPath);
                    MyVideo myVideo = new MyVideo(res.getString(3),uri);
                    RoadProject roadProject = new RoadProject(res.getInt(1),res.getBoolean(2),
                            myVideo,res.getString(5));
                    roadProjectList.add(roadProject);
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
        return roadProjectList;
    }
}
