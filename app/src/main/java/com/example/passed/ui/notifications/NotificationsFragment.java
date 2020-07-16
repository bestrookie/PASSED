package com.example.passed.ui.notifications;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.passed.AnswerActivity;
import com.example.passed.CollectionActivity;
import com.example.passed.LoginActivity;
import com.example.passed.R;
import com.example.passed.WrongSetActivity;
import com.example.passed.bean.Practice;
import com.example.passed.bean.User;
import com.example.passed.dao.PracticeDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment implements View.OnClickListener{

    private NotificationsViewModel notificationsViewModel;
    private View view;
    private String userName;
    private List<Practice> practiceList;
    private Practice practiceInfo;
    private TextView textShow;
    private TextView textShow2;
    private TextView textShow3;
    private TextView textShow4;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle bundle = (Bundle)msg.obj;
            practiceList = (List<Practice>) bundle.get("list");
            practiceInfo = practiceList.get(0);
            int Finish = practiceInfo.getPracticeFinOne()+practiceInfo.getPracticeFinTwo()+practiceInfo.getPracticeFinThree();
            int True =  practiceInfo.getPracticeTrOne()+practiceInfo.getPracticeTrTwo()+practiceInfo.getPracticeTrThree();
            textShow.setText(True+"");
            textShow2.setText(practiceInfo.getPracticePass()+"");
            textShow3.setText(Finish+"");
            textShow4.setText(practiceInfo.getPracticeExam()+"");



        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my, container, false);


        view = root;

        TextView login = view.findViewById(R.id.my_name);
        login.setOnClickListener(this);

        TextView my_error = view.findViewById(R.id.text_view_wrongset);
        my_error.setOnClickListener(this);

        TextView my_collection = view.findViewById(R.id.text_view_collection);
        my_collection.setOnClickListener(this);

        ImageView my_image = view.findViewById(R.id.my_image);

        Intent intent = getActivity().getIntent();
        textShow = view.findViewById(R.id.text_show);
        textShow2 = view.findViewById(R.id.text_show2);
        textShow3 = view.findViewById(R.id.text_show3);
        textShow4 = view.findViewById(R.id.text_show4);
        userName = intent.getStringExtra("userName");

        if(userName != null){
            login.setText(userName);
            my_image.setImageResource(R.drawable.image);
            findPracticeInfo(userName);
        }

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_name:
                if(userName != null){
                    new AlertDialog.Builder(getContext()).setTitle("已经登录账号，确定要退出吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //无操作
                            }
                        }).show();
                }else {
                    Intent intent = new Intent(view.getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.text_view_wrongset:
                if(userName == null){
                    Toast.makeText(getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent2 = new Intent(view.getContext(), AnswerActivity.class);
                    intent2.putExtra("title","错题集");
                    intent2.putExtra("userName",userName);
                    startActivity(intent2);
                }
                break;
            case R.id.text_view_collection:
                if(userName == null){
                    Toast.makeText(getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent3 = new Intent(view.getContext(), AnswerActivity.class);
                    intent3.putExtra("title","收藏夹");
                    intent3.putExtra("userName",userName);
                    startActivity(intent3);
                }
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