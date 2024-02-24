package com.example.rssreader;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchBook extends AsyncTask<String, Void, String> {
    MainActivity context;
    private EditText inputname;
    private TextView titlebook;
    private TextView authorbook;

    private BookAdapter adapter;

    public FetchBook(MainActivity context , BookAdapter adapter, EditText inputname) {
        this.inputname = inputname;
        this.titlebook = titlebook;
        this.authorbook = authorbook;
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    protected String doInBackground(String... tensach) {

        String name_string = tensach[0];

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSon = null;

        try{

            final String BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?";

            final String QUERY_PARAM = "q";
            final String MAX_RESULTS = "maxResults";
            final String PRINT_TYPE = "printType";

            Uri builduri = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, name_string)
                    .appendQueryParameter(MAX_RESULTS,"10")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();

            URL requestURL = new URL(builduri.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            StringBuilder builder = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null){
                builder.append(line + "\n");
            }

            if (builder.length() == 0){
                return null;
            }
            bookJSon = builder.toString();

        }catch (IOException e){
            e.printStackTrace();
        } finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (reader != null){
                try{
                    reader.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        return bookJSon;
    }

    @Override
    protected void onPostExecute(String s) {

        super.onPostExecute(s);
        try{
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("items");

            int i = 0;
            String title = null;
//            String author = null;
            Toast.makeText(context.getApplicationContext(), "jsonArray.length(): " + jsonArray.length() , Toast.LENGTH_SHORT).show();

            while (i < jsonArray.length() || (/*author == null && */title == null) ){
                JSONObject book = jsonArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    title = volumeInfo.getString("title");
//                    author = volumeInfo.getString("author");
                    adapter.addTitle(title);

                }catch (Exception e){
                    e.printStackTrace();
                }

                i++;
            }

//            if (title != null){
//                Toast.makeText(context.getApplicationContext(), "Tên sách: " + title , Toast.LENGTH_SHORT).show();
//                titlebook.setText(title);
//                authorbook.setText(author);
//            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
