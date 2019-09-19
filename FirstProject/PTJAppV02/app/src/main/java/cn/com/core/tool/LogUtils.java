package cn.com.core.tool;

import android.text.TextUtils;
import android.util.Log;

/**
 * Log日志打印
 * @author lijilong
 *
 */
public class LogUtils {
     
	private static String LOGTAG="handWrite";
	
	/**
	 * log.i
	 * @param info
	 */
	public static void i(String info){
		if(!TextUtils.isEmpty(info)){
			Log.i(LOGTAG, info);	
		}
	}
	
	
	/**
	 * log.d
	 * @param info
	 */
	public static void d(String info){
		if(!TextUtils.isEmpty(info)){
			Log.d(LOGTAG, info);	
		}
	}
	
	/**
	 * log.w
	 * @param info
	 */
	public static void w(String info){
		if(!TextUtils.isEmpty(info)){
			Log.w(LOGTAG, info);	
		}
	}
	
	
	/**
	 * log.d
	 * @param info
	 */
	public static void e(String info){
		if(!TextUtils.isEmpty(info)){
			Log.e(LOGTAG, info);	
		}
	}
}
