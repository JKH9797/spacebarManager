package com.sist.manager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value; // 세션 사용하므로 불필요
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.common.util.HttpUtil;
import com.sist.common.util.JsonUtil;
import com.sist.common.util.StringUtil;
import com.sist.manager.model.Admin;
import com.sist.manager.model.Response;
import com.sist.manager.service.AdminService;

@Controller("indexController")
public class IndexController {
	private static Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private AdminService adminService;

	/**
	 * 인덱스 접근 시 세션 체크
	 */
	@RequestMapping(value="/index")
	public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("sessionUserId") != null) {
			return "redirect:/user/list";
		} else {
			return "/index";
		}
	}

	/**
	 * 관리자 로그인 (세션 방식)
	 */
	@RequestMapping(value="/loginProc", method=RequestMethod.POST)
	@ResponseBody
	public Response<Object> loginProc(HttpServletRequest request, HttpServletResponse response) {
		Response<Object> res = new Response<Object>();

		String adminId = HttpUtil.get(request, "adminId");
		String adminPwd = HttpUtil.get(request, "adminPwd");

		if(!StringUtil.isEmpty(adminId) && !StringUtil.isEmpty(adminPwd)) {
			Admin admin = adminService.adminSelect(adminId);

			if(admin != null) {
				if(StringUtil.equals(adminPwd, admin.getAdminPwd())) {
					if(StringUtil.equals(admin.getAdminStat(), "Y")) {
						// 세션에 로그인 정보 저장
						HttpSession session = request.getSession();
						session.setAttribute("sessionUserId", adminId);
						session.setAttribute("sessionRole", "ADMIN");  // 역할도 필요하면 추가

						res.setResponse(0, "success");
					} else {
						res.setResponse(403, "server error");
					}
				} else { // 비밀번호 불일치
					res.setResponse(-1, "password do not match");
				}
			} else { // 아이디 없음
				res.setResponse(404, "not found");
			}
		} else {
			res.setResponse(400, "bad request");
		}

		if(logger.isDebugEnabled()) {
			logger.debug("[IndexController] /loginProc response \n" + JsonUtil.toJsonPretty(res));
		}

		return res;
	}

}
