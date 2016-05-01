package com.androidproject.popularmovies11;

import java.io.Serializable;

public class Reviews implements Serializable{
    private String author, content;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
