package com.cnlive.encoding.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.cnlive.encoding.model.ResultBean;
import com.cnlive.encoding.utils.CommonUtils;
import com.cnlive.encoding.utils.Constants;
import com.cnlive.encoding.utils.HttpReq;
import com.cnlive.encoding.utils.MD5;
import com.cnlive.encoding.utils.SendMail;
import com.cnlive.encoding.utils.UnifyPay;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 用户日志service
 * 
 * @author fan xiao chun
 * @Date 2017年1月1日
 *
 */
public class UserLogService {

	public static UserLogService me = new UserLogService();

	private Logger logger = Logger.getLogger(UserLogService.class);

	/**
	 * 设置用户登录cookie
	 * 
	 */
	public void setUserLoginCookie(Map<String, String> userInfo, HttpServletResponse response) throws UnsupportedEncodingException {

		setCookieOfCnliveUserId(UnifyPay.encrypt(userInfo.get("uid")), response);
		setCookieOfCnliveUserName(URLEncoder.encode(userInfo.get("nickName"), "utf-8"), response);
		setCookieOfCnliveUserSmallFacepath(userInfo.get("faceUrl"), response);
	}

	public void setCookieOfCnliveUserId(String userId, HttpServletResponse response) {
		Cookie idcookie = new Cookie("cnliveUserId", userId);
		idcookie.setDomain(".cnlive.com");
		idcookie.setPath("/");
		idcookie.setMaxAge(3600);
		response.addCookie(idcookie);
	}

	public void setCookieOfCnliveUserSmallFacepath(String cnliveUserSmallFacepath, HttpServletResponse response) {
		Cookie smallFacepathcookie = new Cookie("cnliveUserSmallFacepath", cnliveUserSmallFacepath);
		smallFacepathcookie.setDomain(".cnlive.com");
		smallFacepathcookie.setPath("/");
		smallFacepathcookie.setMaxAge(31536000);
		response.addCookie(smallFacepathcookie);
	}

	public void setCookieOfCnliveUserName(String cnliveUserName, HttpServletResponse response) {
		Cookie namecookie = new Cookie("cnliveUserName", cnliveUserName);
		namecookie.setDomain(".cnlive.com");
		namecookie.setPath("/");
		namecookie.setMaxAge(31536000);
		response.addCookie(namecookie);
	}

	public void delteUserLoginCookie(HttpServletResponse response) {
		Cookie idcookie = new Cookie("cnliveUserId", null);
		idcookie.setDomain(".cnlive.com");
		idcookie.setPath("/");
		idcookie.setMaxAge(0);
		response.addCookie(idcookie);

		Cookie smallFacepathcookie = new Cookie("cnliveUserSmallFacepath", null);
		smallFacepathcookie.setDomain(".cnlive.com");
		smallFacepathcookie.setPath("/");
		smallFacepathcookie.setMaxAge(0);
		response.addCookie(smallFacepathcookie);

		Cookie namecookie = new Cookie("cnliveUserName", null);
		namecookie.setDomain(".cnlive.com");
		namecookie.setPath("/");
		namecookie.setMaxAge(0);
		response.addCookie(namecookie);
	}


