package com.cursoandroid.youtube.model;

import java.io.Serializable;
import java.util.Date;

public class Snippet implements Serializable {
    public Date publishedAt;
    public String channelId;
    public String title;
    public String description;
    public String channelTitle;
    public SnippetThumbnails thumbnails;
    public String liveBroadcastContent;

    public static class SnippetThumbnails implements Serializable {
        public Thumbnail defaultThumbnail;
        public Thumbnail medium;
        public Thumbnail high;
    }

    public static class Thumbnail implements Serializable{
        public String url;
    }
}
