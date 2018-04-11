package com.example.matteobellinaso.listspesaandroid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.matteobellinaso.listspesaandroid.R;
import com.example.matteobellinaso.listspesaandroid.data.ItemList;

/**
 * Created by matteobellinaso on 09/04/18.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
        private ItemList mItemList[];
        private LayoutInflater mInflater;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        private LinearLayout img;
        private ItemList mItemList[];

        public ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.list_name);
            img = v.findViewById(R.id.list_background);
        }
    }

    public MyRecyclerAdapter(Context context,ItemList[] itemLists ){
        mItemList = itemLists;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_layout_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyRecyclerAdapter.ViewHolder holder, int position) {
        String nameList = mItemList[position].getName();
        holder.mTextView.setText(nameList);

        String uri = mItemList[position].getUri();

        

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
