package view;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.MediaController;
import android.widget.Toast;

import object.PlayStats;

/**
 * Created by Ruben on 16/11/2015.
 */
public class NpawMediaPlayer extends MediaPlayer
{
    private static final String TAG = NpawMediaPlayer.class.getSimpleName();
    private String videoUrl;

    private PowerManager.WakeLock mWakeLock = null;
    //    private Handler handler;
    private Context context;
    private MediaController mMediaController;
    private PlayStats playStats;

    public OnPreparedListener mOnPreparedListener;
    public OnSeekCompleteListener mOnSeekCompleteListener;
    public OnSeekListener mOnSeekListener;
    public OnCompletionListener mOnCompletionListener;
    public OnErrorListener mOnErrorListener;
    public OnInfoListener mOnInfoListener;
    public OnBufferingUpdateListener mOnBufferingUpdateListener;
    public OnVideoSizeChangedListener mOnVideoSizeChangedListener;
    private MediaController.MediaPlayerControl mMediaPlayerControl;
    private SurfaceHolder.Callback mSurfaceHolderCallback;

    public NpawMediaPlayer()
    {
        super();
    }

    public void initMediaPlayer(Context context, String url)
    {
        Log.i(TAG, "initMediaPlayer");

        this.context = context;
        videoUrl = url;
        playStats = new PlayStats();

        mMediaController = new MediaController(context);
    }

    public void setMediaPlayerAdjustements()
    {
        setScreenOnWhilePlaying(true);
        setAudioStreamType(AudioManager.STREAM_MUSIC);

//        setMediaPlayerListeners();
    }

