package com.cnlive.encoding.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.druid.util.StringUtils;
import com.cnlive.encoding.interceptor.LoginInterceptor;
import com.cnlive.encoding.model.Member;
import com.cnlive.encoding.model.ResultBean;
import com.cnlive.encoding.service.IndexService;
import com.cnlive.encoding.service.ProductionService;
import com.cnlive.encoding.utils.Constants;
import com.cnlive.encoding.utils.MD5;
import com.jfinal.aop.Clear;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.qiniu.util.Auth;

public class IndexController extends Controller {
	private static Log logger = LogFactory.getLog(IndexController.class);
	@Clear
	public void login() {
		removeSessionAttr("userSession");
		render("login.jsp");
	}
	
	public void index() {
		render("index.jsp");
	}

	@Clear
	public void tologin() {
		String name = getPara("username");
		String password = getPara("password");
		Map<String, Object> result = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(password)) {
			result = IndexService.service.checkUser(name, MD5.sign(password));
			if(Integer.parseInt(String.valueOf(result.get("code")))==0){
				Record user = IndexService.service.getUser(name);
				setSessionAttr("userSession", user);
				result.put("role", user.get("role"));
			}
			
		}
		renderJson(result);
	}




	/**
	 * 上传作品前请求数据
	 */
	@Clear(LoginInterceptor.class)
	public void uploadFile() {
		String userId = getCookie("cnliveUserId");
		userId = Member.me.getUserIDByEncryptID(userId);
		if(StringUtils.isEmpty(userId)){
			renderJson(new ResultBean(-1,"用户id为空",null));
		}else{
				String type = getPara(0);
				Record record = ProductionService.service.getUpByUserId(userId, type);
				if(record!=null){
					renderJson(new ResultBean(-1,"请勿重复上传作品",null));
					logger.info("userId===="+userId+",type===="+type+",已上传成功，重复请求。。。。。。");
				}else{
					int group = getParaToInt("group");
					String title = getPara("title");
					String fileName = getPara("fileName");
					String[] split = fileName.split("\\.");
					String suffix = split[split.length-1];
					String domian = Constants.QINIU_DOMIAN;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					long date = new Date().getTime();
					String time = sdf.format(date);
					String[] strings = time.split("-");
					String path = Constants.QINIU_FILE_PATH+"/"+strings[0]+"/"+strings[1]+strings[2]+"/"+title+"_"+String.valueOf(date).substring(time.length()-8, time.length())+"."+suffix;
					boolean success = ProductionService.service.saveVideoSrc(type,domian,path, userId,group,title,Constants.NOTUPLOAD);
					if(success){
						renderJson(new ResultBean(0,"内容创建成功",path));
					}else{
						renderJson(new ResultBean(-1,"内容保存失败",null));
					}
				}
		}
	}

	
	/**
	 * 上传作品后，上传是否成功
	 */
	@Clear(LoginInterceptor.class)
	public void isUpFile(){
		String userId = getCookie("cnliveUserId");
		userId= Member.me.getUserIDByEncryptID(userId);
		if(StringUtils.isEmpty(userId)){
			logger.info("作品更新状态，userId为空");
			renderJson(new ResultBean(-1,"userId为空",null));
		}else{
			//已上传，更新文件上传状态
			String type = getPara(0);
			Record production = ProductionService.service.getVideoByUserId(userId,type);
			if(production!=null){
				if(production.getInt("is_upload")==1){
					logger.info("userId="+userId+",用户作品已上传成功，重复上传请求。。。。。。");
					renderJson(new ResultBean(-1,"请勿重复上传作品",null));
				}else{
					int isUpload =  getParaToInt("is_upload");
					production.set("is_upload", isUpload);
					boolean isSuccess = Db.update("t_production", production);
					if(isSuccess){
						logger.info("userId="+userId+",用户作品信息状态更新成功");
						renderJson(new ResultBean(0,"状态更新成功",null));
					}else{
						logger.info("userId="+userId+",用户作品信息状态更新失败");
						renderJson(new ResultBean(-1,"状态更新失败",null));
					}
				}
				
			}else{
				logger.info("userId="+userId+",用户作品信息未创建");
				renderJson(new ResultBean(-1,"用户作品信息未创建",null));
			}
		}
		
		
	}
	
	
	/*@Clear(LoginInterceptor.class)
	public void download() {
		String id = getPara();
		Record production = ProductionService.service.getVideoByUserId(id);
		String path = production.get("file_path").toString();
		File file = new File(path);
		logger.info("下载文件，path====="+path+",存在==="+file.exists());
		renderFile(file);
	}*/
	
	
	
	@Clear(LoginInterceptor.class)
	public void getToken() {
		/*HttpServletResponse response = getResponse();
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "Requested-With");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");*/
		Map<String,String> map = new HashMap<String,String>();
		Auth auth = Auth.create(Constants.QINIU_ACCESS_KEY, Constants.QINIU_SECRE_KEY);
		String upToken = auth.uploadToken(Constants.QINIU_BUCKET);
		map.put("domian", Constants.QINIU_DOMIAN);
		map.put("token", upToken);
		renderJson(new ResultBean(0,"成功",map));
		
	}

	

}
