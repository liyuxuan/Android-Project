package com.tool;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class ImageUtil extends Util implements ImageUtils {

	public ImageUtil() {

	}

	public void thumbnail_w_h(File img, int width, int height, OutputStream out) throws IOException {
		BufferedImage bi = ImageIO.read(img);
		double srcWidth = bi.getWidth(); // 源图宽度
		double srcHeight = bi.getHeight(); // 源图高度

		double scale = 1;

		if (width > 0) {
			scale = width / srcWidth;
		}
		if (height > 0) {
			scale = height / srcHeight;
		}
		if (width > 0 && height > 0) {
			scale = height / srcHeight < width / srcWidth ? height / srcHeight : width / srcWidth;
		}

		thumbnail(img, (int) (srcWidth * scale), (int) (srcHeight * scale), out);

	}

	public static void thumbnail(File img, int width, int height, OutputStream out) throws IOException {
		BufferedImage BI = ImageIO.read(img);
		Image image = BI.getScaledInstance(width, height, Image.SCALE_SMOOTH);

		BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = tag.getGraphics();
		g.setColor(Color.RED);
		g.drawImage(image, 0, 0, null); // 绘制处理后的图
		g.dispose();
		ImageIO.write(tag, "JPEG", out);
	}

	public void compress(String from, String to){
		File img = new File(from);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(to);
			thumbnail_w_h(img, 1000, 1000, fos);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 读取图片并且拷贝到指定的文件夹;
	public String copyImageA2B(String frompath, String topath) {
		// 标签编号;
		String result = null;
		// 检测来源文件;
		File from = new File(frompath);

		if (from.isFile() && from.exists()) {

			// 检测去处的文件是否存在;
			File to = new File(topath);
			// 去处的文件是否存在;
			if (to.isFile() && to.exists()) {
				to.delete();
			}

			// 打开输入流
			FileInputStream fis;
			// 打开输出流
			FileOutputStream fos;
			try {
				fis = new FileInputStream(from);
				fos = new FileOutputStream(to);

				// 读取和写入信息
				int len = 0;
				while ((len = fis.read()) != -1) {
					fos.write(len);
				}

				// 关闭流 先开后关 后开先关
				fos.close(); // 后开先关
				fis.close(); // 先开后关

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// 返回标签;
		return result;
	}

}
