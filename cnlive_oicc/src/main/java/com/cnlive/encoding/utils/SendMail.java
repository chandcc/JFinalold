package com.cnlive.encoding.utils;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import com.sun.mail.util.MailSSLSocketFactory;

public class SendMail {
	// 收件人邮箱地址
	private String to ;
	// 发件人邮箱地址
	private String from="2020内蒙古和林格尔新区国际文化创意大会";
	// SMTP服务器地址
	private String smtpServer="smtp.cnlive.com";
	// 登录SMTP服务器的用户名
	//private String username="oicc@cnlive.com";
	private String username="1906551626@qq.com";
	// 登录SMTP服务器的密码
	//private String password="Oicc2324";
	private String password="hxnzeweixclbbcij";
	// 邮件主题
	private String subject;
	// 邮件正文
	private String content;
	// 记录所有附件文件的集合
	List<String> attachments = new ArrayList<String>();

	// 无参数的构造器
	public SendMail() {
	}

	public SendMail(String to, String from, String smtpServer, String username, String password, String subject,
			String content) {
		this.to = to;
		this.from = from;
		this.smtpServer = smtpServer;
		this.username = username;
		this.password = password;
		this.subject = subject;
		this.content = content;
	}

	// to属性的setter方法
	public void setTo(String to) {
		this.to = to;
	}

	// from属性的setter方法
	public void setFrom(String from) {
		this.from = from;
	}

	// smtpServer属性的setter方法
//	public void setSmtpServer(String smtpServer) {
//		this.smtpServer = smtpServer;
//	}

	// username属性的setter方法
	public void setUsername(String username) {
		this.username = username;
	}

	// password属性的setter方法
	public void setPassword(String password) {
		this.password = password;
	}

	// subject属性的setter方法
	public void setSubject(String subject) {
		this.subject = subject;
	}

	// content属性的setter方法
	public void setContent(String content) {
		this.content = content;
	}

	// 把邮件主题转换为中文
	public String transferChinese(String strText) {
		try {
			strText = MimeUtility.encodeText(new String(strText.getBytes(), "GB2312"), "GB2312", "B");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strText;
	}

	// 增加附件，将附件文件名添加的List集合中
	public void attachfile(String fname) {
		attachments.add(fname);
	}

	// 发送邮件
	public boolean send() {

        Properties prop = new Properties();
        //协议
        prop.setProperty("mail.transport.protocol", "smtp");
        //服务器
        //prop.setProperty("mail.smtp.host", "smtp.exmail.qq.com");
		prop.setProperty("mail.smtp.host", "smtp.qq.com");
        //端口
        prop.setProperty("mail.smtp.port", "465");
        //使用smtp身份验证
        prop.setProperty("mail.smtp.auth", "true");
        //使用SSL，企业邮箱必需！
        //开启安全协议
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
        } catch (GeneralSecurityException e1) {
            e1.printStackTrace();
        }
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);
        //
        //获取Session对象
        Session s = Session.getDefaultInstance(prop,new Authenticator() {
            //此访求返回用户和密码的对象
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                PasswordAuthentication pa = new PasswordAuthentication(username, password);
                return pa;
            }
        });
        //设置session的调试模式，发布时取消
        s.setDebug(true);
        MimeMessage mimeMessage = new MimeMessage(s);
        try {
            mimeMessage.setFrom(new InternetAddress(username,from));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            //设置主题
            mimeMessage.setSubject(subject);
            mimeMessage.setSentDate(new Date());
            //设置内容
            mimeMessage.setContent(content,"text/html;charset = gbk");
            mimeMessage.saveChanges();
            //发送
            Transport.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
		}
		return true;

	
	/*public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        //协议
        prop.setProperty("mail.transport.protocol", "smtp");
        //服务器
        prop.setProperty("mail.smtp.host", "smtp.exmail.qq.com");
        //端口
        prop.setProperty("mail.smtp.port", "465");
        //使用smtp身份验证
        prop.setProperty("mail.smtp.auth", "true");
        //使用SSL，企业邮箱必需！
        //开启安全协议
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
        } catch (GeneralSecurityException e1) {
            e1.printStackTrace();
        }
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);
        //
        //获取Session对象
        Session s = Session.getDefaultInstance(prop,new Authenticator() {
            //此访求返回用户和密码的对象
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
            PasswordAuthentication pa = new PasswordAuthentication("oicc@cnlive.com", "Oicc2324");
                return pa;
            }
        });
        //设置session的调试模式，发布时取消
        s.setDebug(true);
        MimeMessage mimeMessage = new MimeMessage(s);
        try {
            mimeMessage.setFrom(new InternetAddress("oicc@cnlive.com","鄂市文创"));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("1012792334@qq.com"));
            //设置主题
            mimeMessage.setSubject("账户密码重置");
            mimeMessage.setSentDate(new Date());
            //设置内容
            mimeMessage.setContent("尊敬的用户：<br/>"+
			"您好，您已申请注册鄂市文创账户。"+
			"请尽快点击这里激活账户：http://oicc.cnlive.com/cnlive_oicc/api，如果您并未申请创建账户，请忽略此信息。</br>"
			+"<div style='float: right;'>鄂尔多斯国际文化创意大会</div>","text/html;charset = gbk");
            mimeMessage.saveChanges();
            //发送
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
     */   
    }
}
