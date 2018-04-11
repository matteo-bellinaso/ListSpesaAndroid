package com.example.matteobellinaso.listspesaandroid.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.matteobellinaso.listspesaandroid.R;

/**
 * Created by matteobellinaso on 09/04/18.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

        private LayoutInflater mInflater;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        private LinearLayout img;

        public ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.list_name);
            img = v.findViewById(R.id.list_background);
        }
    }

    public MyRecyclerAdapter(){

    }


    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_layout_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyRecyclerAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
