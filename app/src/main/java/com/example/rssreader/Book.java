package com.example.rssreader;

public class Book {
    private String title;
    private String selfLink;

    public Book(String title, String selfLink) {
        this.title = title;
        this.selfLink = selfLink;
    }

    public String getTitle() {
        return title;
    }

    public String getSelfLink() {
        return selfLink;
    }
}
