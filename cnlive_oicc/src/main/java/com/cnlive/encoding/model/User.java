package com.cnlive.encoding.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 *系统用户
 */
@SuppressWarnings("serial")
public class User extends Model<User> {
	public static final User me = new User();
	
	public Page<User> paginateByOperator(int pageNumber, int pageSize ,String role) {
		return paginate(pageNumber, pageSize, "select *", "from t_user where role = ? order by create_time desc",role);
	}
	
	public Page<User> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from t_user order by create_time desc");
	}
	
	
}
