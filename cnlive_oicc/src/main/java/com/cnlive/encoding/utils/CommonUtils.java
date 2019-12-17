package com.cnlive.encoding.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.druid.util.StringUtils;

public class CommonUtils {
	public static String createLoginCfg(String host, String user, String pwd) {
		String cfgPath = "/opt/cfg/" + host + ".cfg";
		File loginFile = new File(cfgPath);
		if (!loginFile.exists()) {
			System.out.println("file not exite:"+cfgPath);
			try {
				Thread.sleep(1000);
				BufferedWriter output = new BufferedWriter(new FileWriter(
						loginFile));
				// host 101.251.198.179
				// user video
				// pass videoFTP!@#
				output.write("host " + host + "\n" + "user " + user + "\n"
						+ "pass " + pwd);
				output.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("cfg is exits");
		}
		return cfgPath;
	}
	public static String getUUID() {
		String str = UUID.randomUUID().toString().replaceAll("-", "");
		return str; // 返回随机种结果
	}
	/* 获取服务器IP地址 */
	public static String getIp() {
		InetAddress inet = null;
		try {
			inet = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		String ip = inet.getHostAddress();
		if (ip.contains("172.16") || ip.contains("192.168")) {
			return ip;
		} else {
			String address = "";
			try {
				String command = "cmd.exe /c ipconfig";
				Process p = Runtime.getRuntime().exec(command);
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
				while ((line = br.readLine()) != null) {
					if (line.indexOf("IP Address") > 0) {
						int index = line.indexOf(":");
						index += 2;
						if (line.contains("172.16") || line.contains("192.168")) {
							address = line.substring(index);
							address = address.trim();
						}
					}
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return address;
		}
	}

	public static String getHttpUrl(HttpServletRequest request) {
		String ip = getIpAddr(request);
		if (ip.contains("172.16.") ||ip.contains("192.168.")|| ip.contains("127.0.") || ip.contains("124.193.218")) {
			// 内网
			return "lan";
		} else {
			return "wan";
		}
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 获取无数据源加密
	 * 
	 * @返回随机结果
	 * @生成随机种子(a-z,0-9)
	 * @return String
	 */
	public static String getEncryptCode() {
		String str = ""; // 种子结果
		try {
			long result;
			/**
			 * 定义种子数子
			 */
			char[] ch = {

			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 'i',
					's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8',
					'9', '0',

			};
			for (int i = 1; i < ch.length; i++) {
				result = Math.round((Math.random() * ch.length));
				int len = (int) result; // 等到种子长度
				/**
				 * 如果种子超过数组使其减1
				 */
				if (len == ch.length)
					len -= 1;
				str = str + ch[len];
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			str = e.getMessage();
		}
		return str; // 返回随机种结果
	}


	public static String second2time(int s) {
		int m = s / 60;
		int h = m / 60;
		return (h >= 10 ? (h + "") : ("0" + h)) + ":"
				+ (m % 60 >= 10 ? (m % 60 + "") : ("0" + m % 60)) + ":"
				+ (s % 60 >= 10 ? (s % 60 + "") : ("0" + s % 60));
	}


	public static String FormetFileSize(long fileS) {// 转换文件大小
		DecimalFormat df = new DecimalFormat("#");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}
	public static void startCmd(String cmd) {
		System.out.println(cmd);
		Process process = null;
		InputStream error_stream = null;
		OutputStream output_stream = null;
		BufferedReader bre = null;
		try {
			process = Runtime.getRuntime().exec(cmd);
			if (process == null) {
			} else {
				final InputStream input_stream = process.getInputStream();
				new Thread(new Runnable() {
					public void run() {
						BufferedReader bri = new BufferedReader(new InputStreamReader(input_stream));
						try {
							String line = null;
							while ((line = bri.readLine()) != null) {
								System.out.println(line);
							}
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							try {
								if (input_stream != null) {
									input_stream.close();
								}
								if (bri != null) {
									bri.close();
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}).start();
				error_stream = process.getErrorStream();
				bre = new BufferedReader(new InputStreamReader(error_stream));
				String line = null;
				output_stream = process.getOutputStream();
				while ((line = bre.readLine()) != null) {
					// buf.append(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (output_stream != null) {
					output_stream.close();
				}
				if (error_stream != null) {
					error_stream.close();
				}
				if (bre != null) {
					bre.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * @author fan xiao chun
	 * @date 2017年1月1日
	 * @param map
	 * @return
	 */
	public static String MapToStr(Map<String,String> map){
		String str="";
		if(map!=null && !map.isEmpty()){
			Set<String> keys=map.keySet();
			
			for (String key : keys) {
				str +=key+"="+map.get(key)+"&";
			}
			
			str=str.substring(0, str.lastIndexOf("&"));
		}
		return str;
	}
	
	/**
	 * 获取当前系统时间戳
	 * @author fan xiao chun
	 * @date 2017年1月1日
	 * @return
	 */
	public static Timestamp getCurrentTimestamp(){
		return new Timestamp(new Date().getTime());
	}
	
	//反转义
	public static String unEscapesHtml(String html) {
		if(!StringUtils.isEmpty(html)){
			html =  html.replaceAll("&lt;", "<");
			html =  html.replaceAll("&gt;", ">");
			html =  html.replaceAll("&#40;", "\\(");
			html =  html.replaceAll("&#41;", "\\)");
			html =  html.replaceAll("&#39;", "'");
			html =  html.replaceAll("&lt;", "<");
			html =  html.replaceAll("&lt;", "<");
			html =  html.replaceAll("&lt;", "<");
		}
		return html;
	}
	
	//转义
	public static String escapesHtml(String html) {
		if(!StringUtils.isEmpty(html)){
			html = html.replaceAll("<","&lt;");
			html = html.replaceAll(">","&gt;");
			html = html.replaceAll("\\(","&#40;");
			html = html.replaceAll("\\)","&#41;");
			html = html.replaceAll("'","&#39;");
//	        escapeCharMap.put("\"","&quot;");
//			html.replaceAll("eval\\((.*)\\","");
//			html.replaceAll("eval\\((.*)\\","");
//			html.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']","");
		}
		return html;
	}
	
	
	public static void main(String[] args) {
		startCmd("wget http://180.166.55.40/65/bf/00/58/74/97/00587497_141229100521_23.mpg -o d:/opt/cnliveWorkSpace/videoDownLoad/administrator/滕丽名晒浴照直呼“走光了”/");
	}
}
