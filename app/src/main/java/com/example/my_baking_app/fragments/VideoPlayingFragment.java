package com.example.my_baking_app.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_baking_app.R;
import com.example.my_baking_app.models.Recipe;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoPlayingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoPlayingFragment extends Fragment {
    PlayerView playerView;
    private SimpleExoPlayer player;
    Recipe.StepsBean object;
    String SELECTED_POSITION="selectedposition";
    String OBJECT="object";
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private boolean playWhenReady = true;
    String url;
    ProgressBar progressBar;
    TextView textView;


    public Recipe.StepsBean getObject() {
        return object;
    }

    public void setObject(Recipe.StepsBean object) {
        this.object = object;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
            Toast.makeText(getContext(), "ONSTART", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT < 24 || player == null)) {
            initializePlayer();

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();

        }
    }
    public VideoPlayingFragment() {
        // Required empty public constructor
    }


    public static VideoPlayingFragment newInstance(String param1, String param2) {
        VideoPlayingFragment fragment = new VideoPlayingFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            playbackPosition=savedInstanceState.getLong(SELECTED_POSITION,0);
            object=savedInstanceState.getParcelable(OBJECT);
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        //to get current position
        playbackPosition=player.getCurrentPosition();

        outState.putLong(SELECTED_POSITION,playbackPosition);
        outState.putParcelable(OBJECT,object);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_video_playing,container,false);
        playerView=rootView.findViewById(R.id.videoView);
        progressBar=rootView.findViewById(R.id.progressBar);
        textView=rootView.findViewById(R.id.noMedia);
        progressBar.setVisibility(View.VISIBLE);
        textView.setVisibility(View.INVISIBLE);



        return rootView;

    }
    private void initializePlayer() {

        player = new SimpleExoPlayer.Builder(getContext()).build();
        playerView.setPlayer(player);
        url=object.getVideoURL();
        if (!url.equals("")){
            Uri uri = Uri.parse(url);
            Toast.makeText(getContext(), "url :"+url, Toast.LENGTH_SHORT).show();
            MediaSource mediaSource = buildMediaSource(uri);
            progressBar.setVisibility(View.INVISIBLE);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
            player.prepare(mediaSource, false, false);

        }else{
            textView.setVisibility(View.VISIBLE);
            playerView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }

    }
    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), "exoplayer");

      /*  DashMediaSource.Factory mediaSourceFactory = new DashMediaSource.Factory(dataSourceFactory);
        return mediaSourceFactory.createMediaSource(uri);*/
        return new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);


    }
    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }

    }


}