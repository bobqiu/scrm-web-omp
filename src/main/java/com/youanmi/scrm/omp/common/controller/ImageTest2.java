package com.youanmi.scrm.omp.common.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class ImageTest2 {

	public static void main(String[] a) {
		ImageTest2.createStringMark("E:\\临时资料\\pic_test\\1.jpg", "hello_world", Color.white, 100, "E:\\临时资料\\pic_test\\1_bak.jpg");
	}

	// 给jpg添加文字
	public static boolean createStringMark(String filePath, String markContent, Color markContentColor, float qualNum, String outPath) {
//		ImageIcon imgIcon = new ImageIcon(filePath);
//		Image theImg = imgIcon.getImage();
//		int width = theImg.getWidth(null) == -1 ? 200 : theImg.getWidth(null);
//		int height = theImg.getHeight(null) == -1 ? 200 : theImg.getHeight(null);
//		System.out.println(width);
//		System.out.println(height);
//		System.out.println(theImg);
//		BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//		Graphics2D g = bimage.createGraphics();
//		g.setColor(markContentColor);
//		g.setBackground(Color.red);
//		g.drawImage(theImg, 0, 0, null);
//		g.setFont(new Font(null, Font.BOLD, 15)); // 字体、字型、字号
//		g.drawString(markContent, width / 2, height / 2); // 画文字
//		g.dispose();
//		try {
//			FileOutputStream out = new FileOutputStream(outPath); // 先用一个特定的输出文件名
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
//			param.setQuality(qualNum, true);
//			encoder.encode(bimage, param);
//			out.close();
//		} catch (Exception e) {
//			return false;
//		}
		return true;
	}
	
	public static byte[] createStringMarkBytes(String filePath, String markContent, Color markContentColor) throws IOException {
		ImageIcon imgIcon = new ImageIcon(filePath);
		Image theImg = imgIcon.getImage();
		int width = theImg.getWidth(null) == -1 ? 200 : theImg.getWidth(null);
		int height = theImg.getHeight(null) == -1 ? 200 : theImg.getHeight(null);
		System.out.println(width);
		System.out.println(height);
		System.out.println(theImg);
		BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bimage.createGraphics();
		g.setColor(markContentColor);
		g.setBackground(Color.red);
		g.drawImage(theImg, 0, 0, null);
		g.setFont(new Font(null, Font.BOLD, 15)); // 字体、字型、字号
		g.drawString(markContent, width / 2, height / 2); // 画文字
		g.dispose();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(bimage, "jpg", out);
		byte[] b = out.toByteArray();  
		return b;
	}
}