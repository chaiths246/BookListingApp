package com.example.chaithra.booklistingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button button;
    ListView listView;
    BookAdapter bookAdapter;
    private static final String USGS_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes";
    EditText editText;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.search);
        textView = (TextView) findViewById(R.id.textview);
        editText = (EditText) findViewById(R.id.text_to_search);
        listView = (ListView) findViewById(R.id.list);
        bookAdapter = new BookAdapter(this);
        listView.setAdapter(bookAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isonline = isOnline();
                if (isonline == false) {
                    textView.setText("No network connected");
                } else {
                    textView.setVisibility(View.INVISIBLE);
                    editText.clearFocus();
                    String url = USGS_REQUEST_URL + "?" + generateQueryParameters(20, editText.getText().toString());
                    Log.e("MainActivity", url);
                    BookAsynchronous bookAsynchronous = new BookAsynchronous();
                    bookAsynchronous.execute(url);
                }
            }

        });

    }


    private String generateQueryParameters(int volume, String searchText) {
        return "maxVolume=" + volume + "&q=" + (TextUtils.isEmpty(searchText) ? "test_result" : searchText);
    }

    public void updateUi() {

        listView.setAdapter(bookAdapter);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private class BookAsynchronous extends AsyncTask<String, Void, ArrayList<Book>> {


        @Override
        protected ArrayList<Book> doInBackground(String... url) {

            ArrayList<Book> books = Utils.fetchBookData(url[0]);
            return books;
        }

        @Override
        protected void onPostExecute(ArrayList<Book> books) {
            BookManager.Instance.removeBooks();
            BookManager.Instance.addBooks(books);
            updateUi();

        }
    }
}

