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
												<form action="${base}/system/member/1-${query.type}" method="post">
													<input type="text" name="mobile"
														class="form-control animated fadeIn"
														placeholder="选手名或者报名手机号" value="${query.mobile}">
													<c:if test="${2 ne query.type}">
													<select class="input-lg m-bot15 btn-sm" name="is_upload"
													id="is_upload" style="height: 34px;">
														<option value="" >作品状态</option>
														<option value="0" <c:if test="${query.is_upload eq 0}"> selected="selected"</c:if>>上传初始化</option>
														<option value="1" <c:if test="${query.is_upload eq 1}"> selected="selected"</c:if>>上传成功</option>
														<option value="-1" <c:if test="${query.is_upload eq -1}"> selected="selected"</c:if>>上传失败</option>
														<option value="2" <c:if test="${query.is_upload eq 2}"> selected="selected"</c:if>>未上传</option>
													</select> 
													</c:if>	
													<c:if test="${2 eq query.type}">
													<select class="input-lg m-bot15 btn-sm" name="is_upload"
													id="is_upload" style="height: 34px;">
														<option value="" >信息提交状态</option>
														<option value="1" <c:if test="${query.is_upload eq 1}"> selected="selected"</c:if>>已提交</option>
														<option value="0" <c:if test="${query.is_upload eq 0}"> selected="selected"</c:if>>未提交</option>
													</select> 
													
													</c:if>
													<input type='submit' class="input-group-addon input-focus"
														value="查询">
												</form>
											</div>


										<table
											class="table table-small-font table-bordered table-striped">
											<thead>
												<tr>
													<!-- <th>用户ID</th> -->
													<th>用户昵称</th>
													<th>电话/邮箱</th>
													<th>报名时间</th>
													<th>
													<c:if test="${query.type ne 2}">
													作品上传</c:if>
													<c:if test="${query.type eq 2}">
													信息提交状态
													</c:if>
													</th>
												</tr>
											</thead>

											<tbody>
												<c:forEach items="${page.list}" var="user">
													<tr>
														<%-- <td>${user.id}</td> --%>
														<td class="sorting_1">${user.nickName}</td>
														<td>${user.mobile}</td>
														
														<td><c:choose>
															<c:when test="${query.type eq 0}" >${user.createDate_oicc}</c:when>
															<c:when test="${query.type eq 1}" >${user.createDate_ip}</c:when>
															<c:when test="${query.type eq 2}" >${user.createDate_js}</c:when>
														</c:choose></td>
														<td>
														<c:if test="${query.type ne 2}">
															<c:forEach items="${productionList}" var="p">
																<c:if test="${user.id eq p.t_user_id}">
																<c:choose>
																	<c:when test="${p.is_upload eq 1}" >上传成功</c:when>
																	<c:when test="${p.is_upload eq -1}" >上传失败</c:when>
																	<c:when test="${p.is_upload eq 0}" >上传初始化</c:when>
																</c:choose>
																</c:if>
															</c:forEach>
															
															<c:forEach items="${noProduction}" var="n">
																<c:if test="${user.id eq n.id}">未上传</c:if>
															</c:forEach>
														</c:if>
														<c:if test="${query.type eq 2}">
															<c:set var="stop" value="1" scope="page"></c:set>
															<c:forEach items="${enrolList}" var="n">
																<c:if test="${stop ne 0}">
																	<c:if test="${user.id eq n.memberId}">已提交<c:set var="stop" value="0" scope="page"></c:set></c:if>
																</c:if>
															</c:forEach>
															<c:forEach items="${noEnrol}" var="n">
																<c:if test="${user.id eq n.id}">未提交</c:if>
															</c:forEach>
														</c:if>
															</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										<div class="row">
											<div class="col-xs-6">
												<div class="dataTables_info">共${page.getTotalRow()}条数据/每页${page.getPageSize()}条</div>
											</div>
											<div class="col-xs-6">
												<div class="dataTables_paginate paging_bootstrap"
													id="example-1_paginate1">
													<ul class="pagination">
														<!-- p -->
														<c:set var="currentPage"
															value="${page.pageNumber}" />
														<c:set var="totalPage" value="${page.totalPage}" />
														<c:set var="actionUrl" value="${base}/system/member/" />
														<c:set var="urlParas" value="-${query.type}?mobile=${param.mobile}&is_upload=${param.is_upload}" />
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



