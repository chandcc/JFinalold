package com.cnlive.encoding.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cnlive.encoding.model.Enrol;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class EnrolService {
	private static Log logger = LogFactory.getLog(EnrolService.class);
	public static final EnrolService service = new EnrolService();

	public Page<Enrol> getEnrolByPage(Integer pageNo, String query) {
		logger.info("报名信息page查询pageNo="+pageNo+",query="+query);
		Page<Enrol> enrolPage = null;
		int pageSize = PropKit.use("init.properties").getInt("pageSize");
		enrolPage = Enrol.me.getEnrolByPage(pageNo, pageSize, query);
		return enrolPage;
	}

	public Enrol getOneByUserId(String userId) {
		logger.info("报名信息单条查询userId="+userId);
		return Enrol.me.findFirst("select * from t_enrol where memberId=? and is_team=0", userId);
	}

	public Enrol getOneByTel(String tel) {
		return Enrol.me.findFirst("select * from t_enrol where tel=?",tel);
	}

	public boolean isUpTeam(String userId) {
		Enrol enrol = Enrol.me.findFirst("select * from t_enrol where memberId=? and is_team=1",userId);
		if(enrol!=null)return true;
		return false;
	}



}
