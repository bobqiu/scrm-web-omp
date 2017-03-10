package com.youanmi.scrm.omp.util;


import com.baidu.disconf.client.expand.IConfProcessedExpand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Administrator on 2016/11/29.
 */
public class YamConfProcessedExpand implements IConfProcessedExpand {
    /**
     * 调测日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(YamConfProcessedExpand.class);

    // 排除配置
    private List<String> excludes;


    @Override
    public void reloadConf(String fileName) {
        if (LOG.isInfoEnabled()) {
            LOG.info("加载文件：" + fileName);
        }
        processConf(fileName);
    }


    public void processConf(String fileName) {
    }


    public List<String> getExcludes() {
        return excludes;
    }


    public void setExcludes(List<String> excludes) {
        this.excludes = excludes;
    }

}