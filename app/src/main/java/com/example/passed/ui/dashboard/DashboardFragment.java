package com.example.passed.ui.dashboard;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.passed.R;
import com.example.passed.adapter.IntroAdapter;
import com.example.passed.adapter.VideoAdapter;
import com.example.passed.bean.RoadProject;
import com.example.passed.bean.TestIntro;
import com.example.passed.dao.TestIntroDao;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private List<TestIntro> testIntroList;
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        view = root;
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<TestIntro> testIntroList = initTitle();
                Bundle bundle = new Bundle();
                bundle.putSerializable("testIntroList", (ArrayList) testIntroList);
                Message message = handler.obtainMessage(1,bundle);
                handler.sendMessage(message);
            }
        }).start();

//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle bundle = (Bundle) msg.obj;

            testIntroList = new ArrayList<>();
            testIntroList = (List<TestIntro>) bundle.get("testIntroList");

            RecyclerView recyclerView = view.findViewById(R.id.recyclerview_intro);
            LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
            recyclerView.setLayoutManager(layoutManager);

            IntroAdapter adapter = new IntroAdapter(testIntroList);
            recyclerView.setAdapter(adapter);
        }
    };

    private List<TestIntro> initTitle() {

        TestIntroDao testIntroDao = new TestIntroDao();
        List<TestIntro> testIntroList = testIntroDao.selectTestIntro();
        for(int i=0; i<testIntroList.size(); i++){
            if(i % 2 == 0){
                testIntroList.get(i).setImageId(R.drawable.blue_file);
            }else {
                testIntroList.get(i).setImageId(R.drawable.flow);
            }
        }
        return testIntroList;
    }
}