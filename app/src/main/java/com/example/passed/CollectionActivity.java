package com.example.passed;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.passed.bean.CollectionSet;
import com.example.passed.bean.ErrorSet;
import com.example.passed.bean.Exercise;
import com.example.passed.bean.RoadProject;
import com.example.passed.dao.CollectionDao;
import com.example.passed.dao.ErrorSetDao;
import com.example.passed.dao.ExerciseDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionActivity extends BaseActivity {

    private static final int COLLECTIONEXERCISE = 1;
    private List<CollectionSet> collectionSetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        new Thread(new Runnable() {
            @Override
            public void run() {
                CollectionDao collectionDao = new CollectionDao();
                ExerciseDao exerciseDao = new ExerciseDao();
                List<Exercise> exerciseList = new ArrayList<>();
                List<CollectionSet> collectionSetList = collectionDao.selectCollection("");
                for(int i=0; i<collectionSetList.size(); i++){
                    Exercise exercise = exerciseDao.selectExerciseById(collectionSetList.get(i).getExerciseId());
                    exerciseList.add(exercise);
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("collectionExercise",(ArrayList)exerciseList);
                Message message = handler.obtainMessage(COLLECTIONEXERCISE,bundle);
                handler.sendMessage(message);
            }
        }).start();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = (Bundle) msg.obj;
            switch (msg.what){
                case COLLECTIONEXERCISE:
                    collectionSetList = new ArrayList<>();
                    collectionSetList = (List<CollectionSet>) bundle.get("collectionExercise");
                    //显示收藏的题目
                    break;
                default:
                    break;
            }
        }
    };
}