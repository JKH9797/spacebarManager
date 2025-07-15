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
$("document").ready(function(){
   
   $("a[name='userUpdate']").colorbox({
      iframe:true, 
      innerWidth:1235,
      innerHeight:470,
      scrolling:false,
      onComplete:function()
      {
         $("#colorbox").css("width", "1235px");
         $("#colorbox").css("height", "470px");
         $("#colorbox").css("border-radius", "10px");
      }      
   });
});

function fn_search()
{
	document.searchForm.curPage.value = 1;
	document.searchForm.action = "/user/list";
	document.searchForm.submit();
}

function fn_paging(curPage)
{	
	var gubun = document.searchForm.gubun.value;
	
	if(gubun != 'Y')
	{
		fn_pageInit();
	}
	
	
	document.searchForm.curPage.value = curPage;
	document.searchForm.action = "/user/list";
	document.searchForm.submit();
}

function fn_pageInit()
{
	$("#userStat option:eq(0)").prop("selected", true);
	$("#searchType option:eq(0)").prop("selected", true);
	  $("#userType option:eq(0)").prop("selected", true);
	  $("#approvStat option:eq(0)").prop("selected", true);
	$("#searchValue").val("");
	
}

//color-box에서 호출됨.
function fn_pageInit2()
{
	$("#userStat option:eq(0)").prop("selected", true);
	$("#searchType option:eq(0)").prop("selected", true);
	  $("#userType option:eq(0)").prop("selected", true);
	  $("#approvStat option:eq(0)").prop("selected", true);
	$("#searchValue").val("");
	
	fn_search();
}

function approveUser(userId, btn) {
	  if (!confirm("호스트를 승인 처리하시겠습니까?")) return;

	  $.ajax({
	    type: "POST",
	    url: "/user/approve",
	    data: { userId: userId },
	    dataType: "json",           
	    beforeSend: function(xhr) {
	      xhr.setRequestHeader("AJAX", "true");
	    },
	    success: function(res) {
	      if (res.code === 0) {
	        
	        $(btn).closest("td").text("승인");
	        alert("승인되었습니다.");
	        
	      } else {
	        alert("승인 실패: " + res.message);
	      }
	    },
	    error: function(xhr, status, error) {
	      console.error("승인 처리 에러:", status, error);
	      alert("서버 에러가 발생했습니다. 다시 시도해주세요.");
	    }
	  });
	}
	
function showPendingHosts(){
	  // 검색폼의 필터를 호스트+미승인으로 강제 설정
	 $("#userType").val("H");
	  $("#approvStat").val("N");
	  $("#searchValue").val("");
	  $("#searchType").val("");
	  $("#userStat").val("");
	  $("#curPage").val(1);
	  fn_search();
	}
</script>
</head>
<body id="school_list">
<%@ include file="/WEB-INF/views/include/admin_gnb.jsp" %>
   <div id="school_list" style="width:90%; margin:auto; margin-top:5rem;">
      <div class="mnb" style="display:flex; margin-bottom:0.8rem;">
         <h2 style="margin-right:auto; color: #525252;">회원 리스트</h2>
         <form class="d-flex" name="searchForm" id="searchForm" method="post" style="place-content: flex-end;">
         	<select name="userType" id="userType" style="font-size: 1rem; width: 6rem; height: 3rem;">
			  <option value="">사용자유형</option>
			  <option value="G" <c:if test="${userType == 'G'}">selected</c:if>>게스트</option>
			  <option value="H" <c:if test="${userType == 'H'}">selected</c:if>>호스트</option>
			</select>
            <select id="userStat" name="userStat" style="font-size: 1rem; width: 3rem; height: 3rem; margin-left:.5rem;">
               <option value="">상태</option>
               <option value="Y" <c:if test="${userStat == 'Y'}">selected</c:if>>정상</option>
               <option value="N" <c:if test="${userStat == 'N'}">selected</c:if>>정지</option>
            </select>
            <select id="searchType" name="searchType" style="font-size: 1rem; width: 5rem; height: 3rem; margin-left:.5rem; ">
               <option value="">검색타입</option>
               <option value="1" <c:if test="${searchType == '1'}">selected</c:if>>회원아이디</option>
               <option value="2" <c:if test="${searchType == '2'}">selected</c:if>>회원명</option>
               <option value="3" <c:if test="${searchType == '3'}">selected</c:if>>전화번호</option>
               <option value="4" <c:if test="${searchType == '4'}">selected</c:if>>닉네임</option>
            </select>
            <input name="searchValue" id="searchValue" class="form-control me-sm-2" style="width:15rem; margin-left:.5rem;" type="text" value="${searchValue}">
            <a class="btn my-2 my-sm-0" href="javascript:void(0)" onclick="fn_search()" style="width:3rem; margin-left:.5rem; background-color: rgb(239, 239, 239); border-color:rgb(118, 118, 118);">조회</a>
            
            <button type="button" class="btn btn-warning"
			        onclick="showPendingHosts()" style="width:5rem; margin-left:.5rem; background-color: rgb(219, 219, 219); border-color:rgb(188, 188, 188);">
			  미승인목록
			</button>
			
            <input type="hidden" name="curPage" value="${curPage}" />
            <input type="hidden" name="gubun" value="${gubun}" />
            <input type="hidden" id="approvStat" name="approvStat" value="${approvStat}" />
         </form>
      </div>
      <div class="school_list_excel">
         <table class="table table-hover" style="border:1px solid #c4c2c2;">
            <thead style="border-bottom: 1px solid #c4c2c2;">
            <tr class="table-thead-main">
               <th scope="col" style="width:15%;">아이디</th>
               <th scope="col">이름</th>
               <th scope="col">이메일</th>
               <th scope="col">전화번호</th>
               <th scope="col">닉네임</th>
               <th scope="col">상태</th>
               <th scope="col">사용자유형</th>
               <th scope="col">승인상태</th>
               <th scope="col">마일리지</th>
               <th scope="col">등록일</th>
            </tr>
            </thead>
            <tbody>
            
   <c:if test="${!empty list}">
   		<c:forEach items="${list}" var="user" varStatus="userStat">
            <tr>
                <th scope="row" class="table-thead-sub" style="border: 1px solid #c4c2c2;"><a href="/user/update?userId=${user.userId}" name="userUpdate">${user.userId}</a></th>
                <td>${user.userName}</td>
                <td>${user.email}</td>
                <td>${user.phone}</td>
                <td>${user.nickname}</td>
                <td><c:if test="${user.userStat == 'Y'}">정상</c:if> <c:if test="${user.userStat == 'N'}">정지</c:if> </td>
                <td><c:if test="${user.userType == 'G'}">게스트</c:if> <c:if test="${user.userType == 'H'}">호스트</c:if> </td>
                <td>
				  <c:choose>
				    <c:when test="${user.userType == 'H'}">
				      <c:choose>

				        <c:when test="${user.approvStat == 'N'}">
				          <button type="button"
				                  onclick="approveUser('${user.userId}', this)">
				            미승인
				          </button>
				        </c:when>

				        <c:otherwise>
				          승인
				        </c:otherwise>
				      </c:choose>
				    </c:when>

				    <c:otherwise>
				      -
				    </c:otherwise>
				  </c:choose>
				</td>
				
				<td>${user.mile}</td>
                <td>${user.joinDt}</td>
            </tr>
        </c:forEach>    
	</c:if>

	<c:if test="${empty list}">
            <tr>
                <td colspan="5">등록된 회원정보가 없습니다.</td>
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