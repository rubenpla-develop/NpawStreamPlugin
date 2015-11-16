package view;

import android.media.MediaPlayer;
import android.os.PowerManager;

/**
 * Created by Ruben on 16/11/2015.
 */
public class NpawMediaPlayer extends MediaPlayer implements MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnVideoSizeChangedListener, MediaPlayer.OnBufferingUpdateListener
{
    private static final String TAG = NpawMediaPlayer.class.getSimpleName();

    private PowerManager.WakeLock mWakeLock = null;
    private boolean mScreenOnWhilePlaying;
    private boolean mStayAwake;
    private boolean mIsStopping;

    public NpawMediaPlayer()
    {

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent)
    {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra)
    {
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra)
    {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp)
    {

    }

    @Override
    public void onSeekComplete(MediaPlayer mp)
    {

    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height)
    {

    }

}
