package com.cbule.contentprovider.mediarecorder;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.cbule.contentprovider.R;

public class MediaRecorderActivity extends AppCompatActivity {
    MediaRecorder mediaRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madia_recoder);
    }

    public void start(View view) {
        try {
            //创建录音
            mediaRecorder = new MediaRecorder();
            //设置来源
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
            //设置格式
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);
            //设置编码
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
            Long time = System.currentTimeMillis();
            //设置存储路径
            mediaRecorder.setOutputFile("/sdcard/" + time + ".amr");
            //准备
            mediaRecorder.prepare();
            //开始
            mediaRecorder.start();
            Toast.makeText(MediaRecorderActivity.this, "开始录音", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop(View view){
        if (mediaRecorder!=null){
            //结束录音
            mediaRecorder.stop();
            Toast.makeText(MediaRecorderActivity.this, "结束录音", Toast.LENGTH_SHORT).show();
            mediaRecorder=null;
        }
    }
}
