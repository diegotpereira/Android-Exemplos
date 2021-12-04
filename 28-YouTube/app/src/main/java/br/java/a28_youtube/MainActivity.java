package br.java.a28_youtube;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends YouTubeBaseActivity {

    private static final String API_KEY = "AIzaSyB4WJ_nspMyFA_zsSZ9ky_SoQiS2QOp70I";
    private static final String VIDEO_ID = "BQANelCcrSY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        YouTubePlayerView youTubeView = new YouTubePlayerView(this);
        setContentView(youTubeView);

        youTubeView.initialize(API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(
                            YouTubePlayer.Provider provider,
                            YouTubePlayer player,
                            boolean wasRestored) {

                        if (!wasRestored) {
                            player.cueVideo(VIDEO_ID);
                        }

                    }

                    @Override
                    public void onInitializationFailure(
                            YouTubePlayer.Provider provider,
                            YouTubeInitializationResult result) {

                        Toast.makeText(MainActivity.this,
                                "Erro ao reproduzir vídeo",
                                Toast.LENGTH_SHORT).show();

                    }
                });
    }
}