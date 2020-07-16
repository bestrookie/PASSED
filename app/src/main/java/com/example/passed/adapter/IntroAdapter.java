package com.example.passed.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passed.DetailsIntroActivity;
import com.example.passed.R;
import com.example.passed.bean.TestIntro;

import java.util.List;

public class IntroAdapter extends RecyclerView.Adapter<IntroAdapter.ViewHolder> {
    List<TestIntro> testIntroList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View introView;
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            introView = itemView;
            imageView = itemView.findViewById(R.id.image_view);
            textView = itemView.findViewById(R.id.text_view_title);
        }
    }

    public IntroAdapter(List<TestIntro> testIntroList) {
        this.testIntroList = testIntroList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.intro_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.introView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                TestIntro testIntro = testIntroList.get(position);
                Intent intent = new Intent(holder.introView.getContext(), DetailsIntroActivity.class);
                intent.putExtra("detailTitle",testIntro.getTitle());
                intent.putExtra("detailContent",testIntro.getContent());
                holder.introView.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TestIntro testIntro = testIntroList.get(position);
        holder.imageView.setImageResource(testIntro.getImageId());
        holder.textView.setText(testIntro.getTitle());
    }

    @Override
    public int getItemCount() {
        return testIntroList.size();
    }

}
