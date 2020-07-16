package com.example.passed;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.passed.dao.UserDao;
import com.example.passed.ui.exam.ExamOneFragment;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private static final int USER = 1;
    EditText edUserName;
    EditText edPassword;
    private final static int LOGIN_TIP = 1;
    private final static int LOGIN_FAIL = -1;
    private final static int REGISTER_TIP = 2;
    private final static int REGISTER_FAIL = -2;
    private static final int FORMAT_ERROR = -3;
    private static final int EMPTY_WARNING = -4;
    private TextView tv_register;
    private Button btn_login;
    private String userName;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        edUserName = findViewById(R.id.edit_text_username);
        edPassword = findViewById(R.id.edit_text_password);

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        tv_register = findViewById(R.id.tv_register);
        tv_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                btnLoginClick();
                break;
            case R.id.tv_register:
                btnRegisterClick();
                break;
            default:
                break;
        }
    }

    private void btnLoginClick(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                userName = edUserName.getText().toString().trim();
                password = edPassword.getText().toString().trim();

                UserDao userDao = new UserDao();
                Message message = new Message();

                //判断是否为空
                if(userName != null && password != null && !userName.equals("") && !password.equals("")){
                    boolean resultInput = userDao.judgeInput(userName,password);

                    //判断格式是否正确
                    if(resultInput == true){
                        boolean result = userDao.login(userName,password);

                        //判断数据库插入操作是否成功
                        if(result == true){
                            message.what = LOGIN_TIP;
                            handler.sendMessage(message);
                        }else {
                            message.what = LOGIN_FAIL;
                            handler.sendMessage(message);
                        }
                    }else {
                        message.what = FORMAT_ERROR;
                        handler.sendMessage(message);
                    }
                }
                else {
                    message.what = EMPTY_WARNING;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    private void btnRegisterClick(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String userName = edUserName.getText().toString().trim();
                String password = edPassword.getText().toString().trim();

                UserDao userDao = new UserDao();
                Message message = new Message();

                //判断是否为空
                if(userName != null && password != null && !userName.equals("") && !password.equals("")){
                    boolean resultInput = userDao.judgeInput(userName,password);

                    //判断格式是否正确
                    if(resultInput == true){
                        boolean result = userDao.register(userName,password);

                        //判断数据库插入操作是否成功
                        if(result == true){
                            message.what = REGISTER_TIP;
                            handler.sendMessage(message);
                        }else {
                            message.what = REGISTER_FAIL;
                            handler.sendMessage(message);
                        }
                    }else {
                        message.what = FORMAT_ERROR;
                        handler.sendMessage(message);
                    }
                }else {
                    message.what = EMPTY_WARNING;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case LOGIN_TIP:
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("userName",userName);
                    startActivity(intent);
                    break;
                case LOGIN_FAIL:
                    Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                    break;
                case REGISTER_TIP:
                    Toast.makeText(LoginActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(LoginActivity.this,MainActivity.class);
                    intent2.putExtra("userName",userName);
                    startActivity(intent2);
                    break;
                case REGISTER_FAIL:
                    Toast.makeText(LoginActivity.this,"用户已存在，注册失败",Toast.LENGTH_SHORT).show();
                    break;
                case FORMAT_ERROR:
                    Toast.makeText(LoginActivity.this,"输入格式错误",Toast.LENGTH_SHORT).show();
                    break;
                case EMPTY_WARNING:
                    Toast.makeText(LoginActivity.this,"输入不能为空",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

}