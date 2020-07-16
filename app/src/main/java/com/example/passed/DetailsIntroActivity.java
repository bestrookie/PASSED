package com.example.passed;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsIntroActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_intro);

        TextView textView_title = findViewById(R.id.text_view_title);
        TextView textView_content = findViewById(R.id.text_view_content);
        textView_content.setMovementMethod(ScrollingMovementMethod.getInstance());

        Intent intent = getIntent();
        String title = intent.getStringExtra("detailTitle");
        String content = intent.getStringExtra("detailContent");

        if(title != null && content != null){
            textView_title.setText(title);
            textView_content.setText(content);
        }

    }
}