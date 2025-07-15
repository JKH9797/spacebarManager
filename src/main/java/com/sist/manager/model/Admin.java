package com.sist.manager.model;

import java.io.Serializable;

public class Admin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5527011652561894865L;
	

	private String adminId;	// 관리자 아이디
	private String adminPwd;	// 관리자 비밀번호
	private String adminName;	// 관리자명
	private String adminStat;	// 상태(Y:정상, N:정지)
	private String regDt;	// 등록일
	
	
	public Admin() {
		adminId = "";
		adminPwd = "";
		adminName = "";
		adminStat = "N";
		regDt = "";
	}


	public String getAdminId() {
		return adminId;
	}


	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}


	public String getAdminPwd() {
		return adminPwd;
	}


	public void setAdminPwd(String adminPwd) {
		this.adminPwd = adminPwd;
	}


	public String getAdminName() {
		return adminName;
	}


	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}


	public String getAdminStat() {
		return adminStat;
	}


	public void setAdminStat(String adminStat) {
		this.adminStat = adminStat;
	}


	public String getRegDt() {
		return regDt;
	}


	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	

	
}