	/**
	 * 发送登录请求(密码登录)
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> sendLoginRequest(String userName, String userPwd, String uid, String frmId) throws Exception {
		Map<String, String> requestParams = new HashMap<String, String>();

		requestParams.put("appId", Constants.APPID);
		requestParams.put("userName", userName);
		requestParams.put("pwd", userPwd);
		requestParams.put("uuid", uid);
		requestParams.put("clientPlat", "w");

		if (StringUtils.isEmpty(frmId))
			frmId = "oicc";
		requestParams.put("frmId", frmId);

		String signUrl = UnifyPay.buildURL(Constants.API_LOGINURL, requestParams, Constants.APPKEY);

		String response = HttpReq.requestUrl(signUrl, "POST", null);
		logger.info("登录请求:" + signUrl + "===响应body:" + response);
		return JSON.parseObject(response, Map.class);
	}

	/**
	 * 短信快捷登录
	 */
	public Map<String, Object> sendMsgLoginRequest(String userName, String vCode, String uid, String frmId) throws Exception {
		Map<String, String> requestParams = new HashMap<String, String>();

		requestParams.put("appId", Constants.APPID);
		requestParams.put("userName", userName);
		requestParams.put("verificationCode", vCode);
		requestParams.put("uuid", uid);
		requestParams.put("clientPlat", "w");

		if (StringUtils.isEmpty(frmId))
			frmId = "oicc";
		requestParams.put("frmId", frmId);

		String signUrl = UnifyPay.buildURL(Constants.API_MSGLOGINURL, requestParams, Constants.APPKEY);
		String response = HttpReq.requestUrl(signUrl, "POST", null);
		logger.info("登录请求:" + signUrl + "===响应body:" + response);
		return JSON.parseObject(response, Map.class);
	}
	
	
	/**
	 * 发送注册请求
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> sendRegistRequest(String mobile, String userPwd, String vcode, String uuid, String frmId) throws Exception {

		Map<String, String> requestParams = new HashMap<String, String>();

		requestParams.put("appId", Constants.APPID);
		requestParams.put("userName", mobile);
		requestParams.put("pwd", userPwd);
		requestParams.put("verificationCode", vcode);
		requestParams.put("uuid", uuid);
		requestParams.put("clientPlat", "w");
		if (!StringUtils.isEmpty(frmId))
			requestParams.put("frmId", frmId);

		String signUrl = UnifyPay.buildURL(Constants.API_REGISTURL, requestParams, Constants.APPKEY);

		String response = HttpReq.requestUrl(signUrl, "POST", null);
		logger.info("注册请求:" + signUrl + "===响应body:" + response);
		return JSON.parseObject(response, Map.class);

	}

	/**
	 * 发送手机验证码
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> sendMobileVcode(String mobile) throws Exception {
		Map<String, String> requestParams = new HashMap<String, String>();

		requestParams.put("appId", Constants.APPID);
		requestParams.put("mobile", mobile);
		requestParams.put("clientPlat", "w");

		String signUrl = UnifyPay.buildURL(Constants.API_SENDVCODEURL, requestParams, Constants.APPKEY);

		String response = HttpReq.requestUrl(signUrl, "POST", null);
		logger.info("发送验证码请求:" + signUrl + "===响应body:" + response);
		return JSON.parseObject(response, Map.class);

	}


	/**
	 * 重置密码，通过手机
	 * 
	 */
	public Map<String, String> resetPwd(String vCode, String userPwd, String mobile) throws Exception {

		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("appId", Constants.APPID);
		requestParams.put("mobile", mobile);
		requestParams.put("newPwd", userPwd);
		requestParams.put("verificationCode", vCode);
		requestParams.put("clientPlat", "w");

		String signUrl = UnifyPay.buildURL(Constants.API_RESETPWD, requestParams, Constants.APPKEY);

		String response = HttpReq.requestUrl(signUrl, "POST", null);

		logger.info("重置密码请求:" + signUrl + "===响应body:" + response);

		return JSON.parseObject(response, Map.class);
	}
	/**
	 * 重置密码，通过邮箱
	 */
	public Map<String, String> resetPwdByEmail(String userPwd, String email) throws Exception {

		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("appId", Constants.APPID);
		requestParams.put("email", email);
		requestParams.put("newPwd", userPwd);
		requestParams.put("clientPlat", "w");
		String signUrl = UnifyPay.buildURL(Constants.API_RESETPWDBYEMAIL, requestParams, Constants.APPKEY);

		String response = HttpReq.requestUrl(signUrl, "POST", null);

		logger.info("重置密码请求:" + signUrl + "===响应body:" + response);

		return JSON.parseObject(response, Map.class);
	}
	
	

