package com.servlet;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tool.DBaseUtils;
import com.tool.DataUtils;
import com.tool.SysUtil;

/**
 * Servlet implementation class UserManagerServlet
 */
@WebServlet("/UserManagerServlet")
public class UserManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserManagerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SysUtil    sysUtils  = new SysUtil();
		// 进行数据库的开启;
		DBaseUtils dBaseUtils= new DBaseUtils();
		DataUtils  dataUtils = new DataUtils();
		
		sysUtils.setHttpServletParameter(request, response);
		sysUtils.setTag("用户管理");
		sysUtils.tagStartModule();
		
		String 	   address	=	request.getParameter("address").toString();
		String 	   birthday	=	request.getParameter("birthday").toString();
		String 	   idcard	=	request.getParameter("idcard").toString();
		String 	   name		=	request.getParameter("name").toString();
		String     nation	=	request.getParameter("nation").toString();
		String 	   sex		=	request.getParameter("sex").toString();
		String 	   mobile	=	request.getParameter("mobile").toString();
		
		String issue_authority	= request.getParameter("issue_authority").toString();
		String valid_period		= request.getParameter("valid_period").toString();
		String head_portrait	= request.getParameter("head_portrait").toString().replaceAll(" ","+");
		
		String result		=	null;
		System.out.println(idcard);
		System.out.println(name);
		System.out.println(birthday);
		System.out.println(sex);
		System.out.println(nation);
		System.out.println(address);
		System.out.println(issue_authority);
		System.out.println(valid_period);
		System.out.println(mobile);
		
		String     path    = SysUtil.PATH+File.separator+idcard;
		
		// 将Base64的图像进行转换;
		dataUtils.Base64ToImage(path, idcard, head_portrait);
		
		String registerimg = sysUtils.getUploadFile(path, request, response);
		
		String sql		   =  "insert into userinfo (idcard,name,birthday,sex,nation,address,issue_authority,valid_period,idimg,registerimg,mobile) "
							+ "values("
							+ "\'"+idcard+"\','"+name+"\',\'"+birthday+"\',\'"+sex+"\',\'"+nation+"\',\'"+address+"\',\'"+issue_authority+"\',\'"+valid_period+"\',\'"+idcard+"\',\'"+registerimg+"\',\'"+mobile+"\'"
							+ ") ";
		System.out.println("操作=\r\n"+sql);
		//	数据库的信息进行相应的更新操作;
		int flag=dBaseUtils.update(sql);
//		System.out.println("flag="+flag);
		if(flag>0)
        	result=idcard;

		sysUtils.tagEndStartModule();
		sysUtils.tagComputeProcessingTime();
        
		dBaseUtils.close();
		//	 
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
