/*
 * 文件名：ImgCutUtil.java
 * 版权：Copyright 2015 youanmi Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ImgCutUtil.java
 * 修改人：刘红艳
 * 修改时间：2015年5月11日
 * 修改内容：新增
 */
package com.youanmi.scrm.omp.util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;

import com.alibaba.fastjson.util.IOUtils;
import com.youanmi.fastdfs.utils.FastDFSUtil;
import com.youanmi.scrm.commons.util.file.LocalFileUtils;


/**
 * 图片裁剪工具类。
 * <p>
 * <p>
 * 
 * <pre>
 * </pre>
 * 
 * @author 刘红艳
 * @version YouAnMi-OTO 2015年5月11日
 * @since YouAnMi-OTO
 */
public class ImgCutUtil {
    /**
     * 调测日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(ImgCutUtil.class);

    /**
     * logg_icon地址
     */
    public static final String LOGG_ICON_ADDR = "temp/img/icon.png";


    /**
     * 
     * 从远程服务获取文件流。
     * 
     * @param remotePath
     * @return
     * @throws IOException
     */
    public static InputStream getPicIns(String remotePath) throws IOException {
        LOG.info("Cut img getPicIns:" + remotePath);
        URL url = null;
        InputStream is = null;
        url = new URL(remotePath);
        // 打开连接
        URLConnection con;
        con = url.openConnection();
        LOG.info("Cut img getPicIns openConnection" + remotePath);
        // 输入流
        is = con.getInputStream();
        LOG.info("Cut img getPicIns:" + remotePath);
        return is;
    }


    /**
     * 对图片裁剪，并把裁剪完成的新图片保存到fastdf 。
     * 
     * @param cutParam
     *            裁剪参数
     * @return 返回的文件地址
     * @throws IOException
     */
    public static String cutByUrl(CutImageDTO cutParam) throws IOException {
        /**
         ** @param remotePath
         *            源图片路径
         * @param x
         *            剪切点x坐标
         * @param y
         *            剪切点y坐标
         * @param width
         *            剪切宽度
         * @param height
         *            剪切高度
         */
        String remotePath = cutParam.getUrl();
        LOG.info("Cut img by url:" + remotePath);
        int x = cutParam.getPositionx();
        int y = cutParam.getPositiony();
        int width = cutParam.getWidth();
        int height = cutParam.getHeight();
        InputStream is = null;
        ImageInputStream iis = null;
        // 剪切图片存放路径
        String subpath = null;
        // logo剪切图片存放路径
        String logoSubpath = null;
        try {
            // 读取图片文件
            is = getPicIns(remotePath);
            LOG.info("Cut img getPicIns over url:" + remotePath);
            /*
             * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 声称能够解码指定格式。
             * 参数：formatName - 包含非正式格式名称 .（例如 "jpeg" 或 "tiff"）等 。
             */
            String ext = remotePath.substring(remotePath.lastIndexOf(".") + 1);
            String fileEnd = "";
            if (!AssertUtils.isNull(ext)) {
                fileEnd = "." + ext;
            }
            subpath = ImageUploadUtils.getSysTempPath() + File.separator + CommonUtils.getImgName() + fileEnd;
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(ext);
            ImageReader reader = it.next();
            LOG.info("Cut img reader.");
            // 获取图片流
            iis = ImageIO.createImageInputStream(is);
            LOG.info("Cut img reader over.");
            /*
             * <p>iis:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索’。
             * 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
             */
            reader.setInput(iis, true);
            /*
             * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O
             * 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件 将从其 ImageReader 实现的
             * getDefaultReadParam 方法中返回 ImageReadParam 的实例。
             */
            ImageReadParam param = reader.getDefaultReadParam();
            /**//*
                  * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象
                  * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。
                  */
            Rectangle rect = new Rectangle(x, y, width, height);
            // 提供一个 BufferedImage，将其用作解码像素数据的目标。
            param.setSourceRegion(rect);
            /**//*
                  * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 它作为一个完整的
                  * BufferedImage 返回。
                  */
            BufferedImage bi = reader.read(0, param);
            // 保存新图片
            LOG.info("Cut img write start");
            ImageIO.write(bi, ext, new File(subpath));
            LOG.info("Cut img write to :" + subpath);
            String iconPath = getWebPath();

            logoSubpath =
                    ImageUploadUtils.getSysTempPath() + File.separator + CommonUtils.getImgName() + fileEnd;
            LOG.info("draw img logo icon.");
            ImageMarkLogoByIcon.markImageByIcon(iconPath, subpath, logoSubpath);
            LOG.info("draw img logo icon over.");
            // 将裁剪的图片上传到文件服务器
            LOG.info("uploadFile start.");
            String imgPath = FastDFSUtil.uploadFile(subpath, null);
            LOG.info("uploadFile over.");
            return imgPath;
        }
        finally {
            IOUtils.close(is);
            IOUtils.close(iis);
            // 上传文件后删除临时文件
            LocalFileUtils.delFile(subpath);
            // 上传文件后删除临时文件
            // LocalFileUtils.delFile(logoSubpath);

        }
    }


