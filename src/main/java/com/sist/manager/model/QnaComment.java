package com.sist.manager.model;

import java.io.Serializable;

public class QnaComment implements Serializable
{
	private static final long serialVersionUID = 8504233946339202782L;
	
	private int	qnaCmtSeq;			//문의사항 댓글 시퀀스 (기본키)
	private int	qnaSeq;				//문의사항 시퀀스 (외래키)
	private String adminId;			//작성자 사용자 ID (외래키)
	private String qnaCmtContent;	//댓글 내용
	private String qnaCmtStat;		//댓글 상태
	private String createDt;		//작성일
	private String updateDt;		//마지막 업데이트 일시
	
	public QnaComment()
	{
		qnaCmtSeq = 0;
		qnaSeq = 0;
		adminId = "";
		qnaCmtContent = "";
		qnaCmtStat = "";
		createDt = "";
		updateDt = "";
	}

	public int getQnaCmtSeq() {
		return qnaCmtSeq;
	}

	public void setQnaCmtSeq(int qnaCmtSeq) {
		this.qnaCmtSeq = qnaCmtSeq;
	}

	public int getQnaSeq() {
		return qnaSeq;
	}

	public void setQnaSeq(int qnaSeq) {
		this.qnaSeq = qnaSeq;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getQnaCmtContent() {
		return qnaCmtContent;
	}

	public void setQnaCmtContent(String qnaCmtContent) {
		this.qnaCmtContent = qnaCmtContent;
	}

	public String getQnaCmtStat() {
		return qnaCmtStat;
	}

	public void setQnaCmtStat(String qnaCmtStat) {
		this.qnaCmtStat = qnaCmtStat;
	}

	public String getCreateDt() {
		return createDt;
	}

	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}

	public String getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(String updateDt) {
		this.updateDt = updateDt;
	}
	
}
