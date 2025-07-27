<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
    <meta charset="UTF-8">
    <title>관리자 대시보드</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background-color: #f4f7f6; margin: 0; }
        .dashboard-container { padding: 25px; }
        .card-container { background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.05); margin-bottom: 30px; }
        .controls { display: flex; gap: 15px; margin-bottom: 20px; align-items: center; flex-wrap: wrap; }
        .controls label { font-weight: bold; }
        .controls input, .controls select, .controls button { padding: 8px; border-radius: 4px; border: 1px solid #ccc; font-size: 14px; }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #eee; }
        th { background-color: #f8f9fa; }
        .btn-sort { background: #f0f0f0; border: 1px solid #ccc; padding: 8px 12px; cursor: pointer; }
        .btn-sort.active { background: #007bff; color: white; border-color: #007bff; }
        .modal { display: none; position: fixed; z-index: 1000; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.4); }
        .modal-content { background-color: #fefefe; margin: 10% auto; padding: 20px; border: 1px solid #888; width: 80%; max-width: 600px; border-radius: 8px; }
        .close-button { color: #aaa; float: right; font-size: 28px; font-weight: bold; cursor: pointer; }
        #sellerModal .controls { gap: 10px; margin-bottom: 15px; }
        #seller-search-input { flex-grow: 1; }
        .pagination-controls { margin-top: 15px; text-align: center; }
        .pagination-controls button { padding: 5px 10px; margin: 0 5px; cursor: pointer; }
        .pagination-controls span { margin: 0 10px; font-weight: bold; }
        .dashboard-section.hidden { display: none; }
    </style>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="/resources/js/dashboard/main.js"></script>
</head>
<body>
    <%@ include file="/WEB-INF/views/include/admin_gnb.jsp" %>
    
    <div class="dashboard-container">

        <div id="total-sales" class="dashboard-section card-container">
            <h2>전체 매출 통계</h2>
            <div class="controls">
                <label>기간:</label>
                <input type="date" id="totalSales-startDate">
                <span>~</span>
                <input type="date" id="totalSales-endDate">
                <label>단위:</label>
                <select id="totalSales-groupBy">
                    <option value="month">월간</option>
                    <option value="week">주간</option>
                </select>
                <button id="btn-load-total-sales">조회</button>
            </div>
            <canvas id="totalSalesChart"></canvas>
        </div>

        <div id="seller-sales" class="dashboard-section card-container">
            <h2>판매자별 매출 통계</h2>
            <div class="controls">
                <label>판매자:</label>
                <input type="hidden" id="selected-host-id">
                <input type="text" id="selected-host-name" placeholder="판매자를 선택하세요" readonly>
                <button id="btn-open-seller-modal">판매자 찾기</button>
                <label>기간:</label>
                <input type="date" id="sellerSales-startDate">
                <span>~</span>
                <input type="date" id="sellerSales-endDate">
                <label>단위:</label>
                <select id="sellerSales-groupBy">
                    <option value="month">월간</option>
                    <option value="week">주간</option>
                </select>
                <button id="btn-load-seller-sales">조회</button>
            </div>
            <canvas id="sellerSalesChart"></canvas>
        </div>

        <div id="seller-ranking" class="dashboard-section card-container">
            <h2>판매자 매출 순위</h2>
            <div id="rankingTable-controls" class="controls">
                <label>기간:</label>
                <input type="date" id="ranking-startDate">
                <span>~</span>
                <input type="date" id="ranking-endDate">
                <div style="margin-left: 20px;">
                    <button class="btn-sort active" data-orderby="amount">금액순</button>
                    <button class="btn-sort" data-orderby="count">건수순</button>
                    <button class="btn-sort" data-orderby="rating">평점순</button>
                </div>
                <button id="btn-load-seller-ranking">조회</button>
            </div>
            <table id="rankingTable">
                <thead>
                    <tr>
                        <th>순위</th><th>판매자 ID</th><th>판매자 이름</th><th>총 판매금액</th>
                        <th>총 판매건수</th><th>평균 평점</th><th>리뷰 수</th>
                    </tr>
                </thead>
                <tbody></tbody>
            </table>
            <div id="ranking-pagination" class="pagination-controls"></div>
        </div>
    </div>

    <div id="sellerModal" class="modal">
        <div class="modal-content">
            <span class="close-button">&times;</span>
            <h3>판매자 선택</h3>
            <div class="controls">
                <input type="text" id="seller-search-input" placeholder="아이디 또는 이름으로 검색">
                <button id="btn-seller-search">검색</button>
            </div>
            <table id="seller-list-table">
                <thead>
                    <tr><th>아이디</th><th>이름</th><th>선택</th></tr>
                </thead>
                <tbody></tbody>
            </table>
            <div id="seller-pagination" class="pagination-controls"></div>
        </div>
    </div>
</body>
</html>