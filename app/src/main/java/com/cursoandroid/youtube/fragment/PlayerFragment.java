package com.cursoandroid.youtube.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.cursoandroid.youtube.R;
import com.cursoandroid.youtube.config.YoutubeConfig;
import com.cursoandroid.youtube.model.Item;
import com.google.android.youtube.player.*;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PlayerFragment extends Fragment implements YouTubePlayer.OnInitializedListener {
    private Item video;

    @NotNull
    public static PlayerFragment newInstance(Item video){
        PlayerFragment playerFragment = new PlayerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("VIDEO",video);
        playerFragment.setArguments(bundle);
        return playerFragment;
    }
    public PlayerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        video = new Item();
        if (bundle != null){
            video = (Item) bundle.getSerializable("VIDEO");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        YouTubePlayerSupportFragmentX youTubePlayerFragment = (YouTubePlayerSupportFragmentX) getChildFragmentManager()
                .findFragmentById(R.id.youtubeFragment);

        if (youTubePlayerFragment != null){
            youTubePlayerFragment.initialize(YoutubeConfig.KEY,this);
        }

        TextView title = view.findViewById(R.id.titleVideo);
        TextView titleChannel = view.findViewById(R.id.titleChannel);
        title.setText(video.snippet.title);
        titleChannel.setText(video.snippet.channelTitle);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if(!b){
            youTubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION|
                    youTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);
            youTubePlayer.loadVideo(video.id.videoId);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(Objects.requireNonNull(getActivity()), 1).show();
        }else{
            Log.e("PlayerActivity", youTubeInitializationResult.toString());
        }

    }
}