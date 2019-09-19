package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.tool.DataUtils;
import com.tool.Util;

public class Test {
	public static void main(String[] args) {
		long t1=System.currentTimeMillis();
		Util util = new Util();
		String path = Util.PATH2;
		util.checkFile(path);

		Test test = new Test();
		// 读取相应的文件夹;
		ArrayList<String> list=test.getFiles(path);
		long t2=System.currentTimeMillis();
		long td=t2-t1;
		System.out.println("耗時間:"+td+"毫秒");
	}

	private ArrayList<String> getFiles(String path) {
		File[]    allFiles = new File(path).listFiles();
		DataUtils dataUtils= new DataUtils();
		ArrayList<String> list=new ArrayList<String>();
		for (int i = 0; i < allFiles.length; i++) {
			File file = allFiles[i];
			String filename=file.getName();
			String path2=path+File.separator+filename;
			String temp= dataUtils.ImageToBase64(path2);
			String result="{\"filename\":\""+filename+"\",\"file\":\""+temp+"\"}";
			list.add(result);
		}
		return list;
	}

}
