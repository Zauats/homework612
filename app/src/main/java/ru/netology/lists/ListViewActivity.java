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

    List<String> from = new ArrayList<String>();
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

        int[] texts = {findViewById(R.id.el1).getId(), findViewById(R.id.el2).getId()};
        String[] fromTemp = (String[])from.toArray();
        return new SimpleAdapter(this, values, android.R.layout.simple_list_item_1, fromTemp, texts);
    }

    @NonNull
    private List<Map<String, String>> prepareContent() {
        List<Map<String, String>> strings = new ArrayList<Map<String, String>>();

        String[] arrayContent = getString(R.string.large_text).split("\n\n");
        for (int i = 0; i < arrayContent.length - 1; i += 2) {
            Map<String, String> content = new HashMap<String, String>();
            content.put(arrayContent[i], Integer.toString(arrayContent[i].length()));
            content.put(arrayContent[i + 1], Integer.toString(arrayContent[i + 1].length()));
            from.add(arrayContent[i]);
            from.add(arrayContent[i + 1]);
            strings.add(content);
        }
        return strings;
    }
}
