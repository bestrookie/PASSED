package com.example.passed;

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
import com.example.passed.dao.VideoDao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExamThreeFragment extends Fragment implements View.OnClickListener {
    private static List<RoadProject> roadProjects;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_third,container,false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RoadProject> roadProjectList = initVideo();
                Bundle bundle = new Bundle();
                bundle.putSerializable("videoList", (Serializable) roadProjectList);
                Message message = handler.obtainMessage(2,bundle);
                handler.sendMessage(message);
            }
        }).start();

        TextView third_intro = view.findViewById(R.id.three_intro);
        TextView flow_intro = view.findViewById(R.id.three_flow_intro);
        third_intro.setOnClickListener(this);
        flow_intro.setOnClickListener(this);

        return view;
    }
    private List<RoadProject> initVideo() {
        List<RoadProject> videoList = new ArrayList<>();

        String[] nameList = new String[]{"灯光模拟","上车准备","起步","直线行驶","变更车道","通过路口", "加减档操作",
                "路口左转弯","路口右转弯","会车","超车","掉头","靠边停车","通过人行横道", "通过学校区域","通过公共汽车站"};
        Integer[] pathList = new Integer[]{R.raw.light,R.raw.ready,R.raw.start,R.raw.straight_drive,R.raw.change_line,
        R.raw.through_cross,R.raw.add_sub,R.raw.left_turn,R.raw.right_turn,R.raw.encounter,R.raw.overtake,R.raw.return_head,
        R.raw.park_side,R.raw.through_zebra,R.raw.through_school,R.raw.through_bus};
        String rootPath = "android.resource://com.example.passed/";

        for(int i=0; i<nameList.length; i++){
            MyVideo myVideo = new MyVideo(nameList[i], Uri.parse(rootPath+pathList[i]));
            RoadProject roadProject = new RoadProject(true,myVideo,"科三");
            videoList.add(roadProject);
        }
        return videoList;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle bundle = (Bundle) msg.obj;
            roadProjects = new ArrayList<>();
            roadProjects = (List<RoadProject>) bundle.get("videoList");
            RecyclerView video_recyclerview = view.findViewById(R.id.video_recyclerview);
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            video_recyclerview.setLayoutManager(layoutManager);
            VideoAdapter videoAdapter = new VideoAdapter(roadProjects);
            video_recyclerview.setAdapter(videoAdapter);
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.two_intro:

                break;
            case R.id.two_flow_intro:

                break;
            default:
                break;
        }
    }
}
