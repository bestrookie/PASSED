package com.example.passed.dao;

import android.util.Log;

import com.example.passed.bean.User;
import com.example.passed.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private String TAG = "UserDao";

    private JdbcUtil jdbcUtil;
    private Connection conn;

    public UserDao(){
        jdbcUtil = JdbcUtil.getInstance();
        conn = jdbcUtil.getConnection();
    }

    //注册
    public boolean register(String name, String password){
        boolean flag = false;
        if (conn==null){
            Log.i(TAG,"register:conn is null");
            flag = false;
        }else {
            ResultSet rs = null;
            String sqlSelect = "select * from user where user_id = ?";
            PreparedStatement pst = null;
            try {
                pst = conn.prepareStatement(sqlSelect);
                pst.setString(1,name);
                rs = pst.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(rs == null){
                flag = false;
            }else{
                //进行数据库操作
                String sql = "insert into user(user_id,user_password) values(?,?)";
                try {
                    PreparedStatement pre = conn.prepareStatement(sql);
                    pre.setString(1,name);
                    pre.setString(2,password);
                    pre.execute();
                    flag = true;
                } catch (SQLException e) {
                    flag = false;
                }finally {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        flag = false;
                        e.printStackTrace();
                    }
                }
            }
        }
        return flag;
    }
    //登录
    public boolean login(String name,String password){
        if (conn==null){
            Log.i(TAG,"login:conn is null");
            return false;
        }else {
            String sql = "select * from user where user_id=? and user_password=?";
            try {
                PreparedStatement pres = conn.prepareStatement(sql);
                pres.setString(1,name);
                pres.setString(2,password);
                ResultSet res = pres.executeQuery();
                boolean result = res.next();
                return result;
            } catch (SQLException e) {
                return false;
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean judgeInput(String userName, String password){
        boolean result = false;
        String patternAccount = "^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$";
        String patternPass = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        if (userName.matches(patternAccount) && password.matches(patternPass)){
            result = true;
        }
        return result;
    }

    //根据id查找User
    public User selectUser(String userName){
        User user = null;
        if (conn==null){
            Log.i(TAG,"login:conn is null");
        }else {
            String sql = "select * from user where user_id=?";
            try {
                PreparedStatement pres = conn.prepareStatement(sql);
                pres.setString(1,userName);
                ResultSet res = pres.executeQuery();
                if(res.next()){
                    user = new User(res.getString(1),res.getString(2));
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
        return user;
    }

}
