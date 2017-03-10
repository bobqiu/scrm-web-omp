/*
 * 文件名：IndustryService.java
 * 版权：深圳柚安米科技有限公司版权所有
 * 修改人：guohao
 * 修改时间：2016年12月6日
 * 修改内容：新增
 */
package com.youanmi.scrm.omp.service.bussiness;

import net.wildpig.base.common.entity.PageData;


/**
 * 工作机服务
 * 
 * @author guohao
 * @since 2.2.4
 */
public interface WorkingMachineService {

    /**
     * 检索工作机列表
     * 
     * @param pd
     * @return
     */
    public PageData queryDeviceList(PageData pd) throws Exception;
    
}