    public void initSurfaceHolder()
    {
        Log.i(TAG, "InitSurfaceHolder()");

        mSurfaceHolderCallback = new SurfaceHolder.Callback()
        {
            @Override
            public void surfaceCreated(SurfaceHolder holder)
            {
                setDisplay(holder);
                try
                {
                    setDataSource(videoUrl);
                    prepareAsync();
                } catch (Exception e)
                {
                    Log.e("TAG", "SurfaceCreated error : " + e.getMessage());
                }

//                start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
            {
                Log.d(TAG, "surfaceHolder.callBack.surfaceChanged()");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder)
            {
                Log.d(TAG, "surfaceHolder.callBack.surfaceDestroyed()");
                stop();
                release();
            }
        };
    }

    public SurfaceHolder.Callback callBackIntoHolder()
    {
        Log.i(TAG, "callBackIntoHolder()");

        initSurfaceHolder();
        return mSurfaceHolderCallback;
    }

    public void setSurfaceHolderCallback(SurfaceHolder.Callback holderCallback)
    {
        this.mSurfaceHolderCallback = holderCallback;
    }

    public SurfaceHolder.Callback getSurfaceHolderCallBack()
    {
        return mSurfaceHolderCallback;
    }

    public void setMediaPlayerControl(MediaController.MediaPlayerControl mpc)
    {
        this.mMediaPlayerControl = mpc;
    }

    public MediaController.MediaPlayerControl getMediaPlayerControl()
    {
        return mMediaPlayerControl;
    }

    public MediaController getMediaController()
    {
        return mMediaController;
    }

    public PlayStats getPlayStats()
    {
        return playStats;
    }

    public void filterOnErrorListenerResult(int what, int extra)
    {
        Log.e(TAG, "default OnError()");
        String whatName = "";
        String extraName = "";

        switch (what)
        {
            case MEDIA_ERROR_SERVER_DIED:
                whatName = "MEDIA_ERROR_SERVER_DIED";
                break;
            case MEDIA_ERROR_UNKNOWN:
                whatName = "MEDIA_ERROR_DOWNLOAD";
                break;
            default:
                whatName = "NOT SPECIFIC ERROR";
                break;
        }

        switch (extra)
        {
            case MEDIA_ERROR_IO:
                extraName = "MEDIA_ERROR_IO";
                break;
            case MEDIA_ERROR_MALFORMED:
                extraName = "MEDIA_ERROR_MALFORMED";
                break;
            case MEDIA_ERROR_TIMED_OUT:
                extraName = "MEDIA_ERROR_TIMED_OUT";
                break;
            case MEDIA_ERROR_UNSUPPORTED:
                extraName = "MEDIA_ERROR_UNSUPPORTED";
                break;
            default:
                extraName = "NOT SPECIFIC ERROR";
                break;
        }

        Toast.makeText(context.getApplicationContext(),
                "Cannot play the video, see logcat for the detailed exception",
                Toast.LENGTH_LONG).show();
        Log.e(TAG, "what : " + whatName + "/ extra : " + extraName);

//                mProgress.setVisibility(View.GONE);
        mMediaController.setEnabled(false);
    }

    public void filterOnInfoListenerResult(int what, int extra)
    {
        String whatName = "";
        String extraName = "";
        switch (what)
        {
            case MEDIA_INFO_BUFFERING_END:
                whatName = "MEDIA_INFO_BUFFERING_END";
                break;
            case MEDIA_INFO_BUFFERING_START:
                whatName = "MEDIA_INFO_BUFFERING_START";
                break;
            case MEDIA_INFO_VIDEO_RENDERING_START:
                whatName = "MEDIA_INFO_VIDEO_RENDERING_START";
                break;
            case MEDIA_INFO_VIDEO_TRACK_LAGGING:
                whatName = "MEDIA_INFO_VIDEO_TRACK_LAGGING";
                break;
            default:
                whatName = "MEDIA_INFO_UNKNOWN";
                break;
        }

        switch (extra)
        {
            case MEDIA_INFO_BAD_INTERLEAVING:
                extraName = "MEDIA_INFO_";
                break;
            case MEDIA_INFO_NOT_SEEKABLE:
                extraName = "MEDIA_INFO_NOT_SEEKABLE";
                break;
            case MEDIA_INFO_METADATA_UPDATE:
                extraName = "MEDIA_INFO_METADATA_UPDATE";
                break;
            case MEDIA_INFO_UNSUPPORTED_SUBTITLE:
                extraName = "MEDIA_INFO_UNSUPPORTED_SUBTITLE";
                break;
            case MEDIA_INFO_SUBTITLE_TIMED_OUT:
                extraName = "MEDIA_INFO_SUBTITLE_TIMED_OUT";
                break;
            default:
                extraName = "MEDIA_INFO NOT KNOWN";
                break;
        }
        Log.d(TAG, "onInfo WHAT" + whatName + "/ EXTRA : " + extraName);
    }

    public void startTimeElapse()
    {
        playStats.setStartTime(System.nanoTime());
    }

    public long calculateElapsedTime()
    {
        playStats.setElapseTime(System.nanoTime() - playStats.getStartTime());
        playStats.setTotalElapseTime(playStats.getElapseTime());
        return System.nanoTime() - playStats.getStartTime();
    }

    public void showCompleteStats()
    {
        Toast.makeText(context.getApplicationContext(), playStats.showPlayStatsReport(), Toast.LENGTH_LONG).show();
    }

    public void showPausesStat()
    {
        Toast.makeText(context.getApplicationContext(), playStats.showPauses(), Toast.LENGTH_LONG).show();
    }

    public void showResumesStat()
    {
        Toast.makeText(context.getApplicationContext(), playStats.showResumes(), Toast.LENGTH_LONG).show();
    }

    public interface OnSeekListener
    {
        public void onSeek(MediaPlayer mp);
    }

    public void setOnSeekListener(OnSeekListener listener)
    {
        mOnSeekListener = listener;
    }

    public void setMediaController(MediaController mc)
    {
        this.mMediaController = mc;
    }

    public void setPreparedListener(NpawMediaPlayer.OnPreparedListener l)
    {
        if (l != null)
            this.mOnPreparedListener = l;

        setOnPreparedListener(mOnPreparedListener);
    }

    public void setSeekCompleteListener(NpawMediaPlayer.OnSeekCompleteListener l)
    {
        if (l != null)
            this.mOnSeekCompleteListener = l;

        setOnSeekCompleteListener(mOnSeekCompleteListener);
    }

    public void setCompletionListener(NpawMediaPlayer.OnCompletionListener l)
    {
        if (l != null)
            this.mOnCompletionListener = l;

        setOnCompletionListener(mOnCompletionListener);
    }

    public void setErrorListener(NpawMediaPlayer.OnErrorListener l)
    {
        if (l != null)
            this.mOnErrorListener = l;

        setOnErrorListener(mOnErrorListener);
    }

    public void setInfoListener(NpawMediaPlayer.OnInfoListener l)
    {
        if (l != null)
            this.mOnInfoListener = l;

        setOnInfoListener(mOnInfoListener);
    }

    public void setBufferingUpdateListener(NpawMediaPlayer.OnBufferingUpdateListener l)
    {
        if (l != null)
            this.mOnBufferingUpdateListener = l;

        setOnBufferingUpdateListener(mOnBufferingUpdateListener);
    }

    public void setVideoSizeChangedListener(NpawMediaPlayer.OnVideoSizeChangedListener l)
    {
        if (l != null)
            this.mOnVideoSizeChangedListener = l;

        setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
    }


}
