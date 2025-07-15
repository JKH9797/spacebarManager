package com.sist.manager.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.manager.model.Notice;
import com.sist.manager.model.NoticeReply;
import com.sist.manager.service.NoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

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
        // sessionRole 체크는 필터 또는 세션에서 직접 읽도록 변경 가능
        Object role = request.getSession().getAttribute("sessionRole");
        if (!"ADMIN".equals(role)) {
            return "redirect:/notice/list";
        }
        return "/notice/noticeForm";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute Notice dto, HttpServletRequest request) {
        String adminId = (String) request.getSession().getAttribute("sessionUserId");
        dto.setAdminId(adminId);
        noticeService.writeNotice(dto);
        return "redirect:/notice/list";
    }

    @PostMapping("/reply")
    public String reply(@ModelAttribute NoticeReply replyDTO, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("sessionUserId");
        replyDTO.setUserId(userId);
        noticeService.writeReply(replyDTO);
        return "redirect:/notice/detail?noticeSeq=" + replyDTO.getNoticeSeq();
    }
}
