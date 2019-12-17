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
								<li><a><i class="fa fa-home"></i>报名信息管理</a></li>
								<li class="active"><a>报名信息列表</a></li>
							</ol>
						</header>
						<div class="content-body">

							<div class="row">
								<div class="col-md-12 col-sm-12 col-xs-12">

									<div class="dataTables_wrmessageer form-inline">
										<div class="dataTables_wrapper form-inline">
											<div class="input-group">
												<form action="${base}/system/enrol/getEnrol/1" method="post">
													<input type="text" name="query"
														class="form-control animated fadeIn"
														placeholder="选手ID或手机号" value="${query}">
														
													<input type='submit' class="input-group-addon input-focus"
														value="查询">
												</form>
											</div>
										</div>
										<table class="table table-bordered">
											<thead>
												<tr>
												<!-- 	<th>上传用户ID</th> -->
													<th>姓名</th>
													<th>手机号</th>
													<th>年龄</th>
													<th>公司</th>
													<th>性别</th>
													<th>身份证号</th>
													<th>提交信息时间</th>
												</tr>
											</thead>

											<tbody>
												<c:forEach items="${enrolPage.list}" var="u">
													<tr class="odd">
														<%-- <td style="width:10%">${u.memberId}</td> --%>
														<td style="width:10%">${u.username}</td>
														<td style="width:10%">${u.tel}</td>
														<td style="width:8%">${u.age}</td>
														<td style="width:24%;word-break:break-all">${u.company}</td>
														<td style="width:8%"> 
														<c:if test="${0 eq u.sex}">男</c:if>
														<c:if test="${1 eq u.sex}">女</c:if>
														</td>
														<td style="width:18%">${u.IdNumber}</td>
														<td style="width:22%"><fmt:formatDate value="${u.insertDate}"
																pattern="yyyy-MM-dd HH:mm:ss" /></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										<div class="row">
											<div class="col-xs-6">
												<div class="dataTables_info">共${enrolPage.getTotalRow()}条数据/每页${enrolPage.getPageSize()}条</div>
											</div>
											<div class="col-xs-6">
												<!-- page start -->
												<div class="dataTables_paginate paging_bootstrap">
													<ul class="pagination">
														<c:set var="currentPage" value="${enrolPage.pageNumber}" />
														<c:set var="totalPage" value="${enrolPage.totalPage}" />
														<c:set var="actionUrl" value="${base}/system/enrol/getEnrol/" />
														
														<c:set var="urlParas"
															value="?query=${query}" />
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