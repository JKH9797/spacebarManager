/**
 * <pre>
 * 프로젝트명 : Manager
 * 패키지명   : com.icia.manager.controller
 * 파일명     : IndexController.java
 * 작성일     : 2021. 7. 30.
 * 작성자     : daekk
 * </pre>
 */
package com.sist.manager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.common.util.CookieUtil;
import com.sist.common.util.HttpUtil;
import com.sist.common.util.JsonUtil;
import com.sist.common.util.StringUtil;
import com.sist.manager.model.Admin;
import com.sist.manager.model.Response;
import com.sist.manager.service.AdminService;


/**
 * <pre>
 * 패키지명   : com.icia.manager.controller ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ2시29분
 * 파일명     : IndexController.java
 * 작성일     : 2021. 7. 30.
 * 작성자     : daekk
 * 설명       : 인덱스 컨트롤러
 * </pre>
 */
@Controller("indexController")
public class IndexController
{
	private static Logger logger = LoggerFactory.getLogger(IndexController.class);

	// 막내 테스트
	
	// 쿠키명
	@Value("#{env['auth.cookie.name']}")
	private String AUTH_COOKIE_NAME;
	
	@Autowired
	private AdminService adminService;
	
	/**
	 * <pre>
	 * 메소드명   : index 남경식텥ㅌㅌㅌㅌㅌㅌㅌㅌㅌㅌ스트
	 * 작성일     : 2021. 7. 30.
	 * 작성자     : daekk
	 * 설명       : 인덱스
	 * </pre>
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @return String
	 */
	@RequestMapping(value="/index")
	public String index(Model model, HttpServletRequest request, HttpServletResponse response)
	{
		if(CookieUtil.getCookie(request, AUTH_COOKIE_NAME) != null)
		{
			return "redirect:/user/list";
		}
		else
		{
			return "/index";
		}
	}
	
	
	// 관리자 로그인
	@RequestMapping(value="/loginProc", method=RequestMethod.POST)
	@ResponseBody
	public Response<Object> loginProc(HttpServletRequest request, HttpServletResponse response)
	{
		Response<Object> res = new Response<Object>();
		
		String adminId = HttpUtil.get(request, "adminId");
		String adminPwd = HttpUtil.get(request, "adminPwd");
		
		if(!StringUtil.isEmpty(adminId) && !StringUtil.isEmpty(adminPwd))
		{
			Admin admin = adminService.adminSelect(adminId);
			
			if(admin != null)
			{
				if(StringUtil.equals(adminPwd, admin.getAdminPwd()))
				{
					if(StringUtil.equals(admin.getAdminStat(), "Y"))
					{
						CookieUtil.addCookie(response, "/", -1, AUTH_COOKIE_NAME, CookieUtil.stringToHex(adminId)); // -1 은 브라우저가 닫힐 떄까지
						res.setResponse(0, "success");
					}
					else
					{
						res.setResponse(403, "server error");
					}
				}
				else // 비밀번호가 다름
				{
					res.setResponse(-1, "password do not match");
				}
			}
			else // 아이디가 존재하지 않음
			{
				res.setResponse(404, "not found");
			}
		}
		else
		{
			res.setResponse(400, "bad request");
		}
		
		if(logger.isDebugEnabled())
		{
			logger.debug("[IndexController] /loginProc response \n" + JsonUtil.toJsonPretty(res));
		}
		
		return res;
	}
	
}
