package com.cnlive.encoding.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.druid.util.StringUtils;
import com.cnlive.encoding.model.CanHui;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;

public class CanHuiService {
	private static Log logger = LogFactory.getLog(CanHuiService.class);
	public static final CanHuiService service = new CanHuiService();
	/**
	 * 通过手机号获取用户信息
	 * @param mobile
	 * @return
	 */
	public CanHui getByMobile(String mobile) {
		 List<CanHui> list = CanHui.me.find("select * from t_canhui where mobile=?",mobile);
		 if(list!=null&&list.size()>0){
			 return list.get(0);
		 }else{
			 return null;
		 }
		 	
	}
	
	/**
	 * 获取参会列表页面
	 * @param pageNo 
	 * @param query
	 * @return
	 */
	public Page<CanHui> getPage(int pageNo, String query) {
		int pageSize = PropKit.use("init.properties").getInt("pageSize");
		if(StringUtils.isEmpty(query)){
			return	CanHui.me.paginate(pageNo, pageSize, "select * ", "from t_canhui order by id desc");
		}else{
			return CanHui.me.paginate(pageNo, pageSize, "select * ", 
					"from t_canhui where mobile like ? or userName like ? order by id desc","%"+query+"%","%"+query+"%");
		}
	}




}
