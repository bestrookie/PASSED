package com.example.passed.dao;

import android.util.Log;

import com.example.passed.bean.TestIntro;
import com.example.passed.util.JdbcUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestIntroDao {
    private String TAG = "TestIntroDao";
    private JdbcUtil jdbcUtil = JdbcUtil.getInstance();
    private Connection conn = jdbcUtil.getConnection();

    public List<TestIntro> selectTestIntro(){
        List<TestIntro> testIntroList = new ArrayList<>();
        if (conn==null){
            Log.i(TAG,"selectTestIntro:conn is null");
        }else {
            ResultSet rs;
            String sqlSelect = "select * from testIntro";
            try {
                PreparedStatement pst = conn.prepareStatement(sqlSelect);
                rs = pst.executeQuery();
                while (rs.next()){
                    TestIntro testIntro = new TestIntro(rs.getString(1),rs.getString(2));
                    testIntroList.add(testIntro);
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
        return testIntroList;
    }

    public TestIntro selectTestIntroByTitle(String title){
        TestIntro testIntro = null;
        if (conn==null){
            Log.i(TAG,"selectTestIntro:conn is null");
        }else {
            ResultSet rs;
            String sqlSelect = "select * from testIntro where title = ?";
            try {
                PreparedStatement pst;
                pst = conn.prepareStatement(sqlSelect);
                pst.setString(1,title);
                rs = pst.executeQuery();

                if (rs.next()){
                    testIntro = new TestIntro(title,rs.getString(2));
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
        return testIntro;
    }

   /* //读取文件内容，返回字符串
    private String readFile(String filePath){

        FileInputStream fileInputStream;
        BufferedReader bufferedReader;
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(filePath);
        if(!file.exists()){
            System.out.println("文件不存在");
        }
        Log.d(TAG,"fileName:"+file.getName());
        try {
            fileInputStream = new FileInputStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return stringBuilder.toString();
    }*/
}
