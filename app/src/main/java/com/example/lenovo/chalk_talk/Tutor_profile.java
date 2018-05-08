package com.example.lenovo.chalk_talk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Tutor_profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile);
    }

    public void backarrow1(View view) {
        Intent i= new Intent(Tutor_profile.this,Tutor_home_page.class);
        startActivity(i);
    }
}
