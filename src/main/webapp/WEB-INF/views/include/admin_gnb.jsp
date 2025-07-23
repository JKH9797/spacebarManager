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
      </ul>

      <!-- 우측 사용자명 + 로그아웃 -->
      <span class="navbar-text text-white pe-3">
        ${gnbAdmName}
      </span>
      <a class="nav-link text-white" href="/loginOut">로그아웃</a>
    </div>
  </div>
</nav>
