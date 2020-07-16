package com.example.passed;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ExamFourFragment extends Fragment implements View.OnClickListener {
    private View view;
    private String userName;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_exam_four,container,false);
        Intent intent = getActivity().getIntent();
        userName = intent.getStringExtra("userName");

        TextView practice = view.findViewById(R.id.four_order);
        TextView practice_one = view.findViewById(R.id.four_random);
        TextView practice_two = view.findViewById(R.id.four_simulation);
        TextView practice_three = view.findViewById(R.id.four_special);
        practice.setOnClickListener(this);
        practice_one.setOnClickListener(this);
        practice_two.setOnClickListener(this);
        practice_three.setOnClickListener(this);

        return  view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.four_order:
                Intent intent = new Intent(view.getContext(), AnswerActivity.class);
                intent.putExtra("title","顺 序 练 习");
                intent.putExtra("userName",userName);
                startActivity(intent);
                break;
            case R.id.four_random:
                Intent intent1 = new Intent(view.getContext(),AnswerActivity.class);
                intent1.putExtra("title","随 机 练 习");
                intent1.putExtra("userName",userName);
                startActivity(intent1);
                break;
            case R.id.four_simulation:
                Intent intent2 = new Intent(view.getContext(), AnswerActivity.class);
                intent2.putExtra("title","模 拟 考 试");
                intent2.putExtra("userName",userName);
                startActivity(intent2);
                break;
            case R.id.four_special:
                Intent intent3 = new Intent(view.getContext(),SpecialChooseActivity.class);
                intent3.putExtra("userName",userName);
                startActivity(intent3);
                break;
            default:
                break;
        }

    }
}