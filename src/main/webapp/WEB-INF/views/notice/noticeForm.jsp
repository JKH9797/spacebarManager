<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <%@ include file="/WEB-INF/views/include/head.jsp" %>

  <style>
    .form-container {
      display: flex;
      justify-content: center;
      align-items: center;
      width: 100vw;
      height: 100vh;
      padding: 20px;
      box-sizing: border-box;
      background: #f5f5f5;
    }
    .form-box {
      width: 50vw;
      max-width: 600px;
      background: #fff;
      padding: 30px;
      border-radius: 8px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.1);
      box-sizing: border-box;
    }
    .form-box .form-control {
      width: 100%;
      box-sizing: border-box;
    }
    .form-box .d-flex > button {
      flex: 1;
      margin: 0 5px;
    }
    .form-box h2 {
      text-align: center;
      margin-bottom: 20px;
    }
  </style>

  <!-- jQuery 로드 (head.jsp에 없다면) -->
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

  <script>
  $(function(){
    const ctx = '${pageContext.request.contextPath}';

    // 리스트 버튼
    $('#btnList').click(function(){
      location.href = ctx + '/notice/list';
    });

    // 저장 버튼
    $('#btnWrite').click(function(){
      const title   = $.trim($('#noticeTitle').val());
      const content = $.trim($('#noticeContent').val());

      if (!title) {
        alert('제목을 입력해주세요.');
        $('#noticeTitle').focus();
        return;
      }
      if (!content) {
        alert('내용을 입력해주세요.');
        $('#noticeContent').focus();
        return;
      }

      $.ajax({
        url: ctx + '/notice/writeProc',
        method: 'POST',
        dataType: 'json',
        data: {
          noticeTitle: title,
          noticeContent: content
        }
      })
      .done(function(res) {
        console.log('>>> 응답', res);
        const code = parseInt(res.code, 10);
        const msg  = res.msg || '알 수 없는 오류입니다.';
        if (code === 0) {
          alert(msg);
          location.href = ctx + '/notice/list';
        } else {
          alert(msg);
        }
      })
      .fail(function(xhr, status, err) {
        console.error('AJAX 에러', status, err);
        alert('통신 중 오류가 발생했습니다.');
      });
    });
  });
  </script>
</head>
<body>
  <%@ include file="/WEB-INF/views/include/admin_gnb.jsp" %>

  <div class="form-container">
    <div class="form-box">
      <h2>공지사항 등록</h2>
      <form id="writeForm">
        <input type="text"
               id="noticeTitle"
               name="noticeTitle"
               class="form-control mb-3"
               placeholder="제목을 입력해주세요." />

        <textarea id="noticeContent"
                  name="noticeContent"
                  class="form-control mb-4"
                  rows="10"
                  placeholder="내용을 입력해주세요."></textarea>

        <div class="d-flex">
          <button type="button"
                  id="btnList"
                  class="btn btn-secondary">
            리스트
          </button>
          <button type="button"
                  id="btnWrite"
                  class="btn btn-primary">
            저장
          </button>
        </div>
      </form>
    </div>
  </div>
</body>
</html>
