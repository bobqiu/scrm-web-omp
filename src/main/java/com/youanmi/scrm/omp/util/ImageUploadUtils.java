/*
 * 文件名：ImageUploadUtils.java
 * 版权：Copyright 2014 youanmi Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ImageUploadUtils.java
 * 修改人：刘红艳
 * 修改时间：2014年12月29日
 * 修改内容：新增
 */
package com.youanmi.scrm.omp.util;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.util.IOUtils;
import com.youanmi.commons.exceptions.ViewExternalDisplayException;
import com.youanmi.fastdfs.utils.FastDFSUtil;
import com.youanmi.scrm.commons.util.number.SysUniqueIdUtil;
import com.youanmi.scrm.omp.constants.ResultCode;


/**
 * 上传图片工具类。
 * <p>
 * 
 * <p>
 * 
 * 
 * <pre>
 * </pre>
 * 
 * @author 刘红艳
 * @version YouAnMi-OTO 2014年12月29日
 * @since YouAnMi-OTO
 */
public class ImageUploadUtils {
    /**
     * 调测日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(ImageUploadUtils.class);

    /** 文件存贮的临时目录 */
    private static final String TEMP_PATH = "sys.tempPath";

    private static String sysTempPath="temp";

    /** 缩略图宽 */
    private static final int THUM_WIDTH = 72;

    /** 缩略图高 */
    private static final int THUM_HEIGHT = 72;

    private static final String FORMAT_PNG = "png";

    static {
        init();
    }


    /**
     * 
     * 初始化文件存储临时目录。
     *
     */
    private static void init() {
        sysTempPath = CommonUtils.getSysConf(TEMP_PATH);
        File file = new File("/tmp/upload");
        if (!file.exists()) {
            try {
                FileUtils.forceMkdir(file);
            }
            catch (IOException e) {
                String msg = "Failed to init sysTempPath[" + sysTempPath + "]";
                LOG.error(msg, e);
                throw new RuntimeException(msg, e);
            }
        }
    }


    /**
     *
     * 检测文件。 所有大小检测如果非0则检测
     *
     * @param request
     *            .file 文件
     * @param valid
     *            valid.maxSize:最大字节数,valid.maxWidth:最大宽 ,valid.maxHeight:最大高,
     *            valid.isEquals:尺寸是否固定宽高
     * @throws IOException
     */
    public static void checkImg(HttpServletRequest request, String inName, ImageValidDTO valid) {
        if (valid == null) {
            return;
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获得图片（根据前台的name名称得到上传的文件）
        MultipartFile file = multipartRequest.getFile(inName);
        ImageUploadUtils.checkImg(file, valid);

    }


    /**
     *
     * 检测文件。 所有大小检测如果非0则检测
     *
     * @param file
     *            文件
     * @param valid
     *            valid.maxSize:最大字节数,valid.maxWidth:最大宽 ,valid.maxHeight:最大高,
     *            valid.isEquals:尺寸是否固定宽高
     * @throws IOException
     */
    public static void checkImg(MultipartFile file, ImageValidDTO valid) {
        if (file == null) {
            LOG.info("Check img is null");
            return;
        }
        long maxSize = valid.getMaxSize();
        int maxWidth = valid.getMaxWidth();
        int maxHeight = valid.getMaxHeight();
        boolean isEquals = valid.isEquals();
        // 文件大小检测
        if (maxSize != 0) {
            if (file.getSize() > maxSize) {
                throw new ViewExternalDisplayException(ResultCode.FILE_TOO_LARGE);
            }
        }
        // 文件尺寸检测
        if (maxWidth != 0 && maxHeight != 0) {
            BufferedImage bi;
            try {
                bi = ImageIO.read(file.getInputStream());
            }
            catch (IOException e) {
                throw new ViewExternalDisplayException(ResultCode.UNKNOWN_ERROR, e);
            }
            if (isEquals) {
                if (bi.getWidth() != maxWidth || bi.getHeight() != maxHeight) {
                    throw new ViewExternalDisplayException(ResultCode.IMG_SIZE_ERROR);
                }
            }
            else {
                if (bi.getWidth() > maxWidth || bi.getHeight() > maxHeight) {
                    throw new ViewExternalDisplayException(ResultCode.IMG_SIZE_ERROR);
                }
            }
        }
    }


    /**
     * 
     * WEB上传图片，将文件压缩后上传到FastDFS。
     * 
     * @param request
     * @param inputFileName
     *            上传的文件输入参数
     * @return 上传的路径（绝对路径）
     */
    public static String upload(HttpServletRequest request, String inputFileName) {
        // 转型为MultipartHttpRequest(重点的所在)
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获得图片（根据前台的name名称得到上传的文件）
        MultipartFile shopImage = multipartRequest.getFile(inputFileName);
        if (shopImage == null || shopImage.getSize() == 0) {
            return null;
        }
        String filename = shopImage.getOriginalFilename();
        String ext = filename.substring(filename.lastIndexOf(".") + 1, filename.length()).toLowerCase();
        String fileEnd = "";
        if (!AssertUtils.isNull(ext)) {
            fileEnd = "." + ext;
        }
        String localCompressfile = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;
        OutputStream os = null;
        InputStream is = null;

        try {
            is = shopImage.getInputStream();
            os = new FileOutputStream(new File(localCompressfile));
            ImageUtils.compress(is, os, ext);
        }
        catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Failed to addShopImage", e);
            }
            throw new ViewExternalDisplayException(ResultCode.IMG_UPLOAD_FAILD, e);
        }
        finally {
            IOUtils.close(is);
            IOUtils.close(os);
        }
        String imgPath = FastDFSUtil.uploadFile(localCompressfile, null);

