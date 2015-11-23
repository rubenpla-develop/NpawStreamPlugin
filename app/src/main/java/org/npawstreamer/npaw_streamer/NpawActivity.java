package org.npawstreamer.npaw_streamer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import org.npawstreamer.npaw_streamer.activity.MediaPlayerActivity;
import org.npawstreamer.npaw_streamer.activity.VideoViewActivity;
import org.npawstreamer.npaw_streamer.utils.Const;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NpawActivity extends AppCompatActivity
{
    private final static String TAG = NpawActivity.class.getSimpleName();

    @Bind(R.id.mp_button) Button mpButton;
    @Bind(R.id.vv_button) Button vvButton;

    private final int MEDIA_PLAYER_CODE = 1;
    private final int VIDEO_VIEW_CODE = 2;

    @Bind((R.id.toolbar))
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
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
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_npaw, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void launchStreamProcess(String urlMovie, int code)
    {
        Intent movieIntent = null;
        if (code == 1)
        {
            Log.i(TAG, "MediaPlayer Activity");
            movieIntent = new Intent(this, MediaPlayerActivity.class)
                    .putExtra(Const.EXTRA_MOVIE_PARAM, urlMovie);
        } else if (code == 2)
        {
            Log.i( TAG , "VideoView Activity");
            movieIntent = new Intent(this, VideoViewActivity.class)
                    .putExtra(Const.EXTRA_MOVIE_PARAM, urlMovie);
        }

        //TODO temp, remove it
        if (code ==1)
            startActivity(movieIntent);
    }

    @OnClick(R.id.mp_button)
    public void onMediaPlayerBtnClick()
    {
        launchStreamProcess(Const.URL_MOVIE_ONE, MEDIA_PLAYER_CODE);
    }

    @OnClick(R.id.vv_button)
    public void onVideoViewBtnClick()
    {
        launchStreamProcess(Const.URL_MOVIE_ONE, VIDEO_VIEW_CODE);
    }
}
