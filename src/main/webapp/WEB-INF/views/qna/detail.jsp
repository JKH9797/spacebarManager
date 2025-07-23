<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <%@ include file="/WEB-INF/views/include/head.jsp" %>
  
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(function(){
	  const ctx = '${pageContext.request.contextPath}';
	  
	  //=======================수정 기능=================================//
	  // 1) 수정 버튼 클릭
	  $('.reply-list').on('click', '.btn-edit-reply', function(e){
	    e.preventDefault();
	    const $li = $(this).closest('li');
	    if ($li.data('editing')) return;
	    $li.data('editing', true);

	    const $content = $li.find('.reply-content');
	    const origText = $content.text().trim();
	    $li.data('orig-text', origText);

	    $content.hide();
	    $('<textarea>')
	      .addClass('edit-reply-text form-control')
	      .val(origText)
	      .insertAfter($content);

	    $(`
	      <div class="reply-actions">
	        <a href="#" class="reply-action save">저장</a>
	        <a href="#" class="reply-action cancel">취소</a>
	      </div>
	    `).appendTo($li);
	  });

	  // 2) 저장 클릭
	  $('.reply-list').on('click', '.reply-action.save', function(e){
	    e.preventDefault();
	    const $li = $(this).closest('li');
	    const newText = $li.find('.edit-reply-text').val().trim();
	    if (!newText) { 
	      alert('댓글을 입력하세요.'); 
	      return; 
	    }

	    $.post(`${ctx}/qna/qnaCmtUpdateProc`, {
	      qnaSeq: $li.data('qna-seq'),        
	      qnaCmtSeq: $li.data('reply-seq'),   
	      qnaCmtContent: newText
	    })
	    .done(function(res){
	      $li.find('.reply-content')
	         .text(newText)
	         .show();

	      $li.find('.edit-reply-text').remove();
	      $li.find('.reply-actions').remove();
	      $li.removeData('editing').removeData('orig-text');
	    })
	    .fail(function(){
	      alert('수정 중 오류 발생');
	    });
	  });

	  // 3) 취소 클릭
	  $('.reply-list').on('click', '.reply-action.cancel', function(e){
	    e.preventDefault();
	    const $li = $(this).closest('li');
	    const origText = $li.data('orig-text');

	    $li.find('.reply-content')
	       .text(origText)
	       .show();

	    $li.find('.edit-reply-text').remove();
	    $li.find('.reply-actions').remove();
	    $li.removeData('editing').removeData('orig-text');
	  });
	  
	  //=======================삭제 기능=================================//
	  $('.reply-list').on('click', '.btn-delete-reply', function(e){
	    e.preventDefault();
	    if (!confirm('정말 이 댓글을 삭제하시겠습니까?')) return;
	    
	    const $li = $(this).closest('li');
	    
	    $.post(`${ctx}/qna/qnaCmtDeleteProc`, {
	      qnaSeq: $li.data('qna-seq'),  
	      qnaCmtSeq: $li.data('reply-seq')
	    })
	    .done(function(){
	      $li.remove();
	    })
	    .fail(function(){
	      alert('삭제 중 오류 발생');
	    });
	  });

	  //=======================댓글 등록=================================//
	  $('#btnWrite').on('click', function(e){
	    e.preventDefault();
	    
	    const sessionUserId = '${sessionScope.SESSION_USER_ID}';
	    if (!sessionUserId) {
	      alert('로그인 후 댓글 작성이 가능합니다.');
	      return;
	    }
	    
	    const qnaSeq = $('#qnaSeq').val();
	    const qnaCmtContent = $('#qnaCmtContent').val().trim();
	    
	    console.log('전송할 데이터:', {qnaSeq: qnaSeq, qnaCmtContent: qnaCmtContent});
	    
	    if (!qnaSeq || qnaSeq == '0') {
	      alert('게시글 정보를 찾을 수 없습니다.');
	      return;
	    }
	    
	    if (!qnaCmtContent) {
	      alert('댓글 내용을 입력해주세요.');
	      $('#qnaCmtContent').focus();
	      return;
	    }

	    $.ajax({
	      type: "POST",
	      url: "${pageContext.request.contextPath}/qna/qnaCmtWriteProc",
	      data: {
	        qnaSeq: qnaSeq,
	        qnaCmtContent: qnaCmtContent
	      },
	      dataType: "JSON",
	      beforeSend: function(xhr) 
	      {
	        xhr.setRequestHeader("AJAX", "true");
	        $('#btnWrite').prop('disabled', true).text('등록 중...');
	      },
	      success: function(response) 
	      {
	        console.log('서버 응답:', response);
	        
	        if(response.code == 0) 
	        {
	          alert("Q&A답변이 등록 되었습니다.");
	          location.reload();
	        } 
	        else if(response.code == 400) 
	        {
	          alert("파라미터 값이 올바르지 않습니다.");
	        }
	        else if(response.code == 500) 
	        {
	          alert("Q&A 답변 등록 중 오류가 발생하였습니다.");
	        }
	        else 
	        {
	          alert("알 수 없는 응답 코드: " + response.code);
	        }
	      },
	      error: function(xhr, status, error) 
	      {
	        alert("댓글 등록 중 오류가 발생했습니다. 다시 시도해주세요.");
	      },
	      complete: function() 
	      {
	        $('#btnWrite').prop('disabled', false).text('저장');
	      }
	    });
	  });

	});
