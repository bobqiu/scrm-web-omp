package com.youanmi.scrm.omp.common.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

import com.github.knightliao.apollo.utils.io.FileUtils;
import com.youanmi.scrm.omp.util.ImageUtils;

public class ImageNoSpringTest {

	//测试比例压缩
	@Test
	public void testImageCompress(){
		
		//开启流
		InputStream fromSource = null;
		OutputStream toSource = null;
		
		try {
			File f = new File("F:/a.jpg");
			fromSource = new FileInputStream(f);
			toSource = new FileOutputStream("F:/e1.jpg");
			//默认压缩；等比例压缩。b.jpg
//			ImageUtils.compress(fromSource, toSource);
			//设置值压缩--等比例。c.jpg
//			ImageUtils.compress(fromSource, toSource, "jpg", 100, 100, true);
			
			//设置值压缩--剪裁。d.jpg---没什么用，compress默认设不设置都好像是等比例压缩
//			ImageUtils.compress(fromSource, toSource, "jpg", 300, 100, false);
			
			//剪裁图片e.jpg
			ImageUtils.cutImage(fromSource, toSource, "jpg", 100, 20, 200, 300,300,500);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally{
			//关闭流
			FileUtils.closeInputStream(fromSource);
			FileUtils.closeOutputStream(toSource);
		}
		
	}
	
}
