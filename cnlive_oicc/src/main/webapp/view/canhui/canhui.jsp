<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html class=" ">
<head>
<title>鄂尔多斯峰会后台管理系统</title>
<%@include file="../common/res.jsp"%>
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
								<li><a><i class="fa fa-home"></i>用户管理</a></li>
								<li class="active"><a>用户列表</a></li>
							</ol>
						</header>
						<div class="content-body">
							<div class="row">
								<div class="col-md-12 col-sm-12 col-xs-12">
									<div class="dataTables_wrapper form-inline">
											<div class="input-group">
												<form action="${base}/system/canhui/1" method="post">
													<input type="text" name="query"
														class="form-control animated fadeIn"
														placeholder="姓名或者手机号" value="${query}">
													<input type='submit' class="input-group-addon input-focus"
														value="查询">
												</form>
											</div>


										<table
											class="table table-small-font table-bordered table-striped">
											<thead>
												<tr>
													<th>姓名</th>
													<th>电话</th>
													<th>邮箱</th>
												</tr>
											</thead>

											<tbody>
												<c:forEach items="${chPage.list}" var="user">
													<tr>
														<td class="sorting_1">${user.userName}</td>
														<td>${user.mobile}</td>
														<td>${user.email}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										<div class="row">
											<div class="col-xs-6">
												<div class="dataTables_info">共${chPage.getTotalRow()}条数据/每页${chPage.getPageSize()}条</div>
											</div>
											<div class="col-xs-6">
												<div class="dataTables_paginate paging_bootstrap"
													id="example-1_paginate1">
													<ul class="pagination">
														<!-- p -->
														<c:set var="currentPage"
															value="${chPage.pageNumber}" />
														<c:set var="totalPage" value="${chPage.totalPage}" />
														<c:set var="actionUrl" value="${base}/system/canhui/" />
														<c:set var="urlParas" value="?query=${query}" />
														<%@include file="../common/page.jsp"%>
													</ul>
												</div>
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
	<script type="text/javascript"
		src="${base}/resource/js/video/base64.js"></script>
	<script type="text/javascript"
		src="${base}/resource/js/video/webThunderDetect.js"></script>
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


</body>
</html>



