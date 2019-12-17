package com.cnlive.encoding.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.druid.util.StringUtils;
import com.cnlive.encoding.model.Enrol;
import com.cnlive.encoding.model.Member;
import com.cnlive.encoding.model.ResultBean;
import com.cnlive.encoding.service.EnrolService;
import com.cnlive.encoding.utils.CommonUtils;
import com.cnlive.encoding.utils.Constants;
import com.jfinal.aop.Clear;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

public class EnrolController extends Controller {
	private static Log logger = LogFactory.getLog(EnrolController.class);

	/**
	 * 获取用户报名信息
	 */
	public void getEnrol(){
		int pageNo = getParaToInt(0);
		String query = getPara("query");
		setSessionAttr("query", null);
		setSessionAttr("query", query);
		setAttr("enrolPage", EnrolService.service.getEnrolByPage(pageNo, query));
		render("enrol.jsp");
	}
	
	/**
	 * 提交报名信息,个人报名
	 */
	@Clear()
	public void enrol() {
		String userId = getCookie("cnliveUserId");
		 userId = Member.me.getUserIDByEncryptID(userId);
		if(StringUtils.isEmpty(userId)){
			renderJson(new ResultBean(-1,"用户id为空",null));
		}else{
				Integer age = null;
				String tel = getPara("tel");
				Enrol en = EnrolService.service.getOneByTel(tel);
				if(en!=null){
					renderJson(new ResultBean(-1,"该手机号用户已报名",null));
				}
				String username = getPara("username");
				String ageStr = getPara("age");
				if(!StringUtils.isEmpty(ageStr))age=Integer.parseInt(ageStr);
				String company = getPara("company");
				int sex = getParaToInt("sex");
				String IdNumber = getPara("IdNumber");
				Record enrol = new Record();
				enrol.set("memberId", userId);
				enrol.set("username", username);
				enrol.set("age", age);
				enrol.set("company", company);
				enrol.set("sex", sex);
				enrol.set("tel", tel);
				enrol.set("IdNumber", IdNumber);
				enrol.set("insertDate",  CommonUtils.getCurrentTimestamp());
				boolean success = Db.save("t_enrol", enrol);
				if(success){
					renderJson(new ResultBean(0,"内容创建成功",null));
				}else{
					renderJson(new ResultBean(-1,"内容保存失败",null));
				}
			
		}
	}
	
	/**
	 * 获取用户提交个人报名信息
	 */
	@Clear
	public void getOneEnrol(){
		String userId = getCookie("cnliveUserId");
		 userId = Member.me.getUserIDByEncryptID(userId);
		if(!StringUtils.isEmpty(userId)){
			Enrol enrol = EnrolService.service.getOneByUserId(userId);
			if(enrol!=null){
				Record record = new Record();
				record.set("username", enrol.get("username"));
				record.set("age", enrol.get("age"));
				record.set("company", enrol.get("company"));
				record.set("sex", enrol.get("sex"));
				record.set("tel", enrol.get("tel"));
				record.set("IdNumber", enrol.get("IdNumber"));
				renderJson(new ResultBean(0, "获取数据成功", record));
			}else{
				renderJson(new ResultBean(-1, "未提交报名单", null));
			}
		}else{
			renderJson(new ResultBean(-1, "未登录", null));
		}
		
	}

	
	/**
	 * 检测是否已上传团体报名表
	 */
	@Clear
	public void isUpTeam(){
		String userId = getCookie("cnliveUserId");
		 userId = Member.me.getUserIDByEncryptID(userId);
		if(!StringUtils.isEmpty(userId)){
			boolean isUp =	EnrolService.service.isUpTeam(userId);
			renderJson(new ResultBean(0, "获取数据成功", isUp));
		}else{
			renderJson(new ResultBean(-1, "未登录", null));
		}
	}
	
