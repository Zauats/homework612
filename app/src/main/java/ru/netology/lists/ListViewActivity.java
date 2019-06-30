package ru.netology.lists;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class ListViewActivity extends AppCompatActivity {


    List<Map<String, String>> simpleAdapterContent = new ArrayList<>();
    SharedPreferences data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // что-то там дефолтное
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ListView list = findViewById(R.id.list);

        // добавляю данные в SharedPreference
        data = getPreferences(MODE_PRIVATE);
        if (!data.contains(getString(R.string.large_text))){
            SharedPreferences.Editor myEditor = data.edit();
            myEditor.putString("string", getString(R.string.large_text));
            myEditor.apply();
        }

        simpleAdapterContent = getContent();
        final BaseAdapter listContentAdapter = createAdapter(simpleAdapterContent);
        list.setAdapter(listContentAdapter);

        // удаляю элемент
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> element = (Map<String, String>)listContentAdapter.getItem(position);
                simpleAdapterContent.remove(element);
                listContentAdapter.notifyDataSetChanged();
            }
        });

        // Создаю обновление, но он почему-то не обновляется и ломается удаление
        final SwipeRefreshLayout swipeLayout = findViewById(R.id.swipe);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                simpleAdapterContent = getContent();
                listContentAdapter.notifyDataSetChanged();
                swipeLayout.setRefreshing(false);
            }
        });

    }


    @NonNull
    private BaseAdapter createAdapter(List<Map<String, String>> values) {

        int[] texts = {R.id.el1, R.id.el2};
        return new SimpleAdapter(this, values, R.layout.list_item, new String[] {"Подзаголовок", "Заголовок" }, texts);
    }

    @NonNull
    private List<Map<String, String>> getContent() {
        List<Map<String, String>> strings = new ArrayList<Map<String, String>>();

        String[] arrayContent = data.getString("string", "").split("\n\n");
        for (int i = 0; i < arrayContent.length - 1; i++) {
            Map<String, String> content = new HashMap<String, String>();
            content.put("Заголовок", arrayContent[i]);
            content.put("Подзаголовок", Integer.toString(arrayContent[i].length()));
            strings.add(content);
        }
        return strings;
    }
}