    /**
     * 对图片裁剪然后拉升，并把裁剪完成的新图片保存到fastdf 。
     * 
     * @param cutParam
     *            裁剪参数
     * @param compressWidth
     *            剪裁宽度
     * @param compressHeight
     *            剪裁高度
     * @param needCalcRate
     *            是否需要计算剪裁比例
     * @return 返回的文件地址
     * @throws IOException
     */
    public static String cutByUrlAndCompress(CutImageDTO cutParam, int compressWidth, int compressHeight,
            boolean needCalcRate) throws IOException {
        /**
         ** @param remotePath
         *            源图片路径
         * @param x
         *            剪切点x坐标
         * @param y
         *            剪切点y坐标
         * @param width
         *            剪切宽度
         * @param height
         *            剪切高度
         */
        String remotePath = cutParam.getUrl();
        LOG.info("Cut img by url:" + remotePath);
        int x = cutParam.getPositionx();
        int y = cutParam.getPositiony();
        int width = cutParam.getWidth();
        int height = cutParam.getHeight();
        InputStream is = null;
        ImageInputStream iis = null;
        OutputStream compressOs = null;
        // 剪切图片存放路径
        String subpath = null;
        // logo剪切图片存放路径
        String logoSubpath = null;
        try {
            // 读取图片文件
            is = getPicIns(remotePath);
            LOG.info("Cut img getPicIns over url:" + remotePath);
            /*
             * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 声称能够解码指定格式。
             * 参数：formatName - 包含非正式格式名称 .（例如 "jpeg" 或 "tiff"）等 。
             */
            String ext = remotePath.substring(remotePath.lastIndexOf(".") + 1);
            String fileEnd = "";
            if (!AssertUtils.isNull(ext)) {
                fileEnd = "." + ext;
            }
            subpath = ImageUploadUtils.getSysTempPath() + File.separator + CommonUtils.getImgName() + fileEnd;
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(ext);
            ImageReader reader = it.next();
            LOG.info("Cut img reader.");
            // 获取图片流
            iis = ImageIO.createImageInputStream(is);
            LOG.info("Cut img reader over.");
            /*
             * <p>iis:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索’。
             * 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
             */
            reader.setInput(iis, true);
            /*
             * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O
             * 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件 将从其 ImageReader 实现的
             * getDefaultReadParam 方法中返回 ImageReadParam 的实例。
             */
            ImageReadParam param = reader.getDefaultReadParam();
            /**//*
                  * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象
                  * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。
                  */
            Rectangle rect = new Rectangle(x, y, width, height);
            // 提供一个 BufferedImage，将其用作解码像素数据的目标。
            param.setSourceRegion(rect);
            /**//*
                  * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 它作为一个完整的
                  * BufferedImage 返回。
                  */
            BufferedImage bi = reader.read(0, param);
            // 保存新图片
            LOG.info("Cut img write start");
            ImageIO.write(bi, ext, new File(subpath));
            LOG.info("Cut img write to :" + subpath);

            compressOs = new FileOutputStream(new File(subpath));

            // 压缩图片
            ImageUtils.compress(bi, compressOs, ext, compressWidth, compressHeight, needCalcRate);

            String iconPath = getWebPath();

            logoSubpath =
                    ImageUploadUtils.getSysTempPath() + File.separator + CommonUtils.getImgName() + fileEnd;
            LOG.info("draw img logo icon.");
            ImageMarkLogoByIcon.markImageByIcon(iconPath, subpath, logoSubpath);
            LOG.info("draw img logo icon over.");
            // 将裁剪的图片上传到文件服务器
            LOG.info("uploadFile start.");
            String imgPath = FastDFSUtil.uploadFile(subpath, null);
            LOG.info("uploadFile over.");
            return imgPath;
        }
        finally {
            IOUtils.close(compressOs);
            IOUtils.close(is);
            IOUtils.close(iis);
            // 上传文件后删除临时文件
            LocalFileUtils.delFile(subpath);
            // 上传文件后删除临时文件
            // LocalFileUtils.delFile(logoSubpath);

        }
    }


