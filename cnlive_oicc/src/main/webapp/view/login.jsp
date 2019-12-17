<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html class=" ">
    <head>
        <title>鄂尔多斯峰会后台管理系统-登陆</title>
        <%@ include file="./common/res.jsp"%>
    </head>
    <body class="login_page">
        <div class="login-wrapper" style="background: #010741; padding: 85px 0 70px 0;  color: #FFFFFF;">
            <div id="login" style="margin-top:0;" class="login loginpage col-lg-offset-4 col-lg-4 col-md-offset-3 col-md-6 col-sm-offset-3 col-sm-6 col-xs-offset-2 col-xs-8">
                <h1 style="text-align:center;">
                    <img src="http://yweb3.cnliveimg.com/sites/eswc/170814185008076_952.jpg" style="height: 100%;width: 100%;"/> 
                </h1>

                <form name="loginform" id="loginform" action="" method="post">
                    <p>
                        <label for="user_login">用户名<br />
                        <input name="name" id="user_login" type="text" class="input" value="" required/></label>
                    </p>
                    <p>
                        <label for="user_pass">密码<br />
                        <input type="password" name="password" id="user_pass"  class="input" value="" /></label>
                    </p>
                    <p class="submit">
                        <input type="button" name="wp-submit" id="wp-submit" class="btn btn-orange btn-block" value="登录" />
                    </p>
                </form>
                <p id="nav">
                </p>

            </div>
        </div>
        
        
        <!-- LOAD FILES AT PAGE END FOR FASTER LOADING -->


        <!-- CORE JS FRAMEWORK - START --> 
        <script src="${base}/resource/assets/js/jquery-1.11.2.min.js" type="text/javascript"></script> 
        <script src="${base}/resource/assets/js/jquery.easing.min.js" type="text/javascript"></script> 
        <script src="${base}/resource/assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script> 
        <script src="${base}/resource/assets/plugins/pace/pace.min.js" type="text/javascript"></script>  
        <script src="${base}/resource/assets/plugins/perfect-scrollbar/perfect-scrollbar.min.js" type="text/javascript"></script> 
        <script src="${base}/resource/assets/plugins/viewport/viewportchecker.js" type="text/javascript"></script>  
        <!-- CORE JS FRAMEWORK - END --> 


        <!-- OTHER SCRIPTS INCLUDED ON THIS PAGE - START --> 
        <script src="${base}/resource/assets/plugins/icheck/icheck.min.js" type="text/javascript"></script>
        <!-- OTHER SCRIPTS INCLUDED ON THIS PAGE - END --> 


        <!-- CORE TEMPLATE JS - START --> 
        <script src="${base}/resource/assets/plugins/jquery-validation/js/jquery.validate.min.js"   type="text/javascript"></script>
        <script src="${base}/resource/assets/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
        <script src="${base}/resource/assets/plugins/jquery-validation/js/jquery.validate.message_cn.js" type="text/javascript"></script>
        <script src="${base}/resource/assets/js/scripts.js" type="text/javascript"></script> 
        <!-- END CORE TEMPLATE JS - END --> 
        
        <!-- General section box modal start -->
        <div class="modal fade" id="warning-alert" tabindex="-1" role="dialog" aria-labelledby="ultraModal-Label" aria-hidden="true">
           <div class="modal-dialog animated shake">
               <div class="modal-content">
                   <div class="modal-header">
                       <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                       <h4 class="modal-title">提示</h4>
                   </div>
                   <div class="modal-body">

                   </div>
                   <div class="modal-footer">
                       <button data-dismiss="modal" class="btn btn-danger" type="button">Ok</button>
                   </div>
               </div>
           </div>
        </div>
        <!-- modal end -->
        
        <script type="text/javascript">
	         $("#loginform").validate();
	         $('#wp-submit').click(function(){
	            $.post('${base}/tologin',
	                {username:$("#user_login").val(),
	                password:$("#user_pass").val()},
	                function(data){
	                if(data.code == 0){
	                	if(data.role=="admin"||data.role=="oiccadmin"){
	                    location.href="${base}/system/production/1-0";
	                	}else if(data.role=="ipadmin"){
	                	location.href="${base}/system/production/1-1";
	                	} else if(data.role=="jsadmin"){
		                location.href="${base}/system/enrol/getEnrol/1";
		                }
	                }else if(data.code == 1){
	                	jQuery('#warning-alert').modal('show', {backdrop: 'static'});
	                	jQuery('#warning-alert .modal-body').html(data.msg);
	                }else if(data.code == 2){
	                	jQuery('#warning-alert').modal('show', {backdrop: 'static'});
	                    jQuery('#warning-alert .modal-body').html(data.msg);
	                }
	            },'json');
	
	        }); 
        </script>

        <!-- Sidebar Graph - START --> 
        <script src="${base}/resource/assets/plugins/sparkline-chart/jquery.sparkline.min.js" type="text/javascript"></script>
        <script src="${base}/resource/assets/js/chart-sparkline.js" type="text/javascript"></script>
        <!-- Sidebar Graph - END --> 
        
        
        
</html>