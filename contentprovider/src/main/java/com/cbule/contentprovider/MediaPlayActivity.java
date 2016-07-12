package com.cbule.contentprovider;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.io.IOException;

public class MediaPlayActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_play);
        toolbar = (Toolbar) findViewById(R.id.media_play_toolbar);
        toolbar.setNavigationIcon(R.drawable.home);
       toolbar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();
           }
       });
        toolbar.inflateMenu(R.menu.mediaplay_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mediaplay_activity_item01:
                        Intent intent = getIntent();
                        String content = intent.getStringExtra("content");
                        Toast.makeText(MediaPlayActivity.this, ""+content, Toast.LENGTH_SHORT).show();
                        mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setDataSource(content);
                            mediaPlayer.prepare();
                            mediaPlayer.start();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.mediaplay_activity_item02:
                        if (mediaPlayer!=null){
                            mediaPlayer.stop();
                        }
                        break;
                }
                return true;
            }
        });
    }
    public void  start(View view){
        Intent intent = getIntent();
        String content = intent.getStringExtra("content");
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(content);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void  stop(View view){
        if (mediaPlayer!=null){
            mediaPlayer.stop();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer!=null){
            mediaPlayer.stop();
        }
    }
}
