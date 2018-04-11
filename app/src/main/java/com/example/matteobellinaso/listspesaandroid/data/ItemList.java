package com.example.matteobellinaso.listspesaandroid.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matteobellinaso on 09/04/18.
 */

public class ItemList {
    private long id;
    private String name;
    private String img;
    private List<Item> itemList;

    public ItemList(long id, String name, String img){
        this.id = id;
        this.name = name;
        this.img = img;
    }

    public void addItem(Item item){
        itemList.add(item);
    }

    public List<Item> getList(){
        return this.itemList;
    }


}
