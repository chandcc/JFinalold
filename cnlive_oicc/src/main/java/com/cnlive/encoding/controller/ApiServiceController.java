package com.cnlive.encoding.controller;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.druid.util.StringUtils;
import com.cnlive.encoding.model.Member;
import com.cnlive.encoding.model.ResultBean;
import com.cnlive.encoding.service.UserLogService;
import com.cnlive.encoding.utils.Constants;
import com.cnlive.encoding.utils.HeaderUtils;
import com.cnlive.encoding.utils.UnifyPay;
import com.jfinal.aop.Clear;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * API接口 api
 * 
 */

public class ApiServiceController extends Controller {

	private Logger logger = Logger.getLogger(ApiServiceController.class);

	@Clear
	public void login() {
		render("login.jsp");
	}



	/**
	 * 统一异常处理页面
	 */
	@Clear
	public void error() {
		render("500.jsp");
	}

	/**
	 * 登录
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Clear
	public void loginValidate() {
		String userName = null;
		String userId = null;
		try {
			userName = getPara("userName");
			String userPwd = getPara("userPwd");
			String uid = getCookie("uid","oicc");
			String frmId = getPara("frmId");
			String vCode = getPara("vCode");
			String type = getPara("type");
			Map<String, Object> resMap = new HashMap<String, Object>();
			String errcode = "";
			String errorMsg="";
			Date date1 = new Date();
			long time1 = date1.getTime();
			if(StringUtils.isEmpty(vCode)){//密码登录
				 resMap = UserLogService.me.sendLoginRequest(userName, userPwd, uid, frmId);
				 errcode = String.valueOf(resMap.get("errorCode"));
				 errorMsg = String.valueOf(resMap.get("errorMessage"));
			}else{//短信快捷登录
				 resMap = UserLogService.me.sendMsgLoginRequest(userName, vCode, uid, frmId);
				 errcode = String.valueOf(resMap.get("errorCode"));
				 errorMsg = String.valueOf(resMap.get("errorMessage"));
			}
			Date date2 = new Date();
			long time2 = date2.getTime();
			logger.info("登录请求时间====="+(time2-time1));
			Map<String, String> data = (Map<String, String>) resMap.get("data");
			if ("0".equals(errcode)) {
				UserLogService.me.setUserLoginCookie(data, getResponse());
				userId = data.get("uid");
				String token = data.get("token");
				
				Record member = new Record();
				member.set("id", Integer.parseInt(userId));
				member.set("token", token);
				member.set("mobile", userName);
				member.set("encryptID", UnifyPay.encrypt(userId));
				member.set("nickName", URLDecoder.decode(data.get("nickName"), "utf-8"));
				if("0".equals(type)){
					member.set("wcbd", 1);
				}else if("1".equals(type)){
					member.set("ycip", 1);
				}else if("2".equals(type)){
					member.set("jscy", 1);
				}
				Member.me.saveOrUpdate(member,type);
				renderJson(new ResultBean(0, "登录成功", null));
			} else {
					renderJson(new ResultBean(-1, errorMsg, null));
			}
		} catch (Exception e) {
			logger.error("登录异常,用户名:" + userName, e);
			renderJson(new ResultBean(-1, "登录异常", null));
		}

	}
	
	
	/**
	 * 验证是否登录
	 */
	@Clear
	public void isLogin() {
		String uid = "";
		try {
			 uid = getCookie("cnliveUserId");
			Member member = Member.me.findByEncryptID(uid);
			if(member!=null){
				String type = getPara(0);
				if(!StringUtils.isEmpty(type)){
					String tableName="";
					if("0".equals(type)){
						tableName="wcbd";
					}else if("1".equals(type)){
						tableName="ycip";
					}else if("2".equals(type)){
						tableName="jscy";
					}
					 Object tableValue = member.get(tableName);
					if(tableValue==null||"0".equals(tableValue.toString())){
						member.set(tableName, 1);
						Record record = new Record();
						record.setColumns(member);
						Member.me.saveOrUpdate(record, type);
					}
				}
				Record record = new Record();
				//record.set("id", member.get("id"));
				record.set("nickName", member.get("nickName"));
				renderJson(new ResultBean(0, "已登录", record));
			}else{
				renderJson(new ResultBean(-1, "未登录", null));
			}
		} catch (Exception e) {
			logger.error("验证登录异常,token=:"+uid, e);
			renderJson(new ResultBean(-1, "验证登录异常", null));
		}

	}
	
	
	
