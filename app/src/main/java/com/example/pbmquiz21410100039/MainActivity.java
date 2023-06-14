package com.example.pbmquiz21410100039;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static TextView debugText;
    static Context xc;


    static ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        debugText = findViewById(R.id.debugText);
        listView = findViewById(R.id.listView);
//        Untuk Mendapatkan konteks Class / view
        xc = getApplicationContext();

        Async x = new Async();
        x.execute("");
    }
}