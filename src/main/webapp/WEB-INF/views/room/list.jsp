<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%
   // GNB 번호 (숙소관리)
   request.setAttribute("_gnbNo", 2);
%>
<!DOCTYPE html>
<html>
<head>
  <%@ include file="/WEB-INF/views/include/head.jsp" %>

  <style>
    /* 테이블 기본 스타일 */
    .table-hover th, .table-hover td {
      border:1px solid #c4c2c2;
      text-align:center;
    }
    /* 버튼 스타일 */
    .btn-status {
      padding:4px 8px;
      font-size:0.9rem;
      cursor:pointer;
    }
  </style>

  <script type="text/javascript">
    // 검색
    function fn_search(){
      document.searchForm.curPage.value = 1;
      document.searchForm.action = "/room/list";
      document.searchForm.submit();
    }
    // 페이징
    function fn_paging(curPage){
      document.searchForm.curPage.value = curPage;
      document.searchForm.action = "/room/list";
      document.searchForm.submit();
    }
    // 삭제 여부 토글
    function toggleDelete(roomSeq, btn){
      if(!confirm("삭제 여부를 변경하시겠습니까?")) return;
      $.ajax({
        type: "POST",
        url: "/room/toggleDelete",
        data: { roomSeq: roomSeq },
        dataType: "json",
        success: function(res){
          if(res.code===0){
            // 버튼 텍스트 반전
            var now = $(btn).text() === "삭제" ? "복원" : "삭제";
            $(btn).text(now);
            alert("처리되었습니다.");
            location.reload();
          } else {
            alert("실패: " + res.message);
          }
        },
        error: function(){
          alert("서버 에러");
        }
      });
    }
    // 판매 상태 토글
    function toggleSale(roomSeq, btn){
      if(!confirm("판매 상태를 변경하시겠습니까?")) return;
      $.ajax({
        type: "POST",
        url: "/room/toggleSale",
        data: { roomSeq: roomSeq },
        dataType: "json",
        success: function(res){
          if(res.code===0){
            var now = $(btn).text() === "판매중" ? "판매중지" : "판매중";
            $(btn).text(now);
            alert("처리되었습니다.");
            location.reload();
          } else {
            alert("실패: " + res.message);
          }
        },
        error: function(){
          alert("서버 에러");
        }
      });
    }
  </script>
</head>

<body>
  <%@ include file="/WEB-INF/views/include/admin_gnb.jsp" %>

  <div id="room_list" style="width:90%; margin:auto; margin-top:5rem;">
    <!-- 상단: 제목 + 검색폼 -->
    <div class="mnb" style="display:flex; margin-bottom:0.8rem;">
      <h2 style="margin-right:auto; color:#525252;">숙소 리스트</h2>
      <form class="d-flex" name="searchForm" method="get" style="place-content:flex-end;">
      	
         <select id="delYn" name="delYn" style="font-size: 1rem; width: 5rem; height: 3rem; margin-left:.5rem;">
            <option value="">삭제여부</option>
            <option value="Y" <c:if test="${delYn == 'Y'}">selected</c:if>>삭제됨</option>
            <option value="N" <c:if test="${delYn == 'N'}">selected</c:if>>정상</option>
         </select>
         
         <select id="saleYn" name="saleYn" style="font-size: 1rem; width: 5rem; height: 3rem; margin-left:.5rem;">
            <option value="">판매상태</option>
            <option value="Y" <c:if test="${saleYn == 'Y'}">selected</c:if>>판매중</option>
            <option value="N" <c:if test="${saleYn == 'N'}">selected</c:if>>판매중지</option>
         </select>
      
        <select name="searchType" style="font-size:1rem; width:5rem; height:3rem; margin-left:.5rem;">
          <option value="">검색타입</option>
          <option value="1"  <c:if test="${searchType=='1'}">selected</c:if>>제목</option>
          <option value="2"  <c:if test="${searchType=='2'}">selected</c:if>>호스트아이디</option>
          <option value="3"  <c:if test="${searchType=='3'}">selected</c:if>>카테고리</option>
          <option value="4"  <c:if test="${searchType=='4'}">selected</c:if>>지역</option>
        </select>
        <input name="searchValue" class="form-control" 
               style="width:15rem; margin-left:.5rem;" 
               type="text" value="${searchValue}" 
               placeholder="검색어"/>
        <a class="btn" href="javascript:void(0)" 
           onclick="fn_search()" 
           style="width:4rem; margin-left:.5rem;
                  background:#efefef; border:1px solid #767676;">
          조회
        </a>
        <input type="hidden" name="curPage" value="${curPage}" />
      </form>
    </div>

    <!-- 리스트 테이블 -->
    <div class="room_list_table">
      <table class="table table-hover" style="border:1px solid #c4c2c2;">
        <thead>
          <tr style="background:#f1f1f1;">
            <th style="width:8%;">번호</th>
            <th style="width:15%;">호스트ID</th>
            <th style="width:25%;">제목</th>
            <th style="width:15%;">지역</th>
            <th style="width:12%;">등록일</th>
            <th style="width:10%;">삭제</th>
            <th style="width:10%;">판매</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="room" items="${list}">
            <tr>
              <td>${room.roomSeq}</td>
              <td>${room.hostId}</td>
              <td>${room.roomTitle}</td>
              <td>${room.region}</td>
              <td>${room.regDt}</td>
              <td>
                <button type="button" class="btn-status"
                        onclick="toggleDelete(${room.roomSeq}, this)">
                  <c:choose>
                    <c:when test="${room.delYn=='N'}">삭제</c:when>
                    <c:when test="${room.delYn=='Y'}">복원</c:when>
                  </c:choose>
                </button>
              </td>
              <td>
                <button type="button" class="btn-status"
                        onclick="toggleSale(${room.roomSeq}, this)">
                  <c:choose>
                    <c:when test="${room.saleYn=='Y'}">판매중</c:when>
                    <c:when test="${room.saleYn=='N'}">판매중지</c:when>
                  </c:choose>
                </button>
              </td>
            </tr>
          </c:forEach>

          <c:if test="${empty list}">
            <tr>
              <td colspan="7">등록된 숙소가 없습니다.</td>
            </tr>
          </c:if>
        </tbody>
      </table>

      <!-- 페이징 -->
      <div class="paging-right" style="float:right; margin-top:.5rem;">
        <c:if test="${!empty paging}">
          <c:if test="${paging.prevBlockPage gt 0}">
            <a href="javascript:void(0)" class="btn2 btn-primary"
               onclick="fn_paging(${paging.prevBlockPage})">&laquo;</a>
          </c:if>
          <c:forEach var="i" begin="${paging.startPage}" end="${paging.endPage}">
            <c:choose>
              <c:when test="${i ne curPage}">
                <a href="javascript:void(0)" class="btn2 btn-primary"
                   onclick="fn_paging(${i})">${i}</a>
              </c:when>
              <c:otherwise>
                <span class="btn2 btn-primary" 
                      style="font-weight:bold;">${i}</span>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          <c:if test="${paging.nextBlockPage gt 0}">
            <a href="javascript:void(0)" class="btn2 btn-primary"
               onclick="fn_paging(${paging.nextBlockPage})">&raquo;</a>
          </c:if>
        </c:if>
      </div>

    </div>
  </div>
</body>
</html>