	/**
	 * 登出
	 */
	@Clear
	public void outLogin() {
		try {
			UserLogService.me.delteUserLoginCookie(getResponse());
			renderJson(new ResultBean(0, "登出成功", null));
		} catch (Exception e) {
			logger.error("登出异常", e);
			renderJson(new ResultBean(-1, "登出异常", null));
		}

	}
	
	

	/**
	 * 验证注册手机号
	 */
	@Clear
	public void validateMobile() {
		HeaderUtils.crossDomain(getResponse());
		String mobile = getPara("mobile");
		try {
			if (StringUtils.isEmpty(mobile))
				mobile = String.valueOf(getSession().getAttribute("userName"));

			Map<String, Object> response = UserLogService.me.sendMobileVcode(mobile);
			String errorCode = String.valueOf(response.get("errorCode"));
			String errorMsg = String.valueOf(response.get("errorMessage"));

			if ("0".equals(errorCode)) {
				getSession().setAttribute("userName", mobile);
				getSession().setAttribute("registStep", "2");
				renderJson(new ResultBean(0, "发送验证码成功", null));
			} else {
				renderJson(new ResultBean(-1, errorMsg, null));
			}
		} catch (Exception e) {
			logger.error("验证手机号失败,mobile:" + mobile, e);
			renderJson(new ResultBean(-1, "服务异常，请稍后重试", null));
		}
	}

	/**
	 * 注册
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Clear
	public void registValidate() {
		String type = getPara("type");
		String vCode = getPara("vCode");
		String userPwd = getPara("userPwd");
		String mobile = getPara("mobile");
		String uuid = getCookie("uid","oicc");
		String frmId = getPara("frmId");
		String nickname = getPara("nickname");
		try {
			if(Constants.EMAIL_TYPE.equals(type)){
				renderJson(UserLogService.me.regByEmail(mobile, userPwd,nickname));
			}else{
				Map<String, Object> reponse = UserLogService.me.sendRegistRequest(mobile, userPwd, vCode, uuid, frmId);
				String errorCode = String.valueOf(reponse.get("errorCode"));
				String errorMsg = String.valueOf(reponse.get("errorMessage"));

				if ("0".equals(errorCode)) {
					Map<String, String> data = (Map<String, String>) reponse.get("data");
					UserLogService.me.setUserLoginCookie(data, getResponse());// 设置cookie

					// 保存会员信息
					Integer userId = Integer.parseInt(data.get("uid"));
					Record member = new Record();
					member.set("id", userId);
					member.set("nickName", nickname);
					member.set("token", data.get("token"));
					member.set("mobile", data.get("mobile"));
					Member.me.saveOrUpdate(member,null);

					getSession().setAttribute("registStep", "3");

					renderJson(new ResultBean(0, "注册成功", null));
				} else {
					renderJson(new ResultBean(-1, errorMsg, null));
				}
			}
		
		} catch (Exception e) {
			logger.error("注册失败,mobile:" + mobile, e);
			renderJson(new ResultBean(-1, "服务异常，请稍后重试", null));
		}
	}



	/**
	 * 重置密码
	 * 
	 */
	@Clear
	public void resetPwd() {
		String mobile = getPara("mobile");
		try {
			String vCode = getPara("vCode");
			String userPwd = getPara("userPwd");
			String type=getPara("type");
			if(Constants.EMAIL_TYPE.equals(type)){
				renderJson(UserLogService.me.resetPwd(userPwd, mobile));
			}else{
				if (StringUtils.isEmpty(mobile))
					mobile = String.valueOf(getSession().getAttribute("userName"));

				Map<String, String> response = UserLogService.me.resetPwd(vCode, userPwd, mobile);

				String errorCode = response.get("errorCode");
				String errorMsg = response.get("errorMessage");

				if ("0".equals(errorCode)) {
					UserLogService.me.delteUserLoginCookie(getResponse());
					renderJson(new ResultBean(0, "重置密码成功", null));

				} else {
					renderJson(new ResultBean(-1, errorMsg, null));
				}
			}

		
		} catch (Exception e) {
			logger.error("修改密码失败,mobile:" + mobile, e);
			renderJson(new ResultBean(-1, "服务异常,请稍后重试", null));
		}
	}

