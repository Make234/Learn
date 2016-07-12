package com.cbule.contentprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordActivity extends AppCompatActivity {
    MediaRecorder mediaRecorder;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
    }

    //开始录音
    public void start(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mediaRecorder = new MediaRecorder();
                    //设置来源
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
                    //设置格式
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);
                    //设置编码
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
                    //设置保存路径
                    long time = System.currentTimeMillis();
                    path = "/sdcard/" + time + ".amr";
                    mediaRecorder.setOutputFile(path);
                    //准备
                    mediaRecorder.prepare();
                    //开始
                    mediaRecorder.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    //停止录音
    public void stop(View view) {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            Uri uri = Uri.parse("content://com.cbule.note");
            ContentResolver contentResolver = getContentResolver();
            ContentValues values = new ContentValues();

            Long time1 = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
            Date date = new Date(time1);
            String data = sdf.format(date);
            values.put("title", data+".amr");
            values.put("content",path);
            values.put("time", "" + time1);
            contentResolver.insert(uri, values);
            Toast.makeText(RecordActivity.this, "数据保存完成", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
