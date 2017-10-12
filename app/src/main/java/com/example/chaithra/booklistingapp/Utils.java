package com.example.chaithra.booklistingapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by chaithra on 9/18/17.
 */

public final class Utils {


    public static final String LOG_TAG = Utils.class.getSimpleName();


    public static ArrayList<Book> fetchBookData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        ArrayList<Book> listOfbooks = jsonconverter(jsonResponse);

        return listOfbooks;
    }


    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";


        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                Log.d("Network", "" + line);
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static ArrayList<Book> jsonconverter(String bookJSON) {

        ArrayList<Book> bookarraylist = new ArrayList<Book>();


        try {
            JSONObject bookjsonObject = new JSONObject(bookJSON);
            if (!bookjsonObject.isNull("items")) {
                JSONArray bookjsonarray = bookjsonObject.getJSONArray("items");

                for (int i = 0; i < bookjsonarray.length(); i++) {

                    JSONObject itemJsonObject = bookjsonarray.getJSONObject(i);
                    JSONObject volumeInfoObject = itemJsonObject.getJSONObject("volumeInfo");

                    String title = volumeInfoObject.getString("title");

                    String[] authors = new String[]{"No Authors"};

                    if (!volumeInfoObject.isNull("authors")) {
                        JSONArray authorsArray = volumeInfoObject.getJSONArray("authors");
                        Log.d(LOG_TAG, "authors #" + authorsArray.length());
                        authors = new String[authorsArray.length()];
                        for (int j = 0; j < authorsArray.length(); j++) {
                            authors[j] = authorsArray.getString(j);
                        }
                    }
                    bookarraylist.add(new Book(title, authors));
                }
            } else {
                bookarraylist = null;
            }
        } catch (JSONException e) {
            Log.d(LOG_TAG, e.toString());
        }
        return bookarraylist;

    }
}


