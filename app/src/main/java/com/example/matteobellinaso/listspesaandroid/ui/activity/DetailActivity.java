package com.example.matteobellinaso.listspesaandroid.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.matteobellinaso.listspesaandroid.R;
import com.example.matteobellinaso.listspesaandroid.data.db.DatabaseHelper;
import com.example.matteobellinaso.listspesaandroid.data.db.DatabaseItemManager;
import com.example.matteobellinaso.listspesaandroid.ui.adapter.MyCursorAdapter;

public class DetailActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private DatabaseItemManager databaseItemManager;
    private Cursor cursor;
    private MyCursorAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Intent getDetail = getIntent();

        databaseItemManager = new DatabaseItemManager(this);
        databaseItemManager.open();

        cursor = databaseItemManager.fetchFromId(getDetail.getIntExtra("listId", 0));
        mAdapter = new MyCursorAdapter(this, cursor);

        ListView listView = (ListView) findViewById(R.id.listViewDetail);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView checked = (ImageView) view.findViewById(R.id.imgCheck);

                if (cursor.getInt(cursor.getColumnIndex("checked")) == 0) {
                    checked.setVisibility(View.VISIBLE);
                    databaseItemManager.updateList(cursor.getInt(cursor.getColumnIndex("_id")), 1);
                } else {
                    checked.setVisibility(View.INVISIBLE);
                    databaseItemManager.updateList(cursor.getInt(cursor.getColumnIndex("_id")), 0);
                }

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        databaseItemManager.close();
    }
}
