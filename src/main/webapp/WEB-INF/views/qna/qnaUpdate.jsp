<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <%@ include file="/WEB-INF/views/include/head.jsp" %>
  <meta charset="UTF-8">
  <title>Q&A  수정</title>
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
      <h2>Q&A  수정</h2>
      <form action="${pageContext.request.contextPath}/qna/updateProc" method="post">
        <input type="hidden" name="qnaCmtSeq" value="${qnaCmt.qnaCmtSeq}" />
         <input type="hidden" name="adminId"   value="${qnaCmt.adminId}"   />

        <label for="noticeContent">내용</label>
        <textarea
          id="noticeContent"
          name="noticeContent"
          class="form-control"
          rows="10"
          required
        >${fn:escapeXml(qnaCmt.qnaCmtContent)}</textarea>

        <div class="btn-group">
          <a
            href="${pageContext.request.contextPath}/qna/detail?qnaSeq=${qnaCmt.qnaSeq}"
            class="btn btn-secondary"
          >취소</a>
          <button type="button" id="btnUpdate" class="btn btn-primary">저장</button>
        </div>
      </form>
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/aos.js"></script>

  <script>
  $(function(){
    const ctx = '${pageContext.request.contextPath}';
    
    // 저장 버튼
    $('#btnUpdate').click(function(){
      const qnaSeq = $('#qnaSeq').val();
      const content = $.trim($('#qnaCmtContent').val());

      if (!content) 
      {
        alert('내용을 입력해주세요.');
        $('#qnaCmtContent').focus();
        return;
      }

      $.ajax({
        url: ctx + '/qna/qnaCmtUpdateProc',
        method: 'POST',
        dataType: 'json',
        data: {
          qnaSeq : qnaSeq,
          qnaCmtContent: content
        }
      })
      .done(function(res) {
        console.log('>>> 응답', res);
        const code = parseInt(res.code, 10);
        const msg  = res.msg || '알 수 없는 오류입니다.';
        if (code === 0) 
        {
          alert(msg);
          location.href = ctx + '/qna/list';
        } 
        else 
        {
          alert(msg);
        }
      })
      .fail(function(xhr, status, err) 
      {
        console.error('AJAX 에러', status, err);
        alert('통신 중 오류가 발생했습니다.');
      });
    });
  });
  </script>  
  
</body>
</html>
