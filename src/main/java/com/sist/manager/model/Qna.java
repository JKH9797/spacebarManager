package com.sist.manager.model;

import java.io.Serializable;

public class Qna implements Serializable
{
	private static final long serialVersionUID = 2810431655191391622L;
	
	private int	qnaSeq;			//문의사항 시퀀스 (기본키)
	private String qnaTitle;	//문의사항 제목
	private String qnaContent;	//문의사항 내용
	private String userId;		//작성자 사용자 ID (외래키)
	private String qnaStat;		//문의사항 상태
	private String regDt;		//등록일
	private String updateDt;	//마지막 업데이트 일시
	
	private int startRow;			//시작페이지 rownum	
	private int endRow;				//끝페이지 rownum
	private String searchValue;		
	private String qnaCmtStat;		//댓글 상태
	
	private QnaComment qnaCmt;
	
	public Qna()
	{
		qnaSeq = 0;
		qnaTitle = "";
		qnaContent = "";
		userId = "";
		qnaStat	 = "";
		regDt = "";	
		updateDt = "";	
		
		startRow = 0;
		endRow = 0;
		searchValue = "";
		qnaCmtStat = "";
		
		qnaCmt = null;
	}

	public QnaComment getQnaCmt() {
		return qnaCmt;
	}

	public void setQnaCmt(QnaComment qnaCmt) {
		this.qnaCmt = qnaCmt;
	}

	public String getQnaCmtStat() {
		return qnaCmtStat;
	}

	public void setQnaCmtStat(String qnaCmtStat) {
		this.qnaCmtStat = qnaCmtStat;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
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

	public int getQnaSeq() {
		return qnaSeq;
	}

	public void setQnaSeq(int qnaSeq) {
		this.qnaSeq = qnaSeq;
	}

	public String getQnaTitle() {
		return qnaTitle;
	}

	public void setQnaTitle(String qnaTitle) {
		this.qnaTitle = qnaTitle;
	}

	public String getQnaContent() {
		return qnaContent;
	}

	public void setQnaContent(String qnaContent) {
		this.qnaContent = qnaContent;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getQnaStat() {
		return qnaStat;
	}

	public void setQnaStat(String qnaStat) {
		this.qnaStat = qnaStat;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(String updateDt) {
		this.updateDt = updateDt;
	}
	
}
