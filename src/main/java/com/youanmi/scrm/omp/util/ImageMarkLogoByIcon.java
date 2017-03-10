/*
 * 文件名：ImageMarkLogoByIcon.java
 * 版权：Copyright 2015 youanmi Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ImageMarkLogoByIcon.java
 * 修改人：chenwenlong
 * 修改时间：2015年9月17日
 * 修改内容：新增
 */
package com.youanmi.scrm.omp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 图片水印工具类
 * 
 * <pre>
 * </pre>
 * 
 * @author chenwenlong
 * @version YouAnMi-OTO 2015年9月17日
 * @since YouAnMi-OTO
 */
public class ImageMarkLogoByIcon
{
    /**
     * 调测日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(ImageMarkLogoByIcon.class);
    
    /**
     * // 水印透明度
     */
    private static float alpha = 0.5f;
    
    // 水印横向位置
    private static int positionWidth = 150;
    
    // 水印纵向位置
    private static int positionHeight = 300;
    
    // 水印文字字体
    private static Font font = new Font("微软雅黑", Font.ITALIC, 30);
    
    // 水印文字颜色
    private static Color color = Color.darkGray;
    
    /**
     * 设置水印参数
     * 
     * @param apa
     *            水印透明度
     * @param width
     *            水印横向位置
     * @param height
     *            水印纵向位置
     * @param ft
     *            水印文字字体
     * @param c
     *            水印文字颜色
     */
    public static void setImageMarkOptions(float apa, int width, int height, Font ft, Color c)
    {
        if (alpha != 0.0f)
        {
            ImageMarkLogoByIcon.alpha = apa;
        }
        if (width != 0)
        {
            ImageMarkLogoByIcon.positionWidth = width;
        }
        if (height != 0)
        {
            ImageMarkLogoByIcon.positionHeight = height;
        }
        if (ft != null)
        {
            ImageMarkLogoByIcon.font = ft;
        }
        if (c != null)
        {
            ImageMarkLogoByIcon.color = c;
        }
    }
    
    /**
     * 给图片添加水印图片
     * 
     * @param iconPath
     *            水印图片路径
     * @param srcImgPath
     *            源图片路径
     * @param targerPath
     *            目标图片路径
     */
    public static void markImageByIcon(String iconPath, String srcImgPath, String targerPath)
    {
        //markImageByIcon(iconPath, srcImgPath, targerPath, null);
    }
    
    /**
     * 给图片添加水印图片、可设置水印图片旋转角度
     * 
     * @param iconPath
     *            水印图片路径
     * @param srcImgPath
     *            源图片路径
     * @param targerPath
     *            目标图片路径
     * @param degree
     *            水印图片旋转角度
     */
    public static void markImageByIcon(String iconPath, String srcImgPath, String targerPath, Integer degree)
    {
        OutputStream os = null;
        try
        {
            
            Image srcImg = ImageIO.read(new File(srcImgPath));
            
            // icon大小80X24 距边框20像素
            positionWidth = srcImg.getWidth(null) - 100;
            positionHeight = srcImg.getHeight(null) - 44;
            BufferedImage buffImg =
                new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
            
            // 1、得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            
            // 2、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
            // 3、设置水印旋转
            if (null != degree)
            {
                g.rotate(Math.toRadians(degree), (double)buffImg.getWidth() / 2, (double)buffImg.getHeight() / 2);
            }
            
            // 4、水印图片的路径 水印图片一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(iconPath);
            
            // 5、得到Image对象。
            Image img = imgIcon.getImage();
            
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            
            // 6、水印图片的位置
            g.drawImage(img, positionWidth, positionHeight, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            // 7、释放资源
            g.dispose();
            
            // 8、生成图片
            os = new FileOutputStream(targerPath);
            ImageIO.write(buffImg, "JPG", os);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (null != os)
                {
                    os.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 给图片添加水印文字
     * 
     * @param logoText
     *            水印文字
     * @param srcImgPath
     *            源图片路径
     * @param targerPath
     *            目标图片路径
     */
    public static void markImageByText(String logoText, String srcImgPath, String targerPath)
    {
        markImageByText(logoText, srcImgPath, targerPath, null);
    }
    
    /**
     * 给图片添加水印文字、可设置水印文字的旋转角度
     * 
     * @param logoText
     * @param srcImgPath
     * @param targerPath
     * @param degree
     */
    public static void markImageByText(String logoText, String srcImgPath, String targerPath, Integer degree)
    {
        
        InputStream is = null;
        OutputStream os = null;
        try
        {
            // 1、源图片
            Image srcImg = ImageIO.read(new File(srcImgPath));
            
            positionWidth = srcImg.getWidth(null) * 3 / 5;
            positionHeight = srcImg.getHeight(null) * 90 / 100;
            BufferedImage buffImg =
                new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
            
            // 2、得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            // 3、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
            // 4、设置水印旋转
            if (null != degree)
            {
                g.rotate(Math.toRadians(degree), (double)buffImg.getWidth() / 2, (double)buffImg.getHeight() / 2);
            }
            // 5、设置水印文字颜色
            g.setColor(color);
            // 6、设置水印文字Font
            g.setFont(font);
            // 7、设置水印文字透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 8、第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y)
            g.drawString(logoText, positionWidth, positionHeight);
            // 9、释放资源
            g.dispose();
            // 10、生成图片
            os = new FileOutputStream(targerPath);
            ImageIO.write(buffImg, "JPG", os);
            
            LOG.info("图片完成添加水印文字");
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (null != is)
                {
                    is.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            try
            {
                if (null != os)
                {
                    os.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 
     * 测试
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        String srcImgPath = "E:/image/aaa.jpg";
        String logoText = " 柚安米科技 ";
        String iconPath = "E:/image/ab.jpg";
        
        String targerTextPath = "E:/image/tt1.jpg";
        String targerTextPath2 = "E:/image/tt2.jpg";
        
        String targerIconPath = "E:/image/tt3.jpg";
        String targerIconPath2 = "E:/image/tt4.jpg";
        
        LOG.info("给图片添加水印文字开始...");
        // 给图片添加水印文字
        markImageByText(logoText, srcImgPath, targerTextPath);
        // 给图片添加水印文字,水印文字旋转-45
        markImageByText(logoText, srcImgPath, targerTextPath2, -15);
        LOG.info("给图片添加水印文字结束...");
        
        LOG.info("给图片添加水印图片开始...");
        setImageMarkOptions(0.3f, 1, 1, null, null);
        // 给图片添加水印图片
        markImageByIcon(iconPath, srcImgPath, targerIconPath);
        // 给图片添加水印图片,水印图片旋转-45
        markImageByIcon(iconPath, srcImgPath, targerIconPath2, -45);
        LOG.info("给图片添加水印图片结束...");
    }
}
