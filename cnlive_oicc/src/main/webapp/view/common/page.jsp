<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="include.jsp"%> 
<%
// 如下参数需要在 include 该页面的地方被赋值才能使用，以下是示例
/*  
	<c:set var="currentPage" value="${userPage.pageNumber}" />
	<c:set var="totalPage" value="${userPage.totalPage}" />
	<c:set var="actionUrl" value="/user/" />
	<c:set var="urlParas" value="" />
	
	使用 urlParas 如：
	  urlParas 也可以是问号挂参的形式，如： urlParas="?var=${var}" 
	   （urlParas="&sort=${sort}"），就会变成 url+pageNumber+  urlParas参数的形式
*/
%>
<c:if test="${urlParas == null}">
	<c:set var="urlParas" value="" />
</c:if>
<c:if test="${(totalPage > 0) && (currentPage <= totalPage)}">
	<c:set var="startPage" value="${currentPage - 4}" />
	<c:if test="${startPage < 1}" >
		<c:set var="startPage" value="1" />
	</c:if>
	<c:set var="endPage" value="${currentPage + 4}" />
	<c:if test="${endPage > totalPage}" >
		<c:set var="endPage" value="totalPage" />
	</c:if>
	
	<div class="pagination btn-sm">
		<c:if test="${currentPage <= 8}">
			<c:set var="startPage" value="1" />
		</c:if>
		
		<c:if test="${(totalPage - currentPage) < 8}">
			<c:set var="endPage" value="${totalPage}" />
		</c:if>
		
		<c:choose>
			<c:when test="${currentPage == 1}">
			<li class="prev disabled"><a href="#">← Previous</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="${actionUrl}${currentPage - 1}${urlParas}">← Previous</a></li>
			</c:otherwise>
		</c:choose>
		
		<c:if test="${currentPage > 8}">
			<li><a href="${actionUrl}${1}${urlParas}">${1}</a></li>
			<li><a href="${actionUrl}${2}${urlParas}">${2}</a></li>
			<li><span>…</span></li>
		</c:if> 
		
		<c:forEach begin="${currentPage}" end="${endPage}" var="i">
			<c:choose>
				<c:when test="${currentPage == i}">
					<li class="active"><a href="#">${i}</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="${actionUrl}${i}${urlParas}">${i}</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		
		<c:if test="${(totalPage - currentPage) >= 8}">
			<li><span>…</span></li>
			<li><a href="${actionUrl}${totalPage - 1}${urlParas}">${totalPage - 1}</a></li>
			<li><a href="${actionUrl}${totalPage}${urlParas}">${totalPage}</a></li>
		</c:if> 
		
		<c:choose>
			<c:when test="${currentPage == totalPage}">
				<li class="next disabled"><a href="#">Next → </a></li>
			</c:when>
			<c:otherwise>
				<li class="next"><a href="${actionUrl}${currentPage + 1}${urlParas}">Next → </a></li>
			</c:otherwise>
		</c:choose>
	</div>
</c:if>
