package com.strangeman.vipqa;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by panzhi on 2017/5/8.
 */

import java.util.List;

public class Question {
    private String questionId;
    private String question;
    private String productId;
    private String userId;
    private String askDate;
    private List<Answer> answerList;

    public Question(){

    }
    public Question(String questionId, String question, String productId, String userId,String askDate) {
        this.questionId = questionId;
        this.question = question;
        this.productId = productId;
        this.userId = userId;
        this.askDate=askDate;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getQuestion() {
        return question;
    }

    public String getProductId() {
        return productId;
    }

    public String getUserId() {
        return userId;
    }

    public String getAskDate(){
        return askDate;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answers) {
        this.answerList = answers;
    }
}
