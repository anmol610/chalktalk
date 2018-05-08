package com.example.lenovo.chalk_talk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Tutor_home_page extends AppCompatActivity {

    private TextView mTextMessage;
    FragmentManager manager;
    DrawerLayout mDrawerLayout;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.add_course:
                    manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                     add_course add = new add_course();
                    transaction.replace(R.id.main_frame, add);
                    transaction.commit();
                    return true;
                case R.id.all_courses_tutor:
                    manager = getSupportFragmentManager();
                    FragmentTransaction transaction1 = manager.beginTransaction();
                    tutor_course all = new tutor_course();
                    transaction1.replace(R.id.main_frame, all);
                    transaction1.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_home_page);
        mDrawerLayout=findViewById(R.id.tutor_home);
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        add_course add = new add_course();
        transaction.replace(R.id.main_frame, add);
        transaction.commit();
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    public void home(View view) {
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        course_learner learner = new course_learner();
        transaction.replace(R.id.main_frame, learner);
        transaction.commit();
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }


    public void tutor_profile(View view) {
        mDrawerLayout.closeDrawer(Gravity.LEFT);
        Intent i = new Intent(Tutor_home_page.this,Tutor_profile.class);
        startActivity(i);
    }

    public void upload(View view) {
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    public void profile(View view) {
        mDrawerLayout.closeDrawer(Gravity.LEFT);
        Intent i = new Intent(Tutor_home_page.this,User_profile.class);
        startActivity(i);
    }

    public void drawer_tutor(View view) {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    public void logut_tutor(View view) {
        FirebaseAuth f_auth = FirebaseAuth.getInstance();
        f_auth.signOut();
        finish();
        startActivity(new Intent(this, learner_login.class));
    }
    }


