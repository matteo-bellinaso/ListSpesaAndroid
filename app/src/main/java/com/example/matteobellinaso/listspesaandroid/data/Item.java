package com.example.matteobellinaso.listspesaandroid.data;

/**
 * Created by matteobellinaso on 09/04/18.
 */

public class Item {

    private long id;
    private String name;
    private String dose;
    private int qnt;

    public Item(long id, String name, String dose, int qnt){
        this.id = id;
        this.name = name;
        this.dose = dose;
        this.qnt = qnt;
    }

}
