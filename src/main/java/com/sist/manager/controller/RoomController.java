package com.sist.manager.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.sist.common.util.HttpUtil;
import com.sist.common.util.StringUtil;
import com.sist.manager.model.Paging;
import com.sist.manager.model.Response;
import com.sist.manager.model.Room;
import com.sist.manager.service.RoomService;

@Controller("roomController")
public class RoomController {
    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
    
    
    @Value("#{env['auth.session.name']}")
	private String AUTH_SESSION_NAME;
	
	private static final int LIST_COUNT = 10;
	private static final int PAGE_COUNT = 3;

    @Autowired
    private RoomService roomService;

    /**
     * 숙소 리스트 + 검색 + 페이징
     */
    @RequestMapping(value="/room/list", method=RequestMethod.GET)
    public String list(ModelMap model, HttpServletRequest request) {
        // 1) 파라미터 받기
        int curPage      = HttpUtil.get(request, "curPage", 1);
        String delYn     = HttpUtil.get(request, "delYn", "");
        String saleYn    = HttpUtil.get(request, "saleYn", "");
        String searchType  = HttpUtil.get(request, "searchType", "");
        String searchValue = HttpUtil.get(request, "searchValue", "");
        
        String gubun = "";

        // 2) 검색 조건 세팅
        Room room = new Room();
        if (!StringUtil.isEmpty(delYn))  room.setDelYn(delYn);
        if (!StringUtil.isEmpty(saleYn)) room.setSaleYn(saleYn);

        if (!StringUtil.isEmpty(searchType) && !StringUtil.isEmpty(searchValue)) {
        	if(StringUtil.equals(searchType, "1"))
			{
				room.setRoomTitle(searchValue);
				gubun = "Y";
			}
			else if(StringUtil.equals(searchType, "2"))
			{
				room.setHostId(searchValue);
				gubun = "Y";
			}
			else if(StringUtil.equals(searchType, "3"))
			{
				room.setRoomCatName(searchValue);
				gubun = "Y";
			}
			else if(StringUtil.equals(searchType, "4"))
			{
				room.setRegion(searchValue);
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

        // 3) 총 건수 조회
        int totalCount = roomService.roomListCount(room);

        Paging paging = null;
        java.util.List<Room> list = null;
        if (totalCount > 0) {
            paging = new Paging(
                "/room/list",
                totalCount,
                LIST_COUNT,
                PAGE_COUNT,
                curPage,
                "curPage"
            );
            room.setStartRow(paging.getStartRow());
            room.setEndRow(paging.getEndRow());
            list = roomService.roomList(room);
        }

        // 4) 모델 등록
        model.addAttribute("list", list);
        model.addAttribute("paging", paging);
        model.addAttribute("curPage", curPage);
        model.addAttribute("delYn", delYn);
        model.addAttribute("saleYn", saleYn);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchValue", searchValue);

        return "/room/list";
    }

    /**
     * 삭제 여부 토글
         */
    
    @PostMapping("/room/toggleDelete")
    @ResponseBody
    public Response<Object> toggleDelete(HttpServletRequest request) {
        Response<Object> res = new Response<>();
        int roomSeq = HttpUtil.get(request, "roomSeq", 0);

        if (roomSeq <= 0) {
            res.setResponse(400, "bad request");
            return res;
        }
        try {
            long cnt = roomService.toggleDelete(roomSeq);
            if (cnt > 0) {
                res.setResponse(0, "success");
            } else {
                res.setResponse(-1, "fail");
            }
        } catch (Exception e) {
            logger.error("toggleDelete error", e);
            res.setResponse(-1, "server error");
        }
        return res;
    }


    /**
     * 판매 상태 토글
         */ 
    @PostMapping("/room/toggleSale")
    @ResponseBody
    public Response<Object> toggleSale(HttpServletRequest request) {
        Response<Object> res = new Response<>();
        int roomSeq = HttpUtil.get(request, "roomSeq", 0);

        if (roomSeq <= 0) {
            res.setResponse(400, "bad request");
            return res;
        }
        try {
            long cnt = roomService.toggleSale(roomSeq);
            if (cnt > 0) {
                res.setResponse(0, "success");
            } else {
                res.setResponse(-1, "fail");
            }
        } catch (Exception e) {
            logger.error("toggleSale error", e);
            res.setResponse(-1, "server error");
        }
        return res;
    }
   
}
