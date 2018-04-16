package com.example.matteobellinaso.listspesaandroid.ui.adapter;

import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.matteobellinaso.listspesaandroid.R;
import com.example.matteobellinaso.listspesaandroid.data.Item;
import com.example.matteobellinaso.listspesaandroid.data.ItemList;
import com.example.matteobellinaso.listspesaandroid.data.db.DbListManager;

import java.util.ArrayList;
import java.util.List;

import static com.example.matteobellinaso.listspesaandroid.R.id.list_background;

/**
 * Created by matteobellinaso on 09/04/18.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>  {

        private AdapterView.OnItemClickListener longListener;
        private LayoutInflater mInflater;
        private Cursor cursor;
        private DbListManager dbListManager;
        private Context contesto;

    public  class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        private ImageView img;
        private View root;
        AlertDialog.Builder builder;

        public ViewHolder(final View view) {
            super(view);
            root = view;
            mTextView = view.findViewById(R.id.list_name);
            img = view.findViewById(R.id.list_background);

        }

        public void setOnItemClickCustom(Context context, final int position){
            context = root.getContext();
            builder = new AlertDialog.Builder(context);

            root.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    cursor.moveToPosition(position);
                    dbListManager.open();

                    builder.setTitle(R.string.alert_remove_list);
                    builder.setMessage("vuoi eliminare la lista " + cursor.getString(cursor.getColumnIndex(dbListManager.KEY_LIST_NAME)) + " ?");

                    builder.setPositiveButton((R.string.alert_confim), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dbListManager.deleteList(cursor.getInt(cursor.getColumnIndex(DbListManager.KEY_LIST_ID)));
                            swapCursor();
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

    public void swapCursor(){
        if(cursor != null && !cursor.isClosed()){
            cursor.close();
        }
        cursor = dbListManager.fetchAllList();
        notifyDataSetChanged();
    }

    public MyRecyclerAdapter(Context context){
        dbListManager = new DbListManager(context);
        dbListManager.open();
        mInflater = LayoutInflater.from(context);
        cursor = dbListManager.fetchAllList();
        contesto = context;
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

        String nameList = cursor.getString(cursor.getColumnIndex(dbListManager.KEY_LIST_NAME));
        holder.mTextView.setText(nameList);
        holder.setOnItemClickCustom(contesto, position);

        String  uri = cursor.getString(cursor.getColumnIndex(DbListManager.KEY_LIST_IMG));
        try{
            if(uri != null && uri.length() > 0) {
                holder.img.setImageURI(getUriFromString(uri));
            }else{
                holder.img.setImageURI(null);
                holder.mTextView.setTextColor(contesto.getColor(R.color.fontColor));
            }
        }catch(Exception e){
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


