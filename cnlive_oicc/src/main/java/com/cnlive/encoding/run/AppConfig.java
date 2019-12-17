package com.cnlive.encoding.run;

import org.apache.log4j.Logger;

import com.cnlive.encoding.interceptor.LoginInterceptor;
import com.cnlive.encoding.model.CanHui;
import com.cnlive.encoding.model.Enrol;
import com.cnlive.encoding.model.Member;
import com.cnlive.encoding.model.ProductionSrc;
import com.cnlive.encoding.model.User;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log4jLoggerFactory;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;

/**
 * API引导式配置
 */
public class AppConfig extends JFinalConfig {

	// 记录日志
	private static Logger log = Logger.getLogger(AppConfig.class);

	// 视图根目录
	// public static String baseViewPath = "/WEB-INF/view";

	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		loadPropertyFile("init.properties");
		me.setLoggerFactory(new Log4jLoggerFactory());

		me.setDevMode(PropKit.getBoolean("devMode"));
		// me.setBaseViewPath(baseViewPath);
		me.setError404View("/view/common/404.jsp");
		me.setError500View("/view/common/500.jsp");
		me.setViewType(ViewType.JSP);
		me.setEncoding("utf-8");
	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add(new AdminRoutes());
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		
		// 配置DruidPlugin数据库连接池插件
		Prop p = PropKit.use("init.properties");
		DruidPlugin dp = new DruidPlugin(p.get("jdbcUrl"), p.get("username").trim(), p.get("password").trim());
		me.add(dp);

		log.info("configPlugin 配置Druid数据库连接池大小");
		dp.set(Integer.parseInt(p.get("mysql.initialSize")), Integer.parseInt(p.get("mysql.minIdle")), Integer.parseInt(p.get("mysql.maxActive")));
		me.add(dp);

		// 配置ActiveRecord插件
		log.info("configPlugin 表扫描注册");
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		// 配置大小写不敏感
		// arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));
		arp.setShowSql(true);
		me.add(arp);
		arp.addMapping("t_user", User.class);
		arp.addMapping("t_production", ProductionSrc.class);
		arp.addMapping("t_enrol", Enrol.class);
		arp.addMapping("t_member", Member.class);
		arp.addMapping("t_canhui", CanHui.class);
	}

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		me.add(new LoginInterceptor());

	}

	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		me.add(new ContextPathHandler("base"));// 添加项目contextPath,以便在页面直接获取该值
												// ${base?if_exists}
	}

	/**
	 * run
	 */
	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
	}

}
