package com.sist.manager.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.common.util.HttpUtil;
import com.sist.common.util.StringUtil;
import com.sist.manager.model.Paging;
import com.sist.manager.model.Qna;
import com.sist.manager.model.QnaComment;
import com.sist.manager.service.QnaService;
import com.sist.manager.model.Response;

@Controller("qnaController")
public class QnaController
{
	// Logger 클래스를 QnaController.class로 수정
	private static Logger logger = LoggerFactory.getLogger(QnaController.class);

	@Autowired
	private QnaService qnaService;

	@Value("#{env['auth.session.name']}")
	private String AUTH_SESSION_NAME;

	private static final int LIST_COUNT = 10;
	private static final int PAGE_COUNT = 3;

	//Q&A리스트
	@RequestMapping(value="/qna/list", method = {RequestMethod.GET, RequestMethod.POST})
	public String qnaList(Model model, HttpServletRequest request, HttpServletResponse response)
	{
		String qnaStat = HttpUtil.get(request, "qnaStat", "");
		String searchValue = HttpUtil.get(request, "searchValue", "");
		int curPage = HttpUtil.get(request, "curPage", 1);
		
		List<Qna> list = null;
		
		Qna qna = new Qna();
		int totalCount = 0;
		Paging paging = null;
		
		if(!StringUtil.isEmpty(searchValue))
		{
			qna.setSearchValue(searchValue);
		}
		else
		{
			searchValue = "";
		}
		
		if (!StringUtil.isEmpty(qnaStat)) 
		{
		    qna.setQnaStat(qnaStat);
		} 
		else 
		{
		    qnaStat = "";
		}
		
		totalCount = qnaService.qnaListCount(qna); 

		if(totalCount > 0)
		{
			paging = new Paging("/qna/list", totalCount, LIST_COUNT, PAGE_COUNT, curPage, "curPage");

			qna.setStartRow(paging.getStartRow());
			qna.setEndRow(paging.getEndRow());

			list = qnaService.qnaList(qna); 
			
		    for (Qna q : list) 
		    {
		    	QnaComment comment = qnaService.qnaCmtSelect(q.getQnaSeq());
		        q.setQnaCmt(comment);
		    }
		}
		
		model.addAttribute("list", list);
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("curPage", curPage);
		model.addAttribute("qnaStat", qnaStat); 
		model.addAttribute("paging", paging);

		
		return "/qna/list";
	}
	
	//Q&A상세페이지 + QNA 답글 화면 + QNA 수정 화면
	@RequestMapping(value="/qna/detail", method = {RequestMethod.GET, RequestMethod.POST})
	public String qnaDatail(Model model, HttpServletRequest request, HttpServletResponse response)
	{
		String sessionUserId = (String)request.getSession().getAttribute(AUTH_SESSION_NAME);
		int qnaSeq = HttpUtil.get(request, "qnaSeq", 0);
		String searchValue = HttpUtil.get(request, "searchValeu", "");
		int curPage = HttpUtil.get(request, "curPage", 1);
		
		logger.debug("Q&A 상세페이지 sessionUserId : " + sessionUserId);
		logger.debug("qnaSeq : " + qnaSeq);
		logger.debug("searchValue : " + searchValue);
		logger.debug("curPage : " + curPage);
		
		Qna qna = qnaService.qnaDetail(qnaSeq);
		model.addAttribute("qna", qna);
		model.addAttribute("qnaSeq", qnaSeq);
		
		QnaComment qnaCmt = qnaService.qnaCmtSelect(qnaSeq);
		model.addAttribute("qnaCmt", qnaCmt);
		
		return "/qna/detail";
	}

