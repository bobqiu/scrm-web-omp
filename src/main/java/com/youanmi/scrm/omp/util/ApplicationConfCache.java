/*
 * 文件名：ApplicationConfCache.java
 * 版权：深圳柚安米科技有限公司版权所有
 * 修改人：龙汀
 * 修改时间：2016年1月12日
 * 修改内容：新增
 */
package com.youanmi.scrm.omp.util;


import org.apache.commons.lang.StringUtils;

import com.youanmi.scrm.commons.util.file.PropertiesUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * 系统配置缓存
 * <p>
 * 
 * 
 * @author 龙汀
 * @since 3.0.0
 */
public class ApplicationConfCache {

    /**
     * 配置缓存
     * 
     * <文件名,配置内容>
     */
    private static final ConcurrentMap<String, Map<String, String>> CONFS = 
            new ConcurrentHashMap<String, Map<String, String>>();


    public static Map<String, String> getConf(String fileName) {
        return CONFS.get(fileName);
    }


    public static String getValue(String fileName, String key) {
        Map<String, String> conf = CONFS.get(fileName);
        if (conf == null) {
            return "";
        }
        String v = conf.get(key);
        if (AssertUtils.isNull(v)) {
            v = conf.get(key);
        }
        if (AssertUtils.isNull(v)) {
            return "";
        }
        return v;
    }


    public static void setConf(String fileName, String key, String value) {
        if (AssertUtils.isNull(fileName) || AssertUtils.isNull(key)) {
            return;
        }
        Map<String, String> conf = CONFS.get(fileName);
        if (conf == null) {
            synchronized (ApplicationConfCache.class) {
                conf = CONFS.get(fileName);
                if (conf == null) {
                    CONFS.put(fileName, new HashMap<String, String>());
                }
            }
        }
        CONFS.get(fileName).put(StringUtils.trim(key), StringUtils.trim(value));
    }


    /**
     * 
     * 初始化配置文件
     * 
     * @param confName
     */
    public static void initConf(String confName) {
        Properties p = PropertiesUtils.getProperties(confName);

        if (p != null && !p.entrySet().isEmpty()) {
            for (Entry<Object, Object> s : p.entrySet()) {
                ApplicationConfCache.setConf(confName, (String) s.getKey(), (String) s.getValue());
            }
        }
    }

}
