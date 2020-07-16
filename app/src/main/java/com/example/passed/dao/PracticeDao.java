package com.example.passed.dao;


import android.util.Log;

import com.example.passed.bean.Practice;
import com.example.passed.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PracticeDao {
    private  String TAG = "PracticeDao";
    private JdbcUtil jdbcUtil = JdbcUtil.getInstance();
    private Connection conn = jdbcUtil.getConnection();

    /**
     * 获取账号的练习信息
     * @param userId
     * @return
     * @throws SQLException
     */
    public Practice selectPracticeInfoById(String userId) throws SQLException {
        Practice practiceInfo = new Practice();
        if (conn == null){
            Log.i(TAG,"selectPracticeInfoById:conn is bull");
        }else {
            ResultSet rs = null;
            String sql = "select * from practice where user_id = ? ";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,userId);
            rs = pst.executeQuery();
            if (rs.next())
                practiceInfo.setPracticeId(rs.getLong(1));
                practiceInfo.setUserId(rs.getString(2));
                practiceInfo.setPracticeFinOne(rs.getInt(3));
                practiceInfo.setPracticeTrOne(rs.getInt(4));
                practiceInfo.setPracticeFinTwo(rs.getInt(5));
                practiceInfo.setPracticeTrTwo(rs.getInt(6));
                practiceInfo.setPracticeFinThree(rs.getInt(7));
                practiceInfo.setPracticeTrThree(rs.getInt(8));
                practiceInfo.setPracticeExam(rs.getInt(9));
                practiceInfo.setPracticePass(rs.getInt(10));
        }
        return practiceInfo;
    }

    /**
     * 更新练习信息
     * @param practice
     * @param jude
     * @return
     * @throws SQLException
     */
    public Boolean updatePracticeInfo(Practice practice,int jude) throws SQLException {
        Boolean flag = false;
        String sql = null;
        switch (jude){
            case 1:
                sql = "update practice set practice_finish_one = ?,practice_true_one = ? where user_id =?";
                PreparedStatement state = conn.prepareStatement(sql);
                state.setInt(1,practice.getPracticeFinOne());
                state.setInt(2,practice.getPracticeTrOne());
                state.setString(3,practice.getUserId());
                int result = state.executeUpdate();
                if (result == 1){
                    flag =true;
                }
                state.close();
                break;
            case 2:
                sql = "update practice set practice_finish_two = ?,practice_true_two = ? where user_id =?";
                PreparedStatement state1 = conn.prepareStatement(sql);
                state1.setInt(1,practice.getPracticeFinTwo());
                state1.setInt(2,practice.getPracticeTrTwo());
                state1.setString(3,practice.getUserId());
                int result1 = state1.executeUpdate();
                if (result1 == 1){
                    flag =true;
                }
                state1.close();
                break;
            case 3:
                sql = "update practice set practice_finish_three = ?,practice_true_three = ? where user_id =?";
                PreparedStatement state2 = conn.prepareStatement(sql);
                state2.setInt(1,practice.getPracticeFinThree());
                state2.setInt(2,practice.getPracticeTrThree());
                state2.setString(3,practice.getUserId());
                int result2 = state2.executeUpdate();
                if (result2 == 1){
                    flag =true;
                }
                state2.close();
                break;
            case 4:
                sql = "update practice set practice_exam = ?,practice_pass = ? where user_id =?";
                PreparedStatement stat3 = conn.prepareStatement(sql);
                stat3.setInt(1,practice.getPracticeExam());
                stat3.setInt(2,practice.getPracticePass());
                stat3.setString(3,practice.getUserId());
                int result3 = stat3.executeUpdate();
                if (result3 == 1){
                    flag =true;
                }
                stat3.close();
                break;
            default:
                break;
        }

            return flag;
    }

}
