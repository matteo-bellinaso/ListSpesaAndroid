package com.example.matteobellinaso.listspesaandroid.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matteobellinaso.listspesaandroid.R;
import com.example.matteobellinaso.listspesaandroid.data.db.DatabaseListManager;
import com.example.matteobellinaso.listspesaandroid.logic.Utils;
import com.example.matteobellinaso.listspesaandroid.ui.activity.DetailActivity;
import com.example.matteobellinaso.listspesaandroid.ui.activity.ListActivity;

/**
 * Created by matteobellinaso on 09/04/18.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private AdapterView.OnItemClickListener longListener;
    private LayoutInflater mInflater;
    private Cursor cursor;
    private DatabaseListManager databaseListManager;
    private Context contesto;
    private int[] idToDetail;
    private AlertDialog.Builder builder;

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        private ImageView img;
        private View root;

        public ViewHolder(final View view) {
            super(view);
            root = view;
            mTextView = view.findViewById(R.id.list_name);
            img = view.findViewById(R.id.list_background);

        }


        public void setOnItemClickCustom(Context context, final int position) {
            context = root.getContext();
            final Context finalContext = context;

            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    cursor.moveToPosition(position);
                    databaseListManager.open();

                    Intent intentDetail = new Intent(contesto, DetailActivity.class);
                    intentDetail.putExtra("listId", idToDetail[position]);
                    intentDetail.putExtra("listName", cursor.getString(cursor.getColumnIndex(databaseListManager.KEY_LIST_NAME)));
                    contesto.startActivity(intentDetail);
                    databaseListManager.close();
                }
            });


            root.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    cursor.moveToPosition(position);
                    databaseListManager.open();

                    builder = new AlertDialog.Builder(finalContext);
                    builder.setTitle(R.string.alert_remove_list);
                    builder.setMessage("vuoi eliminare la lista " + cursor.getString(cursor.getColumnIndex(databaseListManager.KEY_LIST_NAME)) + "?");

                    builder.setPositiveButton((R.string.alert_confim), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            databaseListManager.deleteList(cursor.getInt(cursor.getColumnIndex(databaseListManager.KEY_LIST_ID)));
                            swapCursor();
                            Utils.setNList(finalContext);
                            ListActivity.setNlist();
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

        }
    }

    public void swapCursor() {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        cursor = databaseListManager.fetchListByUser(Utils.readId(contesto));
        notifyDataSetChanged();
    }

    public MyRecyclerAdapter(Context context) {
        databaseListManager = new DatabaseListManager(context);
        databaseListManager.open();
        mInflater = LayoutInflater.from(context);
        cursor = databaseListManager.fetchListByUser(Utils.readId(context));
        contesto = context;
        idToDetail = new int[cursor.getCount()];
    }

    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Passing the inflater job to the cursor-adapter
        View v = mInflater.inflate(R.layout.item_layout_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyRecyclerAdapter.ViewHolder holder, int position) {

        cursor.moveToPosition(position);

        idToDetail[position] = cursor.getInt(cursor.getColumnIndex("_id"));
        Log.d("iddetail","pos "+position);
        String nameList = cursor.getString(cursor.getColumnIndex(databaseListManager.KEY_LIST_NAME));
        holder.mTextView.setText(nameList);
        holder.setOnItemClickCustom(contesto, position);

        String imgString = cursor.getString(cursor.getColumnIndex(databaseListManager.KEY_LIST_IMG));

        String uri = cursor.getString(cursor.getColumnIndex(DatabaseListManager.KEY_LIST_IMG));
        try {
            if (uri != null && uri.length() > 0) {
                holder.img.setImageURI(getUriFromString(uri));
            } else {
                holder.img.setImageURI(null);
                holder.mTextView.setTextColor(contesto.getColor(R.color.fontColor));
            }
        } catch (Exception e) {
            Log.w("LIST_EXAMPLE", "Exception while retrieving resource " + uri);
        }
    }


    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public Uri getUriFromString(String stringUri) {
        Uri uri = Uri.parse(stringUri);
        return uri;
    }


}