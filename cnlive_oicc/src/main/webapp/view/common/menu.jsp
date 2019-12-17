<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../common/include.jsp"%>
<!-- MAIN MENU - START -->
<div class="page-sidebar-wrapper" id="main-menu-wrapper">
	<div class="profile-info row">
		<div class="profile-image col-md-4 col-sm-4 col-xs-4">
			<a href="javaScript:void(0);"> <img
				src="${base}/resource/data/profile/profile.png"
				class="img-responsive img-circle">
			</a>
		</div>
		<div class="profile-details col-md-8 col-sm-8 col-xs-8">
			<h3>
				<a href="javaScript:void(0);">${empty userSession.name ? 'user':userSession.name}</a>
				<span class="profile-status online"></span>
			</h3>
			<p class="profile-title"></p>
		</div>
	</div>
	<!-- USER INFO - END -->
	<ul class='wraplist' id="usertablist">
		<c:if test="${userSession.role eq 'oiccadmin'}">
			<li class=""><a href="#"> <i class="fa fa-suitcase"></i> <span
					class="title">文创北斗奖</span> <span class="arrow "></span>
			</a>
				<ul class="sub-menu">
					<li class=""><a href="${base}/system/production/1-0"> <i
					class="fa fa-gift"></i> <span class="title">参赛作品管理</span> <span
					class="arrow "></span>
			</a></li>
				<li class=""><a href="${base}/system/member/1-0"> <i
					class="fa fa-gift"></i> <span class="title">参赛用户管理</span> <span
					class="arrow "></span>
			</a></li>
				</ul></li>
		</c:if>
		<c:if test="${userSession.role eq 'ipadmin'}">
				<li class=""><a href="#"> <i class="fa fa-suitcase"></i> <span
					class="title">原创IP</span> <span class="arrow "></span>
			</a>
				<ul class="sub-menu">
					<li class=""><a href="${base}/system/production/1-1"> <i
					class="fa fa-gift"></i> <span class="title">参赛作品管理</span> <span
					class="arrow "></span>
			</a></li>
				<li class=""><a href="${base}/system/member/1-1"> <i
					class="fa fa-gift"></i> <span class="title">参赛用户管理</span> <span
					class="arrow "></span>
			</a></li>
				</ul></li>
		</c:if>


		
		<c:if test="${userSession.role eq 'jsadmin'}">
		
				<li class=""><a href="#"> <i class="fa fa-suitcase"></i> <span
					class="title">角色创意</span> <span class="arrow "></span>
			</a>
				<ul class="sub-menu">
					<li class=""><a href="${base}/system/enrol/getEnrol/1"> <i
					class="fa fa-gift"></i> <span class="title">参赛报名信息管理</span> <span
					class="arrow "></span>
			</a></li>
				<li class=""><a href="${base}/system/member/1-2"> <i
					class="fa fa-gift"></i> <span class="title">参赛用户管理</span> <span
					class="arrow "></span>
			</a></li>
				</ul></li>
		</c:if>



<c:if test="${userSession.role eq 'admin'}">
	<li class=""><a href="#"> <i class="fa fa-suitcase"></i> <span
					class="title">文创北斗奖</span> <span class="arrow "></span>
			</a>
				<ul class="sub-menu">
					<li class=""><a href="${base}/system/production/1-0"> <i
					class="fa fa-gift"></i> <span class="title">参赛作品管理</span> <span
					class="arrow "></span>
			</a></li>
				<li class=""><a href="${base}/system/member/1-0"> <i
					class="fa fa-gift"></i> <span class="title">参赛用户管理</span> <span
					class="arrow "></span>
			</a></li>
				</ul></li>
				
					<li class=""><a href="#"> <i class="fa fa-suitcase"></i> <span
					class="title">原创IP</span> <span class="arrow "></span>
			</a>
				<ul class="sub-menu">
					<li class=""><a href="${base}/system/production/1-1"> <i
					class="fa fa-gift"></i> <span class="title">参赛作品管理</span> <span
					class="arrow "></span>
			</a></li>
				<li class=""><a href="${base}/system/member/1-1"> <i
					class="fa fa-gift"></i> <span class="title">参赛用户管理</span> <span
					class="arrow "></span>
			</a></li>
				</ul></li>
				
				<li class=""><a href="#"> <i class="fa fa-suitcase"></i> <span
					class="title">角色创意</span> <span class="arrow "></span>
			</a>
				<ul class="sub-menu">
					<li class=""><a href="${base}/system/enrol/getEnrol/1"> <i
					class="fa fa-gift"></i> <span class="title">参赛报名信息管理</span> <span
					class="arrow "></span>
			</a></li>
				<li class=""><a href="${base}/system/member/1-2"> <i
					class="fa fa-gift"></i> <span class="title">参赛用户管理</span> <span
					class="arrow "></span>
			</a></li>
				</ul></li>
		</c:if>

			<li class=""><a href="${base}/system/canhui/1"> <i class="fa fa-suitcase"></i> <span
					class="title">参会信息列表</span> <span class="arrow "></span>
			</a>



		<!-- user/E -->
	</ul>

</div>
<!-- MAIN MENU - END -->
<div class="project-info">

	<div class="block1">
		<div class="data">
			<span class='title'>New&nbsp;Orders</span> <span class='total'>2,345</span>
		</div>
		<div class="graph">
			<span class="sidebar_orders">...</span>
		</div>
	</div>

	<div class="block2">
		<div class="data">
			<span class='title'>访问人数</span> <span class='total'>345</span>
		</div>
		<div class="graph">
			<span class="sidebar_visitors">...</span>
		</div>
	</div>

</div>
