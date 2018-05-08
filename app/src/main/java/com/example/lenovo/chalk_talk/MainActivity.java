package com.example.lenovo.chalk_talk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void learner_login(View view) {
        Intent i= new Intent(MainActivity.this,learner_login.class);
        startActivity(i);
    }

    public void tutor_login(View view) {
        Intent i = new Intent(MainActivity.this,tutor_login.class);
        startActivity(i);
    }
}