</script>


  <meta charset="UTF-8">
  <title>Q&A 상세페이지</title>
  <style>
    /* 기존 스타일 유지 */
    
    .reply-controls button {
  background: none;
  border: none;
  color: #007bff;
  cursor: pointer;
  font-size: 0.9rem;
  margin-left: 0.75rem;
}
.btn-delete-reply {
  color: #dc3545; /* 빨간 삭제 버튼 */
}
    
    .reply-body {
  margin-top: 8px;
}
.reply-content {
  white-space: pre-wrap;
  line-height: 1.5;
  color: #333;
}
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
  font-size: 2rem;       /* 기존 1.5rem → 2rem 으로 확대 */
  font-weight: 700;      /* 더 굵게 */
  margin-bottom: 0.75rem;
    }
    .notice-meta {
      margin-bottom: 4px;
    }
    .notice-content {
  font-size: 1.125rem;   /* 기존 1rem → 1.125rem 으로 약간 확대 */
  font-weight: 500;
  line-height: 1.6;
  margin-top: 0.5rem;
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
  padding: 0;
  margin: 0;
    }
    .reply-list li {
   border-bottom: 1px solid #eee;
  padding: 12px 0;
    }
    
    .reply-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.5rem;
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
    
    .reply-item {
  flex: 1;
}

.btn-edit-reply {
  background: none;
  border: none;
  color: #007bff;
  cursor: pointer;
  font-size: 0.9rem;
  padding: 4px 8px;
}
.reply-meta {
  display: flex;
  align-items: center;
  gap: 0.5rem; /* user와 date 사이 간격 */
}

  .edit-reply-container {
    display: flex;
    align-items: flex-start;
    margin-top: 8px;
  }
  .edit-reply-text {
    flex: 1;
    margin-right: 8px;
    min-height: 60px;
  }
  .edit-reply-container .btn {
    margin-left: 4px;
  }
  
  .reply-body textarea.edit-reply-text {
  width: 100%;
  min-height: 60px;
  padding: 8px;
  margin-bottom: 8px;
  border: 1px solid #ccc;
  resize: vertical;
}

.reply-action {
  margin-left: 1rem;
  color: #007bff;
  text-decoration: underline;
  cursor: pointer;
  font-size: 0.9rem;
}
.reply-action.cancel {
    color: #d39e00;
}

.reply-actions {
  margin-top: 0.5rem;
  text-align: right;
}

.reply-actions .btn {
   padding: 0.5rem 1rem;
  line-height: 1.25;     /* 텍스트가 중앙 정렬되도록 */
  font-size: 0.875rem;   /* 버튼 글자 크기 */

}
  </style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/admin_gnb.jsp" %>

<div class="container section-block">
  <h2 class="section-heading">Q&A 상세페이지</h2>


  <div class="notice-info">
    <div class="notice-title">${qna.qnaTitle}</div>
    <div class="notice-meta">
      작성자: ${qna.userId} | 등록일: "${qna.regDt}"
    </div>
    <div class="notice-content">${qna.qnaContent}</div>
  </div>

  
<!-- 댓글 목록 -->
<div class="reply-section">
  <h3 class="section-heading" style="border-color:#28a745;">댓글 목록</h3>
  <ul class="reply-list">
    <c:if test="${!empty qnaCmt}">
      <c:choose>
        <c:when test="${qnaCmt.qnaCmtStat == 'N'}">
          <!-- 삭제된 댓글 표시 -->
          <li>
            <div class="reply-header">
              <div class="reply-meta">
                <span class="reply-user" style="color: #999;">삭제된 댓글</span>
                <span class="reply-date">(${qnaCmt.createDt})</span>
              </div>
            </div>
            <div class="reply-body">
              <div class="reply-content" style="color: #999; font-style: italic;">
                이 댓글은 삭제되었습니다.
              </div>
            </div>
          </li>
        </c:when>
        <c:otherwise>
          <!-- 정상 댓글 표시 -->
          <li data-reply-seq="${qnaCmt.qnaCmtSeq}" data-qna-seq="${qna.qnaSeq}">
            <div class="reply-header">
              <!-- 1) user + date 묶음 -->
              <div class="reply-meta">
                <span class="reply-user">${qnaCmt.adminId}</span>
                <span class="reply-date">(${qnaCmt.createDt})</span>
              </div>
              <!-- 2) controls 묶음: 수정 + 삭제 -->
              <div class="reply-controls">
                <button type="button" class="btn-edit-reply">수정</button>
                <button type="button" class="btn-delete-reply">삭제</button>
              </div>
            </div>
            <div class="reply-body">
              <div class="reply-content">${qnaCmt.qnaCmtContent}</div>
            </div>
            <!-- textarea, .reply-actions는 JS로 동적으로 삽입됩니다 -->
          </li>
        </c:otherwise>
      </c:choose>
    </c:if>
    <c:if test="${empty qnaCmt}">
      <li>등록된 댓글이 없습니다.</li>
      <form id="qnaCmtWrite" name="qnaCmtWrite" method="post" class="reply-form" enctype="multipart/form-data">
        <input type="hidden" id="qnaSeq" name="qnaSeq" value="${qna.qnaSeq}" />
        <textarea name="qnaCmtContent" id="qnaCmtContent" placeholder="댓글을 입력하세요." required></textarea>
        <button type="button" id="btnWrite" class="btn btn-primary">저장</button>
      </form>
    </c:if>
  </ul>
  <c:if test="${empty sessionScope.SESSION_USER_ID}">
    <p>댓글 작성은 로그인 후 이용 가능합니다.</p>
  </c:if>
</div>

  <a href="/qna/list" class="btn btn-primary">목록으로 돌아가기</a>
</div>


<script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/aos.js"></script>


</body>
</html>
