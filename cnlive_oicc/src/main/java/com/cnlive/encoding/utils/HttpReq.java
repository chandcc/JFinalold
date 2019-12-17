package com.cnlive.encoding.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.alibaba.druid.util.StringUtils;

/**
 * http 请求类，用于请求各类接口
 * 
 * @author java_developer
 * 
 */
public class HttpReq {

	private static int TIMEOUT = 22000;

	public static String getRequestWidthResult(String url) {
		String response = "";
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIMEOUT);
		GetMethod get = new GetMethod(url);
		try {
			int code = httpClient.executeMethod(get);
			response = get.getResponseBodyAsString();
			return response;
		} catch (HttpException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static int getRequest(String url) {
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIMEOUT);
		GetMethod get = new GetMethod(url);
		try {
			int code = httpClient.executeMethod(get);
			return code;
		} catch (HttpException e) {
			e.printStackTrace();
			return 500;
		} catch (IOException e) {
			e.printStackTrace();
			return 500;
		}
	}

	public static int postRequest(String url, NameValuePair[] param) {
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIMEOUT);
		PostMethod method = new PostMethod(url);
		method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");
		// NameValuePair[] param = { new NameValuePair("age", "11"), new
		// NameValuePair("name", "jay"), };
		method.setRequestBody(param);
		int statusCode = 0;
		try {
			statusCode = httpClient.executeMethod(method);
			method.releaseConnection();
			return statusCode;
		} catch (HttpException e) {
			e.printStackTrace();
			return 500;
		} catch (IOException e) {
			e.printStackTrace();
			return 500;
		}
	}

	public static String postRequestWidthResult(String url, NameValuePair[] param) {
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIMEOUT);
		PostMethod method = new PostMethod(url);
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		method.setRequestBody(param);
		String response = "-1";
		try {
			int statusCode = httpClient.executeMethod(method);
			response = method.getResponseBodyAsString();
			method.releaseConnection();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	public static String postRequestWidthResult(String url, String paramName, String json) {
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIMEOUT);
		PostMethod method = new PostMethod(url);
		NameValuePair[] param = { new NameValuePair(paramName, json) };
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		method.setRequestBody(param);
		String response = "-1";
		try {
			int statusCode = httpClient.executeMethod(method);
			response = method.getResponseBodyAsString();
			System.out.println("执行结果：" + response);
			method.releaseConnection();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	public static String postJsonWidthResult(String url, String jsonObj) {
		String resStr = null;
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIMEOUT);
		PostMethod postMethod = new PostMethod(url);
		postMethod.addRequestHeader("Content-Type", "application/json");
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		postMethod.setRequestBody(jsonObj);
		try {
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				// post和put不能自动处理转发 301：永久重定向，告诉客户端以后应从新地址访问 302：Moved
				if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
					Header locationHeader = postMethod.getResponseHeader("location");
					String location = null;
					if (locationHeader != null) {
						location = locationHeader.getValue();
						System.out.println("执行结果：The page was redirected to :" + location);
					} else {
						System.out.println("执行结果：Location field value is null");
					}
				} else {
					System.out.println("执行结果：Method failed: " + postMethod.getStatusLine());
				}
				return resStr;
			}
			byte[] responseBody = postMethod.getResponseBody();
			resStr = new String(responseBody, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
		return resStr;
	}

	public static String urlPostMethod(String url, String params) {
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIMEOUT);
		PostMethod method = new PostMethod(url);
		try {
			if (params != null && !params.trim().equals("")) {
				RequestEntity requestEntity = new StringRequestEntity(params, "application/json", "UTF-8");
				method.setRequestEntity(requestEntity);
			}
			method.releaseConnection();
			httpClient.executeMethod(method);
			String responses = method.getResponseBodyAsString();
			System.out.println(responses);
			return responses;
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// get http status with authentication
	public static Object[] getStatusAndResponseWithAuth(String host, int port, String realm, String username,
			String password, String url) {
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIMEOUT);
		Object[] rs = new Object[2];
		httpClient.getState().setCredentials(new AuthScope(host, port, realm),
				new UsernamePasswordCredentials(username, password));
		GetMethod get = new GetMethod(url);
		get.setDoAuthentication(true);
		try {
			int status = httpClient.executeMethod(get);
			rs[0] = status;
			rs[1] = get.getResponseBodyAsString();
			return rs;
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			get.releaseConnection();
		}
		return null;
	}
	
	public static String requestUrl(String url,String requestMethod,String param) throws Exception{
		URL requestUrl = new URL(url);
		HttpURLConnection connection = (HttpURLConnection)requestUrl.openConnection();
		connection.setRequestMethod(requestMethod);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		//输出参数
		if(!StringUtils.isEmpty(param)){
			OutputStream out = connection.getOutputStream();
			out.write(param.getBytes("utf-8"));
		}
		connection.connect();//连接
		//获得返回值
		String result = "";
		String currentLine = "";
		if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
			while((currentLine = br.readLine()) != null){
				result += currentLine;
			}
			br.close();
		}
		return result;
	}
     /**
      * 获取refer
      * @author fan xiao chun
      * @date 2017年1月1日
      * @param request
      * @return
      */
	public static String getReferer(HttpServletRequest request) {
		return request.getHeader("Referer");
	}

}