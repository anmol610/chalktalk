package com.example.lenovo.chalk_talk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class course_detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_details);

    }

    public void view_vid(View view)
    {
        startActivity(new Intent(this,course_vidd.class));
    }

    public void book(View view) {
        startActivity(new Intent(this,book_course.class));
    }

    public void feedback(View view) {
        startActivity(new Intent(this,feedback.class));
    }
}
