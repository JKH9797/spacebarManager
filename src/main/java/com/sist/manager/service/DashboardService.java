package com.sist.manager.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.manager.dao.DashboardDao;
import com.sist.manager.model.Paging;
import com.sist.manager.model.SellerRanking;


@Service
public class DashboardService {
	
	private static Logger logger = LoggerFactory.getLogger(DashboardService.class);
	
    @Autowired
    private DashboardDao dashboardDao;
    
    private static final int RANKING_PER_PAGE = 10; // 순위는 한 페이지에 10명씩
    private static final int PAGE_COUNT = 5;
    
    public List<Map<String, Object>> getTotalSalesStats(String startDate, String endDate, String groupBy) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("groupBy", groupBy);
        return dashboardDao.selectTotalSalesStatsByGroup(params);
    }

    public List<Map<String, Object>> getSellerSalesStats(String hostId, String startDate, String endDate, String groupBy) {
        Map<String, Object> params = new HashMap<>();
        params.put("hostId", hostId);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("groupBy", groupBy);
        return dashboardDao.selectSellerSalesStatsByGroup(params);
    }

    public Map<String, Object> getSellerSalesRanking(String startDate, String endDate, String orderBy, int currentPage) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("orderBy", orderBy);

        // 카운트 쿼리에는 페이징 파라미터가 필요 없습니다.
        int totalCount = dashboardDao.countSellerSalesRanking(params);
        
        Paging paging = new Paging("", totalCount, RANKING_PER_PAGE, PAGE_COUNT, currentPage, "page");
        
        // ▼▼▼ 이 부분이 누락되었습니다 ▼▼▼
        // 목록 조회 쿼리에는 페이징 파라미터를 추가해야 합니다.
        params.put("startRow", (currentPage - 1) * RANKING_PER_PAGE + 1);
        params.put("endRow", currentPage * RANKING_PER_PAGE);
        // ▲▲▲ 여기까지 추가 ▲▲▲

        resultMap.put("rankingList", dashboardDao.selectSellerSalesRanking(params));
        resultMap.put("paging", paging);

        return resultMap;
    }
}