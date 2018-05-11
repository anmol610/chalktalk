package com.example.lenovo.chalk_talk;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.chalk_talk.dataModel.ModuleNameModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddCourseContentActivity extends AppCompatActivity {

    private String course_id;

    private RecyclerView course_modules ;

    private List<ModuleNameModel> list ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_content);

        getSupportActionBar().setTitle("Course Modules");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        list = new ArrayList<>();

        course_id = getIntent().getStringExtra("course_id");

        course_modules = findViewById(R.id.all_course_module_recycler);

        course_modules.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false));


        get_course_modules();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return  true;
    }

    private void get_course_modules()
    {
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail().replace("." , "");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();


        database.getReference().child("course_modules").child(course_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                list.clear();

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    ModuleNameModel model = dataSnapshot1.getValue(ModuleNameModel.class);

                    model.module_id = dataSnapshot1.getKey();

                    list.add(model);
                }


                course_modules.setAdapter(new Adapter());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void open_module_option_dialog(View view) {

        ModuleOptionDialog dialog = new ModuleOptionDialog(AddCourseContentActivity.this , R.style.Custom_Dialog , course_id);

        dialog.show();

    }


    public class view_holder extends RecyclerView.ViewHolder
    {

        public TextView name ;

        public ImageView add_content ;

        public view_holder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.module_name);

            add_content = itemView.findViewById(R.id.add_content);
        }
    }

    public  class Adapter extends RecyclerView.Adapter<view_holder>
    {

        @Override
        public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new view_holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_course_module_cell , parent , false));
        }

        @Override
        public void onBindViewHolder(view_holder holder, int position) {

            final ModuleNameModel data = list.get(position);

            holder.name.setText(data.name);

            holder.add_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(data.content_type.equals("VIDEO"))
                    {
                        Intent i = new Intent(AddCourseContentActivity.this , UploadVideoActivity.class);

                        i.putExtra("course_id" , course_id);
                        i.putExtra("module_id" , data.module_id);

                        startActivity(i);

                    }

                    if(data.content_type.equals("IMAGE"))
                    {
                        Intent i = new Intent(AddCourseContentActivity.this , uploading_images.class);

                        i.putExtra("course_id" , course_id);
                        i.putExtra("module_id" , data.module_id);

                        startActivity(i);
                    }

                    if(data.content_type.equals("PDF"))
                    {

                        Intent i = new Intent(AddCourseContentActivity.this , uploading_pdf_files.class);

                        i.putExtra("course_id" , course_id);
                        i.putExtra("module_id" , data.module_id);

                        startActivity(i);
                    }

                    if(data.content_type.equals("QUIZ"))
                    {

                        Intent i = new Intent(AddCourseContentActivity.this , AddQuizActivity.class);

                        i.putExtra("course_id" , course_id);
                        i.putExtra("module_id" , data.module_id);

                        startActivity(i);
                    }



                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}
