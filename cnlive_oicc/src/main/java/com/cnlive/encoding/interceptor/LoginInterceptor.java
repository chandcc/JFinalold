package com.cnlive.encoding.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

public class LoginInterceptor implements Interceptor {


	public void intercept(Invocation ai) {
		Controller controller = ai.getController();
		Record user = null;
		try {
			 user = controller.getSessionAttr("userSession");
		} catch (Exception e) {
			e.printStackTrace();
		}
        if(user != null ) {
        	System.out.println("==================LoginInterceptor-userSession-start===============");
        	System.out.println(user.toJson());
        	System.out.println("==================LoginInterceptor-userSession-end=================");
        	
        	System.out.println("==================LoginInterceptor：已登录==========================");
        	ai.invoke();
        } else {
        	controller.redirect("/login");
        	System.out.println("==================LoginInterceptor：未登录，跳转登陆页面==============");
        }
    }

}
