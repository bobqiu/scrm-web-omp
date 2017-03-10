/*
 * 文件名：ConfUtils.java
 * 版权：深圳柚安米科技有限公司版权所有
 * 描述： ConfUtils.java
 * 修改人：龙汀
 * 修改时间：2015年9月16日
 * 修改内容：新增
 */
package com.youanmi.scrm.omp.util.fastdfs;


import com.youanmi.scrm.commons.util.file.PropertiesUtils;
import com.youanmi.scrm.commons.util.number.NumberUtils;
import com.youanmi.scrm.omp.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map.Entry;
import java.util.Properties;


/**
 * 配置工具类
 *
 */
public class ConfUtils {
    /**
     * 调测日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(ConfUtils.class);


    /**
     * 
     * 初始化项目分页大小
     * 
     * @param
     */
    public static void initPageSize() {
        int pageSize = NumberUtils.parseInt(CustomConfUtils.getConf("paging.size"));
        if (pageSize == 0) {
            pageSize = NumberUtils.parseInt(CommonUtils.getSysConf("paging.size.default"));
        }
        VOUtils.setDefaultPageSize(pageSize);
    }


    /**
     * 
     * 初始化自定义配置
     * 
     * @param  confs
     */
    public static void iniCusConf(String... confs) {
        for (String conf : confs) {
            Properties p = PropertiesUtils.getProperties(conf);
            if (p != null && !p.entrySet().isEmpty()) {
                for (Entry<Object, Object> s : p.entrySet()) {
                    CustomConfUtils.setConf((String) s.getKey(), (String) s.getValue());
                }
            }
        }
    }

}
