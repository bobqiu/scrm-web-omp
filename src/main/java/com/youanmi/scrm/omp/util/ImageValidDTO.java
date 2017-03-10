/*
 * 文件名：ImageValidDTO.java
 * 版权：Copyright 2015 youanmi Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ImageValidDTO.java
 * 修改人：刘红艳
 * 修改时间：2015年3月20日
 * 修改内容：新增
 */
package com.youanmi.scrm.omp.util;

/**
 *  图片校验规格。
 * <p>
 * 
 * <p>
 * 
 * 
 * <pre>
 * </pre>
 * 
 * @author 刘红艳
 * @version YouAnMi-OTO 2015年3月20日
 * @since YouAnMi-OTO
 */
public class ImageValidDTO {

    private long maxSize;

    private int maxWidth;

    private int maxHeight;

    private boolean equals;


    public long getMaxSize() {
        return maxSize;
    }


    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }


    public int getMaxWidth() {
        return maxWidth;
    }


    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }


    public int getMaxHeight() {
        return maxHeight;
    }


    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }


    public boolean isEquals() {
        return equals;
    }


    public void setEquals(boolean equals) {
        this.equals = equals;
    }

}
