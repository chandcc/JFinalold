package com.cnlive.encoding.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

public class UnifyPay {

	/**
	 * 验证签名
	 * 用于服务端
	 * @param request
	 * @param sp_key
	 * @return
	 */
	public static boolean validateSign(ServletRequest request,String sp_key){
		Map<String,String> map = getRequestParam(request);
		String sign = map.get("sign");
		map.remove("sign");
		map = order(map);
		String str = mapJoin(map, true, false);
		String mySign = SHA1.SHA1Digest(str+"&key="+sp_key).toUpperCase();
		return mySign.equals(sign);
	}

	/**
	 * 生成URL
	 * @param url
	 * @param params
	 * @param sp_key
	 * @return
	 */
	public static String buildURL(String url,Map<String,String> params,String sp_key){
		Map<String,String> map = order(params);
		if(map.containsKey("sign")){
			map.remove("sign");
		}
		String str = mapJoin(map,true,false);
		String sign = SHA1.SHA1Digest(str+"&key="+sp_key).toUpperCase();
		map.put("sign", sign);
		return url+"?"+mapJoin(map, false, true);
	}




	/**
	 * 获取请求参数
	 * @param request
	 * @return
	 */
	public static Map<String,String> getRequestParam(ServletRequest request){
		Map<String, String> map = new LinkedHashMap<String, String>();
		Map<String, String[]> map2 = request.getParameterMap();
		for(String key : map2.keySet()){
			if(map2.get(key)==null||map2.get(key).length==0){
				map.put(key,null);
			}else{
				try {
					String value = URLDecoder.decode(map2.get(key)[0], "utf-8");
					map.put(key,value);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}

	/**
	 * 获取附加参数
	 * @param servletRequest
	 * @param attachKey 返回的map key是否以 attach. 开头
	 * @return
	 */
	public static Map<String, String> getAttachParam(ServletRequest servletRequest,boolean attachKey){
		Map<String,String[]> maps = servletRequest.getParameterMap();
		Map<String, String> map = new LinkedHashMap<String, String>();
		for(String key : maps.keySet()){
			String[] vk = key.split("\\.");
			if(vk.length==2&&vk[0].equals("attach")){
				map.put(attachKey?key:vk[1],maps.get(key)[0]);
			}
		}
		return map;
	}


	/**
	 * url 参数串连
	 * @param map
	 * @param keyLower
	 * @param valueUrlencode
	 * @return
	 */
	private static String mapJoin(Map<String, String> map,boolean keyLower,boolean valueUrlencode){
		StringBuilder stringBuilder = new StringBuilder();
		for(String key :map.keySet()){
			if(map.get(key)!=null&&!"".equals(map.get(key))){
				try {
					stringBuilder.append(key)
								 .append("=")
								 .append(valueUrlencode?URLEncoder.encode(map.get(key),"utf-8").replace("+", "%20"):map.get(key))
								 .append("&");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		if(stringBuilder.length()>0){
			stringBuilder.deleteCharAt(stringBuilder.length()-1);
		}
		return stringBuilder.toString();
	}


	/**
	 * Map key 排序
	 * @param map
	 * @return
	 */
	private static Map<String,String> order(Map<String, String> map){
		HashMap<String, String> tempMap = new LinkedHashMap<String, String>();
		List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(	map.entrySet());

		Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
			public int compare(Map.Entry<String, String> o1,Map.Entry<String, String> o2) {
				return (o1.getKey()).toString().compareTo(o2.getKey());
			}
		});

		for (int i = 0; i < infoIds.size(); i++) {
			Map.Entry<String, String> item = infoIds.get(i);
			tempMap.put(item.getKey(), item.getValue());
		}
		return tempMap;
	}
	
	/*
	 * 用户id加密
	 * 
	 */
	public static String encrypt(String userID){
		return  SHA1.SHA1Digest(userID+"&app_key="+Constants.APPID).toUpperCase();
	}

	
	/*public static void main(String[] args) {

		String sp_id = "";		//找相关负责人获取
		String sp_key = "";		//找相关负责人获取

		Map<String, String> map = new HashMap<String, String>();
		map.put("sp_id",sp_id);
		map.put("out_trade_no", "test5");	//业务系统中的 订单号
		map.put("body", "test");			//订单信息
		map.put("total_fee", "1");			//订单总价
		map.put("notify_url","http://....../notify_url");	//回调通知URL
		map.put("page_url","http://....../page_url");		//前端页面
		map.put("attach.phone","13562563254");	//附加字段
		map.put("attach.others","others");		//附加字段
		//map.put("transport_fee", "1");		//运费（可以为空） 如果设置了该项，将要求微信支付后要调用发货接口
		String url = buildURL("http://weixin.music.cnlive.com/weixin/pay", map, sp_key);
		System.out.println(url);

	}*/
}
