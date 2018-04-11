package com.example.matteobellinaso.listspesaandroid.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.matteobellinaso.listspesaandroid.R;
import com.example.matteobellinaso.listspesaandroid.data.Item;
import com.example.matteobellinaso.listspesaandroid.data.ItemList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matteobellinaso on 09/04/18.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
        private List<ItemList> mItemList;
        private LayoutInflater mInflater;
        private Context contesto;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        private LinearLayout img;

        public ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.list_name);
            img = v.findViewById(R.id.list_background);
        }
    }

    public MyRecyclerAdapter(Context context,List<ItemList> itemLists ){
        mItemList = itemLists;
        mInflater = LayoutInflater.from(context);
        contesto = context;
    }


    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_layout_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyRecyclerAdapter.ViewHolder holder, int position) {
        String nameList = mItemList.get(position).getName();
        holder.mTextView.setText(nameList);

        String uri = mItemList.get(position).getUri();

        try{
            if(uri != null && uri.length() >0) {
                int imgResource = contesto.getResources().getIdentifier(uri, "drawable", contesto.getPackageName());
                Drawable image = contesto.getResources().getDrawable(imgResource , null);
                holder.img.setBackground(image);
            }
        }catch(Exception e){
            Log.w("LIST_EXAMPLE", "Exception while retrieving resource " + uri);
        }

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
