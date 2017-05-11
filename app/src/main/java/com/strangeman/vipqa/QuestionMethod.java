package com.strangeman.vipqa;

import java.util.ArrayList;

/**
 * Created by panzhi on 2017/5/8.
 */

public class QuestionMethod {
    private ArrayList<Question> questions;
    private QuestionMethod(){};
    public ArrayList<Question> getQuestions(){
        return questions;
    }
    public void setQuestions(ArrayList<Question> questions){
        this.questions=questions;
    }
}
