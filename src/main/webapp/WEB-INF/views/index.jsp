<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<style>
*, ::after, ::before {
  box-sizing: unset;
}
.btn_img{
  position: relative;
}
.image_text{
  position: absolute;
  left: 3.65rem;
  top:0;
  color: white;
  z-index: 1;
}
</style>
<script type="text/javascript">
$(document).ready(function() 
{
	$("#adminId").keyup(function(e) 
	{
	    if(e.which == 13) 
	    {
	    	fn_loginCheck();
	    }
	});
	
	$("#adminPwd").keyup(function(e) 
	{
	    if(e.which == 13) 
	    {
	    	fn_loginCheck();
	    }
	});
			
	$("#adminId").focus();	
});


function fn_loginCheck()
{
	if($.trim($("#adminId").val()).length <= 0)
	{
		alert("아이디를 입력하세요");
		$("#adminId").val("");
		$("#adminId").focus();
		return;
	}
	
	if($.trim($("#adminPwd").val()).length <= 0)
	{
		alert("비밀번호를 입력하세요.");
		$("#adminPwd").val("");
		$("#adminPwd").focus();
		return;
	}
	
	$.ajax({
		type:"POST",
		url:"/loginProc",
		data:{
			adminId:$("#adminId").val(),
			adminPwd:$("#adminPwd").val()
		},
		datatype:"JSON",
		beforeSend:function(xhr)
		{
			xhr.setRequestHeader("AJAX", "true");
		},
		success:function(res)
		{
			if(res.code == 0)
			{
				location.href = "/user/list";
				alert("로그인 성공");
			}
			else if(res.code == -1)
			{
				alert("비밀번호가 일치하지 않습니다.");
				$("#adminPwd").focus();
			}
			else if(res.code == 400)
			{
				alert("파라미터가 올바르지 않습니다.");
				$("#adminId").focus();
			}
			else if(res.code == 403)
			{
				alert("관리자 아이디를 사용할 수 없습니다.");
				$("#adminId").focus();
			}
			else if(res.code == 404)
			{
				alert("관리자 정보가 존재하지 않습니다.");
				$("#adminId").focus();
			}
			else
			{
				alert("로그인이 실패하였습니다.");
				$("#adminId").focus();
			}
		},
		error:function(error)
		{
			alert("오류가 발생하였습니다.");
			icia.common.error(error);
		}
	});
}

</script>
</head>
<body id="adminLogin" style="background-color: #E6EBEE;">
<div id="login" style="position: relative; top: 30%;">
	<div class="login-contents" style="width: 580px;height: 18rem; margin: auto;text-align: center;border: .1px solid #D4D6D8; border-radius:20px; background-color: #F4F5F8; box-shadow: 1px 2px #AEB3B8;">
	    <div class="user-input">
	        <form name="loginForm" id="loginForm" method="post" onsubmit="return false" style="height:100%;">
	        <div class="login_block">
	            <h2 style="margin-top: 3%; margin-bottom:3%;">관리자</h2>
	        </div>
	        <div class="input-id">
	            <label for="adminId" style="position:relative; left: .4rem; top:-.1rem; height:3rem"><img src="/resources/images/login/account_icon.png" style="height:3.4rem;"></label>
	            <input type="text" id="adminId" name="adminId" style="font-size:1rem; width: 70%; height:3rem; margin-bottom:3%;" maxlength="50" value="" title="아이디 입력" placeholder="아이디 입력" />
	        </div>
	        <div class="input-password">
	            <label for="adminPwd" style="position:relative; left: .4rem; top:-.1rem; height:3rem"><img src="/resources/images/login/password_icon.png" style="height:3.4rem;"></label>
	            <input type="password" id="adminPwd" name="adminPwd" style="font-size:1rem; width: 70%; height:3rem; margin-bottom:3%;" maxlength="50" title="비밀번호 입력" placeholder="비밀번호 입력" />
	        </div>
	        </form>
	    </div>
	    <a href="javascript:void(0)" onclick="fn_loginCheck()" class="btn_img"><img src="/resources/images/login/system_manager_login_btn.png" style="width:30%;height: 15%;"><span class="image_text">LOGIN</span></a>
	</div>
</div>
</body>
</html>