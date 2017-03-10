package com.youanmi.scrm.omp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;
import com.youanmi.scrm.commons.sms.conf.SmsConfBuilder;
import com.youanmi.scrm.commons.util.file.PropertiesUtils;

/**
 * Created by Administrator on 2017/2/23.
 */
@Service
@DisconfFile(filename = SmsConfig.SMS_NAME)
@DisconfUpdateService(classes = {SmsConfig.class})
public class SmsConfig implements IDisconfUpdate, InitializingBean {
    /**
     * sms文件名称
     */
    public static final String SMS_NAME = "common.sms.properties";

    /**
     * 调测日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(SmsConfig.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        LOG.info("init {" + SMS_NAME + "}");
        reload();
    }

    @Override
    public void reload() throws Exception {
        LOG.info("reload {" + SMS_NAME + "}");
        // 配置短信
        SmsConfBuilder.buildSmsConf(PropertiesUtils.getProperties(SMS_NAME));
    }

}
