package com.cnlive.encoding.controller;

import org.apache.log4j.Logger;

import com.alibaba.druid.util.StringUtils;
import com.cnlive.encoding.model.CanHui;
import com.cnlive.encoding.model.ResultBean;
import com.cnlive.encoding.service.CanHuiService;
import com.jfinal.aop.Clear;

/**
 * 参会报名
 * @author zhouweipeng
 * 2017年9月14日
 */
public class CanHuiController extends Controller {
	
	private Logger logger = Logger.getLogger(CanHuiController.class);
	
	/**
	 * 参会页面
	 */
	public void index(){
		int pageNo = getParaToInt(0,1);
		String query = getPara("query");
		setAttr("chPage", CanHuiService.service.getPage(pageNo,query));
		setSessionAttr("query", null);
		setSessionAttr("query", query);	
		render("canhui.jsp");
	}
	
	
	
	/**
	 * 参会报名申请
	 */
	@Clear
	public void apply(){
		String mobile = getPara("mobile");
		String email = getPara("email");
		String userName = getPara("userName");
		if(StringUtils.isEmpty(mobile)){
			logger.info("参会报名，手机号为空");
			renderJson(new ResultBean(-1,"手机号为空",null));
		}else if(StringUtils.isEmpty(userName)){
			logger.info("参会报名，用户名为空");
			renderJson(new ResultBean(-1,"用户名为空",null));
		}else{
			CanHui ch = CanHuiService.service.getByMobile(mobile);
			if(ch!=null){
				logger.info("参会报名，重复报名，手机号==="+mobile);
				renderJson(new ResultBean(-1,"您已报名，请勿重复提交信息",null));
			}else{
				CanHui canHui = new CanHui();
				canHui.set("mobile", mobile);
				canHui.set("email", email);
				canHui.set("userName", userName);
				boolean isSave = canHui.save();
				if(isSave){
					renderJson(new ResultBean(0,"报名成功！",null));
				}else{
					renderJson(new ResultBean(-1,"提交失败，请稍后尝试",null));
				}
			}
		}
	}
}
