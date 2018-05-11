package com.example.lenovo.chalk_talk;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class course_learner extends Fragment {
    RelativeLayout computer, tech, dance, music;

    public course_learner() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.course_fragment, container, false);
        computer = v.findViewById(R.id.computer_course);
        computer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent myIntent = new Intent(getContext(), course_lists.class);
                myIntent.putExtra("course", "Computer Science");
                startActivity(myIntent);


            }
        });
        tech = v.findViewById(R.id.tech_course);
        tech.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent myIntent = new Intent(getContext(), course_lists.class);
                myIntent.putExtra("course", "Technology");
                startActivity(myIntent);

            }

        });
        dance = v.findViewById(R.id.dance_course);
        dance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent myIntent = new Intent(getContext(), course_lists.class);
                myIntent.putExtra("course", "Dance");
                startActivity(myIntent);


            }
        });
        music = v.findViewById(R.id.music_course);
        music.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent myIntent = new Intent(getContext(), course_lists.class);
                myIntent.putExtra("course", "Music");
                startActivity(myIntent);


            }
        });
        return v;
    }

}
