package com.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Decoder.BASE64Encoder;

/**
 * @author Lenovo
 *
 */
/**
 * @author Lenovo
 *
 */
public class Util implements Utils{

	public static final String PATH = "D:"+File.separator+"SYS_image";
	public static final String PATH2= "D:"+File.separator+"SYS_download";
	public static final String PATH3= "D:"+File.separator+"SYS_info";
	public String tag; 
	private long t1;
	private long t2;
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	//	重载文件监测的内容;
	public void checkFile(String path) {
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
	}
	//	开始进行程序的相应的操作;
	public void tagStartModule() {
		System.out.println("'"+this.tag+"'功能-开始运行!");
		this.t1=System.currentTimeMillis();
	}
	
	//	结束进行程序的相应的操作;
	public void tagEndStartModule() {
		System.out.println("'"+this.tag+"'功能-结束运行!");
		this.t2=System.currentTimeMillis();
	}
	
	//	程序花费时间的相应的操作;
	public void tagComputeProcessingTime() {
		long td=this.t2-this.t1;
		System.out.println("'"+this.tag+"'功能-花费时间:"+td+"毫秒!");
	}
	//	设置网页的相应内容;
	public void setHttpServletParameter(HttpServletRequest request,HttpServletResponse response){
		//	输入的字符串编辑内容;
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//	输出的相应的内容信息;
		response.setHeader("Context-Type","text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
	}
	
	//	将图像转化为Base64的相应内容;
	public String ImageToBase64(String imgPath) {
	    byte[] data = null;
	    // 输入文件流;
	    try {
	        InputStream in = new FileInputStream(imgPath);
	        data = new byte[in.available()];
	        in.read(data);
	        in.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    // Base64
	    BASE64Encoder encoder = new BASE64Encoder();
	    // 字符串编辑;
	    return encoder.encode(Objects.requireNonNull(data));
	}

	@Override
	public void clearTempFile(String path) {
		File file=new File(path);
		if(file.exists()) {
			file.delete();
		}
	}
}
