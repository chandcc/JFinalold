package com.cnlive.encoding.model;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * 参赛作品
 */
@SuppressWarnings("serial")
public class ProductionSrc extends Model<ProductionSrc> {

	public static final ProductionSrc me = new ProductionSrc();
/*
	public Page<ProductionSrc> paginate(int pageNumber, int pageSize, Record user, String mobile) {
		return paginate(pageNumber, pageSize, "select * ", " from t_production,t_member t where t_user_id=t.id and region_id = ? and(t_user_id like ? or title like ?) order by t.level desc,t.id desc ",
				user.get("id"), "%" + mobile + "%", "%" + mobile + "%");
	}

	public Page<ProductionSrc> paginateByManagers(int pageNumber, int pageSize, Record user, String mobile) {
		return paginate(pageNumber, pageSize, "select v.*,t.level ", " from t_production v,t_member t where t_user_id=t.id and province_id = ? and (t_user_id like ? or title like ?) order by t.level desc,t.id desc ",
				user.get("province_id"), "%" + mobile + "%", "%" + mobile + "%");
	}*/

	public Page<ProductionSrc> paginateAdmin(int pageNumber, int pageSize, String mobile,  String group_id,String type) {
		String sql ="from t_production p,t_member m where m.id=p.t_user_id and is_upload=1 and type=? ";
		
		boolean isNull_group = StringUtils.isEmpty(group_id);
		boolean isNull_mobile = StringUtils.isEmpty(mobile);
		if("1".equals(type))group_id="0";
		if(isNull_group&&isNull_mobile){
			sql = sql+" order by id desc";
			return paginate(pageNumber, pageSize, "select p.*,m.mobile ",sql,type);
		}else if((!isNull_group)&&isNull_mobile){
			sql=sql+" and group_id=? order by id desc";
			return paginate(pageNumber, pageSize, "select p.*,m.mobile ",sql,type,group_id);
		}else if(isNull_group&&(!isNull_mobile)){
			sql=sql+" and (p.title like ? or m.mobile like ?) order by p.id desc";
			return paginate(pageNumber, pageSize, "select p.*,m.mobile ",sql,type,"%"+mobile+"%","%"+mobile+"%");
		}else{
			sql=sql+" and p.group_id=? and (p.title like ? or m.mobile like ?)  order by id desc";
			return paginate(pageNumber, pageSize, "select p.*,m.mobile ",sql,type,group_id,"%"+mobile+"%","%"+mobile+"%");
		}
	}

}
