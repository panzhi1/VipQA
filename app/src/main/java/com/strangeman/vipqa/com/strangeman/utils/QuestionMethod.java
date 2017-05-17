package com.strangeman.vipqa.com.strangeman.utils;

import com.strangeman.vipqa.com.strangeman.entity.Question;

import java.util.List;

/**
 * Created by panzhi on 2017/5/8.
 */

public class QuestionMethod {
    private List<Question> questions;
    private QuestionMethod(){};
    public List<Question> getQuestions(){
        return questions;
    }
    public void setQuestions(List<Question> questions){
        this.questions=questions;
    }
}
