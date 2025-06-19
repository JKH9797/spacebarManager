package com.sist.manager.model;

import java.io.Serializable;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8761222923477142846L;
			
	private String userId;		// 사용자 아이디
	private String userPwd;		// 비밀번호
	private String userName;	// 사용자명
	private String userEmail;	// 사용자 이메일
	private String status;		// 사용여부(y:사용, n:정지)
	private String regDate;		// 가입일
	
	private int startRow;
	private int endRow; 
	
	public User()
	{
		userId = "";
		userPwd = "";
		userName = "";
		userEmail = "";
		status = "";
		regDate = "";
		
		startRow = 0;
		endRow = 0;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	
}
