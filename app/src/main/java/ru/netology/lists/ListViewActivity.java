package ru.netology.lists;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
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

    ArrayList<Integer> removeElements = new ArrayList<>();
    int a = 0;
    List<Map<String, String>> simpleAdapterContent = new ArrayList<>();
    List<Map<String, String>> deleteElements = new ArrayList<>();
    BaseAdapter listContentAdapter;
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
        listContentAdapter = createAdapter(simpleAdapterContent);
        list.setAdapter(listContentAdapter);


        // удаляю элемент
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> element = (Map<String, String>)listContentAdapter.getItem(position);
                removeElements.add(simpleAdapterContent.indexOf(element));
                simpleAdapterContent.remove(element);
                deleteElements.add(element);
                listContentAdapter.notifyDataSetChanged();
            }
        });

        // Создаю обновление, но он почему-то не обновляется и ломается удаление
        final SwipeRefreshLayout swipeLayout = findViewById(R.id.swipe);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                метод 1, который работает. При удалении элемента, добавляю его в отдельный список. Здесь перебираю этот список и добавляю все обратно.
//                for (Map<String, String> listEl:deleteElements) {
//                    simpleAdapterContent.add(listEl);
//                }
//                deleteElements.clear();

//                метод 2, который не работает. Просто пересоздаю список
//                simpleAdapterContent = getContent();
                removeElements.clear();
                listContentAdapter.notifyDataSetChanged();
                swipeLayout.setRefreshing(false);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("deleteIndexes", removeElements);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        Bundle bundle = new Bundle();
        ArrayList<Integer> indexes =  bundle.getIntegerArrayList("deleteIndexes");
        for (Integer index:indexes) {
            deleteElements.add(simpleAdapterContent.get(index));
            simpleAdapterContent.remove(index.intValue());
            listContentAdapter.notifyDataSetChanged();
        }
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
            Map<String, String> content = new HashMap<>();
            content.put("Заголовок", arrayContent[i]);
            content.put("Подзаголовок", Integer.toString(arrayContent[i].length()));
            strings.add(content);
        }
        return strings;
    }
}
