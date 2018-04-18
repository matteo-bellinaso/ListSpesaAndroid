package com.example.matteobellinaso.listspesaandroid.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matteobellinaso.listspesaandroid.R;
import com.example.matteobellinaso.listspesaandroid.data.db.DatabaseHelper;
import com.example.matteobellinaso.listspesaandroid.data.db.DatabaseItemManager;

public class MyCursorAdapter extends CursorAdapter {

    private DatabaseHelper dbHelper;
    private DatabaseItemManager databaseItemManager;

    public MyCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_layout, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        final TextView name = (TextView) view.findViewById(R.id.productName);
        final ImageView check = (ImageView) view.findViewById(R.id.imgCheck);

        name.setText(cursor.getString(cursor.getColumnIndex("name")));

        if (cursor.getInt(cursor.getColumnIndex("checked")) == 1) {
            check.setVisibility(View.VISIBLE);
        }
    }
}
