package com.strangeman.vipqa.entity;

import java.util.List;

public class Question {
	private String questionId;
	private String content;
	private String productId;
	private String userId;
	private String askDate;
	private List<Answer> answerList;
	private String bestAnswer;
	
	public Question(String questionId, String content, String productId, String userId,String askDate) {
		this.questionId = questionId;
		this.content = content;
		this.productId = productId;
		this.userId = userId;
		this.askDate=askDate;
	}

	public String getQuestionId() {
		return questionId;
	}

	public String getContent() {
		return content;
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

	public String getBestAnswer() {
		return bestAnswer;
	}

	public void setBestAnswer(String bestAnswer) {
		this.bestAnswer = bestAnswer;
	}
}
