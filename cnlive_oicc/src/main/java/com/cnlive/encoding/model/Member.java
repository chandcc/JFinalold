package com.cnlive.encoding.model;

import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.cnlive.encoding.utils.CommonUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

/**
 * 保存用户token
 * @author zhouweipeng
 * 2017年8月29日
 */
public class Member extends Model<Member> {

	public static Member me = new Member();

	public boolean saveOrUpdate(Record member,String type) {
		int userId = member.getInt("id");
		Long count = Db.queryLong("select count(*) from t_member where id=?", userId);
		if (count == 0L) {
			if("0".equals(type)){
				member.set("insertDate_oicc", CommonUtils.getCurrentTimestamp());
				member.set("createDate_oicc", CommonUtils.getCurrentTimestamp());
			}else if("1".equals(type)){
				member.set("insertDate_ip", CommonUtils.getCurrentTimestamp());
				member.set("createDate_ip", CommonUtils.getCurrentTimestamp());
			}else if("2".equals(type)){
				member.set("insertDate_js", CommonUtils.getCurrentTimestamp());
				member.set("createDate_js", CommonUtils.getCurrentTimestamp());
			}
			return Db.save("t_member", member);
		} else {
			 List<Record> list = Db.find("select * from t_member where id=?",userId);
			 Record record = list.get(0);
			if("0".equals(type)){
				member.set("insertDate_oicc", CommonUtils.getCurrentTimestamp());
				if(record.get("createDate_oicc")==null)
					member.set("createDate_oicc", CommonUtils.getCurrentTimestamp());
			}else if("1".equals(type)){
				member.set("insertDate_ip", CommonUtils.getCurrentTimestamp());
				if(record.get("createDate_ip")==null)
					member.set("createDate_ip", CommonUtils.getCurrentTimestamp());
			}else if("2".equals(type)){
				member.set("insertDate_js", CommonUtils.getCurrentTimestamp());
				if(record.get("createDate_js")==null)
					member.set("createDate_js", CommonUtils.getCurrentTimestamp());
			}
		
			return Db.update("t_member", member);
		}

	}

	public Member findByEncryptID(String encryptID) {
		return Member.me.findFirst("select * from t_member where encryptID=?",encryptID);
	}
	public String getUserIDByEncryptID(String encryptID){
		if(!StringUtils.isEmpty(encryptID)){
			Member member = Member.me.findByEncryptID(encryptID);
			if(member!=null)return String.valueOf(member.getInt("id"));
		}
		return null;
	
	}

}
