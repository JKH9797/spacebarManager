package com.sist.manager.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sist.manager.model.Qna;

@Repository("qnaDao")
public interface QnaDao 
{
	//Q&A리스트
	public List<Qna> qnaList(Qna qna);
	
	//QNA 총 갯수
	public int qnaListCount(Qna qna);
	
	//QNA 상세페이지
	public Qna qnaDetail(int qnaSeq);
}
