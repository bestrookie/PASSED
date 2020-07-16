package com.example.passed;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.passed.adapter.VideoAdapter;
import com.example.passed.bean.MyVideo;
import com.example.passed.bean.RoadProject;
import com.example.passed.bean.TestIntro;
import com.example.passed.dao.TestIntroDao;
import com.example.passed.dao.VideoDao;

import java.util.ArrayList;
import java.util.List;

public class ExamSecondFragment extends Fragment implements View.OnClickListener {

    private static final int VIDEOLIST = 1;
    private static final int SECONDINTRO = 2;
    private static final int SECONDFLOW = 3;
    private RecyclerView video_recyclerview;
    private List<RoadProject> roadProjects;
    private TestIntro secondIntro;
    private TestIntro secondFlow;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_two,container,false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RoadProject> roadProjectList = initVideo();
                Bundle bundle = new Bundle();
                bundle.putSerializable("videoList", (ArrayList) roadProjectList);
                Message message = handler.obtainMessage(VIDEOLIST,bundle);
                handler.sendMessage(message);
            }
        }).start();

        TextView second_intro = view.findViewById(R.id.two_intro);
        TextView flow_intro = view.findViewById(R.id.two_flow_intro);
        second_intro.setOnClickListener(this);
        flow_intro.setOnClickListener(this);

        return view;

    }

    //显示视频
    private List<RoadProject> initVideo() {
        List<RoadProject> videoList = new ArrayList<>();

        String[] nameList = new String[]{"倒车入库","侧方停车","坡道定点停车和起步","曲线行驶","直角转弯",
                "窄路掉头","模拟紧急情况处置","模拟隧道行驶","模拟高速公路停车取卡"};
        Integer[] pathList = new Integer[]{R.raw.parking,R.raw.side_parking,R.raw.ramp,R.raw.curve,
                R.raw.quarter_turn,R.raw.narrow,R.raw.urgent,R.raw.tunnel,R.raw.freeway};
        Boolean[] propertyList = new Boolean[]{true,true,true,true,true,false,false,false,false};
        String rootPath = "android.resource://com.example.passed/";

        for(int i=0; i<nameList.length; i++){
            MyVideo myVideo = new MyVideo(nameList[i],Uri.parse(rootPath+pathList[i]));
            RoadProject roadProject = new RoadProject(propertyList[i],myVideo,"科二");
            videoList.add(roadProject);
        }
        return videoList;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.two_intro:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TestIntroDao testIntroDao = new TestIntroDao();
                        TestIntro testIntro = testIntroDao.selectTestIntroByTitle("科目二考试介绍");
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("two_Intro", testIntro);
                        Message message = handler.obtainMessage(SECONDINTRO,bundle);
                        handler.sendMessage(message);
                    }
                }).start();
                break;
            case R.id.two_flow_intro:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TestIntroDao testIntroDao = new TestIntroDao();
                        TestIntro testIntro = testIntroDao.selectTestIntroByTitle("科目二考试流程");
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("two_flow", testIntro);
                        Message message = handler.obtainMessage(SECONDFLOW,bundle);
                        handler.sendMessage(message);
                    }
                }).start();
                break;
            default:
                break;
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = (Bundle) msg.obj;
            switch (msg.what){
                case VIDEOLIST:
                    roadProjects = new ArrayList<>();
                    roadProjects = (List<RoadProject>) bundle.get("videoList");
                    video_recyclerview = view.findViewById(R.id.video_recyclerview);
                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                    video_recyclerview.setLayoutManager(layoutManager);
                    VideoAdapter videoAdapter = new VideoAdapter(roadProjects);
                    video_recyclerview.setAdapter(videoAdapter);
                    break;
                case SECONDINTRO:
                    secondIntro = (TestIntro) bundle.get("two_Intro");
                    Intent intent = new Intent(view.getContext(),DetailsIntroActivity.class);
                    intent.putExtra("detailTitle",secondIntro.getTitle());
                    intent.putExtra("detailContent",secondIntro.getContent());
                    startActivity(intent);
                    break;
                case SECONDFLOW:
                    secondFlow = (TestIntro) bundle.get("two_flow");
                    Intent intent1 = new Intent(view.getContext(),DetailsIntroActivity.class);
                    intent1.putExtra("detailTitle",secondFlow.getTitle());
                    intent1.putExtra("detailContent",secondFlow.getContent());
                    startActivity(intent1);
                    break;
            }
        }
    };

}