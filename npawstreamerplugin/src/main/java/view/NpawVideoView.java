package view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.MediaController;


public class NpawVideoView extends SurfaceView implements SurfaceHolder.Callback, android.widget.MediaController.MediaPlayerControl
{
    private static final String TAG = NpawVideoView.class.getSimpleName();
    private Context mContext;

    private NpawMediaPlayer.OnPreparedListener mOnPreparedListener;
    private NpawMediaPlayer.OnSeekListener mOnSeekListener;
    private NpawMediaPlayer.OnSeekCompleteListener mOnSeekCompleteListener;
    private NpawMediaPlayer.OnCompletionListener mOnCompletionListener;
    private NpawMediaPlayer.OnErrorListener mOnErrorListener;
    private NpawMediaPlayer.OnInfoListener mOnInfoListener;
    private NpawMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener;

    private NpawMediaPlayer mPlayer;
    private MediaController mMediaController;
    private SurfaceHolder mSurfaceHolder;
    private String videoUrl;
    private int mVideoWidth;
    private int mVideoHeight;

    private NpawMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener;

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
//        mPlayer.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
//        mPlayer.setOnPreparedListener(mOnPreparedListener);
//        mPlayer.setOnSeekListener(mOnSeekListener);
//        mPlayer.setOnSeekCompleteListener(mOnSeekCompleteListener);
//        mPlayer.setOnCompletionListener(mOnCompletionListener);
//        mPlayer.setOnErrorListener(mOnErrorListener);
//        mPlayer.setOnInfoListener(mOnInfoListener);
//        mPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
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

    public void setOnPreparedListener(NpawMediaPlayer.OnPreparedListener l)
    {
        this.mOnPreparedListener = l;
    }

//    public void setOnSeekListener(MediaPlayer.OnSeekListener l) {
//        this.mOnSeekListener = l;
//    }

    public void setOnSeekCompleteListener(NpawMediaPlayer.OnSeekCompleteListener l)
    {
        this.mOnSeekCompleteListener = l;
    }

    public void setOnCompletionListener(NpawMediaPlayer.OnCompletionListener l)
    {
        this.mOnCompletionListener = l;
    }

    public void setOnBufferingUpdateListener(NpawMediaPlayer.OnBufferingUpdateListener l)
    {
        this.mOnBufferingUpdateListener = l;
    }

    public void setOnErrorListener(NpawMediaPlayer.OnErrorListener l)
    {
        this.mOnErrorListener = l;
    }

    public void setOnInfoListener(NpawMediaPlayer.OnInfoListener l)
    {
        this.mOnInfoListener = l;
    }


}
