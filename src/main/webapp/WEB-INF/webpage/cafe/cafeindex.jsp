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
<title><c:if test="${isPlanStartDate == true}">我要报名</c:if><c:if test="${isPlanStartDate == false}">缴费成功</c:if></title>

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
	var device = judgeDevice();
	if("${isSignup}" === "true") {
		initShowDialogWidthHeight();
		showWindow();
	} else{
		var alreadyregistered = document.getElementById("alreadyregistered");
		alreadyregistered.setAttribute("class", "issignup display");
	}
	var parent = document.getElementById("signup");
	parent.removeAttribute("data-toggle");
	parent.removeAttribute("data-target");

	
});

function initShowDialogWidthHeight(){
	var currentHeight = ($(window).height() - $('.infoBodysignup').outerHeight());
	var top = currentHeight / 3;
	var left = ($(window).width()
			* (100 - $('.infoBodysignup').outerWidth()) / 100) / 2;
	if("${isPlanStartDate}" === "false"){
		top = currentHeight / 2;
		$('.infoBodysignup').css("min-height", "165px");
		$('.infoBodysignup').css("height", "165px");
	}
	$('.infoBodysignup').css("top", top);
	$('.infoBodysignup').css("left", left);
	$('.infoBodysignup').css("margin", 0);
	
}
function closeWindow(){
	$('#departmentModal').modal('hide');
}
function showWindow(){
	$('[data-toggle="modal"]').click();
}
function signup(){
	$.ajax({
		url : '${pageContext.request.contextPath}/cafeLogin/signup',
		type : 'get',
		dataType : 'json',
		async : false,
		success : function(data) {
			var parent = document.getElementById("signuping");
			if(data === true){
				initShowDialogWidthHeight();
				var signup = document.getElementById("signup");
				signup.removeChild(parent);
				var alreadyregistered = document.getElementById("alreadyregistered");
				alreadyregistered.setAttribute("class", "issignup");
			}else{
				parent.removeAttribute("data-toggle");
				parent.removeAttribute("data-target");
			}
			
			
		},
		error : function(data) {

		}
	});
}
</script>
</head>
<body id='signup' data-toggle="modal" data-target="#departmentModal">
	<div id="imgdiv"><img src="${pageContext.request.contextPath}/resources/images/cafe/juice.jpg" style="max-width:100%;"/></div>
	<c:if test="${isSignup == false}">
		<div id="signuping" class="login" onclick="signup()" data-toggle="modal" data-target="#departmentModal">立即报名</div>
	</c:if>
		<div id="alreadyregistered" class="issignup"><c:if test="${isPlanStartDate == false}">缴费成功</c:if><c:if test="${isPlanStartDate == true}">已报名</c:if></div>
	<div class="modal fade" id="departmentModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog infoBodysignup">
				<div class="infotitle"><c:if test="${isPlanStartDate == false}">缴费成功</c:if><c:if test="${isPlanStartDate == true}">报名成功</c:if></div></br>
				<hr style="height:1px;border:none;border-top:1px solid #555555; margin-bottom: 2%;margin-top: 1%; opacity: 0.4;" />
				<div class="infotextsignup">
					<c:if test="${isPlanStartDate == true}">
					待缴费用：${money}&nbsp;&nbsp;<span class='original'>原价${original}元</span></br>
					缴费地点：二办8楼咖啡厅</br>
					支付方式：工卡/微信
					</c:if>
					<c:if test="${isPlanStartDate == false}">
					请前往咖啡厅确认计划开始时间
					</c:if>
				</div>
				<div class="infotextsignupdedail">
					缴费成功，即可在“我的”里面查看果汁干预计划。维生素检测将由咖啡厅工作人员安排。
				</div>
				<hr style="height:1px;border:none;border-top:1px solid #555555; margin-bottom: -1%;margin-top: 2%; opacity: 0.4;" />
				<div class="infoButton" onclick="closeWindow()"><a>知道啦</a></div>
				
			</div>
	</div>
</body>
</html>