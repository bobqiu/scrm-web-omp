/*
 * 文件名：SystemConfUtils.java
 * 版权：Copyright 2015 youanmi Tech. Co. Ltd. All Rights Reserved. 
 * 描述： SystemConfUtils.java
 * 修改人：龙汀
 * 修改时间：2015年4月8日
 * 修改内容：新增
 */
package com.youanmi.scrm.omp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youanmi.scrm.commons.util.exception.ExceptionUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * 自定义配置信息,各个项目自定义的配置文件获取类
 * 
 * <p>
 * sis.properties smp.properties
 * <p>
 * 
 * <pre>
 * </pre>
 * 
 * @author 龙汀
 * @since YouAnMi-OTO
 */
public class CustomConfUtils {

    /**
     * 调测日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(CustomConfUtils.class);

    /**
     * 配置缓存
     */
    private static final ConcurrentMap<String, String> CONFS = new ConcurrentHashMap<String, String>();

    /**app启动主题图片地址key*/
    public static final String SUBJECT_IMAGE_URL = "subject_image_url";

    /**
     * 
     * 新增缓存内容
     * 
     * @param key
     *            缓存KEY
     * @param value
     *            值
     */
    public static void setConf(String key, String value) {
        CONFS.put(key, value);
    }


    /**
     * 
     * 根据key获取配置
     * 
     * @param key
     *            缓存键
     * @return 值
     */
    public static String getConf(String key) {
        return CONFS.get(key);
    }


    /**
     * 如果配置的值为空,则返回默认值
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getConf(String key, String defaultValue) {
        String value = CONFS.get(key);
        if (AssertUtils.isNull(value)) {
            return defaultValue;
        }
        return value;
    }


    /**
     * 根据key获取配置
     * 
     * @param key
     * @return
     */
    public static boolean getBooleanConf(String key) {
        String value = CONFS.get(key);
        if (value != null) {
            try {
                return Boolean.valueOf(value).booleanValue();
            }
            catch (Exception e) {
                LOG.error("can not parse key=" + key + " to Boolean , value=" + value);
                LOG.error(ExceptionUtils.getStackTrace(e));
            }
        }
        return false;
    }


    /**
     * 根据key获取配置
     * 
     * @param key
     * @return
     */
    public static Integer getIntegerConf(String key) {
        String value = CONFS.get(key);
        if (value != null) {
            try {
                return Integer.valueOf(value);
            }
            catch (Exception e) {
                LOG.error("can not parse key=" + key + " to Integer , value=" + value);
                LOG.error(ExceptionUtils.getStackTrace(e));
            }
        }
        return null;
    }

}
