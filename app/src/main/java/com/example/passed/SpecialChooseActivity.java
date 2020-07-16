package com.example.passed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class SpecialChooseActivity extends BaseActivity implements View.OnClickListener {
    private String userName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.special_practice);
        TextView multiple_choice = findViewById(R.id.multiple_choice);
        TextView jude_practice =findViewById(R.id.jude_practice);

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");

        multiple_choice.setOnClickListener(this);
        jude_practice.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.multiple_choice:
                Intent intent = new Intent(SpecialChooseActivity.this,AnswerActivity.class);
                intent.putExtra("userName",userName);
                intent.putExtra("title","单 选 专 练");
                startActivity(intent);
                break;
            case R.id.jude_practice:
                Intent intent2 = new Intent(SpecialChooseActivity.this,AnswerActivity.class);
                intent2.putExtra("userName",userName);
                intent2.putExtra("title","判 断 专 练");
                startActivity(intent2);
                break;
            default:
                break;
        }

    }
}
