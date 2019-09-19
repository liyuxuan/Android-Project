package com.tool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Utils {
	abstract void checkFile(String path);
	abstract void tagStartModule();
	abstract void tagEndStartModule();
	abstract void tagComputeProcessingTime();
	//	网络配置设置;
	abstract void setHttpServletParameter(HttpServletRequest request,HttpServletResponse response);
	//	将上传的图片转换为字符串;
	abstract String ImageToBase64(String imgPath);
	//	清理临时的文件;
	abstract void clearTempFile(String path);
	
}
