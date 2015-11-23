package view;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.MediaController;


public class NpawVideoView extends SurfaceView implements SurfaceHolder.Callback, android.widget.MediaController.MediaPlayerControl
{
    private static final String TAG = NpawVideoView.class.getSimpleName();
    private Context mContext;

    private NpawMediaPlayer mPlayer;
    private MediaController mMediaController;
    private SurfaceHolder mSurfaceHolder;
    private String videoUrl;
    private int mVideoWidth;
    private int mVideoHeight;

    public NpawVideoView(Context context)
    {
        super(context);
        mContext = context;
        init();
    }

    public NpawVideoView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
        init();
    }

    public NpawVideoView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init()
    {
        Log.i(TAG, "init()");

        getHolder().addCallback(this);
    }

    public void setVideoSource(String url)
    {
        Log.i(TAG, "setVideoSource()");
        videoUrl = url;
//        mSeekWhenPrepared = 0;
        launchVideo();
        requestLayout();
        invalidate();
    }

    private void launchVideo()
    {
        if (mPlayer != null)
        {
            mPlayer.release();
            mPlayer = null;
        }

        mPlayer = new NpawMediaPlayer();
        mPlayer.initMediaPlayer(mContext, videoUrl);
        mPlayer.setDisplay(mSurfaceHolder);
        mPlayer.setMediaPlayerAdjustements();
    }

    public NpawMediaPlayer getMediaPlayer()
    {
        return mPlayer;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        mSurfaceHolder = holder;
        launchVideo();
        mPlayer.prepareAsync();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        mSurfaceHolder = null;
        if (mPlayer != null)
        {
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void start()
    {
        mPlayer.start();
    }

    @Override
    public void pause()
    {
        mPlayer.pause();
        mPlayer.getPlayStats().increasePauses();
        mPlayer.showPausesStat();
        mPlayer.startTimeElapse();
        Log.i(TAG, "PAUSES : " + mPlayer.getPlayStats().getPauses());
    }

    @Override
    public int getDuration()
    {
        return mPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition()
    {
        return mPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos)
    {
        mPlayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying()
    {
        return true;
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
        return mPlayer.getAudioSessionId();
    }

    public void setErrorListener(MediaPlayer.OnErrorListener l)
    {
        getMediaPlayer().setErrorListener(l);
    }

    public void setPreparedListener(MediaPlayer.OnPreparedListener l)
    {
        getMediaPlayer().setPreparedListener(l);
    }

    public void setInfoListener(MediaPlayer.OnInfoListener l)
    {
        getMediaPlayer().setInfoListener(l);
    }

    public void setSeekCompleteListener(MediaPlayer.OnSeekCompleteListener l)
    {
        getMediaPlayer().setSeekCompleteListener(l);
    }

    public void setBufferingUpdateListener(MediaPlayer.OnBufferingUpdateListener l)
    {
        getMediaPlayer().setBufferingUpdateListener(l);
    }

    public void setVideoSizeChangedListener(MediaPlayer.OnVideoSizeChangedListener l)
    {
        getMediaPlayer().setVideoSizeChangedListener(l);
    }

    public void filterErrorListenerResult(int what, int extra)
    {
        getMediaPlayer().filterOnErrorListenerResult(what, extra);
    }

    public void filterInfoListenerResult(int what, int extra)
    {
        getMediaPlayer().filterOnInfoListenerResult(what, extra);
    }
}
