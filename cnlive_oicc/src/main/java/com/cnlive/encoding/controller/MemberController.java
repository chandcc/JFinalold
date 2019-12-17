package com.cnlive.encoding.controller;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;


public class MemberController extends Controller {

	private Logger logger = Logger.getLogger(MemberController.class);

	
	/**
	 * 
	 *用户信息列表
	 */
	public void index() {
		int pageNo = getParaToInt(0);
		String type = getPara(1);
		String tableName="";
		String time = "";
		if("0".equals(type)){
			tableName="wcbd";
			time="_oicc";
		}else if("1".equals(type)){
			tableName="ycip";
			time="_ip";
		}
		
		if("2".equals(type)){
			tableName="jscy";
			time="_js";
			setAttr("enrolList", Db.find("select * from t_enrol"));
			setAttr("noEnrol", Db.find("select * from  t_member where "+tableName+"=1 and  id not in (select memberId from t_enrol)"));
		}
		
		String is_upload = getPara("is_upload");
		String mobile = getPara("mobile", "");
		HashMap<String, String> userMap = new HashMap<String, String>();
		setSessionAttr("query", null);
		userMap.put("mobile", mobile);
		userMap.put("type", type);
		setAttr("productionList", Db.find("select * from t_production where type="+type));
		setAttr("noProduction", Db.find("select * from  t_member where "+tableName+"=1 and  id not in (select t_user_id from t_production where type="+type+")"));
		if(StringUtils.isEmpty(is_upload)){
			Page<Record> page = Db.paginate(pageNo, PropKit.use("init.properties").getInt("pageSize"), "select *",
					"from t_member where "+tableName+"=1 and  (mobile like ? or id like ?) order by createDate"+time+" desc", mobile + "%",mobile + "%");
			setAttr("page", page);
		}else{
			int upload = Integer.parseInt(is_upload);
			
			if("2".equals(type)){
				if(upload==1){
					Page<Record> page = Db.paginate(pageNo, PropKit.use("init.properties").getInt("pageSize"), "select *",
							"from t_member  where "+tableName+"=1 and  id  in (select memberId from t_enrol)  and (mobile like ? or id like ?) order by createDate"+time+" desc", mobile + "%",mobile + "%");
					setAttr("page", page);
				}else{
					Page<Record> page = Db.paginate(pageNo, PropKit.use("init.properties").getInt("pageSize"), "select *",
							"from t_member  where "+tableName+"=1 and  id not in (select memberId from t_enrol) and (mobile like ? or id like ?) order by createDate"+time+" desc", mobile + "%",mobile + "%");
					setAttr("page", page);
				}
				
				
			}else{
				if(upload==2){//未上传用户
					Page<Record> page = Db.paginate(pageNo, PropKit.use("init.properties").getInt("pageSize"), "select *",
							"from t_member  where "+tableName+"=1 and  id not in (select t_user_id from t_production where type="+type+")  and (mobile like ? or id like ?) order by createDate"+time+" desc", mobile + "%",mobile + "%");
					setAttr("page", page);
				}else{
					Page<Record> page = Db.paginate(pageNo, PropKit.use("init.properties").getInt("pageSize"), "select t.*",
							"from t_member t,t_production p where t."+tableName+"=1 and p.type="+type+" and t.id=p.t_user_id and  p.is_upload=?  and (t.mobile like ? or t.id like ?) order by createDate"+time+" desc", upload,mobile + "%",mobile + "%");
					setAttr("page", page);
				}
			}
			
			
				userMap.put("is_upload", is_upload);
		}
		setSessionAttr("query", userMap);
		render("member.jsp");
	}
	



}
