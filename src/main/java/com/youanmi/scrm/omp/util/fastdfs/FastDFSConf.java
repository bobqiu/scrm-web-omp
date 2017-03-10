/*
 * 文件名：FastDFSConf.java
 * 版权：深圳柚安米科技有限公司版权所有
 * 描述： FastDFSConf.java
 * 修改人：龙汀
 * 修改时间：2015年8月21日
 * 修改内容：新增
 */
package com.youanmi.scrm.omp.util.fastdfs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;
import com.youanmi.fastdfs.utils.FastDFSUtil;
import com.youanmi.scrm.omp.constants.ConfName;
import com.youanmi.scrm.omp.util.ApplicationConfCache;


/**
 * 打包配置加载
 * <p>
 * 
 * 

 */
@Component
@DisconfFile(filename = ConfName.DFS_CONF)
@DisconfUpdateService(classes = {FastDFSConf.class})
public class FastDFSConf implements IDisconfUpdate,InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(FastDFSConf.class);
 

    @Override
    public void reload() throws Exception {
        if (LOG.isInfoEnabled()) {
            LOG.info("重新加载配置：" + ConfName.DFS_CONF);
        }
        ApplicationConfCache.initConf(ConfName.DFS_CONF);
        FastDFSUtil.build(ConfName.DFS_CONF);
        ConfUtils.iniCusConf(ConfName.DFS_CONF);
        ConfUtils.initPageSize();
    }
    
    /**
     * 
     * 获取配置的值
     * 
     * @param key
     *            配置键
     * @return value
     */
    public static String getConf(String key) {
        return ApplicationConfCache.getValue(ConfName.DFS_CONF, key);
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		reload();
	}
}
