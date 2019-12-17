package com.cnlive.encoding.controller;

import com.cnlive.encoding.utils.CommonUtils;

public class Controller extends com.jfinal.core.Controller {
	
	public String getPara(String name) {
		return CommonUtils.escapesHtml(super.getPara(name));
	}
	
	
	public String getPara(String name, String defaultValue) {
		String result = super.getPara(name,defaultValue);
		result = CommonUtils.escapesHtml(result);
		return result;
	}
	
	public String getPara() {
		return CommonUtils.escapesHtml(super.getPara());
	}
	
	public String getPara(int index) {
		return CommonUtils.escapesHtml(super.getPara(index));
	}
	
	public String getPara(int index, String defaultValue) {
		return CommonUtils.escapesHtml(super.getPara(index,defaultValue));
	}
	
}
