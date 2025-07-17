package com.sist.manager.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sist.common.util.StringUtil;
import com.sist.manager.model.Notice;
import com.sist.manager.model.NoticeReply;
import com.sist.manager.service.NoticeService;
import com.sist.manager.model.Response;
import com.sist.common.util.HttpUtil;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);

    @Autowired
    private NoticeService noticeService;
    
    @Value("#{env['auth.session.name']}")
    private String AUTH_SESSION_NAME;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("noticeList", noticeService.getNoticeList());
        return "/notice/noticeList";
    }

    // 수정된 부분: String 리턴형, request/response 미사용, model 통해 데이터 전달
    @GetMapping("/detail")
    public String detail(@RequestParam int noticeSeq, Model model) {
        Notice notice = noticeService.getNoticeById(noticeSeq);

        if (notice == null) {
            return "redirect:/notice/list";
        }

        model.addAttribute("notice", notice);
        return "/notice/noticeDetail"; // 뷰 이름만 리턴
    }

    @GetMapping("/write")
    public String writeForm(HttpServletRequest request) {

        return "/notice/noticeForm";
    }

    @PostMapping("/writeProc")
    @ResponseBody
    public Response<Object> writeProc(
            @RequestParam("noticeTitle")   String noticeTitle,
            @RequestParam("noticeContent") String noticeContent,
            HttpSession session) {

        Response<Object> ajaxResponse = new Response<>();

        String adminId = (String) session.getAttribute(AUTH_SESSION_NAME);

        // 최상위 로그인 체크
        if (!StringUtil.isEmpty(adminId)) {

            // 제목/내용 유효성
            if (!StringUtil.isEmpty(noticeTitle) && !StringUtil.isEmpty(noticeContent)) {

                // 공지 등록 시도
                Notice notice = new Notice();
                notice.setAdminId(adminId);
                notice.setNoticeTitle(noticeTitle.trim());
                notice.setNoticeContent(noticeContent.trim());
                // 필요하면 notice.setNoticeStat("Y");

                try {
                    int cnt = noticeService.insertNotice(notice);
                    if (cnt > 0) {
                        ajaxResponse.setResponse(0, "등록되었습니다.");
                    } else {
                        ajaxResponse.setResponse(500, "등록에 실패했습니다.");
                    }
                } catch (Exception e) {
                    
                	logger.error("▶ writeProc 예외 발생", e);
                    ajaxResponse.setResponse(500, "서버 오류가 발생했습니다.");
                }

            } else {
                // 제목이나 내용이 비어있는 경우
                ajaxResponse.setResponse(400, "제목과 내용을 모두 입력해주세요.");
            }

        } else {
            // 세션이 없거나 로그인 안 된 경우
            ajaxResponse.setResponse(500, "로그인 후 이용하세요.");
        }

        // 단 한번만 응답 객체 리턴
        return ajaxResponse;
    }
    
    /** 공지 수정 폼 */
    @GetMapping("/edit")
    public String editForm(@RequestParam int noticeSeq, Model model, HttpSession session) {
        // 권한 체크, 예: 세션 ID가 작성자와 동일한지
        String adminId = (String) session.getAttribute(AUTH_SESSION_NAME);
        Notice notice = noticeService.getNoticeById(noticeSeq);
        if (notice == null || !adminId.equals(notice.getAdminId())) {
            return "redirect:/notice/list";
        }
        model.addAttribute("notice", notice);
        return "/notice/noticeEditForm";
    }

    /** 공지 수정 처리 */
    @PostMapping("/editProc")
    public String editSubmit(@ModelAttribute Notice dto, HttpSession session) {
    	String adminId = (String) session.getAttribute(AUTH_SESSION_NAME);
	    dto.setAdminId(adminId);
	    noticeService.updateNotice(dto);
	    return "redirect:/notice/detail?noticeSeq=" + dto.getNoticeSeq();
    }



    @PostMapping("/reply")
    public String reply(@ModelAttribute NoticeReply replyDTO, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute(AUTH_SESSION_NAME);
        replyDTO.setUserId(userId);
        noticeService.writeReply(replyDTO);
        return "redirect:/notice/detail?noticeSeq=" + replyDTO.getNoticeSeq();
    }
}
