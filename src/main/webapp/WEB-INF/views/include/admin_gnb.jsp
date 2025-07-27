<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<style>
	html, body{
		width: 1903px;
	}
	h2, .h2 {
	    font-size: 2rem;
	}
	.navbar-expand-lg {
	    flex-wrap: nowrap;
	    justify-content: flex-start;
	}
	.navbar-expand-lg .navbar-collapse {
	    display: flex !important;
	    flex-basis: auto;
	}
	.navbar-expand-lg .navbar-nav {
	    flex-direction: row;
	}
	.navbar-expand-lg .navbar-nav .nav-link {
	    padding-right: 1.5rem;
	    padding-left: 1.5rem;
	}
	/* 드롭다운 메뉴 스타일 */
	.navbar-nav .dropdown-menu {
	    position: absolute;
	    background-color: #f8f9fa;
	    border: 1px solid rgba(0,0,0,.15);
	    border-radius: .25rem;
	    box-shadow: 0 .5rem 1rem rgba(0,0,0,.175);
	    margin-top: 0;
	    padding: 0.5rem 0; /* ◀◀ 수정: 좌우 패딩 제거 */
	}
	.navbar-nav .dropdown-item {
	    display: block; /* ◀◀ 추가: 블록 요소로 변경 */
	    width: 100%;    /* ◀◀ 추가: 너비를 100%로 설정 */
	    box-sizing: border-box; /* ◀◀ 추가: 패딩이 너비에 영향을 주지 않도록 함 */
	    color: #212529;
	    padding: .5rem 1.5rem; /* 좌우 패딩을 늘려 글자 여유 공간 확보 */
	}
	.navbar-nav .dropdown-item:hover {
	    background-color: #e9ecef;
	    color: #1e2125;
	}
</style>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">

  <div class="container-fluid">
    <div class="collapse navbar-collapse" id="navbarColor01">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <!-- 1번 메뉴 -->
        <li class="nav-item">
          <a 
            class="nav-link ${_gnbNo == 1 ? 'active' : ''}" 
            href="/user/list"
            aria-current="${_gnbNo == 1 ? 'page' : ''}">
            회원관리
            <c:if test="${_gnbNo == 1}">
              <span class="visually-hidden">(current)</span>
            </c:if>
          </a>
        </li>
        <!-- 2번 메뉴 -->
        <li class="nav-item">
          <a 
            class="nav-link ${_gnbNo == 2 ? 'active' : ''}" 
            href="/room/list"
            aria-current="${_gnbNo == 2 ? 'page' : ''}">
            숙소/공간관리
            <c:if test="${_gnbNo == 2}">
              <span class="visually-hidden">(current)</span>
            </c:if>
          </a>
        </li>
        <!-- 3번 메뉴 -->
        <li class="nav-item">
          <a 
            class="nav-link ${_gnbNo == 3 ? 'active' : ''}" 
            href="/notice/list"
            aria-current="${_gnbNo == 3 ? 'page' : ''}">
            공지사항
            <c:if test="${_gnbNo == 3}">
              <span class="visually-hidden">(current)</span>
            </c:if>
          </a>
        </li>
        <!-- 4번 메뉴 -->
        <li class="nav-item">
          <a 
            class="nav-link ${_gnbNo == 4 ? 'active' : ''}" 
            href="/qna/list"
            aria-current="${_gnbNo == 4 ? 'page' : ''}">
            Q&A
            <c:if test="${_gnbNo == 4}">
              <span class="visually-hidden">(current)</span>
            </c:if>
          </a>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle ${_gnbNo == 5 ? 'active' : ''}" 
             href="#" id="dashboardDropdown" role="button" 
             data-bs-toggle="dropdown" aria-expanded="false">
            대시보드
          </a>
          <ul class="dropdown-menu" aria-labelledby="dashboardDropdown">
            <li><a class="dropdown-item" href="/dashboard/main#total-sales">전체 매출 통계</a></li>
            <li><a class="dropdown-item" href="/dashboard/main#seller-sales">판매자별 매출 통계</a></li>
            <li><a class="dropdown-item" href="/dashboard/main#seller-ranking">판매자 매출 순위</a></li>
          </ul>
        </li>
      </ul>

      <!-- 우측 사용자명 + 로그아웃 -->
      <span class="navbar-text text-white pe-3">
        ${gnbAdmName}
      </span>
      <a class="nav-link text-white" href="/loginOut">로그아웃</a>
    </div>
  </div>
</nav>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
