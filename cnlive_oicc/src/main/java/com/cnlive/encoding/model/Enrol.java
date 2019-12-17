package com.cnlive.encoding.model;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 报名信息
 */
public class Enrol extends Model<Enrol> {

	public static final Enrol me = new Enrol();


	public Page<Enrol> getEnrolByPage(Integer pageNo, int pageSize, String query) {
		String sqlfrom = " from t_enrol ";
		
		
		if(StringUtils.isEmpty(query)){
			sqlfrom=sqlfrom +" order by insertDate desc";
			return paginate(pageNo, pageSize, "select * ", sqlfrom);
		}else{
			sqlfrom=sqlfrom +" where memberId like ? or tel like ? order by insertDate desc";
			return paginate(pageNo, pageSize, "select * ", sqlfrom,"%"+query+"%","%"+query+"%");
		}
		
		
	}

}
