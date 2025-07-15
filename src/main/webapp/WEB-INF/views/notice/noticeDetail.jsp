<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <%@ include file="/WEB-INF/views/include/head.jsp" %>
  <meta charset="UTF-8">
  <title>공지사항 상세</title>
  <style>
    /* 기존 스타일 유지 */
    .section-block {
      padding: 40px 0;
      margin-bottom: 40px;
      border-top: 2px solid #e9ecef;
    }
    .section-heading {
      font-size: 1.75rem;
      font-weight: 600;
      margin: 70px 0 1rem 0;
      padding-bottom: .5rem;
      border-bottom: 3px solid #007bff;
      display: inline-block;
    }
    .notice-info {
      margin-top: 20px;
      border: 1px solid #dee2e6;
      padding: 20px;
      background-color: #f9f9f9;
    }
    .notice-title {
      font-size: 1.5rem;
      font-weight: 600;
      margin-bottom: 10px;
      color: #007bff;
    }
    .notice-meta {
      font-size: 0.9rem;
      color: #666;
      margin-bottom: 20px;
    }
    .notice-content {
      white-space: pre-wrap;
      line-height: 1.5;
      font-size: 1rem;
      color: #333;
    }
    .btn-primary {
      display: inline-block;
      margin-top: 20px;
    }
    .reply-section {
      margin-top: 40px;
    }
    .reply-list {
      list-style: none;
      padding-left: 0;
      border-top: 1px solid #dee2e6;
    }
    .reply-list li {
      border-bottom: 1px solid #dee2e6;
      padding: 10px 0;
    }
    .reply-user {
      font-weight: 600;
      color: #007bff;
      margin-right: 10px;
    }
    .reply-date {
      font-size: 0.8rem;
      color: #999;
    }
    .reply-content {
      margin-top: 5px;
      white-space: pre-wrap;
    }
    .reply-form textarea {
      width: 100%;
      min-height: 80px;
      margin-bottom: 10px;
      padding: 8px;
      border: 1px solid #ccc;
      resize: vertical;
    }
  </style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/admin_gnb.jsp" %>

<div class="container section-block">
  <h2 class="section-heading">공지사항 상세</h2>

  <div class="notice-info">
    <div class="notice-title">${notice.noticeTitle}</div>
    <div class="notice-meta">
      작성자: ${notice.adminId} | 등록일: <fmt:formatDate value="${notice.regDt}" pattern="yyyy-MM-dd" />
    </div>
    <div class="notice-content">${notice.noticeContent}</div>
  </div>

  <div class="reply-section">
    <h3 class="section-heading" style="border-color:#28a745;">댓글 목록</h3>
    <ul class="reply-list">
      <c:forEach var="r" items="${notice.replies}">
        <li>
          <span class="reply-user">${r.userId}</span>
          <span class="reply-date">(<fmt:formatDate value="${r.regDt}" pattern="yyyy-MM-dd HH:mm" />)</span>
          <div class="reply-content">${r.replyContent}</div>
        </li>
      </c:forEach>
      <c:if test="${empty notice.replies}">
        <li>등록된 댓글이 없습니다.</li>
      </c:if>
    </ul>

    <c:if test="${not empty sessionScope.sessionUserId}">
      <form action="/notice/reply" method="post" class="reply-form">
        <input type="hidden" name="noticeSeq" value="${notice.noticeSeq}" />
        <textarea name="replyContent" placeholder="댓글을 입력하세요." required></textarea>
        <button type="submit" class="btn btn-primary">댓글 등록</button>
      </form>
    </c:if>
    <c:if test="${empty sessionScope.sessionUserId}">
      <p>댓글 작성은 로그인 후 이용 가능합니다.</p>
    </c:if>
  </div>

  <a href="/notice/list" class="btn btn-primary">목록으로 돌아가기</a>
</div>

<script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/aos.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/custom.js"></script>
</body>
</html>
