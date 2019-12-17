package com.cnlive.encoding.utils;

import java.io.File;

/**
 * 常量
 * 
 * @author fan xiao chun
 * @Date 2017年1月1日
 *
 */
public class Constants {

	public static final String API_LOGINURL = "https://api.cnlive.com/open/api2/user/loginIn4server";
	public static final String API_REGISTURL = "https://api.cnlive.com/open/api2/user/register4server";
	public static final String API_SENDVCODEURL = "https://api.cnlive.com/open/api2/user/sendVerifyCodeForUnRegistered4server";
	public static final String API_UPDATEUSERINFO = "https://api.cnlive.com/open/api2/user/updateUserById4server";
	public static final String API_avatarUpdate = "https://api.cnlive.com/open/api2/user/updateUserFaceById";
	public static final String API_GETUSERINFO = "https://api.cnlive.com/open/api2/user/readUserById4server";
	public static final String API_RESETPWD = "https://api.cnlive.com/open/api2/user/updatePwdByMobile4server";
	public static final String API_RESETPWDBYEMAIL = "https://api.cnlive.com/open/api2/user/updatePwdByEmail4server";
//	public static final String API_SENDRESETPWDVCODE = "http://120.92.63.227/CnliveM2/user/sendVerifyCodeForRegistered.action";把ip的换成域名，做迁移的准备
	public static final String API_SENDRESETPWDVCODE = "http://sendresetpwdvcode.cnlive.com/CnliveM2/user/sendVerifyCodeForRegistered.action";
	public static final String API_MSGLOGINURL = "https://api.cnlive.com/open/api2/user/loginInQuickly4server";
	public static final String API_SENDRESETLOGINMSGVCODE = "https://api.cnlive.com/open/api2/user/sendVerifyCode4server";
	
	public static final String API_WX_PAY="https://api.cnlive.com/open/api2/unifypay/weixin/pay";
	
	public static final String APPID = "513_j64squ1026";

	public static final String APPKEY = "9639f69acf2f85e691ce56a57d6c9da0f14927d2037630";
	public static final String SECRET = "4b4043f838d03c6531131063bdfefe28e2d1a6fd2f72db";

	// 日志类型

	public static final String LOGIN_LOG = "login";
	public static final String REGIST_LOG = "regist";

	//作品组别
	public static final Integer CHUANDA = 1;
	public static final Integer SHANGMAO = 2;
	public static final Integer YINGYONG = 3;
	public static final Integer SHUXUE_KEXUE = 4;
	public static final Integer WUZHI = 5;
	
	//文件保存根路径
	public static final String FILE_PATH = File.separator+"data"+File.separator+"oicc"+File.separator+"file";
	public static final String FILE_PATH_TEMP = File.separator+"data"+File.separator+"oicc"+File.separator+"temp";
	
	//七牛
	public static final String QINIU_ACCESS_KEY = "DBDyvzcYa4O7gpAiwbXG2_d2xi5LoRIDVrhep6ky";
	public static final String QINIU_SECRE_KEY = "WBuZWz_O57sKDOTI3PsCYrpF1HYq_0GLkCjCKjo9";
	public static final String QINIU_BUCKET = "mam-main";
	public static final String QINIU_FILE_PATH = "513";//上传根路径
	public static final String QINIU_DOMIAN = "http://y2017.qn.cnliveimg.com";//七牛域名
	
	public static final int UPLOAD_SUCCESS = 1;//上传成功
	public static final int NOTUPLOAD = 0;//未上传
	public static final int UPLOAD_FAIL = -1;//上传失败
	
	
	
	public static final int INACTIVE = 0;//未激活
	public static final int  ACTIVE= 1;//激活注册成功
	public static final String EMAIL_TYPE = "email";
}
