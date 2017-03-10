/*
 * 文件名：ImageUrlDTO.java
 * 版权：Copyright 2015 youanmi Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ImageUrlDTO.java
 * 修改人：刘红艳
 * 修改时间：2015年1月17日
 * 修改内容：新增
 */
package com.youanmi.scrm.omp.util;

/**
 *  上传图片URL封装。
 * <p>
 * 
 * <p>
 * 
 * 
 * <pre>
 * </pre>
 * 
 * @author 刘红艳
 * @version YouAnMi-OTO 2015年1月17日
 * @since YouAnMi-OTO
 */
public class ImageUrlDTO {
    /** 原图 */
    private String originImageUrl;

    /** 缩略图 */
    private String thumImageUrl;


    public String getOriginImageUrl() {
        return originImageUrl;
    }


    public void setOriginImageUrl(String originImageUrl) {
        this.originImageUrl = originImageUrl;
    }


    public String getThumImageUrl() {
        return thumImageUrl;
    }


    public void setThumImageUrl(String thumImageUrl) {
        this.thumImageUrl = thumImageUrl;
    }

}
