package com.example.lenovo.chalk_talk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lenovo.chalk_talk.dataModel.ModuleNameModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AddModuleNameActivity extends AppCompatActivity {

    private EditText module_name_et ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_module_name);

        getSupportActionBar().setTitle("Module Content");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        module_name_et = findViewById(R.id.module_name_et);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return  true;
    }

    public void add_module(View view) {



        if(module_name_et.getText().length() <=4 )
        {
            Toast.makeText(AddModuleNameActivity.this , "name must be more then 4 characters " , Toast.LENGTH_SHORT).show();

            return;
        }

        RadioGroup radioGroup= findViewById(R.id.select_material_type);

        if(radioGroup.getCheckedRadioButtonId() == -1)
        {
            Toast.makeText(AddModuleNameActivity.this , "select content type " , Toast.LENGTH_SHORT).show();

            return;

        }

        RadioButton selected_radio_btn= findViewById(radioGroup.getCheckedRadioButtonId());
        String  type = selected_radio_btn.getText().toString();



        final ProgressDialog pd = new ProgressDialog(AddModuleNameActivity.this);

        pd.setTitle("Savving");
        pd.setMessage("Please wait");

        pd.show();

        String module_name = module_name_et.getText().toString();

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
