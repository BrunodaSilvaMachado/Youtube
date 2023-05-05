package com.cursoandroid.youtube.model;

import java.io.Serializable;

public class Item implements Serializable {
    public ItemId id;
    public Snippet snippet;

    public Item(){

    }

    public static class ItemId implements Serializable {
        public String kind;
        public String videoId;
    }
}