	/**
	 * 修改密码发送验证码
	 * 
	 */
	public Map<String, String> sendResetPwdVcode(String mobile) throws Exception {
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("appId", Constants.APPID);
		requestParams.put("mobile", mobile);
		requestParams.put("clientPlat", "w");

		String signUrl = UnifyPay.buildURL(Constants.API_SENDRESETPWDVCODE, requestParams, Constants.APPKEY);

		String response = HttpReq.requestUrl(signUrl, "POST", null);

		logger.info("重置密码请求:" + signUrl + "===响应body:" + response);

		return JSON.parseObject(response, Map.class);

	}
	
	
	/**
	 * 短信登录发送验证码
	 */
	public Map<String, String> sendResetLoginMsgVcode(String mobile) throws Exception {
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("appId", Constants.APPID);
		requestParams.put("mobile", mobile);
		requestParams.put("clientPlat", "w");
		String signUrl = UnifyPay.buildURL(Constants.API_SENDRESETLOGINMSGVCODE, requestParams, Constants.APPKEY);
		String response = HttpReq.requestUrl(signUrl, "POST", null);

		logger.info("短信登录请求:" + signUrl + "===响应body:" + response);

		return JSON.parseObject(response, Map.class);

	}
	/**
	 * 通过邮箱注册，保存注册信息并发送激活邮件
	 * @param email
	 * @param userPwd
	 * @param nickname 
	 * @return
	 */
	public ResultBean regByEmail(String email, String userPwd, String nickname) {
		Record record = Db.findFirst("select * from t_email_type where email=?",email);
		if(record!=null&&Constants.ACTIVE==record.getInt("type")) {
			//邮箱已存在
			return new ResultBean(-1,"Mailbox already exists",null);
		}
		SendMail sendMail = new SendMail();
		String vcode = MD5.sign(email+UUID.randomUUID());
		sendMail.setTo(email);
		sendMail.setSubject("Helingeer Account registration");
/*		sendMail.setContent("尊敬的用户：<br/>您好，您已申请注册鄂市文创账户。请在24小时内点击这里激活账户："
				+ "http://oicc.cnlive.com/cnlive_oicc/api/emailEffect?email="+email+"&vcode="+vcode
				+"，如果您并未申请创建账户，请忽略此信息。</br>"
				+"<div style='float: right;'>鄂尔多斯国际文化创意大会</div>");*/
		sendMail.setContent("Dear users:<br/>Hello, you have applied for registration of the hicc accounts.lease click here to activate the account within 24 hours:"
				+ "<a href= 'http://hicc.cnlive.com/cnlive_oicc/api/emailEffect?email="+email+"&vcode="+vcode +"'/>http://hicc.cnlive.com/cnlive_oicc/api/emailPwd?email="+email+"&vcode="+vcode +"</a>，if you do not apply for the creation of the account, please ignore this information.</br>"
				+"<div style='float: right;'>2020 Inner Mongolia Helinger new area international cultural and creative conference</div>");
		boolean success = sendMail.send();
		if(success){
			if(record!=null){
				record.set("email", email);
				record.set("vcode", vcode);
				record.set("type", Constants.INACTIVE);
				record.set("userPwd", userPwd);
				record.set("nickname", nickname);
				record.set("insertDate", CommonUtils.getCurrentTimestamp());
				Db.update("t_email_type", record);
			}else{
				record = new Record();
				record.set("email", email);
				record.set("vcode", vcode);
				record.set("type", Constants.INACTIVE);
				record.set("userPwd", userPwd);
				record.set("nickname", nickname);
				record.set("insertDate", CommonUtils.getCurrentTimestamp());
				Db.save("t_email_type", record);
			}
		}else{
			//邮件发送失败，请稍等尝试
			return new ResultBean(-1,"Please wait for the email to fail",null);
		}
		//邮件发送成功，请前往激活
		return new ResultBean(0,"Email is successful, please go to activation",null);
	}

	/**
	 * 重置密码，邮箱
	 * @param userPwd
	 * @param email
	 */
	public ResultBean resetPwd(String userPwd, String email) {
		SendMail sendMail = new SendMail();
		String vcode = MD5.sign(email+UUID.randomUUID());
		sendMail.setTo(email);
		sendMail.setSubject("Helingeer Password reset");
		/*sendMail.setContent("您申请了重置密码，点击这里完成重置http://oicc.cnlive.com/cnlive_oicc/api/emailPwd?email="
				+email+"&vcode="+vcode);*/
		sendMail.setContent("Dear users:<br/>Hello, you have applied for modification of the hicc account password.Please click here to confirm the change password within 24 hours:"
				+ "<a href='http://hicc.cnlive.com/cnlive_oicc/api/emailPwd?email="+email+"&vcode="+vcode +"'>http://hicc.cnlive.com/cnlive_oicc/api/emailPwd?email="+email+"&vcode="+vcode +"</a>，if you do not apply for the password change, please ignore this information.</br>"
				+"<div style='float: right;'>2020 Inner Mongolia Helinger new area international cultural and creative conference</div>");
		boolean success = sendMail.send();
		if(success){
			Record record = Db.findFirst("select * from t_email_pwd where email=?",email);
			if(record==null){
				record = new Record();
				record.set("email", email);
				record.set("userPwd", userPwd);
				record.set("type", Constants.INACTIVE);
				record.set("vcode", vcode);
				record.set("insertDate", CommonUtils.getCurrentTimestamp());
				Db.save("t_email_pwd", record);
			}else{
				record.set("email", email);
				record.set("userPwd", userPwd);
				record.set("type", Constants.INACTIVE);
				record.set("vcode", vcode);
				record.set("insertDate", CommonUtils.getCurrentTimestamp());
				Db.update("t_email_pwd", record);
			}
		}else{
			//邮件发送失败，请稍等尝试
			return new ResultBean(-1,"Please wait for the email to fail",null);
		}
		//邮件已发送，请接收邮件并重置密码
		return new ResultBean(0,"The email has been sent. Please receive the email and reset the password",null);
	}

}
