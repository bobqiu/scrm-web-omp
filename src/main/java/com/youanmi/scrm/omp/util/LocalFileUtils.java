package com.youanmi.scrm.omp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


/**
 * 本地文件&目录操作工具类
 * 
 * @author 刘红艳
 *
 */
public class LocalFileUtils {
    /**
     * 调测日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(LocalFileUtils.class);


    /**
     * 删除指定文件
     * 
     * @param path
     * 
     */
    public static boolean delFile(String path) {
        if (AssertUtils.isNull(path)) {
            return false;
        }
        File file = new File(path);
        return file.delete();
    }


    /**
     * path 必须是绝对路径
     * 
     * @param path
     * @param split
     *            目录分割符
     * @throws IOException
     */
    public static void createFile(String path, String split) throws IOException {
        if (path == null || "".equals(path)) {
            return;
        }
        String[] dir = path.split(split);
        String hPath = dir[0];
        File file;
        file = new File(hPath);
        if (!file.exists()) {
            file.mkdir();
        }
        for (int i = 1; i < dir.length; i++) {
            hPath = hPath + File.separator + dir[i];
            file = new File(hPath);
            if (!file.exists()) {
                file.mkdir();
            }
        }
    }


    /**
     * 删除指定文件
     * 
     * @param path
     * @param split
     *            目录分割符
     */
    public static boolean delFile(String path, String split) {
        if (path == null || "".equals(path)) {
            return true;
        }
        String[] dir = path.split(split);
        String hPath = dir[0];
        File file;
        for (int i = 1; i < dir.length; i++) {
            hPath = hPath + File.separator + dir[i];
        }
        file = new File(hPath);

        if (file.exists()) {
            return deleteDir(file);
        }
        return true;
    }


    /**
     * 删除指定目录
     * 
     * @param path
     * @param split
     *            目录分割符
     */
    public static boolean delDir(String path, String split) {
        if (path == null || "".equals(path)) {
            return true;
        }
        String[] dir = path.split(split);
        String hPath = dir[0];
        File file;
        for (int i = 1; i < dir.length; i++) {
            hPath = hPath + File.separator + dir[i];
        }
        file = new File(hPath);

        if (file.exists()) {
            return deleteDir(file);
        }
        return true;
    }


    /**
     * 删除指定目录下内容,除去指定的该文件下的直属子目录。
     * 
     * @param path
     * @param split
     *            目录分割符
     * @param unDelChildren
     *            指定不删除的目录
     */
    public static boolean delDirContent(String path, String split, String[] unDelChildren) {
        if (path == null || "".equals(path)) {
            return true;
        }
        String[] dir = path.split(split);
        String hPath = dir[0];
        File file;
        for (int i = 1; i < dir.length; i++) {
            hPath = hPath + File.separator + dir[i];
        }
        file = new File(hPath);

        if (file.exists()) {
            return deleteDirSubFile(file, unDelChildren);
        }
        return true;
    }


    /**
     * 删除指定目录下内容。
     * 
     * @param path
     * @param split
     *            目录分割符
     */
    public static boolean delDirContent(String path, String split) {
        if (path == null || "".equals(path)) {
            return true;
        }
        String[] dir = path.split(split);
        String hPath = dir[0];
        File file;
        for (int i = 1; i < dir.length; i++) {
            hPath = hPath + File.separator + dir[i];
        }
        file = new File(hPath);

        if (file.exists()) {
            return deleteDirSubFile(file);
        }
        return true;
    }


    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * 
     * @param dir
     *            将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful. If a
     *         deletion fails, the method stops attempting to delete and returns
     *         "false".
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                // 递归删除目录中的子目录下
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }


    /**
     * 删除指定目录下内容。
     * 
     * @param path
     * @param split
     */
    private static boolean deleteDirSubFile(File dir) {
        boolean success = true;
        if (dir.isDirectory()) {
            String[] children = dir.list();
            // 递归删除目录中的子目录下
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        return success;
    }


    /**
     * 删除指定目录下内容,除去指定的该文件下的直属子目录。
     * 
     * @param path
     * @param split
     */
    private static boolean deleteDirSubFile(File dir, String[] unDelChildren) {
        boolean success = true;
        if (dir.isDirectory()) {
            String[] children = dir.list();
            // 递归删除目录中的子目录下
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    if (!inUnDelChildren(children[i], unDelChildren)) {
                        success = deleteDir(new File(dir, children[i]));
                    }
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        return success;
    }


    /**
     * 判断dirName是否在不允许删除的目录内。
     * 
     * @param dirName
     * @param unDelChildre
     * @return 是则返回 true
     */
    private static boolean inUnDelChildren(String dirName, String[] unDelChildre) {
        if (unDelChildre != null) {
            for (int i = 0; i < unDelChildre.length; i++) {
                if (dirName.equals(unDelChildre[i])) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 
     * @param dstPath
     *            目标文件位置
     * @param srcFile
     *            源文件
     * @param uploadSize
     *            一次写入大小
     * @return
     */
    public static boolean uploadFile(String dstPath, File srcFile, int uploadSize) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = null;
            os = null;
            is = new FileInputStream(srcFile);
            os = new FileOutputStream(dstPath);
            byte[] buffer = new byte[uploadSize];
            int length = 0;
            while (-1 != (length = is.read(buffer))) {
                os.write(buffer, 0, length);
            }
        }
        catch (Exception e) {
            LOG.error("Upload file failed. ", e);
            return false;
        }
        finally {
            try {
                if (null != os) {
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
            }
            catch (IOException ie) {
                LOG.error("close stream failure", ie);

            }
        }
        return true;
    }


    /**
     * 
     * 测试
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        createFile("D:/log/tmms/test/test/test.jpg/test.jpg/test.jpg/test.jpg/test.jpg/test.jpg", "/");

        boolean a = false;
        String[] undel = {"test", "123", "123.txt"};
        a = delDirContent("D:/log/tmms/test/", "/", undel);
        // a = delDirContent("D:/log/tmms/test", "/");
        // a = delFile("D:/log/tmms/test", "/");
    }
}
