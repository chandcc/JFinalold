package com.cnlive.encoding.controller;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.alibaba.druid.util.StringUtils;
import com.cnlive.encoding.model.Member;
import com.cnlive.encoding.model.ProductionSrc;
import com.cnlive.encoding.model.ResultBean;
import com.cnlive.encoding.service.ProductionService;
import com.cnlive.encoding.service.UserService;
import com.jfinal.aop.Clear;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * 参赛作品管理
 *
 *
 */
public class ProductionController extends Controller {
	private Logger logger = Logger.getLogger(ProductionController.class);
	
	public void index() {
		String mobile = getPara("mobile", "");
		String group_id = getPara("group_id");
//		String tableName="";
		String type = getPara(1);
	/*	if("0".equals(type)){
			tableName="wcbd";
		}else if("1".equals(type)){
			tableName="ycip";
		}else if("2".equals(type)){
			tableName="jscy";
		}*/
			HashMap<String, String> userMap = new HashMap<String, String>();
			setSessionAttr("query", null);
			userMap.put("mobile", mobile);
			userMap.put("group", group_id);
			userMap.put("type", type);
			setSessionAttr("query", userMap);
		//	setAttr("mList", UserService.service.getAllManager());
			Page<ProductionSrc> page = ProductionService.service.getAdminIndexByPage(getParaToInt(0, 1), mobile,  group_id,type);
			setAttr("videoPage", page);
			//setAttr("participantList", Db.find("select * from t_member where "+tableName+"=1"));
			render("production.jsp");
	}


	/**
	 * 获取用户提交作品
	 */
	@Clear
	public void getProduction(){
		String userId = getCookie("cnliveUserId");
		logger.info("加密userID====="+userId)	;
		userId = Member.me.getUserIDByEncryptID(userId);
		logger.info("查询获取userID====="+userId);
		if(!StringUtils.isEmpty(userId)){
			String type = getPara(0);
			Record production = ProductionService.service.getUpByUserId(userId,type);
			if(production!=null){
				Record record = new Record();
				record.set("title", production.get("title"));
				record.set("group_id", production.get("group_id"));
				record.set("file_path", production.get("file_path"));
				record.set("domain", production.get("domain"));
				renderJson(record);
			}else{
				renderJson(new ResultBean(-1, "未上传作品", null));
			}
		}else{
			renderJson(new ResultBean(-1, "未登录", null));
		}
		
	}
	
	
	/**
	 * 删除作品(初始化)
	 * 
	 */
	public void del(){
		try{
			String id = getPara(0);
			if(ProductionService.service.del(id)){
				renderText("0");
			}else{
				renderText("-1");
			}
		}catch(Exception e){
			renderText("-1");
		}
	}
}
