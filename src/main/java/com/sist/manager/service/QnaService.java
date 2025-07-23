package com.sist.manager.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.manager.dao.QnaDao;
import com.sist.manager.dao.QnaCommentDao;
import com.sist.manager.model.Qna;
import com.sist.manager.model.QnaComment;

@Service("qnaService")
public class QnaService 
{
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private QnaDao qnaDao;
	
	@Autowired
	private QnaCommentDao qnaCmtDao;
	
	//Qna리스트
	public List<Qna> qnaList(Qna qna)
	{
		List<Qna> list = null;
		
		try
		{
			list = qnaDao.qnaList(qna);
		}
		catch(Exception e)
		{
			logger.error("[QnaService] qnaList Exception", e);
		}
		return list;
	}
	
	//QNA 총 갯수
	public int qnaListCount(Qna qna)
	{
		int count = 0;
		
		try
		{
			count = qnaDao.qnaListCount(qna);
		}
		catch(Exception e)
		{
			logger.error("[QnaService] qnaListCount Exception", e);
		}
		
		return count;
	}
	
	//QNA 상세페이지
	public Qna qnaDetail(int qnaSeq)
	{
		Qna qna = null;
		
		try
		{
			qna = qnaDao.qnaDetail(qnaSeq);
		}
		catch(Exception e)
		{
			logger.error("[QnaService] qnaDetail Exception", e);
		}
		
		return qna;
	}
	
	//QNA 답글 조회
	public QnaComment qnaCmtSelect(int qnaSeq)
	{
		QnaComment qnaCmt = null;
		
		try
		{
			qnaCmt = qnaCmtDao.qnaCmtSelect(qnaSeq);
		}
		catch(Exception e)
		{
			logger.error("[QnaService] qnaCmtSelect Exception", e);
		}
		
		return qnaCmt;
	}
	
	//QNA 답글 등록
	public int qnaCmtInsert(QnaComment qnaCmt)
	{
		int count = 0;
		
		try
		{
			count = qnaCmtDao.qnaCmtInsert(qnaCmt);
		}
		catch(Exception e)
		{
			logger.error("[QnaService] qnaCmtInsert Exception", e);
		}
		
		return count;
	}
	
	//QNA 답글 수정
	public int qnaCmtUpdate(QnaComment qnaCmt)
	{
		int count = 0;
		
		try
		{
			count = qnaCmtDao.qnaCmtUpdate(qnaCmt);
		}
		catch(Exception e)
		{
			logger.error("[QnaService] qnaCmtUpdate Exception", e);
		}
		
		return count;
	}
	
	//QNA 답글 삭제
	public int qnaCmtDelete(int qnaSeq)
	{
		int count = 0;
		
		try
		{
			count = qnaCmtDao.qnaCmtDelete(qnaSeq);
		}
		catch(Exception e)
		{
			logger.error("[QnaService] qnaCmtDelete Exception", e);
		}
		
		return count;
	}
}