package com.example.lenovo.chalk_talk;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.chalk_talk.dataModel.AllQuizQuestionsModel;
import com.example.lenovo.chalk_talk.dataModel.QuizModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddQuizActivity extends AppCompatActivity {

    private EditText question_et , option1_et , option2_et , option3_et , option4_et , answer_et ;

    private JSONArray all_questions ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz);

        getSupportActionBar().setTitle("Add question");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        all_questions = new JSONArray();


        question_et = findViewById(R.id.question_et);

        option1_et = findViewById(R.id.option1_et);

        option2_et = findViewById(R.id.option2_et);

        option3_et = findViewById(R.id.option3_et);

        option4_et = findViewById(R.id.option4_et);

        answer_et = findViewById(R.id.answer_et);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return  true;
    }

    public void finish_quiz(View view) {

        final ProgressDialog pd = new ProgressDialog(AddQuizActivity.this);

        pd.setTitle("Savving");
        pd.setMessage("Please wait");

        pd.show();


        FirebaseDatabase database = FirebaseDatabase.getInstance();

        AllQuizQuestionsModel model = new AllQuizQuestionsModel(all_questions.toString());

        database.getReference().child("quiz").child(getIntent().getStringExtra("course_id")+"_"+getIntent().getStringExtra("module_id")).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    public void save_quiz(View view) {

        String question = question_et.getText().toString();
        String option1 = option1_et.getText().toString();
        String option2 = option2_et.getText().toString();
        String option3 = option3_et.getText().toString();
        String option4 = option4_et.getText().toString();

        String answer = answer_et.getText().toString();


        if(question.equalsIgnoreCase("") || option1.equals("") || option2.equals("") || option3.equals("") || option4.equals("") || answer.equals(""))
        {

            Toast.makeText(AddQuizActivity.this , "fill all fields" , Toast.LENGTH_SHORT).show();

            return;

        }

        QuizModel model = new QuizModel(question , option1 , option2 , option3 , option4 , answer);

        Gson gsonObj = new Gson();
        // converts object to json string
        String jsonStr = gsonObj.toJson(model);


        try {
            JSONObject jsonObject = new JSONObject(jsonStr);

            all_questions.put(jsonObject);

            question_et.setText("");

            option1_et.setText("");

            option2_et.setText("");

            option3_et.setText("");

            option4_et.setText("");

            answer_et.setText("");

        } catch (JSONException e) {
            e.printStackTrace();
        }




    }
}
