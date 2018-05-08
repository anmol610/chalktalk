package com.example.lenovo.chalk_talk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.chalk_talk.dataModel.course_details;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class course_lists extends AppCompatActivity {
    String course;
    ArrayList<course_details> course_list;
RecyclerView course_recycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_lists);
        course = getIntent().getStringExtra("course");
        course_list = new ArrayList<>();

        course_recycler = findViewById(R.id.learner_recycle);

        course_recycler.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.VERTICAL, false));
        get_course_list(course);

    }

    private void get_course_list(String course) {
        FirebaseAuth firebase = FirebaseAuth.getInstance();
        FirebaseDatabase data = FirebaseDatabase.getInstance();
        System.out.println("rrrr");

        String category = getIntent().getStringExtra("course");

        data.getReference().child("course").child(category).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                course_list.clear();


                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    for(DataSnapshot data2 : data.getChildren()) {
                        course_details details = data2.getValue(course_details.class);

                        course_details detail_with_key = new course_details(details.course_name , details.course_id , details.courseduration , details.type , details.tutorname , details.course_category , data2.getKey() , data.getKey());
                        System.out.println("rrrrrr");
                        course_list.add(detail_with_key);
                    }


                }
                Adapter adapter = new Adapter();

                course_recycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }
        );
    }
    public class view_holder extends RecyclerView.ViewHolder{

        TextView course_id,course_name;
        LinearLayout course_lay;
        ImageView course_img;
        public view_holder(View itemView) {
            super(itemView);

            course_name = itemView.findViewById(R.id.course_name);
            course_lay = itemView.findViewById(R.id.course_lay);
            course_id = itemView.findViewById(R.id.course_id);
            course_img = itemView.findViewById(R.id.course_img);
        }
    }

    public class Adapter extends RecyclerView.Adapter<view_holder>
    {

        @Override
        public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {

            view_holder v = new view_holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.course_cell,parent , false ));

            return v ;
        }

        @Override
        public void onBindViewHolder(view_holder holder, int position) {


            final course_details data=course_list.get(position);
            holder.course_name.setText(data.course_name);
            if (course.equals("computer")) {
                holder.course_img.setImageDrawable(getResources().getDrawable(R.drawable.cs_icon));

            } if(course.equals("dance")) {
                holder.course_img.setImageDrawable(getResources().getDrawable(R.drawable.dance_icon));

            }
            if(course.equals("music")) {
                holder.course_img.setImageDrawable(getResources().getDrawable(R.drawable.music_icon));

            }
            if(course.equals("tech")) {
                holder.course_img.setImageDrawable(getResources().getDrawable(R.drawable.it_icon));

            }
            holder.course_id.setText(data.course_id);
            holder.course_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String courseid=data.course_id;
                    String coursename=data.course_name;
                    String courseduration=data.courseduration;
                    String tutorname=data.tutorname;

                    Intent i=new Intent(course_lists.this,course_detail.class);
                    i.putExtra("coursename",coursename);
                    i.putExtra("courseid",courseid);
                    i.putExtra("courseduration",courseduration);
                    i.putExtra("coursetutor",tutorname);
                    startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return course_list.size();
        }
    }
}