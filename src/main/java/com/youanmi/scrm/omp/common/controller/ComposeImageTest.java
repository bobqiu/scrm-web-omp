package com.youanmi.scrm.omp.common.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.youanmi.fastdfs.utils.FastDFSUtil;

public class ComposeImageTest {

	/**
	 * 图片合成
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// composeImage1();

		// composeImage2();

	}
	
	public static void composeImage3() throws IOException, FileNotFoundException {
		// 远程图片合成
		byte[] in_bg = FastDFSUtil.getFile("http://ptest.youanmi.com/group1/M00/31/A7/wKgBC1jBA2GARpdJAAATlbQlUQs916.png");
		byte[] in_couponTeml = FastDFSUtil.getFile("http://ptest.youanmi.com/group1/M00/31/A7/wKgBC1jBA2GAJjQ6AAAYs1qVS_w238.jpg");
		byte[] sh_logo = FastDFSUtil.getFile("http://ptest.youanmi.com/group1/M00/31/A7/wKgBC1jBA2GAfn5sAAATo1KB5Bg936.jpg");
		byte[] zdypic = FastDFSUtil.getFile("http://ptest.youanmi.com/group1/M00/31/A7/wKgBC1jBBkmAUaQwAAAIGmgGXvo413.png");
		
		// 字节数组转BufferedImage
		ByteArrayInputStream bais_bg = new ByteArrayInputStream(in_bg);
		BufferedImage bi_bg = ImageIO.read(bais_bg);
		ByteArrayInputStream bais_couponTeml = new ByteArrayInputStream(in_couponTeml);
		BufferedImage bi_couponTeml = ImageIO.read(bais_couponTeml);
		ByteArrayInputStream bais_sh_logo = new ByteArrayInputStream(sh_logo);
		BufferedImage bi_shLogo = ImageIO.read(bais_sh_logo);
		ByteArrayInputStream bais_zdypic = new ByteArrayInputStream(zdypic);
		BufferedImage bi_zdypic = ImageIO.read(bais_zdypic);
		
		// 创建模板图片
		BufferedImage gc_pic = new BufferedImage(bi_bg.getWidth(), bi_bg.getHeight(), BufferedImage.TYPE_INT_RGB);

		// 创建画笔
		Graphics g = gc_pic.getGraphics();

		// 合成图片
		g.drawImage(bi_bg, 0, 0, null);
		g.drawImage(bi_couponTeml, 18, 68, null);
		g.drawImage(bi_shLogo, 110, 0, null);
		g.drawImage(bi_zdypic, 18, 187, null);
		
		// 添加文本
		g.setColor(Color.RED);
		Font font = new Font("宋体", Font.PLAIN, 12);
		g.setFont(font);
		g.drawString("100", 145, 92);
		g.drawString("10", 50, 105);
		g.drawString("请到店使用，限周一至周五", 27, 147);
		g.drawString("有效期：2017.02.15-2017.03.07", 50, 165);
		g.drawString("凭证码：XXXXXXX", 80, 282);
		g.dispose();

		// 输出图片--本地
		OutputStream outImage = new FileOutputStream("E:\\临时资料\\pic_test\\gc_pic.jpg");
		JPEGImageEncoder enc = JPEGCodec.createJPEGEncoder(outImage);
		enc.encode(gc_pic);

		// 输出图片--远程
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(gc_pic, "jpg", out);
		byte[] b = out.toByteArray();
		String remoatPath = FastDFSUtil.uploadFile(b, "jpg", null);
		System.out.println("远程合成图片路径：" + remoatPath);

		// 关闭资源
		bais_bg.close();
		bais_couponTeml.close();
		bais_sh_logo.close();
		bais_zdypic.close();
		outImage.close();
	}

	public static void composeImage2() throws IOException, FileNotFoundException {
		// 远程图片合成
		byte[] in_image1 = FastDFSUtil.getFile("http://ptest.youanmi.com/group1/M00/31/A5/wKgBC1i-XrmAL2EGAABKYZYkGp8327.jpg");
		byte[] in_image2 = FastDFSUtil.getFile("http://ptest.youanmi.com/group1/M00/31/A6/wKgBC1i-gnaAJEH6AABTA99tOms908.jpg");

		// 字节数组转BufferedImage
		ByteArrayInputStream in1 = new ByteArrayInputStream(in_image1);
		BufferedImage image1 = ImageIO.read(in1);
		ByteArrayInputStream in2 = new ByteArrayInputStream(in_image2);
		BufferedImage image2 = ImageIO.read(in2);

		// 创建模板图片
		BufferedImage bg = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);

		// 创建画笔
		Graphics g = bg.getGraphics();

		// 合成图片
		g.drawImage(image1, 0, 0, null);
		g.drawImage(image2, image1.getWidth() + 10, image1.getHeight() + 10, null);

		// 添加文本
		g.setColor(Color.RED);
		g.drawString("helloworld", 300, 300);
		g.drawString("柚安米，你好", 500, 500);
		g.dispose();

		// 输出图片--本地
		OutputStream outImage = new FileOutputStream("E:\\临时资料\\pic_test\\composeImage.jpg");
		JPEGImageEncoder enc = JPEGCodec.createJPEGEncoder(outImage);
		enc.encode(bg);

		// 输出图片--远程
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(bg, "jpg", out);
		byte[] b = out.toByteArray();
		String remoatPath = FastDFSUtil.uploadFile(b, "jpg", null);
		System.out.println("远程合成图片路径：" + remoatPath);

		// 关闭资源
		in1.close();
		in2.close();
		outImage.close();
	}

	// 本地图片合成
	private static void composeImage1() throws FileNotFoundException, IOException {
		int i = 1;
		int j = 2;
		InputStream beijing = new FileInputStream("E:\\临时资料\\pic_test\\bj.png");
		InputStream imagein = new FileInputStream("E:\\临时资料\\pic_test\\" + i + ".jpg");
		InputStream imagein2 = new FileInputStream("E:\\临时资料\\pic_test\\" + j + ".jpg");
		BufferedImage beijingImag = ImageIO.read(beijing);
		BufferedImage image = ImageIO.read(imagein);
		BufferedImage image2 = ImageIO.read(imagein2);
		Graphics g = beijingImag.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.drawImage(image2, 100, 500, null);
		OutputStream outImage = new FileOutputStream("E:\\临时资料\\pic_test\\" + "custom" + j + "-" + i + ".jpg");
		JPEGImageEncoder enc = JPEGCodec.createJPEGEncoder(outImage);
		enc.encode(beijingImag);
		imagein.close();
		imagein2.close();
		outImage.close();
	}
}