	/*
	 * 下载团体报名表模板
	 */
	@Clear
	public void  downTeam(){
		String storageUrl=this.getClass().getResource("/").getPath();
		File file = new File(storageUrl+File.separator+"cos run 团体报名表.xlsx");
		renderFile(file);
	}
	
	
	/**
	 * 团体报名表提交
	 */
	@Clear
	public void  enrolTeam(){
		String userId = getCookie("cnliveUserId");
		 userId= Member.me.getUserIDByEncryptID(userId);
		if(StringUtils.isEmpty(userId)){
			renderJson(new ResultBean(-1,"请先登录",null));
			return;
		}
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String time = sdf.format(new Date().getTime());
			String[] strings = time.split("-");
			String path = Constants.FILE_PATH+File.separator+strings[0]+File.separator
				 +strings[1]+strings[2]+File.separator+userId;
			/*String path = "D:\\"+strings[0]+File.separator
					 +strings[1]+strings[2]+File.separator+userId+".xlsx";*/
			
			 UploadFile upfile = getFile("uploadFile", path, 5*1024 * 1024 * 1024);
		 if(upfile==null) {
				renderJson(new ResultBean(-1,"请选择相应的Excel文件",null));
				return;
			}
			String fileName = upfile.getFileName();
			if(fileName==null||!fileName.endsWith(".xlsx")){
				renderJson(new ResultBean(-1,"文件格式不正确",null));
				return;
			}
		 List<List<String>> excelList = readExcel(upfile.getFile());
		 String company = excelList.get(1).get(1);
		 String linkman = excelList.get(2).get(1);
		 String company_tel = excelList.get(2).get(3).replaceAll(" ","");
		 boolean isAgain = false;
		 String  number="";
		 for (int i = 4; i < excelList.size(); i++) {
			 List<String> list = excelList.get(i);
			 String tel = list.get(4);
			 if(StringUtils.isEmpty(tel)){
				 break;			
			 }
			 Enrol en = EnrolService.service.getOneByTel(tel);
			 if(en!=null){
				 isAgain=true;
				 number=list.get(0);
				 break;
			 }
		
		 }
		 //手机号唯一，有重复不插入
		 if(isAgain){
			 renderJson(new ResultBean(-1,"序号（"+number+"）手机用户已报名，请勿重复报名！",null));
		 }else{
			 for (int i = 4; i < excelList.size(); i++) {
				 List<String> list = excelList.get(i);
				 String tel = list.get(4);
				 if(StringUtils.isEmpty(tel)){
					 break;			
				 }
				 String username = list.get(1);
				 int age = Integer.parseInt(list.get(3).replaceAll(" ",""));
				 String sexStr = list.get(2);
				 int sex=0;
				 if("女".equals(sexStr.replaceAll(" ",""))) sex=1;
				 	Record enrol = new Record();
					enrol.set("memberId", userId);
					enrol.set("username", username);
					enrol.set("age", age);
					enrol.set("company", company);
					enrol.set("sex", sex);
					enrol.set("tel", tel);
					enrol.set("insertDate",  CommonUtils.getCurrentTimestamp());
					enrol.set("company_linkman", linkman);
					enrol.set("company_tel", company_tel);
					enrol.set("is_team", 1);
					Db.save("t_enrol", enrol);
			}
				 renderJson(new ResultBean(0,"报名表单上传成功",null));
		 }
		}catch(Exception e){
			logger.error("团体报名表上传出错，userid=="+userId);
			renderJson(new ResultBean(-1,"Excel文件内容存在错误，请仔细检查后再进行上传！",null));
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	 /**
     * 读取excel
     * @param path
     * @return
     */
    public static List<List<String>> readExcel(File file){ 
    	List<List<String>> list = new ArrayList<List<String>>();
        try { 
            InputStream inputStream = new FileInputStream(file); 
            String fileName = file.getName(); 
            Workbook wb = null; 
            
            if(fileName.endsWith(".xlsx")){
            	wb = new XSSFWorkbook(inputStream);//Excel 2007
        	} else {
        		wb = (Workbook) new HSSFWorkbook(inputStream);//Excel 2003
        	}
            Sheet sheet = wb.getSheetAt(0);//第一个工作表  ，第二个则为1，以此类推...
            int firstRowIndex = sheet.getFirstRowNum(); 
            int lastRowIndex = sheet.getLastRowNum(); 
            for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex ++){ 
                Row row = sheet.getRow(rIndex); 
                if(row != null){ 
                    int firstCellIndex = row.getFirstCellNum(); 
                   // int lastCellIndex = row.getLastCellNum(); 
                    //此处参数cIndex决定可以取到excel的列数。
                    List<String> list2 = new ArrayList<String>();
                    for(int cIndex = firstCellIndex; cIndex < 5; cIndex ++){ 
                        Cell cell = row.getCell(cIndex); 
                        String value = ""; 
                        if(cell != null){ 
                        	if(CellType.NUMERIC==cell.getCellTypeEnum()){
                        		DecimalFormat df = new DecimalFormat("0");
                        		value = df.format(cell.getNumericCellValue());
                        	}else{
                        		value = cell.toString(); 
                        	}
                            list2.add(value);
                        } 
                    } 
                    list.add(list2);
                } 
            } 
        } catch (FileNotFoundException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
        return list;
    } 
    
}
