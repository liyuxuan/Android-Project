package com.tool;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import Decoder.BASE64Encoder;

public class SysUtil extends Util{
	// 短信验证标签;
	public static final int SMS_VERIFICATION_IN_LOGIN = 1;
	// 短信验证注册;
	public static final int SMS_VERIFICATION_IN_RESIGN = 2;

	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	// 进行短信验证的URL;
	private static final String MESSAGE_URL 	= "http://v.juhe.cn/sms/send";
	private static final String MESSAGE_TPL_ID 	= "173814";
	private static final String MESSAGE_APP_KEY = "2296191f4aa2ce5e13fb4fc5cb888859";

	// 人脸识别的URL;
	private static final String FACE_AK_ID1 	= "LTAIUrBVJPMGGtvq"; 
	private static final String FACE_AK_SECRET1 = "mRDhFzQgpLIolxbViRyFlGK56lt2vI"; 
	private static final String FACE_AK_URL 	= "https://dtplus-cn-shanghai.data.aliyuncs.com/face/verify";

	// IDCARD监测的主机;
	private static final String IDCARD_HOST		= "https://ocrsmsb.market.alicloudapi.com";
	// IDCARD监测的路径;
	private static final String IDCARD_PATH		= "/double_ocr/check";
	// IDCARD监测的方法;
	private static final String IDCARD_METHOD	= "POST";
	private static final String IDCARD_APPCODE 	= "4e9dfcecdb174b8d8b08b1d00d9b2896";
	
	
	// APPKEY的方式内容;
	public static final String APPKEY = "*************************";

	public SysUtil() {
		// TODO Auto-generated constructor stub
	}

	public String sendMessage(String mobile) {
		String result 	= null;
		//	文件处理的URL
		String url 		= MESSAGE_URL;
		String tpl_id 	= MESSAGE_TPL_ID;
		String app_key 	= MESSAGE_APP_KEY;
		int    code 	= (int) ((Math.random() * 9 + 1) * 10000);

		result 			= code + "";
//        Map<String,String> params = new HashMap();//�������
//            params.put("mobile",mobile);//���ն��ŵ��ֻ�����
//            params.put("tpl_id",tpl_id);//����ģ��ID����ο��������Ķ���ģ������
//            params.put("tpl_value","#code#="+code);//�������ͱ���ֵ�ԡ������ı��������߱���ֵ�д���#&=�е�����һ��������ţ����ȷֱ����urlencode������ٴ��ݣ�<a href="http://www.juhe.cn/news/index/id/50" target="_blank">��ϸ˵��></a>
//            params.put("key",app_key);//Ӧ��APPKEY(Ӧ����ϸҳ��ѯ)
//            params.put("dtype","");//�������ݵĸ�ʽ,xml��json��Ĭ��json
// 
//        try {
//            result =net(url, params, "GET");
//            JSONObject object = JSONObject.fromObject(result);
//            if(object.getInt("error_code")==0){
//                System.out.println(object.get("result"));
//                result=code+"";
//            }else{
//                System.out.println(object.get("error_code")+":"+object.get("reason"));
//                return null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }

		return result;
	}

