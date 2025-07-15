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
	    align-items: center; /* 세로 가운데 정렬 */
	}
	.navbar-expand-lg .navbar-nav {
	    flex-direction: row;
	}
	.navbar-expand-lg .navbar-nav .nav-link {
	    padding-right: 1.5rem;
	    padding-left: 1.5rem;
	}
	/* 공지사항 버튼 스타일 */
	.notice-btn {
	    background-color: transparent;
	    border: none;
	    color: white;
	    font-size: 1rem;
	    cursor: pointer;
	    padding: 0 1rem 0 0;
	}
	.notice-btn:hover {
	    text-decoration: underline;
	}
</style>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
	<div class="container-fluid">
		<div class="collapse navbar-collapse" id="navbarColor01">
			<ul class="navbar-nav me-auto">
				<li class="nav-item">
					<a class="nav-link<c:if test="${_gnbNo == 1}"> active</c:if>" href="/user/list">회원관리
					    <span class="visually-hidden">(current)</span>
					</a>
				</li>
			</ul>
			<span style="color:white; text-decoration:none; padding-right: 3rem;">${gnbAdmName}</span>
			
			<!-- 공지사항 목록 버튼 추가 -->
			<a style="color:white; text-decoration:none; padding-left: 1.5rem;" href="/notice/list">공지사항</a>
			<a style="color:white; text-decoration:none; padding-left: 1.5rem;" href="/loginOut">로그아웃</a>
		</div>
	</div>
</nav>
