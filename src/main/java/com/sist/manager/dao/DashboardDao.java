package com.sist.manager.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sist.manager.model.SellerRanking;

@Repository
public interface DashboardDao {
	
    // 기능 1: 전체 매출 통계 (차트용)
    public List<Map<String, Object>> selectTotalSalesStatsByGroup(Map<String, Object> params);

    // 기능 2: 판매자별 매출 통계 (차트용)
    public List<Map<String, Object>> selectSellerSalesStatsByGroup(Map<String, Object> params);

    // 기능 3: 판매자 매출 순위 조회
    public List<SellerRanking> selectSellerSalesRanking(Map<String, Object> params);
    
    // 판매자 매출 순위 전체 카운트
    public int countSellerSalesRanking(Map<String, Object> params);
}