	/**
	 * 发送重置密码手机验证码
	 * 
	 */
	@Clear
	public void sendResetPwdVcode() {
		String mobile = getPara("mobile");
		try {
			if (StringUtils.isEmpty(mobile))
				mobile = String.valueOf(getSession().getAttribute("userName"));
			Map<String, String> response = UserLogService.me.sendResetPwdVcode(mobile);
			String errorCode = response.get("errorCode");
			String errorMsg = response.get("errorMessage");

			if ("0".equals(errorCode))
				renderJson(new ResultBean(0, "发送成功", null));
			else
				renderJson(new ResultBean(-1, errorMsg, null));

		} catch (Exception e) {
			logger.error("重置密码发送验证码失败,mobile:" + mobile, e);
			renderJson(new ResultBean(-1, "服务异常,请稍后重试", null));
		}
	}
	
	/**
	 * 发送短信快捷登录手机验证码
	 * 
	 */
	@Clear
	public void sendResetMsgLoginVcode() {
		String mobile = getPara("mobile");
		try {
			if (StringUtils.isEmpty(mobile))
			mobile = String.valueOf(getSession().getAttribute("userName"));
			Map<String, String> response = UserLogService.me.sendResetLoginMsgVcode(mobile);
			String errorCode = response.get("errorCode");
			String errorMsg = response.get("errorMessage");

			if ("0".equals(errorCode))
				renderJson(new ResultBean(0, "发送成功", null));
			else
				renderJson(new ResultBean(-1, errorMsg, null));

		} catch (Exception e) {
			logger.error("短信登录发送验证码失败,mobile:" + mobile, e);
			renderJson(new ResultBean(-1, "服务异常,请稍后重试", null));
		}
	}
	
