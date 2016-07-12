package com.cbule.contentprovider;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
    final int menu_del = 1;
    final int menu_update = 2;
    ListView listView;
    Uri uri;
    ContentResolver resolver;
    Cursor cursor;
    long id;
    SimpleCursorAdapter adapter;
    Toolbar toolbar;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData(null);


    }


    @Override
    protected void onRestart() {
        super.onRestart();
        initData(null);
    }

    private void initData(String str) {
        uri = Uri.parse("content://com.cbule.note");
        resolver = getContentResolver();
        String[] ps = {"_id", "title", "content", "time"};
        String where = "title like '%" + str + "%'";
        if (str == null) {
            where = null;
        }
        cursor = resolver.query(uri, ps, where, null, "time desc");
        String[] from = {"title"};
        int[] to = {android.R.id.text1};
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, from, to);
        listView.setAdapter(adapter);

    }

    private void initView() {
        listView = (ListView) findViewById(R.id.mainactivity_listview);
        searchView = (SearchView) findViewById(R.id.mainactivity_searchview);
        initsearchview();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Long id = adapter.getItemId(i);
                Intent intent = new Intent(MainActivity.this, MediaPlayActivity.class);
                uri = Uri.parse("content://com.cbule.note");
                resolver = getContentResolver();
                cursor = resolver.query(uri, new String[]{"content"}, "_id=" + id, null, null);
                if (cursor != null && cursor.moveToNext()) {
                    String content = cursor.getString(0);
                    System.out.print("---"+content);
                    if (content.endsWith(".amr")) {
                        intent.putExtra("content", content);
                        startActivity(intent);
                    }
                }
            }
        });
        registerForContextMenu(listView);
        toolbar = (Toolbar) findViewById(R.id.mainactivity_toolbar);
        //设置导航图标
        toolbar.setNavigationIcon(R.drawable.home);
        //设置导航监听
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //设置logo图标
//        toolbar.setLogo(R.drawable.game006);
        //设置标题
        toolbar.setTitle("退出");
        //填充toolbar后面内容
        toolbar.inflateMenu(R.menu.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mainactivity_item01:
                        Intent intent = new Intent(MainActivity.this, SaveActivity.class);
                        startActivity(intent);
                        break;
//                    case R.id.mainactivity_searchview:
//                        System.out.println("--55");
//
//                        break;

                }
                return true;
            }
        });
    }

    private void initsearchview() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               initData(newText);
                if (TextUtils.isEmpty(newText)){
                    adapter.notifyDataSetChanged();
                }


                return false;
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case menu_del:
                resolver.delete(uri, "_id=" + id, null);
                cursor.requery();
                adapter.notifyDataSetChanged();
                break;
            case menu_update:
                Intent intent = new Intent(MainActivity.this, SaveActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        id = info.id;
        menu.add(menu.NONE, menu_del, 100, "删除");
        menu.add(menu.NONE, menu_update, 100, "更新");


    }

    public void recorderstart(View view) {
        Intent intent = new Intent(MainActivity.this, RecordActivity.class);
        startActivity(intent);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.toolbar,menu);
//        //获得Menu中的item对象
//        MenuItem menuItem = menu.findItem(R.id.mainactivity_searchview);
//        //获取item对象中的SearchView对象
//        searchView = (SearchView) menuItem.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            //内容提交的时候使用的方法
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(MainActivity.this, "提交的："+query, Toast.LENGTH_SHORT).show();
//                searchView.setIconified(true);
//                return true;
//            }
//            //内容改变的时候使用的方法
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if (!TextUtils.isEmpty(newText)) {
//                    initData(newText);
//                    Toast.makeText(MainActivity.this,newText, Toast.LENGTH_SHORT).show();
//                } else {
//                    adapter.notifyDataSetChanged();
//                }
//                Toast.makeText(MainActivity.this,newText, Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }
}
