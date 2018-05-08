package com.example.lenovo.chalk_talk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class learner_home extends AppCompatActivity {

    private TextView mTextMessage;
    FragmentManager manager;
    DrawerLayout mDrawerLayout;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.all_courses:
                    manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    course_learner learner = new course_learner();
                    transaction.replace(R.id.main_frame, learner);
                    transaction.commit();
                    return true;
                case R.id.course_opt:
                    manager = getSupportFragmentManager();
                    FragmentTransaction transaction1 = manager.beginTransaction();
                    course_opt oopt = new course_opt();
                    transaction1.replace(R.id.main_frame, oopt);
                    transaction1.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learner_home);
        mDrawerLayout= findViewById(R.id.learner_home);
        manager = getSupportFragmentManager();
        FragmentTransaction transaction1 = manager.beginTransaction();
        course_opt oopt = new course_opt();
        transaction1.replace(R.id.main_frame, oopt);
        transaction1.commit();
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void drawer(View v) {

        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    public void user_profile(View view)
    {
        Intent i =new Intent(learner_home.this,User_profile.class);
        startActivity(i);
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    public void home(View view)
    {
        Intent i = new Intent(learner_home.this,learner_home.class);
        startActivity(i);
        mDrawerLayout.closeDrawer(Gravity.LEFT);

    }

    public void setting(View view) {
        Intent i = new Intent(learner_home.this, settings.class);
        startActivity(i);
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    public void feedback(View view) {
        Intent i = new Intent(learner_home.this, feedback.class);
        startActivity(i);
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    public void logout(View view) {
        FirebaseAuth f_auth = FirebaseAuth.getInstance();
        f_auth.signOut();
        finish();
        startActivity(new Intent(this, learner_login.class));
    }

    public void search(View view) {

        Intent i = new Intent(learner_home.this , SearchActivity.class);

        startActivity(i);
    }
}
