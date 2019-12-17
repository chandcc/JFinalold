package com.cnlive.encoding.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.druid.util.StringUtils;
import com.cnlive.encoding.utils.DateUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;


public class IndexService {
	
	public static final IndexService service = new IndexService();
	
	
	/**
	 * 登陆用户保存或修改
	 * @param id
	 * @param username
	 * @param password
	 * @param email
	 * @param company
	 * @param contacts
	 * @param description
	 * @return
	 */
	public boolean saveOrUpdate(
			String id,
			String username,
			String password,
			String email,
			String company,
			String contacts,
			String description,
			String state ) {
		boolean isTrue = false;
		if (StringUtils.isEmpty(id) || id == null) {
			Record user = new Record()
			.set("name", username)
			.set("password", password)
			.set("company", company)
			.set("contacts", contacts)
			.set("role", "user")
			.set("description", description)
			.set("create_time", DateUtil.getDay("yyyy-MM-dd HH:mm:ss"));
			System.out.println("---regist-save----------");
			isTrue = Db.save("t_user", user);
		}else {
			Record user = Db.findById("t_user", id)
					.set("name", username)
					.set("company", company)
					.set("contacts", contacts)
					.set("description", description);
			System.out.println("---regist-update----------");
			isTrue = Db.update("t_user", user);
		}
		return isTrue;
		
	}
	
	/**
	 * 获取用户昵称
	 * @param name
	 * @return
	 */
	public Record getUser(String name) {
		Record user = Db.findFirst("select * from t_user where name=?",name);
		System.out.println("user-------"+user);
		return user;
	}
	
	/**
	 * 用户是否存在
	 * @param name
	 * @return
	 */
	public boolean checkUser(String name) {
		List<Record> userRecords = Db.find("select * from t_user where name = ? ", name);
		if (userRecords.size() == 0 ) {
			return false;
		}else{
			return true;
		}
	}
	
	
	/**
	 * 用户名和密码是否正确
	 * @param name
	 * @param password
	 * @return
	 */
	public boolean checkUserAndPwd(String name,String password) {
		List<Record> userRecords = Db.find("select * from t_user where  name = ? and password = ? ", name, password);
		if (userRecords.size() == 0 ) {
			return false;
		}else{
			return true;
		}
	}
	
	
	
	/**
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public Map<String, Object> checkUser(String name, String password) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Record> userList = Db.find("select * from t_user where  name = ? and password = ?", name, password);
		if (checkUser(name)) {
			if (checkUserAndPwd(name, password)) {
				if (userList.size() != 0 ) {
					result.put("code", 0);
					result.put("msg", "登陆成功");
				}
			}else {
				result.put("code", 2);
				result.put("msg", "密码错误");
			}
		}else {
			result.put("code", 1);
			result.put("msg", "账户不存在,请联系系统管理员");
		}
		return result;
	}
	
	
	
	/**
	 * 根据id获取用户信息
	 * @param id
	 * @return
	 */
	public Record getUserById(String id) {
		return Db.findById("t_user", id);
	}
	
	/**
	 * 根据id删除用户
	 * @param id
	 * @return
	 */
	public boolean delUserById(String id) {
		return Db.deleteById("t_user", id);
	}
	
	
	
	
}
