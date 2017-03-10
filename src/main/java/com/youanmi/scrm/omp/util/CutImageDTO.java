/*
 * 文件名：CutImageDTO.java
 * 版权：Copyright 2015 youanmi Tech. Co. Ltd. All Rights Reserved. 
 * 描述： CutImageDTO.java
 * 修改人：刘红艳
 * 修改时间：2015年5月11日
 * 修改内容：新增
 */
package com.youanmi.scrm.omp.util;

/**
 * 裁剪图片属性。
 * <p>
 * 
 * <p>
 * 示例代码
 * 
 * <pre>
 * </pre>
 * 
 * @author 刘红艳
 * @version YouAnMi-OTO 2015年5月11日
 * @since YouAnMi-OTO
 */
public class CutImageDTO {
    private String url;

    private int positionx;

    private int positiony;

    private int width;

    private int height;

    private String base64;


    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public int getPositionx() {
        return positionx;
    }


    public void setPositionx(int positionx) {
        this.positionx = positionx;
    }


    public int getPositiony() {
        return positiony;
    }


    public void setPositiony(int positiony) {
        this.positiony = positiony;
    }


    public int getWidth() {
        return width;
    }


    public void setWidth(int width) {
        this.width = width;
    }


    public int getHeight() {
        return height;
    }


    public void setHeight(int height) {
        this.height = height;
    }


    public String getBase64() {
        return base64;
    }


    public void setBase64(String base64) {
        this.base64 = base64;
    }

}
