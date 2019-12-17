<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../common/include.jsp"%>
    <!-- TOPBAR - START -->
                <div class='quick-area'>
                <div class='pull-left'>
                    <ul class="info-menu left-links list-inline list-unstyled">
                        <li class="sidebar-toggle-wrap">
                            <a href="#" data-toggle="sidebar" class="sidebar_toggle">
                                <i class="fa fa-bars"></i>
                            </a>
                        </li>
                    </ul>
                </div>      
                <div class='pull-right'>
                    <ul class="info-menu right-links list-inline list-unstyled">
                        <li class="profile">
                            <a href="#" data-toggle="dropdown" class="toggle">
                                <img src="${base}/resource/data/profile/profile.png" alt="user-image" class="img-circle img-inline">
                                <span>${empty userSession.name ? 'user':userSession.name}<i class="fa fa-angle-down"></i></span>
                            </a>
                            <ul class="dropdown-menu profile animated fadeIn">
                                <li>
                                    <a href="${base}/login">
                                        <i class="fa fa-lock"></i>
                                                                                注销
                                    </a>
                                </li>
                            </ul>
                        </li>
                        <li >
                            
                        </li>
                    </ul>           
                </div>      
            </div>
            <!--  TOPBAR - END -->

