package com.example.lenovo.chalk_talk;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lenovo.chalk_talk.dataModel.ModuleNameModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AddQuizNameActivity extends AppCompatActivity {

    EditText quiz_name_et ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz_name);

        quiz_name_et = findViewById(R.id.quiz_name_et);

        getSupportActionBar().setTitle("Add Quiz Name");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return  true;
    }

    public void add(View view) {



        if(quiz_name_et.getText().length() <=4 )
        {
            Toast.makeText(AddQuizNameActivity.this , "name must be more then 4 characters " , Toast.LENGTH_SHORT).show();

            return;
        }


        String  type = "QUIZ";



        final ProgressDialog pd = new ProgressDialog(AddQuizNameActivity.this);

        pd.setTitle("Savving");
        pd.setMessage("Please wait");

        pd.show();

        String module_name = quiz_name_et.getText().toString();

        String course_id =  getIntent().getStringExtra("course_id");

        String module_id = course_id + module_name.replace(" ","").substring(0 , 4);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        String email = auth.getCurrentUser().getEmail().replace("." , "");

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        ModuleNameModel model = new ModuleNameModel(module_name , "study material" , type);

        database.getReference().child("course_modules").child(course_id).child(module_id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    pd.hide();

                    finish();
                }

            }

        });

    }


}
