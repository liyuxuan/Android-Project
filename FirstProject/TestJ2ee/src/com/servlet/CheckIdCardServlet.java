package com.servlet;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tool.SysUtil;
import com.tool.Util;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class CheckIdCardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckIdCardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 进行数据处理;
		SysUtil   sysUtils = new SysUtil();
		sysUtils.setHttpServletParameter(request, response);
		System.out.println("接受数据");
		//	进行程序的主要功能模块;
		sysUtils.setTag("IDCARD识别数据");
		sysUtils.tagStartModule();

		sysUtils.checkFile(Util.PATH);
        String     path    = sysUtils.getUploadFile(Util.PATH, request, response);
        // 验证码的形成;
        String 	   code	   = sysUtils.getIdCardInfo(path);
        System.out.println("code="+code);
        sysUtils.tagEndStartModule();
        sysUtils.tagComputeProcessingTime();
        
        response.getWriter().write(code);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}



}