        // 上传文件后删除临时文件
        LocalFileUtils.delFile(localCompressfile);
        return imgPath;
    }


    /**
     * 上传图片并压缩
     * 
     * @param url
     * @param compressWidth
     * @param compressHeight
     * @return
     */
    public static String upload(String url, int compressWidth, int compressHeight) {
        String ext = url.substring(url.lastIndexOf(".") + 1, url.length()).toLowerCase();
        String fileEnd = "";
        if (!AssertUtils.isNull(ext)) {
            fileEnd = "." + ext;
        }
        String localCompressfile = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;
        OutputStream os = null;
        InputStream is = null;

        try {
            is = new URL(url).openStream();
            os = new FileOutputStream(new File(localCompressfile));
            ImageUtils.compress(is, os, compressWidth, compressHeight);
        }
        catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Failed to addShopImage", e);
            }
            throw new ViewExternalDisplayException(ResultCode.IMG_UPLOAD_FAILD, e);
        }
        finally {
            IOUtils.close(is);
            IOUtils.close(os);
        }
        String imgPath = FastDFSUtil.uploadFile(localCompressfile, null);

        // 上传文件后删除临时文件
        LocalFileUtils.delFile(localCompressfile);
        return imgPath;
    }


    /**
     * 
     * 指定压缩图片的宽度和高度
     * 
     * @param request
     * @param inputFileName
     * @param width
     * @param height
     * @return
     */
    public static String upload(HttpServletRequest request, String inputFileName, int width, int height) {
        return upload(request, inputFileName, width, height, true);
    }


    /**
     * 
     * 指定压缩图片的宽度和高度
     * 
     * @param request
     * @param inputFileName
     * @param width
     * @param height
     * @param needCalcRate
     *            图片压缩压缩是否需要重新计算比例
     * @return
     */
    public static String upload(HttpServletRequest request, String inputFileName, int width, int height,
            boolean needCalcRate) {
        // 转型为MultipartHttpRequest(重点的所在)
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获得图片（根据前台的name名称得到上传的文件）
        MultipartFile shopImage = multipartRequest.getFile(inputFileName);
        if (shopImage == null || shopImage.getSize() == 0) {
            return null;
        }
        String filename = shopImage.getOriginalFilename();
        String ext = filename.substring(filename.lastIndexOf(".") + 1, filename.length()).toLowerCase();
        String fileEnd = "";
        if (!AssertUtils.isNull(ext)) {
            fileEnd = "." + ext;
        }
        String localCompressfile = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;
        OutputStream os = null;
        InputStream is = null;

        try {
            is = shopImage.getInputStream();
            os = new FileOutputStream(new File(localCompressfile));
            ImageUtils.compress(is, os, ext, width, height, needCalcRate);
        }
        catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Failed to addShopImage", e);
            }
            throw new ViewExternalDisplayException(ResultCode.IMG_UPLOAD_FAILD, e);
        }
        finally {
            IOUtils.close(is);
            IOUtils.close(os);
        }
        String imgPath = FastDFSUtil.uploadFile(localCompressfile, null);

        // 上传文件后删除临时文件
        LocalFileUtils.delFile(localCompressfile);
        return imgPath;
    }


    /**
     *
     * WEB上传图片，将文件压缩后上传到FastDFS, 原图和各种尺寸的图片
     *
     * @param request
     * @param inputFileName
     *            上传的文件输入参数
     * @return 上传的路径（绝对路径）
     */
    public static ImageUrlAppIconDTO uploadAppIcon(String imgPathOriginal, HttpServletRequest request,
            String inputFileName) {
        // 转型为MultipartHttpRequest(重点的所在)
        // MultipartHttpServletRequest multipartRequest =
        // (MultipartHttpServletRequest)request;
        // 获得图片（根据前台的name名称得到上传的文件）
        // MultipartFile shopImage = multipartRequest.getFile(inputFileName);
        // if (shopImage == null || shopImage.getSize() == 0)
        // {
        // return null;
        // }
        // String filename = shopImage.getOriginalFilename();
        // String ext = filename.substring(filename.lastIndexOf(".") + 1,
        // filename.length()).toLowerCase();
        String fileEnd = "." + FORMAT_PNG;
        String localCompressfile58 = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;
        String localCompressfile80 = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;
        String localCompressfile87 = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;
        String localCompressfile120 = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;
        String localCompressfile1203x = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;
        String localCompressfile180 = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;

        String localCompressfile48 = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;
        String localCompressfile72 = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;
        String localCompressfile96 = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;
        String localCompressfile144 = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;

        InputStream is = null;
        OutputStream os58 = null;
        OutputStream os80 = null;
        OutputStream os87 = null;
        OutputStream os120 = null;
        OutputStream os1203x = null;
        OutputStream os180 = null;

        OutputStream os48 = null;
        OutputStream os72 = null;
        OutputStream os96 = null;
        OutputStream os144 = null;

        try {
            is = ImgCutUtil.getPicIns(imgPathOriginal);
            os58 = new FileOutputStream(new File(localCompressfile58));
            ImageUtils.compressKeepAspectRatio(is, os58, FORMAT_PNG, 58, 58);

            is = ImgCutUtil.getPicIns(imgPathOriginal);
            os80 = new FileOutputStream(new File(localCompressfile80));
            ImageUtils.compressKeepAspectRatio(is, os80, FORMAT_PNG, 80, 80);

            is = ImgCutUtil.getPicIns(imgPathOriginal);
            os87 = new FileOutputStream(new File(localCompressfile87));
            ImageUtils.compressKeepAspectRatio(is, os87, FORMAT_PNG, 87, 87);

            is = ImgCutUtil.getPicIns(imgPathOriginal);
            os120 = new FileOutputStream(new File(localCompressfile120));
            ImageUtils.compressKeepAspectRatio(is, os120, FORMAT_PNG, 120, 120);

            is = ImgCutUtil.getPicIns(imgPathOriginal);
            os1203x = new FileOutputStream(new File(localCompressfile1203x));
            ImageUtils.compressKeepAspectRatio(is, os1203x, FORMAT_PNG, 120, 120);

            is = ImgCutUtil.getPicIns(imgPathOriginal);
            os180 = new FileOutputStream(new File(localCompressfile180));
            ImageUtils.compressKeepAspectRatio(is, os180, FORMAT_PNG, 180, 180);

            // 安卓
            is = ImgCutUtil.getPicIns(imgPathOriginal);
            os48 = new FileOutputStream(new File(localCompressfile48));
            ImageUtils.compressKeepAspectRatio(is, os48, FORMAT_PNG, 48, 48);

            is = ImgCutUtil.getPicIns(imgPathOriginal);
            os72 = new FileOutputStream(new File(localCompressfile72));
            ImageUtils.compressKeepAspectRatio(is, os72, FORMAT_PNG, 72, 72);

            is = ImgCutUtil.getPicIns(imgPathOriginal);
            os96 = new FileOutputStream(new File(localCompressfile96));
            ImageUtils.compressKeepAspectRatio(is, os96, FORMAT_PNG, 96, 96);

            is = ImgCutUtil.getPicIns(imgPathOriginal);
            os144 = new FileOutputStream(new File(localCompressfile144));
            ImageUtils.compressKeepAspectRatio(is, os144, FORMAT_PNG, 144, 144);
        }
        catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Failed to addShopImage", e);
            }
            throw new ViewExternalDisplayException(ResultCode.IMG_UPLOAD_FAILD, e);
        }
        finally {
            IOUtils.close(is);
            IOUtils.close(os58);

            IOUtils.close(os80);

            IOUtils.close(os87);

            IOUtils.close(os120);

            IOUtils.close(os1203x);

            IOUtils.close(os180);
            // 安卓
            IOUtils.close(os48);

            IOUtils.close(os72);

            IOUtils.close(os96);

            IOUtils.close(os144);
        }
        String imgPath58 = FastDFSUtil.uploadFile(localCompressfile58, null);
        String imgPath80 = FastDFSUtil.uploadFile(localCompressfile80, null);
        String imgPath87 = FastDFSUtil.uploadFile(localCompressfile87, null);
        String imgPath120 = FastDFSUtil.uploadFile(localCompressfile120, null);
        String imgPath1203x = FastDFSUtil.uploadFile(localCompressfile1203x, null);
        String imgPath_180 = FastDFSUtil.uploadFile(localCompressfile180, null);

        String imgPath48 = FastDFSUtil.uploadFile(localCompressfile48, null);
        String imgPath72 = FastDFSUtil.uploadFile(localCompressfile72, null);
        String imgPath96 = FastDFSUtil.uploadFile(localCompressfile96, null);
        String imgPath144 = FastDFSUtil.uploadFile(localCompressfile144, null);

        // 上传文件后删除临时文件
        LocalFileUtils.delFile(localCompressfile58);
        // 上传文件后删除临时文件
        LocalFileUtils.delFile(localCompressfile80);
        // 上传文件后删除临时文件
        LocalFileUtils.delFile(localCompressfile87);
        // 上传文件后删除临时文件
        LocalFileUtils.delFile(localCompressfile120);
        // 上传文件后删除临时文件
        LocalFileUtils.delFile(localCompressfile1203x);
        // 上传文件后删除临时文件
        LocalFileUtils.delFile(localCompressfile180);

        // 上传文件后删除临时文件
        LocalFileUtils.delFile(localCompressfile48);
        // 上传文件后删除临时文件
        LocalFileUtils.delFile(localCompressfile72);
        // 上传文件后删除临时文件
        LocalFileUtils.delFile(localCompressfile96);
        // 上传文件后删除临时文件
        LocalFileUtils.delFile(localCompressfile144);

        ImageUrlAppIconDTO url =
                new ImageUrlAppIconDTO(imgPath58, imgPath80, imgPath87, imgPath120, imgPath1203x,
                    imgPath_180, imgPath48, imgPath72, imgPath96, imgPath144);
        return url;
    }


    /**
     *
     * WEB上传图片，将文件压缩后上传到FastDFS, 原图和各种尺寸的图片
     *
     * @param httpServletRequest
     *            上传的文件输入参数
     * @return 上传的路径（绝对路径）
     */
    public static ImageUrlAppIconDTO uploadAppIconDefault(HttpServletRequest httpServletRequest, String flag) {
        String root = httpServletRequest.getRealPath("/");
        String iconUrl = root + "style/images/logo2.png";
        String ext = "png";
        String fileEnd = ".png";
        String localCompressfile58 = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;
        String localCompressfile80 = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;
        String localCompressfile87 = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;
        String localCompressfile120 = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;
        String localCompressfile1203x = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;
        String localCompressfile180 = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;

        String localCompressfile48 = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;
        String localCompressfile72 = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;
        String localCompressfile96 = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;
        String localCompressfile144 = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;

        InputStream is = null;
        OutputStream os58 = null;
        OutputStream os80 = null;
        OutputStream os87 = null;
        OutputStream os120 = null;
        OutputStream os1203x = null;
        OutputStream os180 = null;

        OutputStream os48 = null;
        OutputStream os72 = null;
        OutputStream os96 = null;
        OutputStream os144 = null;

        try {
            is = new FileInputStream(new File(iconUrl));
            os58 = new FileOutputStream(new File(localCompressfile58));
            ImageUtils.compressKeepAspectRatio(is, os58, FORMAT_PNG, 58, 58);

            is = new FileInputStream(new File(iconUrl));
            os80 = new FileOutputStream(new File(localCompressfile80));
            ImageUtils.compressKeepAspectRatio(is, os80, FORMAT_PNG, 80, 80);

            is = new FileInputStream(new File(iconUrl));
            os87 = new FileOutputStream(new File(localCompressfile87));
            ImageUtils.compressKeepAspectRatio(is, os87, FORMAT_PNG, 87, 87);

            is = new FileInputStream(new File(iconUrl));
            os120 = new FileOutputStream(new File(localCompressfile120));
            ImageUtils.compressKeepAspectRatio(is, os120, FORMAT_PNG, 120, 120);

            is = new FileInputStream(new File(iconUrl));
            os1203x = new FileOutputStream(new File(localCompressfile1203x));
            ImageUtils.compressKeepAspectRatio(is, os1203x, FORMAT_PNG, 120, 120);

            is = new FileInputStream(new File(iconUrl));
            os180 = new FileOutputStream(new File(localCompressfile180));
            ImageUtils.compressKeepAspectRatio(is, os180, FORMAT_PNG, 180, 180);

            // 安卓
            is = new FileInputStream(new File(iconUrl));
            os48 = new FileOutputStream(new File(localCompressfile48));
            ImageUtils.compressKeepAspectRatio(is, os48, FORMAT_PNG, 48, 48);

            is = new FileInputStream(new File(iconUrl));
            os72 = new FileOutputStream(new File(localCompressfile72));
            ImageUtils.compressKeepAspectRatio(is, os72, FORMAT_PNG, 72, 72);

            is = new FileInputStream(new File(iconUrl));
            os96 = new FileOutputStream(new File(localCompressfile96));
            ImageUtils.compressKeepAspectRatio(is, os96, FORMAT_PNG, 96, 96);

            is = new FileInputStream(new File(iconUrl));
            os144 = new FileOutputStream(new File(localCompressfile144));
            ImageUtils.compressKeepAspectRatio(is, os144, FORMAT_PNG, 144, 144);
        }
        catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Failed to addShopImage", e);
            }
            throw new ViewExternalDisplayException(ResultCode.IMG_UPLOAD_FAILD, e);
        }
        finally {
            IOUtils.close(is);

            IOUtils.close(os58);

            IOUtils.close(os80);

            IOUtils.close(os87);

            IOUtils.close(os120);

            IOUtils.close(os1203x);

            IOUtils.close(os180);
            // 安卓
            IOUtils.close(os48);

            IOUtils.close(os72);

            IOUtils.close(os96);

            IOUtils.close(os144);
        }
        String imgPath_58 = FastDFSUtil.uploadFile(localCompressfile58, null);
        String imgPath_80 = FastDFSUtil.uploadFile(localCompressfile80, null);
        String imgPath_87 = FastDFSUtil.uploadFile(localCompressfile87, null);
        String imgPath_120 = FastDFSUtil.uploadFile(localCompressfile120, null);
        String imgPath_120_3x = FastDFSUtil.uploadFile(localCompressfile1203x, null);
        String imgPath_180 = FastDFSUtil.uploadFile(localCompressfile180, null);

        String imgPath_48 = FastDFSUtil.uploadFile(localCompressfile48, null);
        String imgPath_72 = FastDFSUtil.uploadFile(localCompressfile72, null);
        String imgPath_96 = FastDFSUtil.uploadFile(localCompressfile96, null);
        String imgPath_144 = FastDFSUtil.uploadFile(localCompressfile144, null);

        // 上传文件后删除临时文件
        LocalFileUtils.delFile(localCompressfile58);
        // 上传文件后删除临时文件
        LocalFileUtils.delFile(localCompressfile80);
        // 上传文件后删除临时文件
        LocalFileUtils.delFile(localCompressfile87);
        // 上传文件后删除临时文件
        LocalFileUtils.delFile(localCompressfile120);
        // 上传文件后删除临时文件
        LocalFileUtils.delFile(localCompressfile1203x);
        // 上传文件后删除临时文件
        LocalFileUtils.delFile(localCompressfile180);

        // 上传文件后删除临时文件
        LocalFileUtils.delFile(localCompressfile48);
        // 上传文件后删除临时文件
        LocalFileUtils.delFile(localCompressfile72);
        // 上传文件后删除临时文件
        LocalFileUtils.delFile(localCompressfile96);
        // 上传文件后删除临时文件
        LocalFileUtils.delFile(localCompressfile144);

        ImageUrlAppIconDTO url =
                new ImageUrlAppIconDTO(imgPath_58, imgPath_80, imgPath_87, imgPath_120, imgPath_120_3x,
                    imgPath_180, imgPath_48, imgPath_72, imgPath_96, imgPath_144);
        return url;
    }


    /**
     *
     * WEB上传图片，原图和缩略图。
     *
     * @param request
     * @param inputFileName
     *            上传的文件输入参数
     * @return 上传的路径（绝对路径）
     */
    public static ImageUrlDTO uploadImage(HttpServletRequest request, String inputFileName) {
        // 转型为MultipartHttpRequest(重点的所在)
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获得图片（根据前台的name名称得到上传的文件）
        MultipartFile shopImage = multipartRequest.getFile(inputFileName);
        if (shopImage == null) {
            return null;
        }
        String filename = shopImage.getOriginalFilename();
        String ext = filename.substring(filename.lastIndexOf(".") + 1, filename.length()).toLowerCase();
        String fileEnd = "";
        if (!AssertUtils.isNull(ext)) {
            fileEnd = "." + ext;
        }
        String localCompressfile = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;
        String thumfile = sysTempPath + File.separator + CommonUtils.getImgName() + "_thum" + fileEnd;
        OutputStream os = null;
        OutputStream thumOs = null;
        InputStream is = null;
        FileInputStream fis = null;
        try {
            is = shopImage.getInputStream();
            os = new FileOutputStream(new File(localCompressfile));
            thumOs = new FileOutputStream(new File(thumfile));
            // 原图压缩成大图
            ImageUtils.compress(is, os, ext);
            IOUtils.close(os);
            fis = new FileInputStream(new File(localCompressfile));
            // 原图压缩成小图
            ImageUtils.compress(fis, thumOs, ext, THUM_WIDTH, THUM_HEIGHT);

            String bigImgPath = FastDFSUtil.uploadFile(localCompressfile, null);
            String smallImgPath = FastDFSUtil.uploadFile(thumfile, null);

            ImageUrlDTO dto = new ImageUrlDTO();
            dto.setOriginImageUrl(bigImgPath);
            dto.setThumImageUrl(smallImgPath);
            return dto;
        }
        catch (Exception e) {
            throw new ViewExternalDisplayException(ResultCode.IMG_UPLOAD_FAILD, e);
        }
        finally {
            IOUtils.close(is);
            IOUtils.close(os);
            IOUtils.close(fis);
            IOUtils.close(thumOs);
            // 上传文件后删除临时文件
            LocalFileUtils.delFile(localCompressfile);
            // 上传文件后删除临时文件
            LocalFileUtils.delFile(thumfile);
        }
    }


    /**
     *
     * WEB上传图片，原图和缩略图。
     *
     * @param request
     * @param inputFileName
     *            上传的文件输入参数
     * @param thumWidth
     * @param thumHeight
     * @return 上传的路径（绝对路径）
     */
    public static ImageUrlDTO uploadImage(HttpServletRequest request, String inputFileName, int thumWidth,
            int thumHeight) {
        // 转型为MultipartHttpRequest(重点的所在)
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获得图片（根据前台的name名称得到上传的文件）
        MultipartFile shopImage = multipartRequest.getFile(inputFileName);
        if (shopImage == null) {
            return null;
        }
        String filename = shopImage.getOriginalFilename();
        String ext = filename.substring(filename.lastIndexOf(".") + 1, filename.length()).toLowerCase();
        String fileEnd = "";
        if (!AssertUtils.isNull(ext)) {
            fileEnd = "." + ext;
        }
        String localCompressfile = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;
        String thumfile = sysTempPath + File.separator + CommonUtils.getImgName() + "_thum" + fileEnd;
        OutputStream thumOs = null;
        InputStream is = null;
        FileInputStream fis = null;
        try {
            is = shopImage.getInputStream();
            thumOs = new FileOutputStream(new File(thumfile));

            shopImage.transferTo(new File(localCompressfile));
            fis = new FileInputStream(new File(localCompressfile));
            // 原图压缩成小图
            ImageUtils.compress(fis, thumOs, ext, thumWidth, thumHeight);

            String bigImgPath = FastDFSUtil.uploadFile(localCompressfile, null);
            String smallImgPath = FastDFSUtil.uploadFile(thumfile, null);

            ImageUrlDTO dto = new ImageUrlDTO();
            dto.setOriginImageUrl(bigImgPath);
            dto.setThumImageUrl(smallImgPath);
            return dto;
        }
        catch (javax.imageio.IIOException ioe) {
            throw new ViewExternalDisplayException(ResultCode.IMG_FORMART_ERROR, ioe);
        }
        catch (Exception e) {
            throw new ViewExternalDisplayException(ResultCode.IMG_UPLOAD_FAILD, e);
        }
        finally {
            IOUtils.close(is);
            IOUtils.close(fis);
            IOUtils.close(thumOs);
            // 上传文件后删除临时文件
            LocalFileUtils.delFile(localCompressfile);
            // 上传文件后删除临时文件
            LocalFileUtils.delFile(thumfile);
        }
    }


    /**
     * 
     * android上传图片，原图和缩略图。
     * 
     * @param mutiFile
     *            上传的文件输入参数
     * @return 上传的路径（绝对路径）
     */
    public static ImageUrlDTO uploadImage(MultipartFile mutiFile) {
        String filename = mutiFile.getOriginalFilename();
        String ext = filename.substring(filename.lastIndexOf(".") + 1, filename.length()).toLowerCase();
        String fileEnd = "";
        if (!AssertUtils.isNull(ext)) {
            fileEnd = "." + ext;
        }
        String localCompressfile = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;
        String thumfile = sysTempPath + File.separator + CommonUtils.getImgName() + "_thum" + fileEnd;
        OutputStream os = null;
        OutputStream thumOs = null;
        InputStream is = null;
        try {
            is = mutiFile.getInputStream();
            os = new FileOutputStream(new File(localCompressfile));
            thumOs = new FileOutputStream(new File(thumfile));
            // 原图压缩成大图
            ImageUtils.compress(is, os, ext);
            /*
             * is = mutiFile.getInputStream(); ImageUtils.compress(is, thumOs,
             * ext, THUM_WIDTH, THUM_HEIGHT);//原图压缩成小图
             */
            String bigImgPath = FastDFSUtil.uploadFile(localCompressfile, null);
            // String smallImgPath = FastDFSUtil.uploadFile(thumfile, null);

            ImageUrlDTO dto = new ImageUrlDTO();
            dto.setOriginImageUrl(bigImgPath);
            dto.setThumImageUrl(bigImgPath);
            return dto;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ViewExternalDisplayException(ResultCode.IMG_UPLOAD_FAILD, e);

        }
        finally {
            IOUtils.close(is);
            IOUtils.close(os);
            IOUtils.close(thumOs);
            // 上传文件后删除临时文件
            LocalFileUtils.delFile(localCompressfile);
            // 上传文件后删除临时文件
            LocalFileUtils.delFile(thumfile);
        }
    }


    /**
     * 
     * 多图片上传
     * 
     * @param request
     * @return
     */
    public static String uploadImage(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> map = multipartRequest.getFileMap();
        Collection<MultipartFile> values = map.values();
        StringBuilder str = null;
        if (null != values && values.size() > 0) {
            ImageUrlDTO imageUrlDTO = null;
            str = new StringBuilder();
            for (MultipartFile file : values) {
                imageUrlDTO = uploadImage(file);
                str.append(imageUrlDTO.getOriginImageUrl()).append(",");

            }
        }
        if (str != null) {
            return str.toString();
        }
        return null;

    }


    /**
     * 获取上传的文件
     * 
     * @param request
     * @param inputFileName
     * @return
     */
    public static MultipartFile getUplodFile(HttpServletRequest request, String inputFileName) {
        // 转型为MultipartHttpRequest(重点的所在)
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获得图片（根据前台的name名称得到上传的文件）
        MultipartFile image = multipartRequest.getFile(inputFileName);
        return image;
    }


    /**
     * 
     * 上传文件到本地。
     * 
     * @param file
     * @return 上传后的文件绝对路径
     */
    public static File uploadFileToLocal(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String ext = filename.substring(filename.lastIndexOf(".") + 1, filename.length()).toLowerCase();
        String fileEnd = "";
        if (!AssertUtils.isNull(ext)) {
            fileEnd = "." + ext;
        }
        String localFileName = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;
        try {
            File desfile = new File(localFileName);
            file.transferTo(desfile);
            return desfile;
        }
        catch (Exception e) {
            throw new RuntimeException("upload file error", e);
        }
    }


    /**
     * 
     * 上传文件到服务器。
     * 
     * @param file
     * @return 上传后的文件绝对路径
     */
    public static String uploadFileToFastdf(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
        String url = null;
        try {
            LOG.info("upload file.");
            url = FastDFSUtil.uploadFile(file.getBytes(), ext, null);
            LOG.info("upload file success.");
        }
        catch (IOException e) {
            throw new RuntimeException("upload file error", e);
        }
        return url;
    }


    /**
     * 
     * 上传文件到服务器。
     * 
     * @param request
     * @param inputFileName
     * @return
     */
    public static String uploadFileToFastdf(HttpServletRequest request, String inputFileName) {
        MultipartFile file = getUplodFile(request, inputFileName);
        return uploadFileToFastdf(file);
    }


    /**
     * 
     * 获取上传的文件名
     * 
     * @param request
     * @param inputFileName
     * @return
     */
    public static MultipartFile getUploadFileName(HttpServletRequest request, String inputFileName) {
        MultipartFile file = getUplodFile(request, inputFileName);
        return file;
    }


    /**
     * 获取系统临时目录
     * 
     * @return
     */
    public static String getSysTempPath() {
        return sysTempPath;
    }


    /**
     * 
     * 多图片上传,包含原图和缩略图
     * 
     * @param request
     * @return
     */
    public static Map<String, String> uploadImages(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> map = multipartRequest.getFileMap();
        Collection<MultipartFile> values = map.values();
        StringBuilder str = null;
        StringBuilder str1 = null;
        if (null != values && values.size() > 0) {
            ImageUrlDTO imageUrlDTO = null;
            str = new StringBuilder();
            str1 = new StringBuilder();
            for (MultipartFile file : values) {
                imageUrlDTO = uploadImage(file);
                str.append(imageUrlDTO.getOriginImageUrl()).append(",");
                str1.append(imageUrlDTO.getThumImageUrl()).append(",");
            }
        }
        Map<String, String> imageMap = new HashMap<String, String>();
        if (str != null) {
            imageMap.put("origin", str.toString().substring(0, str.toString().length() - 1));
        }
        if (str1 != null) {
            imageMap.put("thum", str1.toString().substring(0, str1.toString().length() - 1));
        }
        return imageMap;

    }

    private static final int TIME_OUT = 5000;


    /**
     * 保存图片
     * 
     * @param url
     * @param ext
     * @throws Exception
     */
    public static String save(String url, String ext) throws Exception {
        String fileEnd = "";
        if (AssertUtils.isNull(ext)) {
            ext = "jpeg";
        }
        if (!AssertUtils.isNull(ext)) {
            fileEnd = "." + ext;
        }
        String localCompressfile = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;
        BufferedOutputStream out = null;
        byte[] bit = getByte(url);
        if (bit.length > 0) {
            try {
                out = new BufferedOutputStream(new FileOutputStream(localCompressfile));
                out.write(bit);
                out.flush();
                return FastDFSUtil.uploadFile(localCompressfile, null);
            }
            finally {
                if (out != null)
                    IOUtils.close(out);
                LocalFileUtils.delFile(localCompressfile);
            }
        }
        return null;

    }


    /**
     * 获取图片字节流
     * 
     * @param uri
     * @return
     * @throws Exception
     */
    static byte[] getByte(String uri) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(uri);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(TIME_OUT).build();
        get.setConfig(requestConfig);
        CloseableHttpResponse resonse = client.execute(get);
        try {
            if (resonse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = resonse.getEntity();
                if (entity != null) {
                    return EntityUtils.toByteArray(entity);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            client.close();
            resonse.close();
        }
        return new byte[0];
    }


    public static String uploadImage(URLConnection url, String ext) {
        String fileEnd = "";
        if (AssertUtils.isNull(ext)) {
            ext = "jpeg";
        }
        if (!AssertUtils.isNull(ext)) {
            fileEnd = "." + ext;
        }
        OutputStream out = null;
        InputStream in = null;
        String bigImgPath = null;
        String localCompressfile = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;
        try {
            out = new FileOutputStream(new File(localCompressfile));
            in = url.getInputStream();
            ImageUtils.upLoadTFilel(in, out, ext);
            bigImgPath = FastDFSUtil.uploadFile(localCompressfile, null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            IOUtils.close(in);
            IOUtils.close(out);
            // 上传文件后删除临时文件
            LocalFileUtils.delFile(localCompressfile);

        }
        return bigImgPath;

    }


    /**
     * 下载网络图片到本地
     * 
     * @param urlString
     * @throws Exception
     *             参考ImageUploadUtils.upload方法
     */
    public static String downloadNetPic(String urlString) {
        String localCompressfile = null;

        try {
            String savePath = sysTempPath + File.separator; // 临时图片存储的位置

            String fileEnd = "";
            if (!AssertUtils.isNull(urlString)) {
                fileEnd = urlString.substring(urlString.lastIndexOf("."), urlString.length());
            }
            String filename = SysUniqueIdUtil.nextId() + fileEnd; // 文件名
            localCompressfile = savePath + filename; // 全路径
            LOG.info("localCompressfile:" + localCompressfile);

            // 构造URL
            URL url = new URL(urlString);
            // 打开连接
            URLConnection con = url.openConnection();
            // 设置请求超时为5s
            con.setConnectTimeout(5 * 1000);
            // 输入流
            InputStream is = con.getInputStream();

            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            File sf = new File(savePath);
            if (!sf.exists()) {
                sf.mkdirs();
            }
            // OutputStream os = new
            // FileOutputStream(sf.getPath()+"\\"+filename);
            OutputStream os = new FileOutputStream(localCompressfile);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完毕，关闭所有链接
            os.close();
            is.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        String imgPath = "";
        try {

            imgPath = FastDFSUtil.uploadFile(localCompressfile, null);
            LOG.info("downloadNetPic接口，localCompressfile：" + localCompressfile);
            // 上传文件后删除临时文件
            LocalFileUtils.delFile(localCompressfile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return imgPath;
    }


    /**
     * 保存base64格式图片
     * 
     * @param base64Code
     * @param ext
     * @throws Exception
     */
    public static String saveBase64(String base64Code, String ext) throws Exception {
        String fileEnd = "";
        if (!AssertUtils.isNull(ext)) {
            fileEnd = "." + ext;
        }
        String localCompressfile = sysTempPath + File.separator + CommonUtils.getImgName() + fileEnd;
        BufferedOutputStream out = null;
        byte[] bit = Base64.getDecoder().decode(base64Code.substring(base64Code.indexOf("base64")+7));
        //当字节数大于10K才进行处理
        if (bit.length > 1024*10) {
            try {
                out = new BufferedOutputStream(new FileOutputStream(localCompressfile));
                out.write(bit);
                out.flush();
                return FastDFSUtil.uploadFile(localCompressfile, null);
            }
            finally {
                if (out != null)
                    IOUtils.close(out);
                LocalFileUtils.delFile(localCompressfile);
            }
        }
        return null;

    }
    
 public static String uploadCutImage(String url, int w, int h) throws IOException {   
        
        // 判断参数是否合法   
        if (null == url || 0 == w || 0 == h) {   
            new Exception ("哎呀，截图出错！！！");   
        }   
        URL orginUrl = new URL(url);  
        HttpURLConnection httpUrl = (HttpURLConnection) orginUrl.openConnection();  
        httpUrl.connect();  
        InputStream inputStream = httpUrl.getInputStream();   
        // 用ImageIO读取字节流   
        BufferedImage bufferedImage = ImageIO.read(inputStream);   
        // 返回源图片的宽度。   
        int srcW = bufferedImage.getWidth();   
        // 返回源图片的高度。   
        int srcH = bufferedImage.getHeight();   
        int x = 0, y = 0;   
        // 使截图区域居中   
        x = srcW / 2 - srcH / 2;   
        y = srcH / 2 - srcH / 2;   
        CutImageDTO dto = new CutImageDTO();
        dto.setUrl(url);
        dto.setPositionx(x);
        dto.setPositiony(y);
        dto.setWidth(srcH);
        dto.setHeight(srcH);
        // 生成图片   
        return ImgCutUtil.cut(dto);
    }  
 
     public static void main(String[] args) throws IOException {
    	 System.out.println(uploadCutImage("http://img1.youanmi.com/yam/M00/03/66/oYYBAFbNEl6AKgoBAAAgsAsOifM898.jpg", 
    			 100, 100));
     }
}