	/**
	 *
	 * @param strUrl �����ַ
	 * @param params �������
	 * @param method ���󷽷�
	 * @return ���������ַ���
	 * @throws Exception
	 */
	public static String net(String strUrl, Map<String, Object> params, String method) throws Exception {
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String rs = null;
		try {
			StringBuffer sb = new StringBuffer();
			if (method == null || method.equals("GET")) {
				strUrl = strUrl + "?" + urlencode(params);
			}
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			if (method == null || method.equals("GET")) {
				conn.setRequestMethod("GET");
			} else {
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
			}
			conn.setRequestProperty("User-agent", userAgent);
			conn.setUseCaches(false);
			conn.setConnectTimeout(DEF_CONN_TIMEOUT);
			conn.setReadTimeout(DEF_READ_TIMEOUT);
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			if (params != null && method.equals("POST")) {
				try {
					DataOutputStream out = new DataOutputStream(conn.getOutputStream());
					out.writeBytes(urlencode(params));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			InputStream is = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sb.append(strRead);
			}
			rs = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return rs;
	}

	// url的encode;
	@SuppressWarnings("rawtypes")
	public static String urlencode(Map<String, Object> data) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	// 人脸识别;
	public String recognizeFace(String idcard, String face) {
		System.out.println("idcard="+idcard);
		// ak的POST方法;
		String ak_id1 		= FACE_AK_ID1;
		String ak_secret1 	= FACE_AK_SECRET1;
		String url 			= FACE_AK_URL;
		DataUtils dataUtils = new DataUtils();

		String path1 = dataUtils.checkLasterFaceIndex(SysUtil.PATH, idcard);
		// 将图像转换为Base64的相应内容;
		String orgin = dataUtils.ImageToBase64(path1);
		String result = "";

		// 进行图像比对的方法体;
		String body = "{\"type\": 1, \"content_1\":\"" + orgin + "\",\"content_2\":\"" + face + "\"}";
		try {
			result = sendPost(url, body, ak_id1, ak_secret1);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return result;
	}

	private String sendPost(String url, String body, String ak_id, String ak_secret) throws Exception {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		int statusCode = 200;
		try {
			URL realUrl = new URL(url);
			/*
			 * http header ����
			 */
			String method = "POST";
			String accept = "application/json";
			String content_type = "application/json";
			String path = realUrl.getFile();
			String date = toGMTString(new Date());

			// 1.body转换为MD5+BASE64编码;
			String bodyMd5 = MD5Base64(body);
			String stringToSign = method + "\n" + accept + "\n" + bodyMd5 + "\n" + content_type + "\n" + date + "\n"
					+ path;

			// 2.HMAC-SHA1
			String signature = HMACSha1(stringToSign, ak_secret);

			// 3.authorization header
			String authHeader = "Dataplus " + ak_id + ":" + signature;

			// 数据的网络连接的相应内容;
			URLConnection conn = realUrl.openConnection();

			// 设置网络连接的参数;
			conn.setRequestProperty("accept", accept);
			conn.setRequestProperty("content-type", content_type);
			conn.setRequestProperty("date", date);
			conn.setRequestProperty("Authorization", authHeader);

			// 网络连接的post方法;
			conn.setDoOutput(true);
			conn.setDoInput(true);

			// URLConnection网络连接的方法;
			out = new PrintWriter(conn.getOutputStream());
			// 将相应的方法体进行打印;
			out.print(body);
			// 将输出流进行清洗;
			out.flush();
			// BufferedReader将相应的数据处理信息进行操作;
			statusCode = ((HttpURLConnection) conn).getResponseCode();
			if (statusCode != 200) {
				in = new BufferedReader(new InputStreamReader(((HttpURLConnection) conn).getErrorStream()));
			} else {
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			}
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		if (statusCode != 200) {
			throw new IOException("\nHttp StatusCode: " + statusCode + "\nErrorMessage: " + result);
		}
		return result;
	}

	/*
	 * 1.javaScript将字符串内容进行处理;
	 */
	private String toGMTString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.UK);
		df.setTimeZone(new java.util.SimpleTimeZone(0, "GMT"));
		return df.format(date);
	}

	/*
	 * 2.获得MD5+BASE64的内容;
	 */
	public static String MD5Base64(String s) {
		if (s == null)
			return null;
		String encodeStr = "";
		byte[] utfBytes = s.getBytes();
		MessageDigest mdTemp;
		try {
			mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(utfBytes);
			byte[] md5Bytes = mdTemp.digest();
			BASE64Encoder b64Encoder = new BASE64Encoder();
			encodeStr = b64Encoder.encode(md5Bytes);
		} catch (Exception e) {
			throw new Error("Failed to generate MD5 : " + e.getMessage());
		}
		return encodeStr;
	}

	/*
	 * 3.HMAC-SHA1
	 */
	public static String HMACSha1(String data, String key) {
		String result;
		try {
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(data.getBytes());
			result = (new BASE64Encoder()).encode(rawHmac);
		} catch (Exception e) {
			throw new Error("Failed to generate HMAC : " + e.getMessage());
		}
		return result;
	}

	//	文件上传的路径;
	public String getUploadFile(String path, HttpServletRequest request, HttpServletResponse response) {
		//	磁盘的路径工厂;
		DiskFileItemFactory factory = new DiskFileItemFactory();

		String fileName = "";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		System.out.println("上传路径:"+path+"|是否存在="+file.exists());
		// 将磁盘的属性进行调节;
		factory.setRepository(new File(path));
		// 磁盘的缓存配置为1024x1024;
		factory.setSizeThreshold(1024 * 1024);
		// 文件上传的路径;
		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			// 上传的路径的;
			List<FileItem> list = (List<FileItem>) upload.parseRequest(request);
			for (FileItem item : list) {
				// 文件名称;
				String name = item.getFieldName();

				// 
				if (item.isFormField()) {
					// 
					String value = item.getString();
					request.setAttribute(name, value);
				}
				// 
				else {
					// 
					String value = item.getName();
					// 
					int start = value.lastIndexOf("\\");
					// 
					fileName = value.substring(start + 1);
					request.setAttribute(name, fileName);

					/*
					 * item.write(new File(path,filename));
					 */
					// 
					OutputStream out = new FileOutputStream(new File(path, fileName));
					System.out.println("..........." + fileName);

					InputStream in = item.getInputStream();

					int length = 0;
					byte[] buf = new byte[1024];

					while ((length = in.read(buf)) != -1) {
						out.write(buf, 0, length);
					}

					in.close();
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return path+File.separator+fileName;
	}
	
	//	进行Idcard的信息内容;
	public String getIdCardInfo(String filepath) {
		String 			result		= null;
		System.out.println("filepath="+filepath);
		// 
		String 			host   		= IDCARD_HOST;
		// 
 	    String 			path   		= IDCARD_PATH;
 	    // 
 	    String 			method 		= IDCARD_METHOD;
 	    String 			appcode 	= IDCARD_APPCODE;
 	    Map<String, String> headers = new HashMap<String, String>();
 	    
 	    // 
 	    headers.put("Authorization", "APPCODE " + appcode);
 	    //
 	    headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
 	    Map<String, String> querys  = new HashMap<String, String>();
 	    Map<String, String> bodys   = new HashMap<String, String>();
 	    HttpResponse 		response= null;
 	    // 
 	    DataUtils 		dataUtils   = new DataUtils();
 	    
 	    String 			data64	   	= dataUtils.ImageToBase64(filepath);

 	    bodys.put("image", data64);

 	    try {
 	    	
 	    	response 				= HttpUtils.doPost(host, path, method, headers, querys, bodys);
 	    	result					= EntityUtils.toString(response.getEntity());
 	    } catch (Exception e) {
 	    	e.printStackTrace();
 	    }
 	    File file=new File(filepath);
 	    if(file.exists()) file.delete();
 	    
		return result;
	} 

}
