<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>我的果汁计划</title>
<!-- Bootstrap -->
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/common.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/cafe/cafe.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/cafe/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/cafe/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/cafe/demo.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/cafe/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/cafe/jquery.easyui.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/platform.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
$(function() {
	var top = $(window).height();
	if(top < 670){
		$('.draway').css("padding-left", "8%");
		$('.draway').css("padding-left", "8%");
		$('.draway').css("font-size", "15px");
	} else if(top > 700){
		$('.centertable').css("margin", "15px 0");
	}
	if($('.easyui-calendar').height() < 200){
		$('.easyui-calendar').css("height", top / 2 + "px");
		$('.calendar-body').css("height", "85%");
	}
	
});
</script>
<script>
	var drawalDate = "${dataMap.drawalDate}";
	var notReceiveDate = "${dataMap.notReceiveDate}";
	var stayReceiveDate = "${dataMap.stayReceiveDate}";
	var dateList = drawalDate + "," + notReceiveDate + "," + stayReceiveDate;
	var startDay = "${dataMap.startDate}";
	startDay = "" != startDay ? startDay : "";
	dateList = dateList.split(",");
	var str = "";
	for (var i = 0; i < dateList.length; i++) {
		str += dateList[i]+",";
	}
	function formatDay(date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();
		var dStr = d < 10 ? '0' + d : d;
		var mStr =  m < 10 ? '0'+m : m;
		var fullDate = y.toString() +"-"+mStr+"-"+dStr;
		if (fullDate < startDay) {
			return d;
		}
		var opts = $(this).calendar('options');
		var css = "";
		for (var index = 0; index < dateList.length; index++) {
			var dateStr = dateList[index];
			var day = dateStr.substr(8, 9);
			if (opts.month != m || !dateStr || dateStr.substr(0, 4) != y
					|| dateStr.substr(5, 2) != m) {
				continue;

			}
			if (str.indexOf(fullDate) == -1) {
				css = "icon-search";
				return '<div class="'+css+' md">' +'<span style="opacity:0.3;">'+d+'</span>' + '</div>';
			}
			if(d != day){
				continue;
			}
			if (drawalDate.indexOf(dateStr) != -1) {
				css = "icon-ok";
			} else if (notReceiveDate.indexOf(dateStr) != -1) {
				css = "icon-no";
			} else if (stayReceiveDate.indexOf(dateStr) != -1) {
				css = "icon-cut";
			}
			return '<div class="'+css+' md">' + d + '</div>';
		}
		return d;
	}
</script>
<style scoped="scoped">
.md {
	height: 100%;
	line-height: 16px;
	background-position: 0px right;
	font-weight: bold;
	padding: 0 0px;
}

.fgh {
	height: auto !important;
	text-align: center;
	margin: auto;
}
.draway {
	opacity:0.5;
	height: auto !important;
	text-align: center;
	margin: auto;
	color:#000000;
	padding-left:5%;
	padding-right:5%;
	font-size: 18px;
}
.centertable {
	margin: 10px 0;
}
</style>
</head>
<body>
	<div id="imgdiv">
		<img
			src="${pageContext.request.contextPath}/resources/images/cafe/${plantype}.png"
			style="max-width: 100%;" />
	</div>
	<div style="margin: 20px 0"></div>

	<div class="easyui-calendar" style="width: 100%; height: 100%;"
		data-options="formatter:formatDay"></div>
	<div class ="centertable"></div>
	<div class="draway">已领取<span style="color:#b4df41;">${drawalCount}</span>/${juiceCount}杯，<c:if test="${nextDrawalMonth != null}">下次领取时间为${nextDrawalMonth}月${nextDrawalDay}日</c:if><c:if test="${nextDrawalMonth == null}">果汁领取计划已结束</c:if></div>
	<div class="fgh">
		<img
			src="${pageContext.request.contextPath}/resources/images/cafe/ok.png"
			style="max-width: 30%;" />&nbsp;&nbsp;<span style="opacity:0.3;">已领取状态</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img
			src="${pageContext.request.contextPath}/resources/images/cafe/cup-not.png"
			style="max-width: 30%;" />&nbsp;&nbsp;<span style="opacity:0.3;">未领取状态</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img
			src="${pageContext.request.contextPath}/resources/images/cafe/cup-will.png"
			style="max-width: 100%; max-height: 50%;" />&nbsp;&nbsp;<span style="opacity:0.3;">待领取状态</span>
	</div>

</body>
</html>