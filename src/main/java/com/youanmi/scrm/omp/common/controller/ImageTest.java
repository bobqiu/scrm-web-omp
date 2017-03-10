package com.youanmi.scrm.omp.common.controller;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import com.youanmi.fastdfs.utils.FastDFSUtil;

public class ImageTest extends BaseJunit4Test {

	@Test
	public void testImageUpload() throws IOException {
		// 创建一个图片
		byte[] image = ImageTest2.createStringMarkBytes("E:\\临时资料\\pic_test\\1.jpg", "hello_world", Color.white);
		String testPaht = FastDFSUtil.uploadFile(image, "jpg", null);
		System.err.println("testPaht=" + testPaht);
	}

	
	@Test
	public void testUploadImage() throws FileNotFoundException{
		/*String bg_url = FastDFSUtil.uploadFile("E:\\临时资料\\pic_gc\\bg.png", null);
		System.err.println("背景图片url:"+bg_url);
		String coupon_teml = FastDFSUtil.uploadFile("E:\\临时资料\\pic_gc\\coupon_teml.jpg", null);
		System.err.println("优惠券模板url:"+coupon_teml);
		String shlogo = FastDFSUtil.uploadFile("E:\\临时资料\\pic_gc\\shlogo.jpg", null);
		System.err.println("商户logo_url:"+shlogo);*/
		
		//自定义图片
		/*String zdypic_url = FastDFSUtil.uploadFile("E:\\临时资料\\pic_gc\\zdypic.png", null);
		System.err.println("自定义图片url:"+zdypic_url);*/
	}
	
	@Test
	public void testRemoteComposeImage() throws FileNotFoundException, IOException {
		ComposeImageTest.composeImage3();
	}

}
