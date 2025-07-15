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
	private String email;		// 사용자 이메일
	private String userStat;	// 사용여부(y:사용, n:정지)
	private String joinDt;		// 가입일
	
	private String phone;		//전화번호
	private String userAddr;	//사용자주소
	private String nickname;	//닉네임
	private String gender;		//성별
	private String birthDt;		//생년월일
	private long mile;			//마일리지
	
	private String userType;	//사용자유형(g:게스트, h:호스트)
	private String approvStat;	//승인상태(y:승인, n:미승인)
	
	private String profImgExt;	//프로필이미지파일확장자
	
	private int startRow;
	private int endRow; 
	
	
	
	public User()
	{
		userId = "";
		userPwd = "";
		userName = "";
		email = "";
		userStat = "";
		joinDt = "";
		
		startRow = 0;
		endRow = 0;
		
		phone = "";		
		userAddr = "";	
		nickname = "";		
		gender = "";			
		birthDt = "";			
		mile = 0;			
		
		userType = "";		
		approvStat = "";
		profImgExt = "";
	}
	
	

	public String getProfImgExt() {
		return profImgExt;
	}



	public void setProfImgExt(String profImgExt) {
		this.profImgExt = profImgExt;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserStat() {
		return userStat;
	}

	public void setUserStat(String userStat) {
		this.userStat = userStat;
	}

	public String getJoinDt() {
		return joinDt;
	}

	public void setJoinDt(String joinDt) {
		this.joinDt = joinDt;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserAddr() {
		return userAddr;
	}

	public void setUserAddr(String userAddr) {
		this.userAddr = userAddr;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthDt() {
		return birthDt;
	}

	public void setBirthDt(String birthDt) {
		this.birthDt = birthDt;
	}

	public long getMile() {
		return mile;
	}

	public void setMile(long mile) {
		this.mile = mile;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getApprovStat() {
		return approvStat;
	}

	public void setApprovStat(String approvStat) {
		this.approvStat = approvStat;
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
