package com.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Objects;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;



public class DataUtils {
	public DataUtils() {
		super();
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
	
	// 图片转化为Base64的图片;
	public String ImageToBase64(InputStream in) {
	    byte[] data = null;
	    // 数据字符串;
	    try {
	        data = new byte[in.available()];
	        in.read(data);
	        in.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    // Base64;
	    BASE64Encoder encoder = new BASE64Encoder();
	    // Base64进行转码;
	    return encoder.encode(Objects.requireNonNull(data));
	}
	
	//	Base64转化为图片;
	public boolean Base64ToImage(String foldername,String filename,String imgStr){
		// 图像不为空;
        if (imgStr == null)   
            return false;  
        BASE64Decoder decoder = new BASE64Decoder();

        // 进行数据处理的主方法;
        try{  
            // Base64;
            byte[] b = decoder.decodeBuffer(imgStr);  
            for(int i=0;i<b.length;++i){
            	//	字节进行数据的转化;
                if(b[i]<0){  
                    b[i]+=256;  
                }  
            } 
            
            File file=new File(foldername);
            if(!file.exists())
            	file.mkdirs();
            
            // 图像的新生逻辑;  
            String imgFilePath =foldername+File.separator+filename+".jpg";
            
            OutputStream out=new FileOutputStream(imgFilePath);
            
            out.write(b);

            out.flush();  
            out.close();  

            return true;  
        }catch (Exception e){  
            return false;  
        }  
    }
	public String StringFromBase64(String s) { 
		if (s == null) 
			return null; 
		BASE64Decoder decoder = new BASE64Decoder(); 
		try { 
			byte[] b = decoder.decodeBuffer(s); 
			return new String(b); 
		} catch (Exception e) { 
			return null; 
		} 
	}
	
	//	寻找出最新的人脸标签;
	public String checkLasterFaceIndex(String folder,String idcard) {
		String filename	=	"";
		folder	 =	folder+"\\"+idcard;
		File file=	new File(folder);
		if(file!=null) {
			String[] names = file.list();
			for(String name:names) {
				if(!name.contains(idcard)) {
					filename=folder+"\\"+name;
					break;
				}
			}
		}

		return filename;
	}
	// 文件读取功能;
	public String fileRead(String path,String format){
		//	获得结果的字符串;
		String result = "";
		//	进行数据的读取;
		File file=new File(path);
		//	判断文件的存在与否;
		if(file.isFile()&&file.exists()) {

	        InputStreamReader    reader;
	        BufferedReader 		 bReader;
	
			try {
				reader = new InputStreamReader (new FileInputStream(file),format);
				
				//  文件的读取功能;
		        bReader 		 = new BufferedReader(reader);
		        StringBuilder sb = new StringBuilder();
		        String 		  s  = "";
		        
		        //	进行文件的读取;
		        while ((s = bReader.readLine()) != null) {
		            sb.append(s + "\r\n");
		        }
		        //	关闭文件读取的标签;
		        bReader.close();
		        result 	= sb.toString();
		        
		        //	将字符串获得的最后一个回车换行符进行替换;
		        String eol = "\r\n";
		        int eolLen = eol.length();
		        result 	   = result.substring(0, result.length()-eolLen);
		        
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		//	将文末的回车替换掉;
        return result;
    }

}
