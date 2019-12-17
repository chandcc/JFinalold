<%@page import="com.alibaba.druid.util.StringUtils"%>
<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html class=" ">
<head>
<title>鄂尔多斯峰会后台管理系统</title>
<%@include file="../common/res.jsp"%>
<script src="${base}/resource/assets/js/jquery-1.11.2.min.js"
	type="text/javascript"></script>

<script type="text/javascript" src="${base}/resource/js/video/base64.js"></script>

<style>
.radio, .checkbox {
	position: relative;
	margin-left: 31px;
}
</style>
</head>

<body class=" ">
	<!-- START TOPBAR -->
	<div class='page-topbar '>
		<div class='logo-area'></div>
		<!-- head start -->
		<%@include file="../common/head.jsp"%>
		<!-- head end -->
	</div>
	<!-- END TOPBAR -->
	<!-- START CONTAINER -->
	<div class="page-container row-fluid">

		<!-- SIDEBAR - START -->
		<div class="page-sidebar ">
			<!-- MAIN MENU - START -->
			<!-- <c:set var="tindex" value="2"/> -->
			<%@include file="../common/menu.jsp"%>
			<!-- MAIN MENU - END -->
		</div>
		<!--  SIDEBAR - END -->

		<!-- START CONTENT -->
		<section id="main-content" class=" ">
			<section class="wrapper"
				style='margin-top: 60px; display: inline-block; width: 100%; padding: 15px 0 0 15px;'>
				<!-- data start -->
				<div class="col-lg-12">
					<section class="box ">
						<header class="panel_header">
							<ol class="breadcrumb">
								<li><a><i class="fa fa-home"></i>参赛作品管理</a></li>
								<li class="active"><a>参赛作品列表</a></li>
							</ol>
						</header>
						<div class="content-body">

							<div class="row">
								<div class="col-md-12 col-sm-12 col-xs-12">

									<div class="dataTables_wrmessageer form-inline">
										<div class="dataTables_wrapper form-inline">
											<div class="input-group">
												<form action="${base}/system/production/1-${query.type}" method="post">
													<input type="text" name="mobile"
														class="form-control animated fadeIn"
														placeholder="作品名称或手机号" value="${query.mobile}">
												<c:if test="${query.type eq 0}">	
												<select class="input-lg m-bot15 btn-sm" name="group_id"
													id="selcountry" style="height: 34px;">
													<option value="">所属领域</option>
														<option value="1" <c:if test="${query.group eq 1}"> selected="selected"</c:if>>传达领域</option>
														<option value="2" <c:if test="${query.group eq 2}"> selected="selected"</c:if>>商贸领域</option>
														<option value="3" <c:if test="${query.group eq 3}"> selected="selected"</c:if>>应用领域</option>
														<option value="4" <c:if test="${query.group eq 4}"> selected="selected"</c:if>>数学与科学领域</option>
														<option value="5" <c:if test="${query.group eq 5}"> selected="selected"</c:if>>物质领域</option>
												</select> 
												</c:if>	
													<input type='submit' class="input-group-addon input-focus"
														value="查询">

													<%-- <a href="${base}/system/production/exportVideoReport"
														class="btn btn-info btn-sm">导出报表</a> --%>
												</form>
											</div>
										</div>


										<table class="table table-bordered">
											<thead>
												<tr>
													<td><input type="checkbox" id="checkAll" /></td>
													<th>作品名称</th>
													<th>选手手机号</th>
													<!-- <th>选手ID</th> -->
													<c:if test="${query.type eq 0}"><th>所属领域</th></c:if>
													<th>上传时间</th>
													<th>作品下载</th>
													<th>操作</th>
												</tr>
											</thead>

											<tbody>
												<c:forEach items="${videoPage.list}" var="u">

													<tr class="odd">
														<td><input type="checkbox"
															id="${u.id}" /></td>
														<td>${u.title}</td>
														<td><%-- <c:forEach items="${participantList}" var="r">
																<c:if test="${r.id eq u.t_user_id}">${r.mobile}
																</c:if>
															</c:forEach> --%>
															${u.mobile}
															</td>
													<%-- 	<td>${u.t_user_id}</td> --%>
														<c:if test="${query.type eq 0}"><td><c:choose>
														<c:when test="${u.group_id eq 1}">传达领域</c:when>
														<c:when test="${u.group_id eq 2}">商贸领域</c:when>
														<c:when test="${u.group_id eq 3}">应用领域</c:when>
														<c:when test="${u.group_id eq 4}">数学与科学领域</c:when>
														<c:when test="${u.group_id eq 5}">物质领域</c:when>
														<c:otherwise></c:otherwise>
														</c:choose>
														</td></c:if>
														<td><fmt:formatDate value="${u.insert_time}"
																pattern="yyyy-MM-dd HH:mm:ss" /></td>
														<td class="dl">
															<a href="${u.domain}/${u.file_path}?attname="
															class="btn" style="padding: 3px 18px;">下载</a> </td>		
														<td class="dl">
															<a href="javaScript:del(${u.id});"
															class="btn" style="padding: 3px 18px;">状态重置</a> </td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										<div class="row">
											<div class="col-xs-6">
												<div class="dataTables_info">共${videoPage.getTotalRow()}条数据/每页${videoPage.getPageSize()}条</div>
											</div>
											<div class="col-xs-6">
												<!-- page start -->
												<div class="dataTables_paginate paging_bootstrap">
													<ul class="pagination">
														<c:set var="currentPage" value="${videoPage.pageNumber}" />
														<c:set var="totalPage" value="${videoPage.totalPage}" />
														<c:set var="actionUrl" value="${base}/system/production/" />
														
														<c:set var="urlParas"
															value="-${query.type}?mobile=${query.mobile}&group_id=${query.group}" />
														<%@include file="../common/page.jsp"%>
													</ul>
												</div>
												<!-- page start -->
											</div>
										</div>


									</div>

								</div>
							</div>
						</div>
					</section>
				</div>
				<!-- data end -->

			</section>
		</section>
		<!-- END CONTENT -->

	</div>
	<!-- END CONTAINER -->
	<!-- LOAD FILES AT PAGE END FOR FASTER LOADING -->
	<script type="text/javascript">
		function del(id){
			var msg = "您确定要重置选中内容吗？";
			if(confirm(msg) == true) {
				$.get("${base}/system/production/del/"+id,function(data){
					if(data=="0"){
						alert("作品状态已重置");
						location.reload();
					}else{
						alert("作品状态重置失败，请稍后尝试");
					}
				})
			}else{
				return false;
			}
		}
	
	</script>

	<!-- CORE JS FRAMEWORK - START -->
	<script src="${base}/resource/assets/js/jquery-1.11.2.min.js"
		type="text/javascript"></script>
	<script src="${base}/resource/assets/js/jquery.easing.min.js"
		type="text/javascript"></script>
	<script
		src="${base}/resource/assets/plugins/bootstrap/js/bootstrap.min.js"
		type="text/javascript"></script>
	<script src="${base}/resource/assets/plugins/pace/pace.min.js"
		type="text/javascript"></script>
	<script
		src="${base}/resource/assets/plugins/perfect-scrollbar/perfect-scrollbar.min.js"
		type="text/javascript"></script>
	<script
		src="${base}/resource/assets/plugins/viewport/viewportchecker.js"
		type="text/javascript"></script>
	<!-- CORE JS FRAMEWORK - END -->


	<!-- CORE TEMPLATE JS - START -->
	<script src="${base}/resource/assets/js/scripts.js"
		type="text/javascript"></script>
	<!-- END CORE TEMPLATE JS - END -->

	<!-- Sidebar Graph - START -->
	<script
		src="${base}/resource/assets/plugins/sparkline-chart/jquery.sparkline.min.js"
		type="text/javascript"></script>
	<script src="${base}/resource/assets/js/chart-sparkline.js"
		type="text/javascript"></script>
	<!-- Sidebar Graph - END -->

	<!-- OTHER SCRIPTS INCLUDED ON THIS PAGE - START -->
	<script
		src="${base}/resource/assets/plugins/messenger/js/messenger.min.js"
		type="text/javascript"></script>
	<script
		src="${base}/resource/assets/plugins/messenger/js/messenger-theme-future.js"
		type="text/javascript"></script>
	<script
		src="${base}/resource/assets/plugins/messenger/js/messenger-theme-flat.js"
		type="text/javascript"></script>
	<script src="${base}/resource/assets/js/messenger.js"
		type="text/javascript"></script>
	<!-- OTHER SCRIPTS INCLUDED ON THIS PAGE - END -->

	<!-- video checkbox start-->
	<script src="${base}/resource/assets/plugins/icheck/icheck.js"
		type="text/javascript"></script>
	<!-- video checkbox end-->




</body>
</html>