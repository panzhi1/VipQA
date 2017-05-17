package com.strangeman.vipqa.com.strangeman.entity;

/**
 * Created by panzhi on 2017/5/10.
 */

public class Answer {
    private String questionId;
    private String answerId;
    private String userId;
    private String content;
    private String answerDate;

    public Answer(String questionId, String answerId, String userId, String content,String answerDate) {
        this.questionId = questionId;
        this.answerId = answerId;
        this.userId = userId;
        this.content = content;
        this.answerDate=answerDate;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public String getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public String getAnswerDate(){
        return answerDate;
    }
}
