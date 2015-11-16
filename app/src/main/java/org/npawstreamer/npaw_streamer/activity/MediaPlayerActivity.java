package org.npawstreamer.npaw_streamer.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.npawstreamer.npaw_streamer.R;
import org.npawstreamer.npaw_streamer.utils.Const;

import butterknife.Bind;
import butterknife.ButterKnife;
import view.NpawMediaPlayer;

public class MediaPlayerActivity extends AppCompatActivity
{
    @Bind(R.id.surface_view)
    SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        ButterKnife.bind(this);

        final String videoUri = getIntent().getStringExtra(Const.EXTRA_MOVIE_PARAM);

        SurfaceHolder holder = surfaceView.getHolder();

        //MediaPlayer object creation
        final NpawMediaPlayer mediaPlayer = new NpawMediaPlayer();

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mediaPlayer.setDisplay(holder);
                try {
                    mediaPlayer.setDataSource(videoUri);
                    mediaPlayer.prepare();
                } catch (Exception e) {}
                mediaPlayer.start();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }
        });
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

}
