package com.example.chaithra.booklistingapp;

import java.util.Arrays;

/**
 * Created by chaithra on 9/17/17.
 */

public class Book {
    private String title;
    private String[] authors;

    public Book(String title, String[] authors) {
        this.title = title;
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public String[] getAuthors() {
        return authors;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", authors=" + Arrays.toString(authors) +
                '}';
    }

}