    /**
     * 获取webapp路径
     * 
     * @return
     */
    private static String getWebPath() {
        String path = ImgCutUtil.class.getResource("/").getPath();
        return path.substring(0, path.length() - 8) + LOGG_ICON_ADDR;
    }


    /**
     * 从base64获取文件流。
     * 
     * @param base64
     * @return
     * @throws IOException
     * @author hy 2015年9月24日 19:03:47
     */
    public static InputStream getPicInsByBase64(String base64) throws IOException {
        String chkStr = "base64,";
        int i = base64.indexOf("base64,");
        if (i != -1) {
            base64 = base64.substring(i + chkStr.length());
        }
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] decoderBytes = decoder.decodeBuffer(base64);
        InputStream is = new ByteArrayInputStream(decoderBytes);
        return is;
    }


    /**
     * 对图片裁剪，并把裁剪完成的新图片保存到fastdf 。
     * 
     * @param cutParam
     *            裁剪参数
     * @return 返回的文件地址
     * @throws IOException
     * @author hy 2015年9月24日 19:04:03
     */
    public static String cut(CutImageDTO cutParam) throws IOException {
        if (AssertUtils.notNull(cutParam.getUrl())) {
            return cutByUrl(cutParam);
        }
        if (AssertUtils.notNull(cutParam.getBase64())) {
            return cutByBase64(cutParam);
        }
        return null;
    }


    /**
     * 对图片裁剪，并把裁剪完成的新图片保存到fastdf 。
     * 
     * @param cutParam
     *            裁剪参数
     * @return 返回的文件地址
     * @throws IOException
     * @author hy 2015年9月24日 19:04:22
     */
    private static String cutByBase64(CutImageDTO cutParam) throws IOException {
        /**
         * @param base64
         *            base64内容
         * @param x
         *            剪切点x坐标
         * @param y
         *            剪切点y坐标
         * @param width
         *            剪切宽度
         * @param height
         *            剪切高度
         */
        String base64 = cutParam.getBase64();
        int x = cutParam.getPositionx();
        int y = cutParam.getPositiony();
        int width = cutParam.getWidth();
        int height = cutParam.getHeight();
        InputStream is = null;
        ImageInputStream iis = null;
        String subpath = null;// 剪切图片存放路径
        // logo剪切图片存放路径
        String logoSubpath = null;
        try {
            // 读取图片文件
            is = getPicInsByBase64(base64);
            String ext = "jpg";// remotePath.substring(remotePath.lastIndexOf(".")
                               // + 1);
            if (base64.indexOf("image/png") != -1) {
                ext = "png";
            }
            else {
                ext = "jpg";
            }
            String fileEnd = (AssertUtils.isNull(ext) ? "" : ("." + ext));
            subpath = ImageUploadUtils.getSysTempPath() + File.separator + CommonUtils.getImgName() + fileEnd;
            // subpath = "D:/tmp/upload" + File.separator +
            // CommonUtils.getImgName() + fileEnd;
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(ext);
            ImageReader reader = it.next();
            // 获取图片流
            iis = ImageIO.createImageInputStream(is);
            reader.setInput(iis, true);
            ImageReadParam param = reader.getDefaultReadParam();
            Rectangle rect = new Rectangle(x, y, width, height);
            // 提供一个 BufferedImage，将其用作解码像素数据的目标。
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            // 保存新图片
            ImageIO.write(bi, ext, new File(subpath));

            String iconPath = getWebPath();
            logoSubpath =
                    ImageUploadUtils.getSysTempPath() + File.separator + CommonUtils.getImgName() + fileEnd;

            ImageMarkLogoByIcon.markImageByIcon(iconPath, subpath, logoSubpath);
            // 将裁剪的图片上传到文件服务器
            String imgPath = FastDFSUtil.uploadFile(subpath, null);
            return imgPath;
        }
        finally {
            IOUtils.close(is);
            IOUtils.close(iis);
            LocalFileUtils.delFile(subpath);// 上传文件后删除临时文件
        }
    }
}
