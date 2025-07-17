<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <%@ include file="/WEB-INF/views/include/head.jsp" %>
  <meta charset="UTF-8">
  <title>공지사항 수정</title>
  <style>
    .form-container {
      display: flex;
      justify-content: center;
      align-items: center;
      padding: 40px 0;
      background: #f5f5f5;
    }
    .form-box {
      width: 600px;
      background: #fff;
      padding: 30px;
      border-radius: 8px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.1);
    }
    .form-box h2 {
      text-align: center;
      margin-bottom: 20px;
    }
    .form-box .form-control,
    .form-box select {
      width: 100%;
      margin-bottom: 15px;
      box-sizing: border-box;
    }
    .form-box .btn-group {
      display: flex;
      justify-content: space-between;
    }
  </style>
</head>
<body>
  <%@ include file="/WEB-INF/views/include/admin_gnb.jsp" %>

  <div class="container form-container">
    <div class="form-box">
      <h2>공지사항 수정</h2>
      <form action="${pageContext.request.contextPath}/notice/editProc" method="post">
        <input type="hidden" name="noticeSeq" value="${notice.noticeSeq}" />
         <input type="hidden" name="adminId"   value="${notice.adminId}"   />

        <label for="noticeTitle">제목</label>
        <input
          type="text"
          id="noticeTitle"
          name="noticeTitle"
          class="form-control"
          value="${fn:escapeXml(notice.noticeTitle)}"
          required
        />

        <label for="noticeContent">내용</label>
        <textarea
          id="noticeContent"
          name="noticeContent"
          class="form-control"
          rows="10"
          required
        >${fn:escapeXml(notice.noticeContent)}</textarea>

        <label for="noticeStat">상태</label>
        <select id="noticeStat" name="noticeStat" class="form-control">
          <option value="Y" <c:if test="${notice.noticeStat == 'Y'}">selected</c:if>>공개</option>
          <option value="N" <c:if test="${notice.noticeStat == 'N'}">selected</c:if>>숨김</option>
        </select>

        <div class="btn-group">
          <a
            href="${pageContext.request.contextPath}/notice/detail?noticeSeq=${notice.noticeSeq}"
            class="btn btn-secondary"
          >취소</a>
          <button type="submit" class="btn btn-primary">저장</button>
        </div>
      </form>
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/aos.js"></script>
</body>
</html>
