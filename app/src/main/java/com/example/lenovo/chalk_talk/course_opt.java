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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.chalk_talk.dataModel.course_details;
import com.example.lenovo.chalk_talk.dataModel.joined_course;
import com.google.android.gms.common.data.FreezableUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class course_opt extends android.support.v4.app.Fragment {
    ArrayList<joined_course> course_list;
    RecyclerView course_recycler;

    TextView course_opt ;

    public course_opt() {
        // Required empty public constructor
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_course_opt, container, false);

        course_opt = v.findViewById(R.id.no_course_txt);

        course_list = new ArrayList<>();
        course_recycler = v.findViewById(R.id.learner_recycle);
        course_recycler.setLayoutManager(new LinearLayoutManager(getActivity() , LinearLayoutManager.VERTICAL, false));
        get_course_list();
        return v;
    }

    private void get_course_list() {

        final ProgressDialog pd = new ProgressDialog(getContext());

        pd.setTitle("Loading");
        pd.setMessage("Please wait");

        pd.show();

        FirebaseAuth firebase = FirebaseAuth.getInstance();
        String email= firebase.getCurrentUser().getEmail().replace(".","");
        FirebaseDatabase data = FirebaseDatabase.getInstance();
        System.out.println("rrrr");
        data.getReference().child("joined_courses").child(email).addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                course_list.clear();

                pd.hide();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    joined_course details = data.getValue(joined_course.class);
                    System.out.println("rrrrrr");
                    course_list.add(details);

                    Adapter adapter = new Adapter();

                    course_recycler.setAdapter(adapter);
                }

                if(course_list.size() > 0)
                {
                    course_opt.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }
        );
    }
    public class view_holder extends RecyclerView.ViewHolder{

        TextView course_id,course_name , joined_on;
        LinearLayout course_lay;
        ImageView course_img;


        public view_holder(View itemView) {
            super(itemView);

            course_name = itemView.findViewById(R.id.course_name);
            course_lay = itemView.findViewById(R.id.course_lay);
            course_id = itemView.findViewById(R.id.course_id);
            course_img = itemView.findViewById(R.id.course_img);

            joined_on = itemView.findViewById(R.id.date);
        }
    }

    public class Adapter extends RecyclerView.Adapter<view_holder>
    {

        @Override
        public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {

            view_holder v = new view_holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.joined_course_cell,parent , false ));

            return v ;
        }

        @Override
        public void onBindViewHolder(view_holder holder, int position) {


            final joined_course data=course_list.get(position);

            holder.course_name.setText(data.course_name);

            holder.course_id.setText(data.course_id);

            holder.joined_on.setText(getDate(Long.parseLong(data.time)));

            if (data.course_category.equals("Computer Science")) {
                holder.course_img.setImageDrawable(getResources().getDrawable(R.drawable.cs_icon));

            } if(data.course_category.equals("Dance")) {
                holder.course_img.setImageDrawable(getResources().getDrawable(R.drawable.dance_icon));

            }
            if(data.course_category.equals("Music")) {
                holder.course_img.setImageDrawable(getResources().getDrawable(R.drawable.music_icon));

            }
            if(data.course_category.equals("Technology")) {
                holder.course_img.setImageDrawable(getResources().getDrawable(R.drawable.it_icon));

            }

            holder.course_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    String courseid=data.course_id;
                    String coursename=data.course_name;
                    String courseduration=data.courseduration;
                    String tutorname=data.tutorname;

                    Intent i=new Intent(getActivity(),ViewJoinedCourseModulesActivity.class);


                    i.putExtra("course_id",courseid);



                    startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return course_list.size();
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