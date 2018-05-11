package com.example.lenovo.chalk_talk;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.chalk_talk.dataModel.course_details;
import com.google.android.gms.common.data.FreezableUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class tutor_course extends android.support.v4.app.Fragment {

    ArrayList<course_details> tutor_course_list ;
    RecyclerView tutor_course_recycler ;
    TextView no ;

    public tutor_course()
    {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_tutor_course, container, false);
        View view = v;

        tutor_course_list = new ArrayList<>();
        tutor_course_recycler = v.findViewById(R.id.learner_recycle);
        tutor_course_recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        no = v.findViewById(R.id.nocourse);
        get_course_list();

        return v;
    }

    private void get_course_list()
    {
        final ProgressDialog pd = new ProgressDialog(getContext());

        pd.setTitle("Fetching");
        pd.setMessage("Please wait.");

        pd.show();

        FirebaseAuth firebase = FirebaseAuth.getInstance();
        final String email = firebase.getCurrentUser().getEmail().replace(".", "");
        FirebaseDatabase data = FirebaseDatabase.getInstance();
        System.out.println("rrrr");
        data.getReference().child("course").addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                pd.hide();

                tutor_course_list.clear();


                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                        if(dataSnapshot1.getKey().equals(email)) {

                            for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                course_details details = dataSnapshot2.getValue(course_details.class);
                                System.out.println("rrrrrr");
                                tutor_course_list.add(details);
                                Adapter adapter = new Adapter();
                                no.setText("");
                                tutor_course_recycler.setAdapter(adapter);
                            }
                        }
                    }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public class view_holder extends RecyclerView.ViewHolder

    {

        TextView course_id, course_name , time;
        LinearLayout course_lay;
        ImageView course_img;

        public view_holder(View itemView) {
            super(itemView);

            course_name = itemView.findViewById(R.id.course_name);
            course_lay = itemView.findViewById(R.id.course_lay);
            course_id = itemView.findViewById(R.id.course_id);
            course_img = itemView.findViewById(R.id.course_img);
            time  = itemView.findViewById(R.id.course_date);

        }
    }

    public class Adapter extends RecyclerView.Adapter<view_holder>

    {

        @Override
        public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {

            view_holder v = new view_holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tutor_course_cell, parent, false));

            return v;
        }

        @Override
        public void onBindViewHolder(view_holder holder, int position) {


            final course_details data = tutor_course_list.get(position);

            holder.course_name.setText(data.course_name);

            holder.course_id.setText(data.course_id);

            holder.time.setText(getDate(Long.parseLong(data.time)));

            if (data.course_category.toLowerCase().contains("computer")) {
                holder.course_img.setImageDrawable(getResources().getDrawable(R.drawable.cs_icon));

            } if (data.course_category.toLowerCase().contains("music")) {
                holder.course_img.setImageDrawable(getResources().getDrawable(R.drawable.music_icon));

            }
            if (data.course_category.toLowerCase().contains("dance")) {
                holder.course_img.setImageDrawable(getResources().getDrawable(R.drawable.dance_icon));

            }
            if  (data.course_category.toLowerCase().contains("technology")) {
                holder.course_img.setImageDrawable(getResources().getDrawable(R.drawable.it_icon));

            }

            holder.course_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getContext() , AddCourseContentActivity.class);

                    i.putExtra("course_id" , data.course_id);

                    startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount()
        {
            return tutor_course_list.size();
        }
    }


    public static String getDate(long milliSeconds)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy hh:mm");

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}