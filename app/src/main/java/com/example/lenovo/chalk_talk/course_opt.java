package com.example.lenovo.chalk_talk;

import android.app.Fragment;
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
import com.google.android.gms.common.data.FreezableUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class course_opt extends android.support.v4.app.Fragment {
    ArrayList<course_details> course_list;
    RecyclerView course_recycler;

    public course_opt() {
        // Required empty public constructor
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_course_opt, container, false);

        course_list = new ArrayList<>();
        course_recycler = v.findViewById(R.id.learner_recycle);
        course_recycler.setLayoutManager(new LinearLayoutManager(getActivity() , LinearLayoutManager.VERTICAL, false));
        get_course_list();
return v;
    }

    private void get_course_list() {
        FirebaseAuth firebase = FirebaseAuth.getInstance();
        String email= firebase.getCurrentUser().getEmail().replace(".","");
        FirebaseDatabase data = FirebaseDatabase.getInstance();
        System.out.println("rrrr");
        data.getReference().child(email).child("course").addListenerForSingleValueEvent(new ValueEventListener() {


                                                                             @Override
                                                                             public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                 course_list.clear();


                                                                                 for (DataSnapshot data : dataSnapshot.getChildren()) {
                                                                                     course_details details = data.getValue(course_details.class);
                                                                                     System.out.println("rrrrrr");
                                                                                     course_list.add(details);

                                                                                     Adapter adapter = new Adapter();

                                                                                     course_recycler.setAdapter(adapter);
                                                                                 }
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
            holder.course_id.setText(data.course_id);
            holder.course_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String courseid=data.course_id;
                    String coursename=data.course_name;
                    String courseduration=data.courseduration;
                    String tutorname=data.tutorname;
                    Intent i=new Intent(getActivity(),course_detail.class);
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