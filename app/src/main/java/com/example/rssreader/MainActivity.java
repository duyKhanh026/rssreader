package com.example.rssreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText inputname;

    RecyclerView recyclerView;
    Button search;

    BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputname = findViewById(R.id.inputname);
        search = findViewById(R.id.buttonsreach);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo Adapter
        adapter = new BookAdapter(this);
        recyclerView.setAdapter(adapter);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchbook();
            }
        });
    }



    public void searchbook(){
        String ten_sach = inputname.getText().toString();

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() && ten_sach.length() != 0){
            new FetchBook(this , adapter, inputname).execute(ten_sach);
        }else {
            Toast.makeText(this, "Chưa kết nối internet", Toast.LENGTH_SHORT).show();
        }
    }


}