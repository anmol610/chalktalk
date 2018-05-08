package com.example.lenovo.chalk_talk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.chalk_talk.dataModel.course_details;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class uploaded extends AppCompatActivity {

    RecyclerView recyclerView ;

    ArrayList<course_details> notes_list ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploaded);

        notes_list = new ArrayList<>();

        recyclerView = findViewById(R.id.notes_me_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(uploaded.this , LinearLayoutManager.VERTICAL , false));


    }

    private void get_data_from_firebase( String department_name )
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        String email = auth.getCurrentUser().getEmail();

        database.getReference().child("course").child("dance").child(email.replace(".","")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot snap : dataSnapshot.getChildren())
                {
                    course_details data = snap.getValue(course_details.class);

                    notes_list.add(data);
                }

                recyclerView.setAdapter(new Adapter());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public class view_holder extends RecyclerView.ViewHolder
    {

        TextView id;
        LinearLayout course_lay;

        public view_holder(View itemView) {
            super(itemView);
id=itemView.findViewById(R.id.cource_id);
course_lay=itemView.findViewById(R.id.course_lay);
        }
    }


    public class Adapter extends RecyclerView.Adapter<view_holder>
    {

        @Override
        public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new view_holder(LayoutInflater.from(uploaded.this).inflate(R.layout.course_cell, parent , false));
        }

        @Override
        public void onBindViewHolder(view_holder holder, int position) {


            course_details data = notes_list.get(position);

            holder.id.setText(data.course_id);

        }

        @Override
        public int getItemCount() {
            return notes_list.size();
        }
    }
}
