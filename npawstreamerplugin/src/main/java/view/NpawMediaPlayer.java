package view;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.MediaController;
import android.widget.Toast;

/**
 * Created by Ruben on 16/11/2015.
 */
public class NpawMediaPlayer extends MediaPlayer/* implements MediaController.MediaPlayerControl*/
{
    private static final String TAG = NpawMediaPlayer.class.getSimpleName();
    private String videoUrl;

    private PowerManager.WakeLock mWakeLock = null;
    //    private Handler handler;
    private Context context;
    private MediaController mMediaController;

    private OnPreparedListener mOnPreparedListener;
    private OnSeekCompleteListener mOnSeekCompleteListener;
    private OnSeekListener mOnSeekListener;
    private OnCompletionListener mOnCompletionListener;
    private OnErrorListener mOnErrorListener;
    private OnInfoListener mOnInfoListener;
    private OnBufferingUpdateListener mOnBufferingUpdateListener;
    private OnVideoSizeChangedListener mOnVideoSizeChangedListener;
    private onPrepareMediaPlayerControlListener mOnPrepareMediaPlayerControlListener;
    private MediaController.MediaPlayerControl mMediaPlayerControl;
    private SurfaceHolder.Callback mSurfaceHolderCallback;
//    private NpawMediaPlayerCallbacks npawMplayerCallbacks;

    public NpawMediaPlayer()
    {
        super();
    }

    public void initMediaPlayer(Context context, String url)
    {
        Log.i(TAG, "initMediaPlayer");

        this.context = context;
        videoUrl = url;

        mMediaController = new MediaController(context);
    }

    public void setMediaPlayerAdjustements()
    {
        setScreenOnWhilePlaying(true);
        setAudioStreamType(AudioManager.STREAM_MUSIC);

        setMediaPlayerListeners();
    }

    public void initMediaPlayerControl()
    {
        Log.i(TAG, "InitMediaPlayerControl()");

        mMediaPlayerControl = new MediaController.MediaPlayerControl()
        {
            @Override
            public void start()
            {
                Log.d(TAG, "mMediaController start()");
                start();
            }

            @Override
            public void pause()
            {
                Log.d(TAG, "mMediaController pause()");
                pause();
            }

            @Override
            public int getDuration()
            {
                Log.d(TAG, "mMediaController getDuration()");
                return 0;
            }

            @Override
            public int getCurrentPosition()
            {
                Log.d(TAG, "mMediaController getCurrentPosition()");
                return 0;
            }

            @Override
            public void seekTo(int pos)
            {
                Log.d(TAG, "mMediaController seekTo()");
                //seekTo(pos);
            }

            @Override
            public boolean isPlaying()
            {
                Log.d(TAG, "mMediaController isPlaying()");
                return false;
            }

            @Override
            public int getBufferPercentage()
            {
                Log.d(TAG, "mMediaController getBufferPercentage()");
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
                return 0;
            }
        };
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

    public MediaController.MediaPlayerControl withMediaPlayerControl(boolean loadMpc)
    {
        Log.i(TAG, "withMediaPLayerControl()");

        if (loadMpc)
        {
            initMediaPlayerControl();
        }

        return mMediaPlayerControl;
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

    public interface onPrepareMediaPlayerControlListener
    {
        void onPrepareMediaPlayerControl(NpawMediaPlayer mp);
    }

    public void setOnPrepareMediaPlayerControlListener(onPrepareMediaPlayerControlListener listener)
    {
        mOnPrepareMediaPlayerControlListener = listener;
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

    private void setMediaPlayerListeners()
    {
//        mOnPreparedListener = new OnPreparedListener()
//        {
//            @Override
//            public void onPrepared(MediaPlayer mp)
//            {
//                Log.w(TAG, "MediaPlayer onPrepared()");
////                npawMplayerCallbacks.onPrepareMediaPlayerControl();
//                mOnPrepareMediaPlayerControlListener.onPrepareMediaPlayerControl(NpawMediaPlayer.this);
//            }
//        };
//
//        setOnPreparedListener(mOnPreparedListener);

        mOnSeekCompleteListener = new OnSeekCompleteListener()
        {
            @Override
            public void onSeekComplete(MediaPlayer mp)
            {
                Log.i(TAG, "default OnSeekComplete()");
            }
        };

        setOnSeekCompleteListener(mOnSeekCompleteListener);

        mOnCompletionListener = new OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                Log.i(TAG, "default onCompletion()");
            }
        };

        setOnCompletionListener(mOnCompletionListener);

        mOnErrorListener = new OnErrorListener()
        {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra)
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
                return true;
            }
        };

        setOnErrorListener(mOnErrorListener);

        mOnInfoListener = new OnInfoListener()
        {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra)
            {
                String whatName = "";
                String extraName = "";
                switch (what)
                {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        whatName = "MEDIA_INFO_BUFFERING_END";
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        whatName = "MEDIA_INFO_BUFFERING_START";
                        break;
                    case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                        whatName = "MEDIA_INFO_VIDEO_RENDERING_START";
                        break;
                    case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
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
                return true;
            }
        };

        setOnInfoListener(mOnInfoListener);

        mOnBufferingUpdateListener = new OnBufferingUpdateListener()
        {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent)
            {
                Log.i(TAG, "default onCompletion()");
            }
        };

        setOnBufferingUpdateListener(mOnBufferingUpdateListener);

        mOnVideoSizeChangedListener = new OnVideoSizeChangedListener()
        {
            @Override
            public void onVideoSizeChanged(MediaPlayer mp, int width, int height)
            {
                Log.i(TAG, "default onVideoSizeChanged()");
            }
        };

        setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
    }
}
