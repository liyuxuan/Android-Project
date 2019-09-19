package com.servlet;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tool.DataUtils;
import com.tool.SysUtil;
import com.tool.Util;

/**
 * Servlet implementation class CheckFileServlet
 */
@WebServlet("/CheckFileServlet")
public class CheckFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckFileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		SysUtil sysUtil=new SysUtil();
		sysUtil.setHttpServletParameter(request, response);
		//	文件正式进行处理的功能部分;
		sysUtil.setTag("文件处理");
		sysUtil.tagStartModule();

		String path = Util.PATH2;
		sysUtil.checkFile(path);
		
		//  结果集合的内容;
		ArrayList<String> list=getFiles(path);
		//	功能标签的标记内容;
		sysUtil.tagEndStartModule();
		//	计算相应的处理时间;
		sysUtil.tagComputeProcessingTime();
		
		response.getWriter().write(list.toString());
	}

	//	获取所有的下载文件信息;
	private ArrayList<String> getFiles(String path) {
		File[]    		  allFiles = new File(path).listFiles();
		DataUtils 		  dataUtils= new DataUtils();
		ArrayList<String> list	   = new ArrayList<String>();
		for (int i = 0; i < allFiles.length; i++) {
			File 	file 	= allFiles[i];
			String  filename= file.getName();
			String  path2	= path+File.separator+filename;
			String  temp	= "";
			
			//	文件的名称的内容;
			if(filename.contains(".jpg")) {
				temp=dataUtils.ImageToBase64(path2);
//				System.out.println("图像长度:"+temp.length());
			}
				
			else {
				temp=dataUtils.fileRead(path2,"utf-8");

				try {
					temp=URLEncoder.encode(temp, "utf-8") ;
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				System.out.println("文字长度:"+temp.length());
			}

			String result="{\"filename\":\""+filename+"\",\"file\":\""+temp+"\"}";
			
			list.add(result);
		}
		return list;
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
