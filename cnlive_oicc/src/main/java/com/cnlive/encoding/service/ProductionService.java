package com.cnlive.encoding.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cnlive.encoding.model.ProductionSrc;
import com.cnlive.encoding.utils.CommonUtils;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class ProductionService {
	private static Log logger = LogFactory.getLog(ProductionService.class);
	public static final ProductionService service = new ProductionService();

	public Page<ProductionSrc> getAdminIndexByPage(Integer pageNo, String mobile, String group_id,String type) {
		Page<ProductionSrc> videoPage = null;
		int pageSize = PropKit.use("init.properties").getInt("pageSize");
		videoPage = ProductionSrc.me.paginateAdmin(pageNo, pageSize, mobile, group_id,type);
		return videoPage;
	}





	/**
	 * 单个作品
	 * 
	 * @param userId
	 * @return
	 */
	public Record getVideoByUserId(String userId,String type) {
		return Db.findFirst("select * from t_production where t_user_id = ? and type=?", userId,type);
	}

	/**
	 * 获取上传成功作品
	 */
	public Record getUpByUserId(String userId,String type) {
		return Db.findFirst("select * from t_production where t_user_id = ? and type=? and is_upload=1", userId,type);
	}

	/**
	 * 根据id查询单条作品
	 * 
	 * @param id
	 * @return
	 */
	public Record getVideoById(String id) {
		return Db.findById("t_production", id);
	}

	/**
	 * 前台 获取个人所有参赛作品
	 * 
	 * @param user
	 * @return
	 */
	public List<Record> getMyWorksByUid(int userId) {
		return Db.find("select * from t_production where t_user_id = ? order by create_time desc ", userId);
	}



	/**
	 * 判断是否已上传作品
	 * 
	 */
	public boolean hasUploadFile(int userId) {

		return Db.queryNumber("select count(1) from t_production where t_user_id=?", userId).intValue() == 0 ? false : true;
	}

	/**
	 * 保存作品信息
	 * @param type 报名方式
	 * @param domian 域名
	 * @param path 路径
	 * @param userId 用户id
	 * @param group 所属领域
	 * @param title 作品标题
	 * @param isUpload 文件是否已上传
	 * @return
	 */
	
	public boolean saveVideoSrc(String type,String domian,String path, String userId, int group, String title, int isUpload) {
		boolean isSuccess = false;
		try {
			Record videoSrc = new Record();
			Record production = getVideoByUserId(String.valueOf(userId),type);
			if(production!=null){
				videoSrc=production;
			}else{
				videoSrc.set("create_time", CommonUtils.getCurrentTimestamp());
			}
			videoSrc.set("insert_time", CommonUtils.getCurrentTimestamp());
			videoSrc.set("t_user_id", userId);
			videoSrc.set("title", title);
			videoSrc.set("group_id", group);
			videoSrc.set("is_upload", isUpload);
			videoSrc.set("file_path", path);
			videoSrc.set("domain", domian);
			videoSrc.set("type", type);
			if(production!=null){
				isSuccess = Db.update("t_production", videoSrc);
			}else{
				isSuccess = Db.save("t_production", videoSrc);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("保存作品文件是否成功：" + isSuccess);
		return isSuccess;
	}




	/**
	 * 作品状态初始化
	 * @param id
	 * @return
	 */
	public boolean del(String id) {
		try{
			Record production = getVideoById(id);
			production.set("is_upload", 0);
			return Db.update("t_production", production);
		}catch(Exception e){
			return false;
		}
		
	}

}
