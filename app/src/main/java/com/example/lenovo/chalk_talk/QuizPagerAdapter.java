package com.example.lenovo.chalk_talk;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.chalk_talk.dataModel.QuizModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by charanghumman on 11/05/18.
 */

public class QuizPagerAdapter extends PagerAdapter {

    private JSONArray questionJsonArray ;
    
    private Context context ;

    public QuizPagerAdapter(JSONArray jsonArray , Context context)
    {
        questionJsonArray = jsonArray ;
        
        this.context = context ;
    }


    @Override
    public int getCount() {
        return questionJsonArray.length();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.play_quiz_layout , container , false);

       TextView  question_txt = view.findViewById(R.id.question_txt);

      TextView  option1_txt = view.findViewById(R.id.option1_txt);

      TextView  option2_txt = view.findViewById(R.id.option2_txt);

      TextView  option3_txt = view.findViewById(R.id.option3_txt);

      TextView  option4_txt = view.findViewById(R.id.option4_txt);

      EditText answer_et = view.findViewById(R.id.answer_et);

      Button previous_btn = view.findViewById(R.id.previous_btn);

      Button next_btn = view.findViewById(R.id.next_btn);

        try {
            JSONObject jsonObject = questionJsonArray.getJSONObject(position);

            Gson gson = new Gson();

            // 1. JSON to Java object, read it from a file.
            QuizModel model  = gson.fromJson(jsonObject.toString() , QuizModel.class);

            question_txt.setText(model.question);

            option1_txt.setText(model.option1);

            option2_txt.setText(model.option2);

            option3_txt.setText(model.option3);

            option4_txt.setText(model.option4);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(position == 0)
        {
            previous_btn.setVisibility(View.GONE);
        }

        if(position == questionJsonArray.length() - 1)
        {
            next_btn.setVisibility(View.GONE);
        }

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(position == questionJsonArray.length() -1)
                {
                    return;
                }

                ShowQuizActivity.quiz_pager.setCurrentItem(position+1);
            }
        });

        previous_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(position == 0)
                {
                    return;
                }

                ShowQuizActivity.quiz_pager.setCurrentItem(position -1);
            }
        });




        container.addView(view);

      return view;


    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }


}