	/**
	 * 激活邮箱，注册
	 */
	@Clear
	public void emailEffect() {
		String email = getPara("email");
		String vcode = getPara("vcode");
		try {
			if(StringUtils.isEmpty(email)||StringUtils.isEmpty(vcode)){
				//setAttr("msg","激活链接无效，请重新申请注册");
				setAttr("msg","The activation link is invalid, please re-apply for registration");
			}else{
				long date = new Date().getTime()-1000*3600*24L;
				Timestamp timestamp = new Timestamp(date);
				Record record = Db.findFirst("select * from t_email_type where email=? and vcode=? and insertDate>?",email,vcode,timestamp);
				if(record!=null){
					if(record.getInt("type")==Constants.ACTIVE){
						//setAttr("msg","您的账号已激活，请勿重复请求");
						setAttr("msg","Your account has been activated. Please do not repeat the request");
					}else{
						String uid = getCookie("uid","oicc");
						Map<String, Object> reponse = UserLogService.me.sendRegistRequest(email, record.getStr("userPwd"), null, uid, "oicc");
						String errorCode = String.valueOf(reponse.get("errorCode"));
					//	String errorMsg = String.valueOf(reponse.get("errorMessage"));
						if ("0".equals(errorCode)) {
							Map<String, String> data = (Map<String, String>) reponse.get("data");
							UserLogService.me.setUserLoginCookie(data, getResponse());// 设置cookie
							// 保存会员信息
							Integer userId = Integer.parseInt(data.get("uid"));
							Record member = new Record();
							member.set("id", userId);
							member.set("nickName", record.get("nickname"));
							member.set("token", data.get("token"));
							member.set("mobile",email);
							Member.me.saveOrUpdate(member,null);
							getSession().setAttribute("registStep", "3");
							//更新邮箱激活状态
							record.set("type", Constants.ACTIVE);
							Db.update("t_email_type",record);
							//setAttr("msg","激活成功");
							setAttr("msg","Activate the success");
						}else{
							String msg = getMsg(errorCode);
							setAttr("msg",msg);
						}
					}
				}else{
					setAttr("msg","The activation link is invalid, please re-apply for registration");
				}
				
			}
			render("emailMsg.jsp");
		} catch (Exception e) {
			renderJson(new ResultBean(-1, "Service exception, please try again later", null));
		}
	}
	
	
	/**
	 * 激活邮箱，重置密码
	 */
	@Clear
	public void emailPwd() {
		String email = getPara("email");
		String vcode = getPara("vcode");
		try {
			if(StringUtils.isEmpty(email)||StringUtils.isEmpty(vcode)){
				//setAttr("msg","该链接无效");
				setAttr("msg","The activation link is invalid, please re-apply for registration");
			}else{
				long date = new Date().getTime()-1000*3600*24L;
				Timestamp timestamp = new Timestamp(date);
				Record record = Db.findFirst("select * from t_email_pwd where email=? and vcode=? and insertDate>?",email,vcode,timestamp);
				if(record!=null){
					if(record.getInt("type")==Constants.ACTIVE){
						//setAttr("msg","您的密码已重置，请勿重复请求");
						setAttr("msg","Your password has been reset. Do not repeat the request");
					}else{
						Map<String, String> response = UserLogService.me.resetPwdByEmail(record.getStr("userPwd"), email);
						String errorCode = response.get("errorCode");
					//	String errorMsg = response.get("errorMessage");
						if ("0".equals(errorCode)) {
							UserLogService.me.delteUserLoginCookie(getResponse());
							record.set("type",Constants.ACTIVE);
							Db.update("t_email_pwd",record);
							//setAttr("msg","重置密码成功");
							setAttr("msg","Reset password success");
						}else{
							String msg = getMsg(errorCode);
							setAttr("msg",msg);
						}
					}
				}else{
					//setAttr("msg","链接无效");
					setAttr("msg","The activation link is invalid, please re-apply for registration");
				}
				
			}
			render("emailMsg.jsp");
		} catch (Exception e) {
			renderJson(new ResultBean(-1, "Service exception, please try again later", null));
		}
	}
	
	
	/**
	 * 用户云接口，通过code获取英文提示
	 * @param code
	 * @return
	 */
	public static String getMsg(String code){
		String result = "";
		switch (code) {
		//邮箱格式错误
		case "41017":
			result = "Email format error";
			break;
		//密码为6-20位字母或数字
		case "41004":
			result = "Passwords are 6-20 letters or Numbers";
			break;
		//密码不可与账号相同
		case "41051":
			result = "The password must not be the same as the account number";
			break;
		//密码不可为连续的字符串
		case "41052":
			result = "Passwords must not be consecutive strings";
			break;
		//密码不可为连续相同的字符串
		case "41053":
			result = "The password must not be the same string";
			break;
		//该邮箱未注册
		case "41054":
			result = "This mailbox is not registered";
			break;
		//用户名为手机号或邮箱
		case "41001":
			result = "The username must be the mailbox";
			break;
		//邮箱不能超过50位
		case "41003":
			result = "The mailbox must not exceed 50";
			break;
		//该邮箱已存在
		case "41007":
			result = "This mailbox already exists";
			break;
		//该昵称已存在
		case "41008":
			result = "The nickname already exists";
			break;
		default:
			result = "The activation link is invalid, please re-apply for registration";
			break;
		}
		
		return result;
	}
	

}
