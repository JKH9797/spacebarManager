package com.sist.manager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.manager.model.SellerRanking;
import com.sist.manager.model.User;
import com.sist.manager.service.DashboardService;
import com.sist.manager.service.UserService;

@Controller
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/dashboard/main")
    public String dashboardPage() {
        return "/dashboard/main"; // admin/dashboard.jsp 페이지로 이동
    }

    @GetMapping("/dashboard/stats/total-sales")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> getTotalSales(
            @RequestParam String startDate, @RequestParam String endDate, @RequestParam String groupBy) {
        return ResponseEntity.ok(dashboardService.getTotalSalesStats(startDate, endDate, groupBy));
    }

    @GetMapping("/dashboard/stats/seller-sales")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> getSellerSales(
            @RequestParam String hostId, @RequestParam String startDate, 
            @RequestParam String endDate, @RequestParam String groupBy) {
        return ResponseEntity.ok(dashboardService.getSellerSalesStats(hostId, startDate, endDate, groupBy));
    }

    @GetMapping("/dashboard/stats/seller-ranking")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getSellerRanking(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "amount") String orderBy,
            @RequestParam(defaultValue = "1") int page) { // page 파라미터 추가
        return ResponseEntity.ok(dashboardService.getSellerSalesRanking(startDate, endDate, orderBy, page));
    }
    
    @GetMapping("/dashboard/api/sellers")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getSellerList(
            @RequestParam(required=false) String query,
            @RequestParam(defaultValue="1") int page) {
        return ResponseEntity.ok(userService.findSellers(query, page));
    }
}