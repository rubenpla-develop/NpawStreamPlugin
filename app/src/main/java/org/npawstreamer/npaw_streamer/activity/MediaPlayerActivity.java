package org.npawstreamer.npaw_streamer.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.MediaController;

import org.npawstreamer.npaw_streamer.R;
import org.npawstreamer.npaw_streamer.utils.Const;

import butterknife.Bind;
import butterknife.ButterKnife;
import View.NpawMediaPlayer;

public class MediaPlayerActivity extends AppCompatActivity implements NpawMediaPlayer.OnPreparedListener, NpawMediaPlayer.OnInfoListener, NpawMediaPlayer.OnErrorListener
{
    final public static String TAG = MediaPlayerActivity.class.getSimpleName();
    private Context ctx;

    @Bind(R.id.surface_view)
    SurfaceView surfaceView;

    private SurfaceHolder holder;
    private NpawMediaPlayer mediaPlayer;
    private MediaController mController;
    private MediaController.MediaPlayerControl mMediaPlayerControl;
    private Handler handler;
    private String videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        ButterKnife.bind(this);

        ctx = this;

        videoUri = getIntent().getStringExtra(Const.EXTRA_MOVIE_PARAM);

        holder = surfaceView.getHolder();

        mediaPlayer = new NpawMediaPlayer();

        mediaPlayer.initMediaPlayer(ctx, videoUri);
        holder.addCallback(mediaPlayer.callBackIntoHolder());
        mController = mediaPlayer.getMediaController();
        mMediaPlayerControl = new MediaController.MediaPlayerControl()
        {
            @Override
            public void start()
            {
                mediaPlayer.start();
                if (getCurrentPosition() > 0)
                {
                    mediaPlayer.increaseResumes();
                    mediaPlayer.setStartTime(System.nanoTime());
                    mediaPlayer.showResumesStat();
                    Log.i(TAG, "RESUMES : " + mediaPlayer.getResumes() /*+ "\nElapse Time :" + mediaPlayer.calculateElapsedTime()*/);
                }
            }

            @Override
            public void pause()
            {
                mediaPlayer.pause();
                mediaPlayer.increasePauses();
                mediaPlayer.showPausesStat();
                mediaPlayer.startTimeElapse();
                Log.i(TAG, "PAUSES : " + mediaPlayer.getPlayStats().getPauses());
            }

            @Override
            public int getDuration()
            {
                return mediaPlayer.getDuration();
            }

            @Override
            public int getCurrentPosition()
            {
                return mediaPlayer.getCurrentPosition();
            }

            @Override
            public void seekTo(int pos)
            {
                mediaPlayer.seekTo(pos);
            }

            @Override
            public boolean isPlaying()
            {
                return mediaPlayer.isPlaying();
            }

            @Override
            public int getBufferPercentage()
            {
                return 0;
            }

            @Override
            public boolean canPause()
            {
                return true;
            }

            @Override
            public boolean canSeekBackward()
            {
                return true;
            }

            @Override
            public boolean canSeekForward()
            {
                return true;
            }

            @Override
            public int getAudioSessionId()
            {
                return mediaPlayer.getAudioSessionId();
            }
        };
        mediaPlayer.setPreparedListener(this);
        mediaPlayer.setErrorListener(this);
        mediaPlayer.setInfoListener(this);
        mediaPlayer.setCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                mediaPlayer.showCompleteStats();
            }
        });

        handler = new Handler();

        if (savedInstanceState != null)
        {
            Log.i(TAG, "savedInstanceState: " + savedInstanceState.isEmpty());
        } else
        {
            mediaPlayer.setMediaPlayerAdjustements();
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        mediaPlayer.release();
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
        if (mediaPlayer != null)
        {
            outState.putString(Const.EXTRA_MOVIE_URL, videoUri);
            outState.putBoolean(Const.EXTRA_MOVIE_ISPLAYING, mediaPlayer.isPlaying());
            outState.putInt(Const.EXTRA_MOVIE_POSITION, mediaPlayer.getCurrentPosition());
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
        Log.w(TAG, "onPause()");
        mediaPlayer.pause();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        Log.d(TAG, "surfaceHolder.callBack.onTouchEvent()");
        mController.show();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp)
    {
        Log.w(TAG, "MediaPlayer onPrepared()");

        mController.setMediaPlayer(mMediaPlayerControl);
        mController.setAnchorView(surfaceView);
        mController.setEnabled(true);

        mediaPlayer.start();

        handler.post(new Runnable()
        {
            public void run()
            {
                mController.setEnabled(true);
                mController.show();
            }
        });
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra)
    {
        mediaPlayer.filterOnErrorListenerResult(what, extra);
        return true;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra)
    {
        mediaPlayer.filterOnInfoListenerResult(what, extra);
        return true;
    }
}
