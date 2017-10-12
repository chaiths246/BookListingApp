package com.example.chaithra.booklistingapp;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by chaithra on 9/17/17.
 */

public class BookAdapter extends ArrayAdapter{


    public BookAdapter(Context context) {

        super(context,0);
    }

    @Override
    public int getCount() {
        return  BookManager.Instance.totalBooks();
    }

    @Override
    public Book getItem(int position) {

        return BookManager.Instance.getBook(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Book book= getItem(position);
        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.booklist, parent, false);
        }
        TextView titletextview= (TextView) convertView.findViewById(R.id.title);
        TextView authortextview= (TextView) convertView.findViewById(R.id.author);

        titletextview.setText(book.getTitle());
        authortextview.setText(TextUtils.join(", ", book.getAuthors()));
        return convertView;
    }

}
