package com.example.lenovo.chalk_talk;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.Transaction;
import java.util.logging.LogRecord;
import java.util.logging.MemoryHandler;

public class firstpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);
        Handler h = new Handler();

        h.postDelayed(new Runnable() {
            @Override
            public void run() {


                    Intent i = new Intent(firstpage.this,MainActivity.class);

                    startActivity(i);

                    finish();

            }
        }, 3000);

    }
}
