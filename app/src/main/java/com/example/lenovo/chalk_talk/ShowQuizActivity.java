package com.example.lenovo.chalk_talk;

import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.lenovo.chalk_talk.dataModel.AllQuizQuestionsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;

public class ShowQuizActivity extends AppCompatActivity {


    public static ViewPager quiz_pager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_quiz);

        getSupportActionBar().setTitle("Quiz");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        quiz_pager = findViewById(R.id.quiz_pager);

        get_quiz();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return  true;
    }


    private void get_quiz()
    {
        System.out.println("fetching  quizzzz");
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        database.getReference().child("quiz").child(getIntent().getStringExtra("course_id")+"_"+getIntent().getStringExtra("module_id")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                System.out.println("fetched  quizzzz");


                AllQuizQuestionsModel model = dataSnapshot.getValue(AllQuizQuestionsModel.class);

                try {

                    System.out.println(model.quiz_question);

                    JSONArray jsonArray = new JSONArray(model.quiz_question);

                    quiz_pager.setAdapter(new QuizPagerAdapter(jsonArray , ShowQuizActivity.this));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
