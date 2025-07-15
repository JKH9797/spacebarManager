package com.sist.manager.controller;

import org.slf4j.LoggerFactory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.common.util.CookieUtil;
import com.sist.common.util.HttpUtil;
import com.sist.common.util.JsonUtil;
import com.sist.common.util.StringUtil;
import com.sist.manager.model.Paging;
import com.sist.manager.model.Response;
import com.sist.manager.model.User;
import com.sist.manager.service.UserService;

@Controller("userController")
public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	// 쿠키명
	@Value("#{env['auth.cookie.name']}")
	private String AUTH_COOKIE_NAME;
	
	
	
	private static final int LIST_COUNT = 10;
	private static final int PAGE_COUNT = 3;
	
	
	
	@RequestMapping(value="/user/list")
	public String list(ModelMap model, HttpServletRequest request, HttpServletResponse response)
	{
		List<User> list = null;
		Paging paging = null;
		int totalCount = 0;
		User user = new User();
		
		int curPage = HttpUtil.get(request, "curPage", 1);
		String userStat = HttpUtil.get(request, "userStat", "");
		String searchType = HttpUtil.get(request, "searchType", "");
		String searchValue = HttpUtil.get(request, "searchValue", "");
		String userType = HttpUtil.get(request, "userType", "");
		String approvStat = HttpUtil.get(request, "approvStat", "");
		
		String gubun = "";
		
		if(!StringUtil.isEmpty(userStat))
		{
			//userStat = "Y";
			user.setUserStat(userStat);
		}
		else
		{
			userStat = "";
		}
		
		if(!StringUtil.isEmpty(userType))
		{
			user.setUserType(userType);
		}
		else
		{
			userType = "";
		}
		
		if(!StringUtil.isEmpty(approvStat))
		{
			user.setApprovStat(approvStat);
		}
		else
		{
			approvStat = "";
		}
		
		if(!StringUtil.isEmpty(searchType) && !StringUtil.isEmpty(searchValue))
		{
			if(StringUtil.equals(searchType, "1"))
			{
				user.setUserId(searchValue);
				gubun = "Y";
			}
			else if(StringUtil.equals(searchType, "2"))
			{
				user.setUserName(searchValue);
				gubun = "Y";
			}
			else if(StringUtil.equals(searchType, "3"))
			{
				user.setPhone(searchValue);
				gubun = "Y";
			}
			else if(StringUtil.equals(searchType, "4"))
			{
				user.setNickname(searchValue);
				gubun = "Y";
			}
			else
			{
				searchType = "";
				searchValue = "";
				gubun = "";
			}
		}
		else
		{
			searchType = "";
			searchValue = "";
		}
		
		boolean pendingList = "H".equals(userType) && "N".equals(approvStat);
		
		totalCount = userService.userListCount(user);
		
		if(totalCount > 0)
		{
			paging = new Paging("/user/list", totalCount, LIST_COUNT, PAGE_COUNT, curPage, "curPage");
			
			user.setStartRow(paging.getStartRow());
			user.setEndRow(paging.getEndRow());
			
			list = userService.userList(user);
		}
		
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("curPage", curPage);
		model.addAttribute("userStat", userStat);
		model.addAttribute("gubun", gubun);

		
		 if (pendingList) {
		        // 한 번만 조회하고 나면, 폼 필터는 초기화 상태로 보여 줌
		        model.addAttribute("userType",   "");
		        model.addAttribute("approvStat", "");
		    } else {
		        // 일반 검색 시엔 원래 값을 계속 유지
		        model.addAttribute("userType",   userType);
		        model.addAttribute("approvStat", approvStat);
		    }

		
		return "/user/list";
	}
	
	// 회원정보 조회
	@RequestMapping(value="/user/update")
	public String update(Model model, HttpServletRequest request, HttpServletResponse response) 
	{
		String userId = HttpUtil.get(request, "userId");
		
		if(!StringUtil.isEmpty(userId))
		{
			User user = userService.userSelect(userId);
			
			if(user != null)
			{
				model.addAttribute("user", user);
			}
		}
		
		return "/user/update";
	}
	
	// 회원정보 수정
	@RequestMapping(value="/user/updateProc", method=RequestMethod.POST)
	@ResponseBody
	public Response<Object> updateProc(HttpServletRequest request, HttpServletResponse response)
	{
		Response<Object> res = new Response<Object>();
		
		String userId = HttpUtil.get(request, "userId");
		String userPwd = HttpUtil.get(request, "userPwd");
		String userName = HttpUtil.get(request, "userName");
		String email = HttpUtil.get(request, "email");
		String userStat = HttpUtil.get(request, "userStat");
		String phone = HttpUtil.get(request, "phone");
		String nickname = HttpUtil.get(request, "nickname");
		String approvStat = HttpUtil.get(request, "approvStat");
		
		if(!StringUtil.isEmpty(userId) && !StringUtil.isEmpty(userPwd) && 
				!StringUtil.isEmpty(email) && !StringUtil.isEmpty(userStat))
		{
			User user = userService.userSelect(userId);
			
			if(user != null)
			{
				user.setUserPwd(userPwd);
				user.setUserName(userName);
				user.setEmail(email);
				user.setUserStat(userStat);
				user.setApprovStat(approvStat);
				user.setPhone(phone);
				user.setNickname(nickname);
				
				if(userService.userUpdate(user) >0)
				{
					res.setResponse(0, "success");
				}
				else
				{
					res.setResponse(-1, "server error");
				}
			}
			else
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
			logger.debug("[UserController]/updateProc response \n" + JsonUtil.toJsonPretty(res));
		}
		
		return res;
	}
	
	// 로그아웃
	@RequestMapping(value="/loginOut", method=RequestMethod.GET)
	public String loginOut(HttpServletRequest request, HttpServletResponse response)
	{
		request.getSession().invalidate();
		

		return "redirect:/"; // 쿠키 삭제하기 위해
	}
	
	//승인상태변경
	@RequestMapping(value="/user/approve", method=RequestMethod.POST)
	@ResponseBody
	public Response<Object> approve(HttpServletRequest request, HttpServletResponse response) {
	    Response<Object> res = new Response<>();
	    String userId = HttpUtil.get(request, "userId");
	    try {
	        int cnt = userService.userApprove(userId);  // 서비스에서 apprvStat = 'Y' 로 업데이트
	        if(cnt > 0) {
	            res.setResponse(0, "success");
	        } else {
	            res.setResponse(-1, "fail");
	        }
	    } catch(Exception e) {
	        res.setResponse(-1, "server error");
	    }
	    return res;
	}
}


