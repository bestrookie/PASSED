package com.example.passed;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.passed.bean.CollectionSet;
import com.example.passed.bean.ErrorSet;
import com.example.passed.bean.Exercise;
import com.example.passed.bean.Practice;
import com.example.passed.bean.User;
import com.example.passed.dao.CollectionDao;
import com.example.passed.dao.ErrorSetDao;
import com.example.passed.dao.ExerciseDao;
import com.example.passed.dao.PracticeDao;
import com.example.passed.dao.UserDao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnswerActivity extends BaseActivity {

    ArrayList<Exercise> exerciseList = new ArrayList<>();//题目列表
    String title;
    RadioGroup radioGroup;//选择列表
    TextView exerciseType;//练习类型
    TextView textCollect;//收藏按钮
    ImageView imageCollect;//收藏图标
    int count = 0;//进度控制
    TextView describe;//题目描述
    TextView exerciseReminder;//答题提示
    private Practice practiceInfo;
    RadioButton raBtOne;
    RadioButton raBtTwo;
    RadioButton raBtThree;
    RadioButton raBtFour;
    String userName;
    ImageView buttonLeft;
    ImageView buttonRight;

    /**
     * 接受数据更新UI
     */
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressLint("ResourceType")
        @Override
        public void handleMessage(@NonNull Message msg) {

            super.handleMessage(msg);
            Bundle bundle = (Bundle) msg.obj;
            List<Practice> practiceList = new ArrayList<>();
            exerciseList = (ArrayList<Exercise>) bundle.get("list");
            practiceList = (List<Practice>) bundle.get("list2");
            practiceInfo = practiceList.get(0);
            switch (title){
                case "顺 序 练 习":
                    orderPractice(exerciseList,practiceInfo);
                    break;
                case "随 机 练 习":
                    randomPractice(exerciseList,practiceInfo);
                    break;
                case "模 拟 考 试":
                    examPractice(exerciseList,practiceInfo);
                    break;
                case "单 选 专 练":
                    selectPractice(exerciseList,practiceInfo);
                    break;
                case "判 断 专 练":
                    judePractice(exerciseList,practiceInfo);
                    break;
                case "错题集":
                    showErrorSet(exerciseList);
                    break;
                case "收藏夹":
                    showCollection(exerciseList);
                    break;
                default:
                    break;
            }
        }

        /*
        * 错题集
        * */
        private void showErrorSet(ArrayList<Exercise> exerciseList) {
            if(exerciseList == null){
                Toast.makeText(AnswerActivity.this,"还没有错题呦",Toast.LENGTH_SHORT).show();
            }else {
                init(exerciseList);
            }

        }

        /*
        * 收藏夹
        * */
        private void showCollection(ArrayList<Exercise> exerciseList){
            if(exerciseList == null){
                Toast.makeText(AnswerActivity.this,"还没有收藏呦",Toast.LENGTH_SHORT).show();
            }else {
                init(exerciseList);
            }

        }

        /**
         * 顺序练习
         * @param exercises
         */
        public void orderPractice(final List<Exercise> exercises, final Practice practice) {
            init(exercises);//答题信息放到控件
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int Finish = practice.getPracticeFinOne();
                    int True = practice.getPracticeTrOne();
                    int id = exercises.get(count).getOption_answer().get(0);
                    if (checkedId != -1) {
                        if (checkedId == id) {
                            count++;
                            True++;
                            if (count == exercises.size()) {
                                new AlertDialog.Builder(AnswerActivity.this)
                                        .setTitle("完成训练").setMessage("恭喜你已经完成全部训练").setPositiveButton("确定", null).show();
                                count--;
                            }

                        } else {
                            new AlertDialog.Builder(AnswerActivity.this)
                                    .setTitle("答题错误").setMessage(exercises.get(count).getExercise_explain()).setPositiveButton("确定",null).show();
                            //将错题添加到错题本
                            ErrorSet errorSet = new ErrorSet(userName,exercises.get(count).getExercise_id());
                            addError(errorSet);
                        }
                        Finish++;
                        practice.setPracticeFinOne(Finish);
                        practice.setPracticeTrOne(True);
                        try {
                            updatePracticeInfo(practice,1);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        update(exercises);
                    }
                }

            });
            //答题提示
            exerciseReminder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(AnswerActivity.this)
                            .setTitle("答题提示").setMessage(exercises.get(count).getExercise_explain()).setPositiveButton("确定", null).show();
                }
            });
            //添加收藏设置
            textCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageCollect.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.result));
                    //添加收藏
                    CollectionSet collectionSet = new CollectionSet(userName,exercises.get(count).getExercise_id());
                    addCollection(collectionSet);
                }
            });
            //删除收藏设置
            imageCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageCollect.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.collection));
                    //删除收藏
                    deleteCollection(userName,exercises.get(count).getExercise_id());
                }
            });
            buttonLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (count>=1){
                        count--;
                        update(exercises);
                    }

                }
            });
            buttonRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count<exercises.size()-1){
                        count++;
                        update(exercises);
                    }
                }
            });
        }
        /**
         * 随机答题
         * @param exercises
         */
        public void randomPractice(final List<Exercise>exercises, final Practice practice){
            Set<Exercise> a = new HashSet<>();
            final List<Exercise> ex = new ArrayList<>();
            for (Exercise exercise : exercises) {
                a.add(exercise);
            }
            for (Exercise exercise : a) {
                ex.add(exercise);
            }
            init(ex);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int Finish = practice.getPracticeFinTwo();
                    int True = practice.getPracticeTrTwo();
                    int id = ex.get(count).getOption_answer().get(0);
                    if (checkedId !=-1){
                        if (checkedId == id) {
                            count++;
                            True++;
                            if (count == ex.size()) {
                                new AlertDialog.Builder(AnswerActivity.this)
                                        .setTitle("完成训练").setMessage("恭喜你已经完成全部训练").setPositiveButton("确定",null).show();
                                count--;
                            }
                        } else {
                            new AlertDialog.Builder(AnswerActivity.this)
                                    .setTitle("答题错误").setMessage(ex.get(count).getExercise_explain()).setPositiveButton("确定",null).show();
                            ErrorSet errorSet = new ErrorSet(userName,exercises.get(count).getExercise_id());
                            addError(errorSet);
                        }
                        Finish++;
                        practice.setPracticeFinTwo(Finish);
                        practice.setPracticeTrTwo(True);
                        try {
                            updatePracticeInfo(practice,2);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        update(ex);
                    }
                }
            });
            exerciseReminder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(AnswerActivity.this)
                            .setTitle("答题提示").setMessage(ex.get(count).getExercise_explain()).setPositiveButton("确定",null).show();
                }
            });
            //添加收藏设置
            textCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageCollect.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.result));
                    //添加收藏
                    CollectionSet collectionSet = new CollectionSet(userName,exercises.get(count).getExercise_id());
                    addCollection(collectionSet);
                }
            });
            //删除收藏设置
            imageCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageCollect.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.collection));
                    //删除收藏
                    deleteCollection(userName,exercises.get(count).getExercise_id());
                }
            });
            buttonLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (count>=1){
                        count--;
                        update(exercises);
                    }

                }
            });
            buttonRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count<exercises.size()-1){
                        count++;
                        update(exercises);
                    }
                }
            });
        }
        /**
         * 模拟考试
         * @param exercises
         */
        public void examPractice(List<Exercise> exercises, final Practice practice){
            Set<Exercise> a = new HashSet<>();
            final List<Exercise> ex = new ArrayList<>();
            for (int i = 0; i <5 ; i++) {
                a.add(exercises.get(i));
            }
            for (Exercise exercise : a) {
                ex.add(exercise);
            }
            init(ex);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int Exam = practice.getPracticeExam();
                    int Pass = practice.getPracticePass();
                    int id = ex.get(count).getOption_answer().get(0);
                    if(checkedId != -1){
                        if (checkedId == id) {
                            count++;
                            if (count == ex.size()) {
                                new AlertDialog.Builder(AnswerActivity.this)
                                        .setTitle("完成训练").setMessage("恭喜你已经完成全部训练").setPositiveButton("确定",null).show();
                                Pass++;
                                count--;
                            }
                        } else {
                            new AlertDialog.Builder(AnswerActivity.this)
                                    .setTitle("成绩不合格").setMessage("当前成绩不合格").setPositiveButton("确定",null).show();
                            Exam++;
                            for (int i = 0;i<radioGroup.getChildCount();i++ ){
                                radioGroup.getChildAt(i).setEnabled(false);
                            }
                            //将错题添加到错题本
                            ErrorSet errorSet = new ErrorSet(userName,ex.get(count).getExercise_id());
                            addError(errorSet);
                        }
                        practice.setPracticeExam(Exam);
                        practice.setPracticePass(Pass);
                        try {
                            updatePracticeInfo(practice,4);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        update(ex);
                    }
                }
            });
            exerciseReminder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(AnswerActivity.this)
                            .setTitle("答题提示").setMessage(ex.get(count).getExercise_explain()).setPositiveButton("确定",null).show();
                }
            });
            //添加收藏设置
            textCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageCollect.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.result));
                    //添加收藏
                    CollectionSet collectionSet = new CollectionSet(userName,ex.get(count).getExercise_id());
                    addCollection(collectionSet);
                }
            });
            //删除收藏设置
            imageCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageCollect.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.collection));
                    //删除收藏
                    deleteCollection(userName,ex.get(count).getExercise_id());
                }
            });
            buttonLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (count>=1){
                        count--;
                        update(ex);
                    }

                }
            });
            buttonRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count<ex.size()-1){
                        count++;
                        update(ex);
                    }
                }
            });

        }

        /**
         * 选择专练
         * @param exercises
         */
        public void selectPractice(final List<Exercise>exercises, final Practice practice){
            init(exercises);//答题信息放到控件
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int id = exercises.get(count).getOption_answer().get(0);
                    int Finish = practice.getPracticeFinThree();
                    int True = practice.getPracticeTrThree();
                    if (checkedId != -1) {
                        if (checkedId == id) {
                            count++;
                            True++;
                            if (count == exercises.size()) {
                                new AlertDialog.Builder(AnswerActivity.this)
                                        .setTitle("完成训练").setMessage("恭喜你已经完成全部训练").setPositiveButton("确定", null).show();
                                count--;
                            }

                        } else {
                            new AlertDialog.Builder(AnswerActivity.this)
                                    .setTitle("答题错误").setMessage(exercises.get(count).getExercise_explain()).setPositiveButton("确定",null).show();
                            //将错题添加到错题本
                            ErrorSet errorSet = new ErrorSet(userName,exercises.get(count).getExercise_id());
                            addError(errorSet);
                        }
                        Finish++;
                        practice.setPracticeFinThree(Finish);
                        practice.setPracticeTrThree(True);
                        try {
                            updatePracticeInfo(practice,3);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        update(exercises);
                    }
                }

            });
            //答题提示
            exerciseReminder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(AnswerActivity.this)
                            .setTitle("答题提示").setMessage(exercises.get(count).getExercise_explain()).setPositiveButton("确定", null).show();
                }
            });
            //添加收藏设置
            textCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageCollect.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.result));
                    //添加收藏
                    CollectionSet collectionSet = new CollectionSet(userName,exercises.get(count).getExercise_id());
                    addCollection(collectionSet);
                }
            });
            //删除收藏设置
            imageCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageCollect.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.collection));
                    //删除收藏
                    deleteCollection(userName,exercises.get(count).getExercise_id());
                }
            });
            buttonLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (count>=1){
                        count--;
                        update(exercises);
                    }

                }
            });
            buttonRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count<exercises.size()-1){
                        count++;
                        update(exercises);
                    }
                }
            });
        }

        /**
         * 判断专练
         * @param exercises
         */
        public void judePractice(final List<Exercise>exercises, final Practice practice){
            init(exercises);//答题信息放到控件
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int Finish = practice.getPracticeFinThree();
                    int True = practice.getPracticeTrThree();
                    int id = exercises.get(count).getOption_answer().get(0);
                    if (checkedId != -1) {
                        if (checkedId == id) {
                            count++;
                            True++;
                            if (count == exercises.size()) {
                                new AlertDialog.Builder(AnswerActivity.this)
                                        .setTitle("完成训练").setMessage("恭喜你已经完成全部训练").setPositiveButton("确定", null).show();
                                count--;
                            }

                        } else {
                            new AlertDialog.Builder(AnswerActivity.this)
                                    .setTitle("答题错误").setMessage(exercises.get(count).getExercise_explain()).setPositiveButton("确定",null).show();
                            //将错题添加到错题本
                            ErrorSet errorSet = new ErrorSet(userName,exercises.get(count).getExercise_id());
                            addError(errorSet);
                        }
                        Finish++;
                        practice.setPracticeFinThree(Finish);
                        practice.setPracticeTrThree(True);
                        try {
                            updatePracticeInfo(practice,3);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        update(exercises);
                    }
                }

            });
            //答题提示
            exerciseReminder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(AnswerActivity.this)
                            .setTitle("答题提示").setMessage(exercises.get(count).getExercise_explain()).setPositiveButton("确定", null).show();
                }
            });
            //添加收藏设置
            textCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageCollect.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.result));
                    //添加收藏
                    CollectionSet collectionSet = new CollectionSet(userName,exercises.get(count).getExercise_id());
                    addCollection(collectionSet);
                }
            });
            //删除收藏设置
            imageCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageCollect.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.collection));
                    //删除收藏
                    deleteCollection(userName,exercises.get(count).getExercise_id());
                }
            });
            buttonLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (count>=1){
                        count--;
                        update(exercises);
                    }

                }
            });
            buttonRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count<exercises.size()-1){
                        count++;
                        update(exercises);
                    }
                }
            });
        }
        /**
         *初始化选择题控件
         * @param exercises
         */
        @SuppressLint("ResourceType")
        public void init(List<Exercise> exercises) {
            if(describe == null){
                System.out.println("describe");
            }if(exercises == null){
                System.out.println("exercises");
            }
            describe.setText(exercises.get(count).getExercise_describe());
            exerciseType.setText(exercises.get(count).getType_name());
            raBtOne = new RadioButton(AnswerActivity.this);
            raBtTwo = new RadioButton(AnswerActivity.this);
            raBtThree = new RadioButton(AnswerActivity.this);
            raBtFour = new RadioButton(AnswerActivity.this);
            raBtOne.setText(exercises.get(count).getOption_one());
            raBtOne.setId(1);
            raBtTwo.setText(exercises.get(count).getOption_two());
            raBtTwo.setId(2);
            radioGroup.addView(raBtOne);
            radioGroup.addView(raBtTwo);
            if (!exercises.get(count).getOption_three().equals("0")) {
                raBtThree.setText(exercises.get(count).getOption_three());
                raBtThree.setId(3);
                raBtFour.setText(exercises.get(count).getOption_four());
                raBtFour.setId(4);
                radioGroup.addView(raBtThree);
                radioGroup.addView(raBtFour);
            }else {
                radioGroup.removeView(raBtThree);
                radioGroup.removeView(raBtFour);
            }

        }
        /**
         * 更新数据
         * @param exercises
         */
        @SuppressLint("ResourceType")
        public void update(List<Exercise> exercises){
            describe.setText(exercises.get(count).getExercise_describe());
            exerciseType.setText(exercises.get(count).getType_name());
            raBtOne.setText(exercises.get(count).getOption_one());
            raBtTwo.setText(exercises.get(count).getOption_two());
            if (!exercises.get(count).getOption_three().equals("0")) {
                if (radioGroup.getChildCount() < 3){
                    raBtThree = new RadioButton(AnswerActivity.this);
                    raBtFour =new RadioButton(AnswerActivity.this);
                    raBtThree.setText(exercises.get(count).getOption_three());
                    raBtFour.setText(exercises.get(count).getOption_four());
                    raBtThree.setId(3);
                    raBtFour.setId(4);
                    radioGroup.addView(raBtThree);
                    radioGroup.addView(raBtFour);
                }else {
                    raBtThree.setText(exercises.get(count).getOption_three());
                    raBtFour.setText(exercises.get(count).getOption_four());
                }
                radioGroup.clearCheck();
            }else {
                radioGroup.removeView(raBtThree);
                radioGroup.removeView(raBtFour);
                radioGroup.clearCheck();
            }
        }

        /**
         * 更新练习信息
         * @param practiceInfo
         * @param jude
         */
        public void updatePracticeInfo(final Practice practiceInfo, final int jude) throws SQLException {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    PracticeDao practiceDao = new PracticeDao();
                    try {
                        practiceDao.updatePracticeInfo(practiceInfo,jude);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    };



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice_answer);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        userName = intent.getStringExtra("userName");

        TextView textView = findViewById(R.id.practice_name);
        exerciseType = findViewById(R.id.type_text);
        //动态设置标题
        textView.setText(title);
        buttonLeft = findViewById(R.id.button_left);
        buttonRight = findViewById(R.id.button_right);
        describe = findViewById(R.id.describe_text);
        radioGroup = findViewById(R.id.radioGroup);
        textCollect = findViewById(R.id.text_collect);
        imageCollect = findViewById(R.id.image_collect);
        exerciseReminder  = findViewById(R.id.text_reminder);
        //根据标题加载题目
        judeTitle(title);



    }

    /**
     * 判断选择的类型加载习题
     *
     * @param title
     */
    public void judeTitle(String title) {
        switch (title) {
            case "单 选 专 练":
                updateDataByType("单选");
                break;
            case "判 断 专 练":
                updateDataByType("判断");
                break;
            case "顺 序 练 习":
                updateDateAll();
                break;
            case "随 机 练 习":
                updateDateAll();
                break;
            case "模 拟 考 试":
                updateDateAll();
                break;
            case "收藏夹":
                updateCollection();
                break;
            case "错题集":
                updateErrorSet();
                break;
            default:
                break;
        }
    }

    /*
    * 加载错误习题
    * */
    private void updateErrorSet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ErrorSetDao errorSetDao = new ErrorSetDao();
                ExerciseDao exerciseDao = new ExerciseDao();
                List<Exercise> exerciseList = new ArrayList<>();
                List<ErrorSet> errorSetList = errorSetDao.selectErrorSet(userName);

                for(int i=0; i<errorSetList.size(); i++){
                    Exercise exercise = exerciseDao.selectExerciseById(errorSetList.get(i).getExerciseId());
                    exerciseList.add(exercise);
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("list",(ArrayList)exerciseList);
                Message message = handler.obtainMessage(1,bundle);
                handler.sendMessage(message);

            }
        }).start();
    }

    /*
    * 加载收藏习题
    * */
    private void updateCollection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                CollectionDao collectionDao = new CollectionDao();
                ExerciseDao exerciseDao = new ExerciseDao();
                PracticeDao practiceDao = new PracticeDao();
                List<Exercise> exerciseList = new ArrayList<>();
                List<CollectionSet> collectionSetList = collectionDao.selectCollection(userName);
                for(int i=0; i<collectionSetList.size(); i++){
                    Exercise exercise = exerciseDao.selectExerciseById(collectionSetList.get(i).getExerciseId());
                    exerciseList.add(exercise);
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("list",(ArrayList)exerciseList);
                Message message = handler.obtainMessage(1,bundle);
                handler.sendMessage(message);

            }
        }).start();
    }

    /**
     * 加载所有的练习题
     */
    public void updateDateAll() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Exercise> exerciseList = new ArrayList<>();
                List<Practice> practiceList = new ArrayList<>();
                PracticeDao practiceDao = new PracticeDao();
                ExerciseDao exerciseDao = new ExerciseDao();
                try {
                    exerciseList = exerciseDao.selectAllExercise();
                    practiceList.add(practiceDao.selectPracticeInfoById(userName));
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", 10);
                    bundle.putSerializable("list", (ArrayList) exerciseList);
                    bundle.putSerializable("list2", (ArrayList) practiceList);
                    Message message = handler.obtainMessage(1, bundle);
                    handler.sendMessage(message);


                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 加载所选类型的题
     *
     * @param type
     */
    public void updateDataByType(final String type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Practice> practiceList = new ArrayList<>();
                List<Exercise> exerciseList = new ArrayList<>();
                PracticeDao practiceDao = new PracticeDao();
                ExerciseDao exerciseDao = new ExerciseDao();
                try {
                    exerciseList = exerciseDao.selectExerciseByType(type);
                    practiceList.add(practiceDao.selectPracticeInfoById(userName));
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", 10);
                    bundle.putSerializable("list", (ArrayList) exerciseList);
                    bundle.putSerializable("list2", (ArrayList) practiceList);
                    Message message = handler.obtainMessage(1, bundle);
                    handler.sendMessage(message);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    /*
     * 取消收藏
     * */
    private void deleteCollection(final String userName, final long exercise_id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(userName == null){
                    Toast.makeText(AnswerActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                }else {
                    CollectionDao collectionDao = new CollectionDao();
                    collectionDao.deleteCollection(userName,exercise_id);
                }
            }
        }).start();
    }

    /*
     * 添加收藏
     * */
    private void addCollection(final CollectionSet collectionSet) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(userName == null){
                    Toast.makeText(AnswerActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                }else {
                    CollectionDao collectionDao = new CollectionDao();
                    ExerciseDao exerciseDao = new ExerciseDao();
                    UserDao userDao = new UserDao();
                    Exercise exercise = exerciseDao.selectExerciseById(collectionSet.getExerciseId());
                    User user = userDao.selectUser(collectionSet.getUserId());
                    boolean result = collectionDao.addCollection(exercise,user);
                }
            }
        }).start();
    }

    /*
     *  添加错误的习题
     */
    private void addError(final ErrorSet errorSet) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                if(userName != null){
                    ErrorSetDao errorSetDao = new ErrorSetDao();
                    ExerciseDao exerciseDao = new ExerciseDao();
                    UserDao userDao = new UserDao();
                    Exercise exercise = exerciseDao.selectExerciseById(errorSet.getExerciseId());
                    User user = userDao.selectUser(errorSet.getUserId());
                    boolean result = errorSetDao.addError(exercise,user);
                }
            }
        }).start();
    }

}
