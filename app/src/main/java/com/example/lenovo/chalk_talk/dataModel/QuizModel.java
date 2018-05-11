package com.example.lenovo.chalk_talk.dataModel;

/**
 * Created by charanghumman on 11/05/18.
 */

public class QuizModel {

    public String question , option1 , option2 , option3 , option4 , answer ;


    public QuizModel()
    {

    }

    public QuizModel(String question , String option1 , String option2 , String option3 , String option4 , String answer)
    {

        this.question =question ;

        this.option1 = option1;

        this.option2 = option2 ;

        this.option3 = option3;

        this.option4 = option4;

        this.answer = answer;

    }
}
