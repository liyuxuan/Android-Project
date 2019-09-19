package com.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tool.DBaseUtils;
import com.tool.ImageUtil;
import com.tool.SysUtil;
import com.tool.WebUtils;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class CheckCaseServlet
 */
@WebServlet("/CheckCaseServlet")
public class CheckCaseServlet extends HttpServlet implements WebUtils{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckCaseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		SysUtil	   sysUtil		=	new SysUtil();
		sysUtil.setHttpServletParameter(request, response);
		sysUtil.setTag("案件管理");
		sysUtil.tagStartModule();
		String 	   result		=	null;

		int oper=0;
		try {
			oper=Integer.parseInt(request.getParameter("operType").toString());
			
		} catch (Exception e) {
			oper=0;
		}
		
		switch (oper) {
		//	进行相应的查询操作;
		case 1:
			result	=	queryAllInfo(request);
			break;
			
		//	进行单条数据的查询;
		case 2:
			//	以显示图片为主;
			result	=	queryItemInfo(request);
			break;
		
		//	进行相应的保存操作;
		default:
			//	
			result	=	saveInfo(request, response);
			break;
		}
		
		//	进行数据出操作的内容;
		sysUtil.tagEndStartModule();
		sysUtil.tagComputeProcessingTime();

		//	将结果进行返回的功能模块;
		response.getWriter().append(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	//	保存数据信息;	
	@Override
	public String saveInfo(HttpServletRequest request,HttpServletResponse response) {
		SysUtil	   sysUtil		=	new SysUtil();
		//	姓名;
		String name		=	request.getParameter("name").toString();
		//	生日;
		String birthday	=	request.getParameter("birthday").toString();
		//	IDCARD;
		String idcard	=	request.getParameter("idcard").toString();
		//	性别;
		String sex		=	request.getParameter("sex").toString();
		//	民族;
		String nation	=	request.getParameter("nation").toString();
		//	电话;
		String phone	=	request.getParameter("phone").toString();
		//	户籍地址ַ;
		String householdaddress =	request.getParameter("householdaddress").toString();
		//	目的地址ַ;
		String serviceaddress	=	request.getParameter("serviceaddress").toString();
		//	邮政编码;
		String zipcode			=	request.getParameter("zipcode").toString();
		//	急救人员;
		String urgentperson		=	request.getParameter("urgentperson").toString();
		//	与之关系;
		String relationship		=	request.getParameter("relationship").toString();
		//	其联系电话;
		String relationtel		=   request.getParameter("relationtel").toString();
		//	单位名称;
		String unitname			=	request.getParameter("unitname").toString();
		//	单位领导;
		String unitboss			=	request.getParameter("unitboss").toString();
		//	单位职位;
        String unitright		=	request.getParameter("unitright").toString();
        //	单位地址1;
	    String unitaddress1		=	request.getParameter("unitaddress1").toString();
	    //	单位地址2;
	    String unitaddress2		=	request.getParameter("unitaddress2").toString();
	    //	单位联系人;
        String unitcontactperson=	request.getParameter("unitcontactperson").toString();
        //	联系人权利;
        String unitcontactright =	request.getParameter("unitcontactright").toString();
        //	所属部门;
        String belongdept		=	request.getParameter("belongdept").toString();
        //	部门电话;
        String deptphone		=	request.getParameter("deptphone").toString();
		//  提交原因;
		String submitreason		=	request.getParameter("submitreason").toString();
		//	提交内容;
		String submitcontent	=	request.getParameter("submitcontent").toString();
		//  送达地址;
		String toaddress		=	request.getParameter("toaddress").toString();
		//	送达邮编;
		String tozipcode		=	request.getParameter("tozipcode").toString();
		//  联系电话;
		String tocontactphone	=	request.getParameter("tocontactphone").toString(); 
		//  联系地址;
		String tomailaddress	=	request.getParameter("tomailaddress").toString();
		//  联系人微信;
		String towechat			=	request.getParameter("towechat").toString();
		//  联络人;
		String tocollector		=	request.getParameter("tocollector").toString();
		//  联络人电话;
		String tocollectorphone =   request.getParameter("tocollectorphone").toString();
		//  证据电话;
		String proofpath		=	request.getParameter("proofpath").toString();
		//  证据签名;
		String signature		=	request.getParameter("signature").toString();
		//  主键;
		long	   key	   		= 	System.currentTimeMillis();
		
		String result			=	null;
			
		//	数据库管理类
		DBaseUtils dBaseUtils=new DBaseUtils();
		//	SQL语句;
		String sql="insert into caseinfo values("+key+",'"+name+"','"+birthday+"','"+idcard+"','"+sex+"',"
				+ "'"+nation+"','"+phone+"','"+householdaddress+"','"+serviceaddress+"','"+zipcode+"','"+urgentperson+"',"
				+ "'"+relationship+"','"+relationtel+"','"+unitname+"','"+unitboss+"','"+unitright+"','"+unitaddress1+"',"
				+ "'"+unitaddress2+"','"+unitcontactperson+"','"+unitcontactright+"','"+belongdept+"','"+deptphone+"',"
				+ "'"+submitreason+"','"+submitcontent+"','"+toaddress+"','"+tozipcode+"','"+tocontactphone+"','"+tomailaddress+"',"
				+ "'"+towechat+"','"+tocollector+"','"+tocollectorphone+"','"+proofpath+"','"+signature+"',"+key+")";
//		System.out.println("传输="+sql);
		
		//	数据库更新;
		dBaseUtils.update(sql);
		//	图片上传
		String path			=	SysUtil.PATH3+File.separator+key;
		sysUtil.getUploadFile(path, request, response);
		
		return result;
	}

	//	查询所有数据;
	@Override
	public String queryAllInfo(HttpServletRequest request) {
		String 	   result	  = null;
		String 	   idcard	  = request.getParameter("idcard").toString();

		//	数据库管理类
		DBaseUtils dBaseUtils = new DBaseUtils();
		String 	   sql		  = "select * from caseinfo where idcard='"+idcard+"' order by datetime desc";
		JSONArray  list		  = dBaseUtils.select2(sql);
		result				  = list.toString();

		dBaseUtils.close();
		return result;
	}

	@Override
	public String queryItemInfo(HttpServletRequest request) {
		String result	    =	null;
		String auto_id	    = 	request.getParameter("auto_id");
		
		//	数据库初始化声明;
		DBaseUtils baseUtils=	new DBaseUtils();
		
		//	服务器地址
		String 	   ip		=	request.getServerName(); 
		//	端口号
        int        port		=	request.getServerPort(); 
        //	项目名称
        String 	   project	= 	request.getContextPath();
//        //	请求页面或其他地址
//        String 	   add		=	request.getServletPath();  
        
        String	   head		=	"http://"+ip+":"+port+project+"/temp";
		//	检测服务器临时文件中是否有对应的文件夹;
		String	   folder	=	request.getSession().getServletContext().getRealPath("\\")+File.separator+"temp";
		
		File   	   file     = 	new File(folder);
		if (!file.exists()) {
			file.mkdirs();
		}
		ImageUtil imageUtil =	new ImageUtil();
		//	查询数据库中的图片信息;
		String 	 			sql	    = 	"select proofpath,signature from caseinfo where auto_id="+auto_id;
		ArrayList<String[]> list 	=	baseUtils.select(sql);
		
		JSONArray 			array	=	new  JSONArray();
		for(String[] items:list) {
			for(String item:items) {
				//	来源文件的路径;
				String 	frompath	=	"D:"+File.separator+"SYS_info"+File.separator+auto_id+File.separator+item;
				//	去处文件的路径;
				String 	topath		=	folder+File.separator+item;
				try {
					imageUtil.compress(frompath, topath);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				//	将图片拷贝到临时存储当中;
//				copyImageA2B(frompath, topath);
				//	添加路径;
				String 	path		=	head+"/"+item;
				array.add(path);
			}
		}
		//	将结果进行装载;
		result				=	array.toString();
		
		return result;
	}

}
