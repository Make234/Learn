package com.cbule.contentprovider.searchview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cbule.contentprovider.R;

import java.util.List;

public class SearchViewActivity extends AppCompatActivity {
    SearchView searchView;
    List<String> data;
    ArrayAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        listView = (ListView) findViewById(R.id.searchactivity_listview);
        data.add("sbsc");
        data.add("sjdfd");
        data.add("sbbadgassc");
        data.add("eetttdf");
        data.add("ljmop");
        data.add("jdsfoipauf");
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);
//        searchView = (SearchView) findViewById(R.id.searchactivity_searchview);
//        listView.setTextFilterEnabled(true);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                listView.setFilterText(newText);
//                return false;
//            }
//        });

    }
}
