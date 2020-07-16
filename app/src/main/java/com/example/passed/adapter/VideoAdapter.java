package com.example.passed.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passed.R;
import com.example.passed.bean.MyVideo;
import com.example.passed.bean.RoadProject;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    List<RoadProject> videoList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView videoNameView;
        VideoView videoView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            videoNameView = itemView.findViewById(R.id.tv_itemname);
            videoView = itemView.findViewById(R.id.videoView);
        }
    }

    public VideoAdapter(List<RoadProject> videoList) {
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.second_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RoadProject roadProject = videoList.get(position);
        holder.videoNameView.setText(roadProject.getMyVideo().getVideoName());
        MediaController controller = new MediaController(holder.view.getContext());
        holder.videoView.setMediaController(controller);
        holder.videoView.setVideoURI(roadProject.getMyVideo().getVideo());
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }
}
