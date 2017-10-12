package com.example.chaithra.booklistingapp;

import java.util.ArrayList;

/**
 * Created by chaithra on 9/25/17.
 */

public class BookManager {
    static final BookManager Instance = new BookManager();
    private ArrayList<Book> listOfBooks = new ArrayList<>();

    public Book getBook(int position) {
        return listOfBooks.get(position);
    }

    public int totalBooks() {
        return listOfBooks.size();
    }


    public void addBooks(ArrayList<Book> books) {
        if (books != null) {
            listOfBooks.addAll(books);
        }


    }

    public void removeBooks() {
        listOfBooks.removeAll(listOfBooks);
    }
}
