package com.youanmi.scrm.omp.util;

import com.youanmi.commons.exceptions.BaseException;
import com.youanmi.scrm.omp.constants.ResultCode;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.http.MediaType;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * image工具类
 * 
 * @author Administrator
 *
 */
public class ImageUtils {

    /**
     * 缩略图宽度
     */
    public final static int THUM_WIDTH = 200;

    /**
     * 缩略图高度
     */
    public final static int THUM_HEIGHT = 200;

    /**
     * formatName=jpg
     */
    public static final String FORMAT_NAME = "jpg";

    /**
     * 默认的最大宽度
     */
    private final static int DEFAULT_MAX_WIDTH = 720;

    /**
     * 最大高度 omp品牌型号图片上传
     */
    private final static int BRAND_MODEL_HEIGHT = 720;

    /**
     * 默认的最大高度
     */
    private final static int DEFAULT_MAX_HEIGHT = 1280;


    /**
     * 获取图片mediaType
     * 
     * @param formatName
     * @param defaultValue
     * @return
     */
    public static MediaType getImageMediaType(String formatName, MediaType defaultValue) {
        formatName = formatName.toLowerCase();
        if (formatName.equals("jpeg") || formatName.equals("jpg")) {
            return MediaType.IMAGE_JPEG;
        }
        else if (formatName.equals("png")) {
            return MediaType.IMAGE_PNG;
        }
        else if (formatName.equals("gif")) {
            return MediaType.IMAGE_GIF;
        }
        return defaultValue;
    }


    /**
     * 获取format
     * 
     * @param o
     * @return
     */
    public static String getFormatName(Object o) {
        ImageInputStream iis = null;
        try {
            iis = ImageIO.createImageInputStream(o);
            Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
            if (!iter.hasNext()) {
                throw new BaseException(ResultCode.IMG_FORMART_ERROR);
            }
            ImageReader reader = (ImageReader) iter.next();
            return reader.getFormatName().toLowerCase();
        }
        catch (IOException e) {
            throw new BaseException(ResultCode.IMG_FORMART_ERROR, e);
        }
        finally {
            if (iis != null) {
                try {
                    iis.close();
                }
                catch (IOException e) {
                    throw new BaseException(ResultCode.IO_CLOSE_ERROR, e);
                }
            }
        }
    }


