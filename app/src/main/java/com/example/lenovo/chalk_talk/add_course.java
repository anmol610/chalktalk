package com.example.lenovo.chalk_talk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lenovo.chalk_talk.dataModel.course_details;
import com.example.lenovo.chalk_talk.dataModel.createaccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class add_course extends Fragment {
    EditText course_name_et, course_id_et, course_duration_et,tutor_name;

    Button done;

    Spinner spinTest;
    String name,id,course_duration,t_name,ss ;
    public add_course() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.activity_add_course, container, false);

        course_name_et = v.findViewById(R.id.course_name);
        done=v.findViewById(R.id.add_btn);
        course_id_et = v.findViewById(R.id.cource_id);
        course_duration_et = v.findViewById(R.id. course_duration);
         tutor_name = v.findViewById(R.id.tutor_name);
        spinTest= (Spinner) v.findViewById(R.id.spintestAdd);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.course_array));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTest.setAdapter(adapter);
        get_tutor_name();
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = course_name_et.getText().toString();

                 id = course_id_et.getText().toString();

                 course_duration = course_duration_et.getText().toString();

                 if(name.equals("") || id.equals("") || course_duration.equals(""))
                 {
                     Toast.makeText(getContext() , "enter all fields" , Toast.LENGTH_SHORT).show();

                     return;
                 }

                 ss = spinTest.getSelectedItem().toString();
                t_name=tutor_name.getText().toString();
                add_course_detail();
            }
        });
        return v;
    }

    private void get_tutor_name()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        String email =  auth.getCurrentUser().getEmail();

        database.getReference().child("Tutor").child(email.replace(".","")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                tutor_name.setText( dataSnapshot.child("name").getValue().toString() );

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void add_course_detail() {

        final ProgressDialog pd = new ProgressDialog(getContext());

        pd.setTitle("Savving");
        pd.setMessage("Please wait");

        pd.show();

        String curr_time = String.valueOf(System.currentTimeMillis());

        course_details data = new course_details(name , id , course_duration  , t_name, ss , curr_time);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        String email = auth.getCurrentUser().getEmail();

        long current_time = System.currentTimeMillis();

        database.getReference().child("course").child(email.replace(".","")).child(id).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                pd.hide();

                course_id_et.setText("");
                course_name_et.setText("");
                course_duration_et.setText("");

                Intent i = new Intent(getContext() , AddCourseContentActivity.class);

                i.putExtra("course_id" , id);

                startActivity(i);

            }
        });




    }



    }

