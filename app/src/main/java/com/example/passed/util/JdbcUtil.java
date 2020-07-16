package com.example.passed.util;

import android.util.Log;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtil {
    private static JdbcUtil jdbcUtil;

    public static JdbcUtil getInstance(){
        if (jdbcUtil == null){
            jdbcUtil = new JdbcUtil();
        }
        return  jdbcUtil;
    }
    public Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://bestrookie.cn:3306/yun_passed?characterEncoding=utf-8&useSSL=false";
            return DriverManager.getConnection(url,"yun_root","123456");
        } catch (ClassNotFoundException | SQLException e) {
            Log.d("DBUtil","连接失败");
            e.printStackTrace();
            return null;
        }
    }

}