	//QNA 답글 등록
	@RequestMapping(value="/qna/qnaCmtWriteProc", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
	public Response<Object> qnaCmtProc(Model model, HttpServletRequest request, HttpServletResponse response)
	{
		Response<Object> ajaxRes = new Response<Object>();
		
		String sessionUserId = (String)request.getSession().getAttribute(AUTH_SESSION_NAME);
		int qnaSeq = HttpUtil.get(request, "qnaSeq", 0);
		String qnaCmtContent = HttpUtil.get(request, "qnaCmtContent", "");
		
		logger.debug("Q&A 답글 등록 sessionUserId : " + sessionUserId);
		logger.debug("qnaSeq : " + qnaSeq);
		logger.debug("qnaCmtContent : " + qnaCmtContent);
		
		if(qnaSeq > 0 && !StringUtil.isEmpty(qnaCmtContent))
		{
			QnaComment qnaCmt = new QnaComment();
			qnaCmt.setQnaSeq(qnaSeq);
			qnaCmt.setQnaCmtContent(qnaCmtContent);
			qnaCmt.setAdminId(sessionUserId);
			qnaCmt.setQnaCmtStat("Y");
			
			if(qnaService.qnaCmtInsert(qnaCmt) > 0)
			{
				ajaxRes.setResponse(0, "success");
			}
			else
			{
				ajaxRes.setResponse(500, "internal server error");
			}
		}
		else
		{
			ajaxRes.setResponse(400, "bad request");
		}
		
		return ajaxRes;
	}
	
	
	//QNA 답글 수정
	@RequestMapping(value="/qna/qnaCmtUpdateProc", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Response<Object> qnaCmtUpdateProc(Model model, HttpServletRequest request, HttpServletResponse response)
    {
		Response<Object> ajaxRes = new Response<Object>();
		
		String sessionUserId = (String)request.getSession().getAttribute(AUTH_SESSION_NAME);
		int qnaSeq = HttpUtil.get(request, "qnaSeq", 0);
		int qnaCmtSeq = HttpUtil.get(request, "qnaCmtSeq", 0);
		String qnaCmtContent = HttpUtil.get(request, "qnaCmtContent", "");
		
		logger.debug("Q&A 답글 수정 sessionUserId : " + sessionUserId);
		logger.debug("qnaSeq : " + qnaSeq);
		logger.debug("qnaCmtSeq : " + qnaCmtSeq);
		logger.debug("qnaCmtContent : " + qnaCmtContent);
		
		if(qnaSeq > 0 && qnaCmtSeq > 0 && !StringUtil.isEmpty(qnaCmtContent))
		{
			QnaComment qnaCmt = new QnaComment();
			qnaCmt.setQnaSeq(qnaSeq);
			qnaCmt.setQnaCmtSeq(qnaCmtSeq);
			qnaCmt.setQnaCmtContent(qnaCmtContent);
			qnaCmt.setAdminId(sessionUserId);
			
			if(qnaService.qnaCmtUpdate(qnaCmt) > 0)
			{
    			ajaxRes.setResponse(0, "success");
			}
			else
			{
				ajaxRes.setResponse(500, "internal server error");
			}
		}
		else
		{
			ajaxRes.setResponse(400, "bad request");
		}
		
		return ajaxRes;
    }
	
	//QNA 답글 삭제
	@RequestMapping(value="/qna/qnaCmtDeleteProc", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Response<Object> qnaCmtDeleteProc(Model model, HttpServletRequest request, HttpServletResponse response)
    {
		Response<Object> ajaxRes = new Response<Object>();
		
		String sessionUserId = (String)request.getSession().getAttribute(AUTH_SESSION_NAME);
		int qnaSeq = HttpUtil.get(request, "qnaSeq", 0);
		int qnaCmtSeq = HttpUtil.get(request, "qnaCmtSeq", 0);
		String qnaCmtContent = HttpUtil.get(request, "qnaCmtContent", "");
		
		logger.debug("Q&A 답글 삭제 sessionUserId : " + sessionUserId);
		logger.debug("qnaSeq : " + qnaSeq);
		logger.debug("qnaCmtSeq : " + qnaCmtSeq);
		logger.debug("qnaCmtContent : " + qnaCmtContent);
		
		if(sessionUserId == null)
		{
        	ajaxRes.setResponse(410, "loging failed");
        	return ajaxRes;
		}
		else
		{
			try
			{
				if(qnaService.qnaCmtDelete(qnaSeq) > 0)
				{
					ajaxRes.setResponse(0, "success");
				}
				else
				{
					ajaxRes.setResponse(500, "internal server error");
				}
			}
			catch(Exception e)
			{
            	logger.error("[QnaController]qnaCmatDeleteProc Exception", e);
            	ajaxRes.setResponse(500, "internal server error2");
			}
		}
		
		return ajaxRes;
    }
	
}