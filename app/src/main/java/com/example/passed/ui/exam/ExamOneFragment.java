package com.example.passed.ui.exam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.passed.AnswerActivity;
import com.example.passed.ExamFourFragment;
import com.example.passed.LoginActivity;
import com.example.passed.R;
import com.example.passed.ExamSecondFragment;
import com.example.passed.SpecialChooseActivity;
import com.example.passed.ExamThreeFragment;
import com.example.passed.bean.Practice;
import com.example.passed.dao.PracticeDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamOneFragment extends Fragment implements View.OnClickListener{

    private HomeViewModel homeViewModel;
    private View view;
    private String userName;
    private List<Practice>practiceList;
    private Practice practiceInfo;
    //顺序练习
    private TextView practicePlan;
    private TextView practiceResult;
    //随机练习
    private TextView practicePlan1;
    private TextView practiceResult1;
    //模拟考试
    private TextView practicePlan2;
    private TextView practiceResult2;
    //专项练习
    private TextView practicePlan3;
    private TextView practiceResult3;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle bundle = (Bundle) msg.obj;
            practiceList = (List<Practice>) bundle.get("list");
            practiceInfo = practiceList.get(0);
            int error = practiceInfo.getPracticeFinOne()-practiceInfo.getPracticeTrOne();
            int error1 = practiceInfo.getPracticeFinTwo()-practiceInfo.getPracticeTrTwo();
            int error2 = practiceInfo.getPracticeExam()-practiceInfo.getPracticePass();
            int error3 = practiceInfo.getPracticeFinThree()-practiceInfo.getPracticeTrThree();
            //顺序练习
            practicePlan.setText(practiceInfo.getPracticeFinOne()+"/500");
            practiceResult.setText(practiceInfo.getPracticeTrOne()+"/"+error);
            //随机练习
            practicePlan1.setText(practiceInfo.getPracticeFinTwo()+"/500");
            practiceResult1.setText(practiceInfo.getPracticeTrTwo()+"/"+error1);
            //模拟考试
            int practiceExam = practiceInfo.getPracticeExam();
            practicePlan2.setText(practiceExam+" ");
            practiceResult2.setText(practiceInfo.getPracticePass()+"/"+error2);
            //专项练习
            practicePlan3.setText(practiceInfo.getPracticeFinThree()+"/500");
            practiceResult3.setText(practiceInfo.getPracticeTrThree()+"/"+error3);


        }
    };

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_exam, container, false);
        view = root;
        Intent intent = getActivity().getIntent();
        /**
         * 信息设置
         */
        practicePlan = view.findViewById(R.id.practice_plan);
        practiceResult = view.findViewById(R.id.practice_result);
        practicePlan1 = view.findViewById(R.id.practice_plan1);
        practiceResult1 = view.findViewById(R.id.practice_result1);
        practicePlan2 = view.findViewById(R.id.practice_plan2);
        practiceResult2 = view.findViewById(R.id.practice_result2);
        practicePlan3 = view.findViewById(R.id.practice_plan3);
        practiceResult3 = view.findViewById(R.id.practice_result3);
        userName = intent.getStringExtra("userName");
        if(userName !=null){
            findPracticeInfo(userName);
        }
/**
 * 上导航栏点击事件
 */
        TextView exam_one = view.findViewById(R.id.exam_one);
        TextView exam_two = view.findViewById(R.id.exam_two);
        TextView exam_three = view.findViewById(R.id.exam_three);
        TextView exam_four = view.findViewById(R.id.exam_four);
        exam_one.setOnClickListener(this);
        exam_two.setOnClickListener(this);
        exam_three.setOnClickListener(this);
        exam_four.setOnClickListener(this);
/**
 * 主体点击事件
 */
        TextView one_order = view.findViewById(R.id.one_order);
        TextView one_random = view.findViewById(R.id.one_random);
        TextView one_simulation = view.findViewById(R.id.one_simulation);
        TextView one_special = view.findViewById(R.id.one_special);
        one_order.setOnClickListener(this);
        one_random.setOnClickListener(this);
        one_simulation.setOnClickListener(this);
        one_special.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exam_one:
                ExamOneFragment examOneFragment = new ExamOneFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.nav_host_fragment,examOneFragment);
                fragmentTransaction.commit();
                break;
            case R.id.exam_two:
                ExamSecondFragment examSecondFragment = new ExamSecondFragment();
                FragmentManager f = getFragmentManager();
                FragmentTransaction ff = f.beginTransaction();
                ff.add(R.id.nav_host_fragment,examSecondFragment);
                ff.commit();
                break;
            case R.id.exam_three:
                ExamThreeFragment examThreeFragment = new ExamThreeFragment();
                FragmentManager fr = getFragmentManager();
                FragmentTransaction ftt = fr.beginTransaction();
                ftt.add(R.id.nav_host_fragment,examThreeFragment);
                ftt.commit();
                break;
            case R.id.exam_four:
             ExamFourFragment exam_four = new ExamFourFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.nav_host_fragment,exam_four);
                ft.commit();
                break;
            case R.id.one_order:
                Intent intent = new Intent(view.getContext(), AnswerActivity.class);
                intent.putExtra("title","顺 序 练 习");
                intent.putExtra("userName",userName);
                startActivity(intent);
                break;
            case R.id.one_random:
                Intent intent1 = new Intent(view.getContext(),AnswerActivity.class);
                intent1.putExtra("title","随 机 练 习");
                intent1.putExtra("userName",userName);
                startActivity(intent1);
                break;
            case R.id.one_simulation:
                Intent intent2 = new Intent(view.getContext(), AnswerActivity.class);
                intent2.putExtra("title","模 拟 考 试");
                intent2.putExtra("userName",userName);
                startActivity(intent2);
                break;
            case R.id.one_special:
                Intent intent3 = new Intent(view.getContext(), SpecialChooseActivity.class);
                intent3.putExtra("userName",userName);
                startActivity(intent3);
                break;
            default:
                break;
        }
    }
    public void findPracticeInfo(final String userId){
        if (userId != null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    PracticeDao practiceDao = new PracticeDao();
                    List<Practice> practiceInfo = new ArrayList<Practice>();
                    try {
                        practiceInfo.add(practiceDao.selectPracticeInfoById(userId));
                        Bundle bundle = new Bundle();
                        bundle.putInt("id",10);
                        bundle.putSerializable("list",(ArrayList)practiceInfo);
                        Message message = handler.obtainMessage(1,bundle);
                        handler.sendMessage(message);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }
}