<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>扫描结果</title>

<!-- Bootstrap -->
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/common.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/cafe/cafe.css" rel="stylesheet">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="${pageContext.request.contextPath}/resources/js/maxcdn/html5shiv.min.js"></script>
      <script src="${pageContext.request.contextPath}/resources/js/maxcdn/respond.min.js"></script>
    <![endif]-->
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script
	src="${pageContext.request.contextPath}/resources/js/jquery/jquery-2.1.3.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/platform.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
$(function() {
	var isGenomics = "${isGenomics}";
	if(isGenomics === "2"){
		$('.infoBody').css("min-height", "180px");
		$('.infoBody').css("height", "180px");
	}
	$('[data-toggle="modal"]').click();
	var top = ($(window).height() - $('.infoBody').outerHeight()) / 3;
	var left = ($(window).width()
			* (100 - $('.infoBody').outerWidth()) / 100) / 2;
	$('.infoBody').css("top", top);
	$('.infoBody').css("left", left);
	$('.infoBody').css("margin", 0);
});
	function jump() {
		var device = judgeDevice();
		if (device == "android") {
			if ("${jump}" == "true") {
				window.android.jumpActivity("my");
				alert("a");
			}
		} else {
			window.OCModel.goBackMyList();
		}
	}
</script>
</head>
<body data-toggle="modal" data-target="#departmentModal">
	<div class="modal fade" id="departmentModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog infoBody">
				<div class="infotitle"><b><c:if test="${isGenomics == 1}">未使用健客扫描</c:if><c:if test="${isGenomics == 2}">未识别出你的华大身份</c:if></b></div></br>
				<hr style="height:1px;border:none;border-top:1px solid #555555;margin-bottom: 5px; margin-bottom: 3%;margin-top: 0%; opacity: 0.4;" />
				<div class="infotext"><c:if test="${isGenomics == 1}">系统识别你未使用健客扫码，您可尝试打开健客，使用华大邮箱和邮箱密码登录APP.
			选择华大邮箱登录后，点击首页右上角的“+”扫码保报名</c:if><c:if test="${isGenomics == 2}">系统未能识别出你的华大身份，你可尝试登录时选择【华大邮箱】登录。选择华大邮箱登录，密码为你邮箱密码</c:if></div>
				<hr style="height:1px;border:none;border-top:1px solid #555555;margin-bottom: 0%;margin-top: 3%; opacity: 0.4;" />
				<div class="infoButton"><c:if test="${isGenomics == 1}"><a href="http://a.app.qq.com/o/simple.jsp?pkgname=com.bgi.genebook.dh#opened">下载/打开健客</a></c:if><c:if test="${isGenomics == 2}"><a onclick='jump()'>知道啦</a></c:if></div>
				
			</div>
		</div>
		
</body>
</html>