package com.strangeman.vipqa.entity;

public class User {
	private String userId;
	private String password;
	private String userName;
	private String userPhoto;
	private int integral;
	
	public User(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}
	public User(String userId, String userName, String userPhoto) {
		this.userId = userId;
		this.userName = userName;
		this.userPhoto = userPhoto;
	}
	public String getUserId() {
		return userId;
	}
	public String getPassword() {
		return password;
	}
	public String getUserName() {
		return userName;
	}
	public String getUserPhoto() {
		return userPhoto;
	}
	public int getIntegral() {
		return integral;
	}
	public void setIntegral(int integral) {
		this.integral = integral;
	}
}
