package com.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DBaseUtils {
	// 数据库的驱动的类;
	private String driver = "com.mysql.jdbc.Driver";
	// 数据库的驱动链接;
	private String url = "jdbc:mysql://localhost:3306/formaldb?useUnicode=true&characterEncoding=UTF8";
	private String user = "root";
	private String password = "root";
	private Connection coon = null;

	// 构造函数;
	public DBaseUtils() {
		try {
			// 构造函数的驱动类;
			Class.forName(driver);
			coon = (Connection) DriverManager.getConnection(url, user, password);
			if (!coon.isClosed()) {
				System.out.println("数据库的连接!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 数据库的关闭;
	public void close() {
		try {
			this.coon.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 数据的更新;
	public int update(String sql) {
		int oper=0;
		PreparedStatement preStmt=null;
		// SQL语句;
		System.out.println(sql);
		
		try {
			preStmt = (PreparedStatement) this.coon.prepareStatement(sql);
			
			//	数据的操作的执行内容;
			oper	=	preStmt.executeUpdate();
			System.out.println("数据表更新完毕!");
			preStmt.close();

		} catch (Exception e) {

			oper	=	0;
		}
		
		return oper;
	}
	//	进行相应的数据查询
	public JSONArray select2(String sql) {
		JSONArray 					list		=	new  JSONArray();
		long 	 	 				time1		= 	System.currentTimeMillis();
		System.out.println("--------------------------------");
		try{
	    	 
		      Statement 			stmt		= (Statement)this.coon.createStatement();
	          // 数据的结果集合;
	          ResultSet 			rs			= (ResultSet)stmt.executeQuery(sql);
	          
	          while(rs.next()){
	        	  int 			  	count		= 1;
	        	  ResultSetMetaData rsmd 		= rs.getMetaData() ; 
	              int 				columnCount = rsmd.getColumnCount();
	        	  JSONObject	  	object		= new JSONObject();
	        	  while(count<=columnCount) {
	        		  // 列数的内容;
	        		  String 	  key			= rsmd.getColumnName(count);
	        		  String 	  value			= rs.getString(key);
	        		  // 参数的添加;
	        		  object.put(key, value);
	        		  count++;
	        	  }
	        	  list.add(object);
	         }
	          stmt.close();
	          
	     }catch(Exception e){
	        e.printStackTrace();
	     }
		    
		    long 				time2		= System.currentTimeMillis();
	        long				timeDist	= time2-time1;
	        System.out.println("数据库操作耗费:"+timeDist+"毫秒!");
	        System.out.println("--------------------------------");
		
		return list;
	}
	
	
	
	// 数据的查询结果的内容;
	public ArrayList<String[]> select(String sql){
		long 				time1		= System.currentTimeMillis(); 
		ArrayList<String[]> list		= new ArrayList<String[]>();
	    try{
	    	 
	      Statement stmt=(Statement)this.coon.createStatement();
          // 数据的结果集合;
          ResultSet rs=(ResultSet)stmt.executeQuery(sql);
          System.out.println("--------------------------------");
          
          ResultSetMetaData rsmd 		= rs.getMetaData() ; 
          int 				columnCount = rsmd.getColumnCount();
          
          System.out.println("数据的列数:"+columnCount);
         
          while(rs.next()){
        	  int 			  count		= 0;
        	  String[] 		  cols		= new String[columnCount];
        	  while(count<columnCount) {
        		  // 列数的内容;
        		  String pameter		=rs.getString((count+1)).toString();
        		  // 参数的添加;
        		  cols[count]			= pameter;
        		  count++;
        	  }
        	  list.add(cols);
         }
          stmt.close();
	     }catch(Exception e){
	        e.printStackTrace();
	     }
	    
	    long 				time2		= System.currentTimeMillis();
        long				timeDist	= time2-time1;
        System.out.println("数据库操作耗费:"+timeDist+"毫秒!");
        System.out.println("--------------------------------");
        
        return list;
   }
}
