package View;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.IOException;

/**
 * Created by ruben on 25/03/16.
 */
public class NPAWVideoView extends VideoView implements SurfaceHolder.Callback, android.widget.MediaController.MediaPlayerControl
{
    private static final String TAG = NpawVideoView.class.getSimpleName();
    private Context mContext;

    private NpawMediaPlayer mPlayer;
    private MediaController mMediaController;
    private SurfaceHolder mSurfaceHolder;
    private String videoUrl;
    private int mVideoWidth;
    private int mVideoHeight;

    public NPAWVideoView(Context context) {
        super(context);
        init();
    }

    public NPAWVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NPAWVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NPAWVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
        //mPlayer.initMediaPlayer(mContext, videoUrl);
        mPlayer.setDisplay(mSurfaceHolder);
        mPlayer.setMediaPlayerAdjustements();
        mPlayer.setOnPreparedListener(mPlayer.mOnPreparedListener);
        mPlayer.setErrorListener(mPlayer.mOnErrorListener);
        mPlayer.setInfoListener(mPlayer.mOnInfoListener);
        mPlayer.setCompletionListener(mPlayer.mOnCompletionListener);
        mPlayer.setOnSeekCompleteListener(mPlayer.mOnSeekCompleteListener);
        mPlayer.setBufferingUpdateListener(mPlayer.mOnBufferingUpdateListener);
        mPlayer.setOnSeekListener(mPlayer.mOnSeekListener);
        mPlayer.setVideoSizeChangedListener(mPlayer.mOnVideoSizeChangedListener);

//        try
//        {
//            mPlayer.setDataSource(videoUrl);
//        } catch (IOException e)
//        {
//            Log.e(TAG, "setDataSource exception: " + Arrays.toString(e.getStackTrace()));
//        }

        // Set the data source asynchronously as this might take a while, e.g. is data has to be
        // requested from the network/internet.
        new AsyncTask<Void, Void, Void>() {
            private IOException mException;

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    mPlayer.setDataSource(videoUrl);
                    Log.d(TAG, "video opened");
                } catch (IOException e) {
                    Log.e(TAG, "video open failed", e);
                    mException = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(mException != null) {
                    mPlayer.mOnErrorListener.onError(mPlayer, MediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
                }else
                {
                    mPlayer.prepareAsync();
                }
            }
        }.execute();
    }

    public NpawMediaPlayer getMediaPlayer()
    {
        return mPlayer;
    }

    public MediaController.MediaPlayerControl getMediaPlayerControl()
    {
        MediaController.MediaPlayerControl mpc = this;
        return mpc;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        mSurfaceHolder = holder;
        launchVideo();
        //            getMediaPlayer().setDataSource(videoUrl);
//        mPlayer.prepareAsync();

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

        if (mPlayer.getCurrentPosition() > 0)
        {
            mPlayer.increaseResumes();
            mPlayer.setStartTime(System.nanoTime());
            mPlayer.showResumesStat();
            Log.i(TAG, "RESUMES : " + mPlayer.getResumes() /*+ "\nElapse Time :" + mediaPlayer.calculateElapsedTime()*/);
        }
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
        if (mPlayer != null)
            mPlayer.setErrorListener(l);
    }

    public void setPreparedListener(MediaPlayer.OnPreparedListener l)
    {
        if (mPlayer != null)
            mPlayer.setPreparedListener(l);
    }

    public void setInfoListener(MediaPlayer.OnInfoListener l)
    {
        if (mPlayer != null)
            mPlayer.setInfoListener(l);
    }

    public void setSeekCompleteListener(MediaPlayer.OnSeekCompleteListener l)
    {
        if (mPlayer != null)
            mPlayer.setSeekCompleteListener(l);
    }

    public void setBufferingUpdateListener(MediaPlayer.OnBufferingUpdateListener l)
    {

        if (mPlayer != null)
            mPlayer.setBufferingUpdateListener(l);
    }

    public void setVideoSizeChangedListener(MediaPlayer.OnVideoSizeChangedListener l)
    {
        if (mPlayer != null)
            mPlayer.setVideoSizeChangedListener(l);
    }

    public void filterErrorListenerResult(int what, int extra)
    {
        if (mPlayer != null)
            mPlayer.filterOnErrorListenerResult(what, extra);
    }

    public void filterInfoListenerResult(int what, int extra)
    {
        if (mPlayer != null)
            mPlayer.filterOnInfoListenerResult(what, extra);
    }
}
