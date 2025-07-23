<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%
   // GNB 번호 (사용자관리)
   request.setAttribute("_gnbNo", 1);
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<style>
*, ::after, ::before {
   box-sizing: unset;
}
.table-hover th, td{
   border: 1px solid #c4c2c2;
   text-align: center;
}
</style>
<script type="text/javascript">
function fn_search() 
{
    document.getElementById('qnaForm').submit();
}


</script>
</head>
<body id="school_list">
<%@ include file="/WEB-INF/views/include/admin_gnb.jsp" %>
   <div id="school_list" style="width:90%; margin:auto; margin-top:5rem;">
      <div class="mnb" style="display:flex; margin-bottom:0.8rem;">
         <h2 style="margin-right:auto; color: #525252;">Q&A 리스트</h2>
         <form class="d-flex" name="qnaForm" id="qnaForm" method="post" style="place-content: flex-end;">
            <select id="qnaStat" name="qnaStat" style="font-size: 1rem; width: 10rem; height: 3rem; margin-left:.5rem;">
               <option value="">Q&A 상태</option>
               <option value="Y" <c:if test="${qnaStat eq 'Y'}">selected</c:if>>답글 완료/대기중</option>
               <option value="N" <c:if test="${qnaStat eq 'N'}">selected</c:if>>Q&A 삭제</option>
            </select>
            <input name="searchValue" id="searchValue" class="form-control me-sm-2" style="width:15rem; margin-left:.5rem;" type="text" value="${searchValue}">
            <a class="btn my-2 my-sm-0" href="javascript:void(0)" onclick="fn_search()" style="width:3rem; margin-left:.5rem; background-color: rgb(239, 239, 239); border-color:rgb(118, 118, 118);">조회</a>
			
            <input type="hidden" name="curPage" value="${curPage}" />
            <input type="hidden" name="qnaStat" value="${qnaStat}" /> 
            <input type="hidden" name="searchValue" value="${searchValue}" />      
         </form>
      </div>
      <div class="school_list_excel">
         <table class="table table-hover" style="border:1px solid #c4c2c2;">
            <thead style="border-bottom: 1px solid #c4c2c2;">
            <tr class="table-thead-main">
               <th scope="col">번호</th>
               <th scope="col">제목</th>
               <th scope="col">작성자</th>
               <th scope="col">등록일</th>
               <th scope="col">답글상태</th>
               <th scope="col">답글공개여부</th>
            </tr>
            </thead>
            <tbody>
            
   <c:if test="${!empty list}">
   		<c:forEach items="${list}" var="qna" varStatus="status">
            <tr>
                <td>${qna.qnaSeq}</td>
                <td><a href="/qna/detail?qnaSeq=${qna.qnaSeq}">${qna.qnaTitle}</a></td>
				<td>${qna.userId}</td>
				<td>${qna.regDt}</td>
          		<td>
		         <c:choose>
		    	    <c:when test="${qna.qnaStat eq 'N'}">질문 삭제</c:when>
		         	<c:when test="${not empty qna.qnaCmt and qna.qnaCmt.qnaCmtSeq > 0}">답변 완료</c:when>
		         	<c:otherwise>답변 대기중</c:otherwise>
		         </c:choose>
          		</td>
          		<td>
          		<c:choose>
		    	    <c:when test="${not empty qna.qnaCmt and qna.qnaCmt.qnaCmtStat eq 'Y'}">답변 공개</c:when>
		    	    <c:when test="${not empty qna.qnaCmt and qna.qnaCmt.qnaCmtStat eq 'N'}">답변 삭제</c:when>
		         	<c:otherwise>답글 대기중</c:otherwise>
		         </c:choose>
          		</td>
            </tr>
        </c:forEach>    
	</c:if>

	<c:if test="${empty list}">
            <tr>
                <td colspan="6">등록된 게시물이 없습니다.</td>
            </tr>   
	</c:if>
            </tbody>
         </table>
         <div class="paging-right" style="float:right;">
            <!-- 페이징 샘플 시작 -->
			<c:if test="${!empty paging}">
               <!--  이전 블럭 시작 -->
			   <c:if test="${paging.prevBlockPage gt 0}">
                  <a href="javascript:void(0)"  class="btn2 btn-primary" onclick="fn_paging(${paging.prevBlockPage})"  title="이전 블럭">&laquo;</a>
			   </c:if>
               <!--  이전 블럭 종료 -->
               <span>
               <!-- 페이지 시작 -->
			   <c:forEach var="i" begin="${paging.startPage}" end="${paging.endPage}">
			   		<c:choose>
			   			<c:when test="${i ne curPage}">
            	            <a href="javascript:void(0)" class="btn2 btn-primary" onclick="fn_paging(${i})" style="font-size:14px;">${i}</a>
						</c:when>
						<c:otherwise>
            	            <h class="btn2 btn-primary" style="font-size:14px; font-weight:bold;">${i}</h>
            	        </c:otherwise>
                   </c:choose>     
			   </c:forEach>
               <!-- 페이지 종료 -->
               </span>
               <!--  다음 블럭 시작 -->
			   <c:if test="${paging.nextBlockPage gt 0}">
                  <a href="javascript:void(0)" class="btn2 btn-primary" onclick="fn_paging(${paging.nextBlockPage})" title="다음 블럭">&raquo;</a>
			   </c:if>
               <!--  다음 블럭 종료 -->
			</c:if>
            <!-- 페이징 샘플 종료 -->
         </div>
      </div>
   </div>
</body>
</html>