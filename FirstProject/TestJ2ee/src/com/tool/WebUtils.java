package com.tool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WebUtils {
	//	存储数据信息;
	abstract String saveInfo(HttpServletRequest request,HttpServletResponse response);
	
	//	查询所有数据的信息;
	abstract String queryAllInfo(HttpServletRequest request) ;
	
	//	查询单条的数据信息;
	abstract String queryItemInfo(HttpServletRequest request) ;
}
