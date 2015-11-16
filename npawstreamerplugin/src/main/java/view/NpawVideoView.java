package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.MediaController;


public class NpawVideoView extends SurfaceView implements SurfaceHolder.Callback,
        MediaController.MediaPlayerControl
{
    private static final String TAG = NpawVideoView.class.getSimpleName();

    private NpawMediaPlayer mPlayer;
    private SurfaceHolder mSurfaceHolder;
    private int mVideoWidth;
    private int mVideoHeight;

    public NpawVideoView(Context context)
    {
        super(context);
    }

    public NpawVideoView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public NpawVideoView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {

    }

    @Override
    public void start()
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public int getDuration()
    {
        return 0;
    }

    @Override
    public int getCurrentPosition()
    {
        return 0;
    }

    @Override
    public void seekTo(int pos)
    {

    }

    @Override
    public boolean isPlaying()
    {
        return false;
    }

    @Override
    public int getBufferPercentage()
    {
        return 0;
    }

    @Override
    public boolean canPause()
    {
        return false;
    }

    @Override
    public boolean canSeekBackward()
    {
        return false;
    }

    @Override
    public boolean canSeekForward()
    {
        return false;
    }

    @Override
    public int getAudioSessionId()
    {
        return 0;
    }
}
