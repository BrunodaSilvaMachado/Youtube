package com.cursoandroid.youtube.activity;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.cursoandroid.youtube.R;
import com.cursoandroid.youtube.config.YoutubeConfig;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX;

public class PlayerActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {

    private String idVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            idVideo = bundle.getString("VIDEO_ID");
        }
        YouTubePlayerSupportFragmentX playerSupportFragmentX = (YouTubePlayerSupportFragmentX) getSupportFragmentManager()
                .findFragmentById(R.id.youtubeFragment);
        if(playerSupportFragmentX != null)
            playerSupportFragmentX.initialize(YoutubeConfig.KEY,this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if(!b){
            youTubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION|
                    youTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);
            youTubePlayer.loadVideo(idVideo);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(this, 1).show();
        }else{
            Log.e("PlayerActivity", youTubeInitializationResult.toString());
        }
    }
}