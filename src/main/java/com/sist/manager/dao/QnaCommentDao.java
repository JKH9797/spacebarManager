package com.sist.manager.dao;

import org.springframework.stereotype.Repository;

import com.sist.manager.model.QnaComment;

@Repository("qnaCommentDao")
public interface QnaCommentDao 
{
	//QNA 답글 조회
	QnaComment qnaCmtSelect(int qnaSeq);
	
	//QNA 답글 등록
	public int qnaCmtInsert(QnaComment qnaCmt);
	
	//QNA 답글 수정
	public int qnaCmtUpdate(QnaComment qnaCmt);
	
	//QNA 답글 삭제
	public int qnaCmtDelete(int qnaSeq);
	
}
