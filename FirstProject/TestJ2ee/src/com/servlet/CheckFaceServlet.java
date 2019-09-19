package com.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tool.DBaseUtils;
import com.tool.SysUtil;
import com.tool.Util;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class CheckFaceServlet
 */
@WebServlet("/CheckFaceServlet")
public class CheckFaceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckFaceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SysUtil    		sysUtils	=  new SysUtil();
		sysUtils.setHttpServletParameter(request, response);
		DBaseUtils 		dBaseUtils	=  new DBaseUtils();

		//	程序的正式开始;
		sysUtils.setTag("人脸识别");
        sysUtils.tagStartModule();
		
		// 将人脸信息进行替换;
		String     		    idcard	=  request.getParameter("idcard").toString();
		// 将人脸信息转为字符串;
		String     			path    =  sysUtils.getUploadFile(Util.PATH, request, response);
		String				face	=  sysUtils.ImageToBase64(path);
		// 识别结果;
        String	  			result	=  sysUtils.recognizeFace(idcard, face);
        
        sysUtils.clearTempFile(path);
		//	根据人脸识别的内容进行数据的查询;
		String    			sql		=  "select * from userinfo where idcard='"+idcard+"'";
		//	进行JSON数据形式的查询;
		JSONArray			array	=	dBaseUtils.select2(sql);
		//	结果集合的传递;
		result						=	result+"%"+array.toString();

		sysUtils.tagEndStartModule();
		sysUtils.tagComputeProcessingTime();
		//	结果输出的内容;
		response.getWriter().write(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
