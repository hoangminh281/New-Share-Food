package com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.ptit.tranhoangminh.newsharefood.R;

public class VideoFragment extends Fragment{
    VideoView videoView;
    String videoURL;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video,null);
        videoView = view.findViewById(R.id.videoView);
        videoURL="http://www.youtubemaza.com/files/data/366/Tom%20And%20Jerry%20055%20Casanova%20Cat%20(1951).mp4";
        Uri video = Uri.parse(videoURL);
        videoView.setVideoURI(video);
        return view;
    }

}
