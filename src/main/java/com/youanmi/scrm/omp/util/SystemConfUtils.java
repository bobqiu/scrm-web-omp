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

import com.youanmi.scrm.commons.util.file.PropertiesUtils;

import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * 系统配置信息 sys.properties文件的配置
 * <p>
 * 
 * <p>
 * 
 * <pre>
 * </pre>
 * 
 * @author 龙汀
 * @since YouAnMi-OTO
 */
public class SystemConfUtils {

    private static final Logger LOG = LoggerFactory.getLogger(SystemConfUtils.class);

    /**
     * 配置缓存
     */
    private static final ConcurrentMap<String, String> CONFS = new ConcurrentHashMap<String, String>();


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
     * 
     * 初始化系统配置文件
     * 
     * @param confName
     */
    public static void initSysCof(String confName) {
        Properties p = PropertiesUtils.getProperties(confName);
        if (p != null && !p.entrySet().isEmpty()) {
            for (Entry<Object, Object> s : p.entrySet()) {
                SystemConfUtils.setConf((String) s.getKey(), (String) s.getValue());
            }
        }
    }

    static {
        initSysCof("common.sys.properties");
    }
}
