package ru.netology.lists;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView list = findViewById(R.id.list);

        List<Map<String, String>> values = prepareContent();

        BaseAdapter listContentAdapter = createAdapter(values);
        list.setAdapter(listContentAdapter);
    }

    @NonNull
    private BaseAdapter createAdapter(List<Map<String, String>> values) {

        int[] texts = {R.id.el1, R.id.el2};
        return new SimpleAdapter(this, values, R.layout.list_item, new String[] {"Заголовок", "Подзаголовок"}, texts);
    }

    @NonNull
    private List<Map<String, String>> prepareContent() {
        List<Map<String, String>> strings = new ArrayList<Map<String, String>>();

        String[] arrayContent = getString(R.string.large_text).split("\n\n");
        for (int i = 0; i < arrayContent.length - 1; i++) {
            Map<String, String> content = new HashMap<String, String>();
            content.put("Заголовок", arrayContent[i]);
            content.put("Подзаголовок", Integer.toString(arrayContent[i].length()));
            strings.add(content);
        }
        return strings;
    }
}
