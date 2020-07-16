package com.example.passed;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.passed.bean.CollectionSet;
import com.example.passed.bean.ErrorSet;
import com.example.passed.bean.Exercise;
import com.example.passed.dao.ErrorSetDao;
import com.example.passed.dao.ExerciseDao;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class WrongSetActivity extends BaseActivity {

    private static final int ERROREXERCISE = 1;
    private List<ErrorSet> errorSetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_set);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ErrorSetDao errorSetDao = new ErrorSetDao();
                ExerciseDao exerciseDao = new ExerciseDao();
                List<Exercise> exerciseList = new ArrayList<>();
                List<ErrorSet> errorSetList = errorSetDao.selectErrorSet("");
                for(int i=0; i<errorSetList.size(); i++){
                    Exercise exercise = exerciseDao.selectExerciseById(errorSetList.get(i).getExerciseId());
                    exerciseList.add(exercise);
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("errorExercise",(ArrayList)exerciseList);
                Message message = handler.obtainMessage(ERROREXERCISE,bundle);
                handler.sendMessage(message);
            }
        }).start();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = (Bundle) msg.obj;
            switch (msg.what){
                case ERROREXERCISE:
                    errorSetList = new ArrayList<>();
                    errorSetList = (List<ErrorSet>) bundle.get("errorExercise");
                    //显示错误的题目
                    Intent intent = new Intent();
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };
}