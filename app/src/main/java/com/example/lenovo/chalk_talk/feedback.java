package com.example.lenovo.chalk_talk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class feedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
    }

    public void backarrow_feed(View view) {
        Intent i = new Intent(feedback.this,learner_home.class);
        startActivity(i);
    }
}
