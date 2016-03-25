package org.npawstreamer.npaw_streamer.activity;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.npawstreamer.npaw_streamer.R;
import org.npawstreamer.npaw_streamer.utils.Const;

import butterknife.Bind;
import butterknife.ButterKnife;
import View.NpawMediaPlayer;
import View.NpawVideoView;

public class VideoViewActivity extends AppCompatActivity implements NpawMediaPlayer.OnErrorListener
{
    public final static String TAG = VideoViewActivity.class.getSimpleName();

    String videoUri;

    @Bind(R.id.view_video)
    NpawVideoView mVideoView;

    @Bind(R.id.vv_progressbar)
    ProgressBar progressBar;

//    private ProgressBar progressBar;

    private MediaController.MediaPlayerControl mMediaPlayerControl;
    private MediaController mMediaController;
//    private NpawMediaPlayerCallbacks videoViewCallBackListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        final String videoUri = getIntent().getStringExtra(Const.EXTRA_MOVIE_PARAM);

        ButterKnife.bind(this);

        mMediaPlayerControl = mVideoView;
        mMediaController = new MediaController(this);
        mMediaController.setAnchorView(findViewById(R.id.videoview_container));
        mMediaController.setMediaPlayer(mMediaPlayerControl);
        mMediaController.setEnabled(false);

        progressBar.setVisibility(View.VISIBLE);

        if (savedInstanceState != null)
        {
            init(savedInstanceState.getString(Const.EXTRA_MOVIE_URL),
                    savedInstanceState.getInt(Const.EXTRA_MOVIE_POSITION),
                    savedInstanceState.getBoolean(Const.EXTRA_MOVIE_ISPLAYING));
        } else
        {
            init(videoUri, -1, false);
        }
    }

    private void init(String uri, final int position, final boolean playback)
    {
        videoUri = uri;

        mVideoView.setVideoSource(videoUri);

        mVideoView.setPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            @Override
            public void onPrepared(MediaPlayer mp)
            {
                progressBar.setVisibility(View.GONE);
                mVideoView.start();
            }
        });

        mVideoView.setErrorListener(this);
        mVideoView.setInfoListener(new MediaPlayer.OnInfoListener()
        {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra)
            {
                mVideoView.filterInfoListenerResult(what, extra);
                return true;
            }
        });

        mVideoView.setSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener()
        {
            @Override
            public void onSeekComplete(MediaPlayer mp)
            {
                Log.d(TAG, "onSeekComplete");
                progressBar.setVisibility(View.GONE);
            }
        });

        mVideoView.setBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener()
        {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent)
            {
                Log.d(TAG, "onBufferingUpdate " + percent + "%");
            }
        });
    }

    @Override
    protected void onStop()
    {
        mMediaController.hide();
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
        if (videoUri != null)
        {
            outState.putString(Const.EXTRA_MOVIE_URL, videoUri);
            outState.putBoolean(Const.EXTRA_MOVIE_ISPLAYING, mVideoView.isPlaying());
            outState.putInt(Const.EXTRA_MOVIE_POSITION, mVideoView.getCurrentPosition());
        }
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

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        Log.d(TAG, "surfaceHolder.callBack.onTouchEvent()");
        mMediaController.show();
        return false;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra)
    {
        Toast.makeText(VideoViewActivity.this,
                "Cannot play the video, see logcat for the detailed exception",
                Toast.LENGTH_LONG).show();
        mVideoView.filterErrorListenerResult(what, extra);
        progressBar.setVisibility(View.GONE);
        mMediaController.setEnabled(false);
        return true;
    }




}