    /**
     * 文件复制
     * 
     * @param s
     * @param t
     */
    public static void fileChannelCopy(File s, File t) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(s);
            fo = new FileOutputStream(t);
            // 得到对应的文件通道
            in = fi.getChannel();
            // 得到对应的文件通道
            out = fo.getChannel();
            // 连接两个通道，并且从in通道读取，然后写入out通道
            in.transferTo(0, in.size(), out);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (fi != null) {
                    fi.close();
                }
                if (in != null) {
                    in.close();
                }
                if (fo != null) {
                    fo.close();
                }
                if (out != null) {
                    out.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * compress方法
     * 
     * @param fromSource
     * @param toSource
     * @param formatName
     * @return
     * @throws IOException
     */
    public static ImageSize compress(InputStream fromSource, OutputStream toSource, String formatName)
            throws IOException {
        return compress(fromSource, toSource, formatName, DEFAULT_MAX_WIDTH, DEFAULT_MAX_HEIGHT);
    }


    /**
     * compress方法
     * 
     * @param fromSource
     * @param toSource
     * @param formatName
     * @param width
     * @param height
     * @return
     * @throws IOException
     */
    public static ImageSize compress(InputStream fromSource, OutputStream toSource, String formatName,
            int width, int height) throws IOException {

        return compress(fromSource, toSource, formatName, width, height, true);
    }


    /**
     * compress方法
     * 
     * @param fromSource
     * @param toSource
     * @param formatName
     * @param width
     * @param height
     * @param needCalcRate
     *            是否需要计算比例
     * @return
     * @throws IOException
     */
    public static ImageSize compress(InputStream fromSource, OutputStream toSource, String formatName,
            int width, int height, boolean needCalcRate) throws IOException {

        BufferedImage bi = ImageIO.read(fromSource);
        int width1 = width;
        int height1 = height;
        if (needCalcRate) {
            double rate = 1;
            if (bi.getWidth() > width || bi.getHeight() > height) {
                if (bi.getWidth() > bi.getHeight()) {
                    rate = (double) width / bi.getWidth();
                }
                else {
                    double hRate = (double) height / bi.getHeight();
                    if (bi.getWidth() * hRate > width) {
                        rate = (double) width / bi.getWidth();
                    }
                    else {
                        rate = hRate;
                    }
                }
            }
            width1 = Double.valueOf(bi.getWidth() * rate).intValue();
            height1 = Double.valueOf(bi.getHeight() * rate).intValue();
        }
        Thumbnails.of(bi).size(width1, height1).outputFormat(formatName).toOutputStream(toSource);
        return new ImageSize(width, height);
    }

    /**
     * compress方法
     * 
     * @param bi
     * @param toSource
     * @param formatName
     * @param width
     * @param height
     * @param needCalcRate
     *            是否需要计算比例
     * @return
     * @throws IOException
     */
    public static ImageSize compress(BufferedImage bi, OutputStream toSource, String formatName,
            int width, int height, boolean needCalcRate) throws IOException {

        int width1 = width;
        int height1 = height;
        if (needCalcRate) {
            double rate = 1;
            if (bi.getWidth() > width || bi.getHeight() > height) {
                if (bi.getWidth() > bi.getHeight()) {
                    rate = (double) width / bi.getWidth();
                }
                else {
                    double hRate = (double) height / bi.getHeight();
                    if (bi.getWidth() * hRate > width) {
                        rate = (double) width / bi.getWidth();
                    }
                    else {
                        rate = hRate;
                    }
                }
            }
            width1 = Double.valueOf(bi.getWidth() * rate).intValue();
            height1 = Double.valueOf(bi.getHeight() * rate).intValue();
        }
        Thumbnails.of(bi).size(width1, height1).outputFormat(formatName).toOutputStream(toSource);
        return new ImageSize(width, height);
    }

    /**
     * compressKeepAspectRatio方法
     * 
     * @param fromSource
     * @param toSource
     * @param formatName
     * @param width
     * @param height
     * @return
     * @throws IOException
     */
    public static ImageSize compressKeepAspectRatio(InputStream fromSource, OutputStream toSource,
            String formatName, int width, int height) throws IOException {

        BufferedImage bi = ImageIO.read(fromSource);
        Thumbnails.of(bi).size(width, height).keepAspectRatio(false).outputFormat(formatName)
            .toOutputStream(toSource);
        return new ImageSize(width, height);
    }


    /**
     * 
     * compress方法
     * 
     * @param fromSource
     * @param toSource
     * @return
     * @throws IOException
     */
    public static ImageSize compress(InputStream fromSource, OutputStream toSource) throws IOException {
        return compress(fromSource, toSource, DEFAULT_MAX_WIDTH, DEFAULT_MAX_HEIGHT);
    }


    /**
     * compress方法
     * 
     * @param fromSource
     * @param toSource
     * @param width
     * @param height
     * @return
     * @throws IOException
     */
    public static ImageSize compress(InputStream fromSource, OutputStream toSource, int width, int height)
            throws IOException {
        return compress(fromSource, toSource, FORMAT_NAME, width, height);
    }


    /**
     * 创建缩略图
     * 
     * @param fromFile
     * @param toFile
     * @param width
     *            原图大小
     * @param height
     *            原图大小
     * 
     * @return
     * @throws Exception
     */
    public static ImageSize createThumbnail(InputStream fromFile, OutputStream toFile, int width, int height)
            throws Exception {
        BufferedImage bi = ImageIO.read(fromFile);
        Thumbnails.of(bi).size(width, height).outputFormat(FORMAT_NAME).toOutputStream(toFile);
        return new ImageSize(width, height);
    }


    /**
     * 创建缩略图
     * 
     * @param fromFile
     * @param toFile
     * @param imageSize
     *            原图大小
     * 
     * @return
     * @throws Exception
     */
    public static ImageSize createThumbnail(InputStream fromFile, OutputStream toFile, ImageSize imageSize)
            throws Exception {
        int r = 0;
        if (imageSize.getWidth() > imageSize.getHeight()) {
            r = imageSize.getHeight();
        }
        else {
            r = imageSize.getWidth();
        }
        Thumbnails.of(fromFile).sourceRegion(0, 0, r, r).size(THUM_WIDTH, THUM_HEIGHT)
            .outputFormat(FORMAT_NAME).toOutputStream(toFile);
        return new ImageSize(THUM_WIDTH, THUM_HEIGHT);
    }


    /**
     * 获取图片地址
     * 
     * @param tableName
     * @param id
     * @return
     */
    public static String getImageUrl(ImageTableName tableName, String id) {
        return "image/" + tableName + "/" + id + "." + FORMAT_NAME;
    }


    /**
     * main测试方法
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        File fromFile = new File("E:/a.png");
        // BufferedImage image = ImageIO.read(fromFile);
        compress(new FileInputStream(fromFile), new FileOutputStream(new File("E:/a1.png")));

    }

    /**
     * 图片尺寸类
     * 
     * @author Administrator
     *
     */
    public static class ImageSize {

        private Integer width;

        private Integer height;


        /**
         * 构造函数。
         * 
         * @param width
         * @param height
         */
        public ImageSize(Integer width, Integer height) {
            this.width = width;
            this.height = height;
        }


        public Integer getWidth() {
            return width;
        }


        public void setWidth(Integer width) {
            this.width = width;
        }


        public Integer getHeight() {
            return height;
        }


        public void setHeight(Integer height) {
            this.height = height;
        }

    }

    public static class ImageInfo {
        private Integer width;

        private Integer height;

        private String formatName;

        private String url;


        public ImageInfo(Integer width, Integer height, String formatName, String url) {
            this.width = width;
            this.height = height;
            this.formatName = formatName;
            this.url = url;
        }


        public ImageInfo(ImageSize imageSize, String formatName, String url) {
            this.width = imageSize.getWidth();
            this.height = imageSize.getHeight();
            this.formatName = formatName;
            this.url = url;
        }


        public Integer getWidth() {
            return width;
        }


        public void setWidth(Integer width) {
            this.width = width;
        }


        public Integer getHeight() {
            return height;
        }


        public void setHeight(Integer height) {
            this.height = height;
        }


        public String getFormatName() {
            return formatName;
        }


        public void setFormatName(String formatName) {
            this.formatName = formatName;
        }


        public String getUrl() {
            return url;
        }


        public void setUrl(String url) {
            this.url = url;
        }


        public Map<String, Object> toMap() {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("width", width);
            map.put("height", height);
            map.put("formatName", formatName);
            map.put("url", url);
            return map;
        }
    }

    public enum ImageTableName {
        goods_image,
        goods_small_image,
        head_image,
        icon;
    }
    /**
     * 
     * 直接上传文件到指定目录
     * 
     * @param fromSource
     * @param toSource
     * @param ext
     * @throws IOException
     */
    public static void upLoadTFilel(InputStream fromSource, OutputStream toSource, String ext)
            throws IOException {
        BufferedImage bi = ImageIO.read(fromSource);
        int width1 = Double.valueOf(bi.getWidth() * 1).intValue();
        int height1 = Double.valueOf(bi.getHeight() * 1).intValue();
        Thumbnails.of(bi).size(width1, height1).outputFormat(ext).toOutputStream(toSource);
    }
    
    /**
     * x,y,w,h表示需要剪裁的区域,如果width>大于w或者height>h将会放大图片，这里默认也是等比例压缩。
     * @param fromSource
     * @param toSource
     * @param formatName
     * @param x
     * @param y
     * @param w
     * @param h
     * @param width					实际剪裁宽度
     * @param height				实际剪裁高度
     * @return
     * @throws IOException
     */
	public static ImageSize cutImage(InputStream fromSource, OutputStream toSource, String formatName, int x, int y, int w, int h,int width,int height) throws IOException {
		BufferedImage bi = ImageIO.read(fromSource);
		Thumbnails.of(bi).sourceRegion(x, y, w, h).size(width,height).outputFormat(formatName).toOutputStream(toSource);
		return new ImageSize(w, h);
	} 
    
}
