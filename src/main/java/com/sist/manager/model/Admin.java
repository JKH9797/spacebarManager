package com.sist.manager.model;

import java.io.Serializable;

public class Admin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5527011652561894865L;
	

	private String admId;	// 관리자 아이디
	private String admPwd;	// 관리자 비밀번호
	private String admName;	// 관리자명
	private String status;	// 상태(Y:정상, N:정지)
	private String regDate;	// 등록일
	
	
	public Admin() {
		admId = "";
		admPwd = "";
		admName = "";
		status = "N";
		regDate = "";
	}


	public String getAdmId() {
		return admId;
	}


	public void setAdmId(String admId) {
		this.admId = admId;
	}


	public String getAdmPwd() {
		return admPwd;
	}


	public void setAdmPwd(String admPwd) {
		this.admPwd = admPwd;
	}


	public String getAdmName() {
		return admName;
	}


	public void setAdmName(String admName) {
		this.admName = admName;
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
	
	
}
