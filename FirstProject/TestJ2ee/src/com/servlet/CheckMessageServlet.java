package com.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tool.DBaseUtils;
import com.tool.SysUtil;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class CompluteServlet
 */
@WebServlet("/CompluteServlet")
public class CheckMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public CheckMessageServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SysUtil sysUtil=new SysUtil();
		sysUtil.setHttpServletParameter(request, response);
		
		// 进行功能
		sysUtil.setTag("短信验证");
		sysUtil.tagStartModule();
		
		// 操作类型;
		int operType = Integer.parseInt(request.getParameter("operType").toString());
		// 电话内容;
		String mobile = request.getParameter("mobile");
		// 当前结果;
		String result = null;
		

		// 操作处理类型判断;
		switch (operType) {
		// 短信验证登录;
		case SysUtil.SMS_VERIFICATION_IN_LOGIN:
			result = SMSVerificationInLogin(mobile);
			break;

		// 短信验证注册;
		case SysUtil.SMS_VERIFICATION_IN_RESIGN:
			result = SMSVerificationInResign(mobile);
			break;
		default:
			break;
		}
	
		sysUtil.tagEndStartModule();
		sysUtil.tagComputeProcessingTime();
		System.out.println("结果:"+result);
		// 结果返回;
		response.getWriter().write(result);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	// 短信验证的登录;
	private String SMSVerificationInLogin(String mobile) {
		// 系统方法;
		SysUtil   			sysUtils   = new SysUtil();
		
		// 数据库的操作工具类;
		DBaseUtils 			dBaseUtils = new DBaseUtils();
		// 数据库的SQL语句;
		String 	   			sql 	   = "select count(*) from userinfo where mobile='" + mobile + "'";

		ArrayList<String[]> list 	   = dBaseUtils.select(sql);
		// 尺寸的测试;
		int size 	= Integer.parseInt(list.get(0)[0].toString());

		// 判断尺寸的长度;
		if (size != 0) {
			//	进行身份信息的查询;
			sql				= "select * from userinfo where mobile='"+mobile+"'";
			JSONArray array = dBaseUtils.select2(sql);
			//	显示出相应的数据信息;
			return sysUtils.sendMessage(mobile)+"%"+array.toString();
		}

		// 返回值的内容;
		return null;
	}

	// 短信的注册验证;
	private String SMSVerificationInResign(String mobile) {
		// 工具类的内容;
		SysUtil   sysUtils	  = new SysUtil();
		
		return sysUtils.sendMessage(mobile)+"%"+mobile;
	}

	

}
