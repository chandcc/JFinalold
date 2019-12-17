package com.cnlive.encoding.service;

import java.util.List;

import com.cnlive.encoding.model.User;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class UserService {

	public static final UserService service = new UserService();

	/**
	 * 获取所有用户
	 * 
	 * @return
	 */
	public List<Record> getAllUsers() {
		return Db.find("select * from t_user ");
	}


	/**
	 * 分页获取所有用户列表数据
	 * 
	 * @param pageNo
	 * @return
	 */
	public Page<User> getIndexByPage(Integer pageNo) {
		return User.me.paginate(pageNo, PropKit.use("init.properties").getInt("pageSize"));
	}



	/**
	 * 根据id获取用户信息
	 * 
	 * @param id
	 * @return
	 */
	public Record getUserById(String id) {
		return Db.findById("t_user", id);
	}

	/**
	 * 根据id获取选手信息
	 * 
	 * @param id
	 * @return
	 */
	public Record getMemberById(String id) {
		return Db.findById("t_member", id);
	}


	/**
	 * 根据id删除用户
	 * 
	 * @param id
	 * @return
	 */
	public boolean delUserById(String id) {
		return Db.deleteById("t_user", id);
	}


	public List<Record> getAllManager() {
		return Db.find("select * from t_user where role=?", "manager");
	}

}
