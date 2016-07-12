package com.cbule.contentprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class SaveActivity extends AppCompatActivity {
    EditText et_content;
    EditText et_title;
    Toolbar toolbar;
    Uri uri;
    ContentResolver contentresolver;
    Long id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        initView();
        initData();
    }

    private void initData() {
        uri = Uri.parse("content://com.cbule.note");
        contentresolver = getContentResolver();
        id = getIntent().getLongExtra("id",0);
        String[] ps = {"title","content"};
        Cursor cursor =  contentresolver.query(uri,ps,"_id="+id,null,null);
        if (cursor!=null&&cursor.moveToNext()){
            String title = cursor.getString(0);
            String content = cursor.getString(1);
            et_title.setText(title);
            et_content.setText(content);
        }
        cursor.close();


}

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.saveactivity_toolbar);
        et_content = (EditText) findViewById(R.id.saveactivity_content_et);
        et_title = (EditText) findViewById(R.id.saveactivity_title_et);
        initToolbar();

    }

    private void initToolbar() {
        toolbar.setNavigationIcon(R.drawable.home);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setTitle("退出");
        //填充toolbar后面内容
        toolbar.inflateMenu(R.menu.save_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    //保存
                    case R.id.mainactivity_item01:
                        Uri uri = Uri.parse("content://com.cbule.note");
                        ContentResolver contentresolver = getContentResolver();
                        ContentValues values = new ContentValues();
                        values.put("title", et_title.getText().toString());
                        values.put("content", et_content.getText().toString());
                        long time = System.currentTimeMillis();
                        values.put("time",""+time);
                        contentresolver.insert(uri,values);
                        Intent intent = new Intent(SaveActivity.this,MainActivity.class);
                        startActivity(intent);
                        break;
                    //跟新
                    case R.id.mainactivity_item02:
                        Uri uri1 = Uri.parse("content://com.cbule.note");
                        ContentResolver contentresolver1 = getContentResolver();
                        ContentValues values1 = new ContentValues();
                        values1.put("title",et_title.getText().toString());
                        values1.put("content", et_content.getText().toString());
                        long time1 = System.currentTimeMillis();
                        values1.put("time",""+time1);
                        contentresolver1.update(uri1,values1,"_id="+id,null);
                        Intent intent2 = new Intent(SaveActivity.this,MainActivity.class);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });
    }


}
