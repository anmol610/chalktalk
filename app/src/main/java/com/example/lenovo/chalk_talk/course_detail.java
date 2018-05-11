package com.example.lenovo.chalk_talk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.chalk_talk.dataModel.joined_course;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class course_detail extends AppCompatActivity {

    private TextView course_id , course_name , course_duration , tutor_name , tutor_email ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_details);

        course_id = findViewById(R.id.course_id);

        course_name = findViewById(R.id.course_name);

        course_duration = findViewById(R.id.course_duration);

        tutor_name = findViewById(R.id.tutor_name);

        tutor_email = findViewById(R.id.tutor_email);


        course_id.setText(getIntent().getStringExtra("courseid"));

        course_name.setText(getIntent().getStringExtra("coursename"));

        course_duration.setText(getIntent().getStringExtra("courseduration")+" months");

        tutor_name.setText(getIntent().getStringExtra("coursetutor"));

        tutor_email.setText(getIntent().getStringExtra("tutor_contact"));

    }



    public void book(View view) {

        final ProgressDialog pd = new ProgressDialog(course_detail.this);

        pd.setTitle("Savving");
        pd.setMessage("Please wait");

        pd.show();

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail().replace("." , "");

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        joined_course course_data = new joined_course( getIntent().getStringExtra("coursename") , getIntent().getStringExtra("courseid") ,
                getIntent().getStringExtra("courseduration") , getIntent().getStringExtra("coursetutor") , getIntent().getStringExtra("course_category") , String.valueOf(System.currentTimeMillis()));


        database.getReference().child("joined_courses").child(email).child(getIntent().getStringExtra("courseid")).setValue(course_data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                pd.hide();

                Toast.makeText(course_detail.this , "course joined" , Toast.LENGTH_SHORT).show();


                finish();

            }
        });

    }


}
