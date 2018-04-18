package com.example.matteobellinaso.listspesaandroid.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.matteobellinaso.listspesaandroid.R;
import com.example.matteobellinaso.listspesaandroid.data.db.DatabaseItemManager;
import com.example.matteobellinaso.listspesaandroid.ui.adapter.MyCursorAdapter;

public class DetailActivity extends AppCompatActivity {

    private DatabaseItemManager databaseItemManager;
    private Cursor cursor;
    private MyCursorAdapter mAdapter;
    private ListView listView;
    private int listId;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Intent getDetail = getIntent();
        listId = getDetail.getIntExtra("listId", 0);

        databaseItemManager = new DatabaseItemManager(this);
        databaseItemManager.open();

        cursor = databaseItemManager.fetchFromId(listId);
        mAdapter = new MyCursorAdapter(this, cursor);

        listView = (ListView) findViewById(R.id.listViewDetail);
        listView.setAdapter(mAdapter);

        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkItems);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mAdapter.swapCursor(databaseItemManager.checkedValues(listId));
                    mAdapter.notifyDataSetChanged();
                } else {
                    mAdapter.swapCursor(databaseItemManager.fetchFromId(listId));
                    mAdapter.notifyDataSetChanged();
                }
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView checked = (ImageView) view.findViewById(R.id.imgCheck);


                Cursor currentCursor = mAdapter.getCursor();

                if (currentCursor.getInt(currentCursor.getColumnIndex("checked")) == 0) {
                    databaseItemManager.updateItem(currentCursor.getInt(currentCursor.getColumnIndex("_id")), 1);
                    checked.setVisibility(View.VISIBLE);
                } else {
                    databaseItemManager.updateItem(currentCursor.getInt(currentCursor.getColumnIndex("_id")), 0);
                    checked.setVisibility(View.INVISIBLE);
                }

                if (checkBox.isChecked()) {
                    mAdapter.swapCursor(databaseItemManager.checkedValues(listId));
                    mAdapter.notifyDataSetChanged();
                } else {
                    mAdapter.swapCursor(databaseItemManager.fetchFromId(listId));
                    mAdapter.notifyDataSetChanged();
                }

            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Cursor currentCursor = mAdapter.getCursor();

                builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setTitle(R.string.alert_remove_list);
                builder.setMessage("Vuoi eliminare il prodotto " + cursor.getString(currentCursor.getColumnIndex("name")) + "?");

                builder.setPositiveButton((R.string.alert_confim), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseItemManager.deleteItem(currentCursor.getInt(currentCursor.getColumnIndex("_id")));
                        mAdapter.swapCursor(databaseItemManager.fetchFromId(listId));
                        listView.setAdapter(mAdapter);

                    }
                });
                builder.setNegativeButton((R.string.alert_undo), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                return true;
            }
        });


        FloatingActionButton buttons = (FloatingActionButton) findViewById(R.id.floating_button);

        buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addListDialog();
            }
        });

    }

    public void addListDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_custom, null);
        ImageButton imgButt = dialogView.findViewById(R.id.add_list_img);
        imgButt.setVisibility(View.GONE);
        dialogBuilder.setView(dialogView);
        final EditText edit = (EditText) dialogView.findViewById(R.id.edit_add_list);
        dialogBuilder.setTitle(R.string.add_name_list);

        dialogBuilder.setPositiveButton(R.string.alert_confim, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                databaseItemManager.createItem(edit.getText().toString(), 0, listId);
                mAdapter.swapCursor(databaseItemManager.fetchFromId(listId));
                listView.setAdapter(mAdapter);
            }
        });

        dialogBuilder.setNegativeButton(R.string.alert_undo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialogBuilder.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        databaseItemManager.close();
    }
}
