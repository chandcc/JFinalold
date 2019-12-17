package com.cnlive.encoding.run;

import com.cnlive.encoding.controller.ApiServiceController;
import com.cnlive.encoding.controller.CanHuiController;
import com.cnlive.encoding.controller.EnrolController;
import com.cnlive.encoding.controller.IndexController;
import com.cnlive.encoding.controller.MemberController;
import com.cnlive.encoding.controller.ProductionController;
import com.cnlive.encoding.controller.UserController;
import com.jfinal.config.Routes;

public class AdminRoutes extends Routes {

	@Override
	public void config() {
		add("/", IndexController.class, "/view");
		add("/system/production", ProductionController.class, "/view/production");
		add("/system/user", UserController.class, "/view/user");
		add("/api", ApiServiceController.class, "/view/api");
		add("/system/member",MemberController.class, "/view/member");
		add("/system/enrol", EnrolController.class, "/view/enrol");
		add("/system/canhui", CanHuiController.class, "/view/canhui");
	}

}
