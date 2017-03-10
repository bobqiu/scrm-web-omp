/*
 * 文件名：IndustryService.java
 * 版权：深圳柚安米科技有限公司版权所有
 * 修改人：guohao
 * 修改时间：2016年12月5日
 * 修改内容：新增
 */
package com.youanmi.scrm.omp.service.bussiness;

import net.wildpig.base.common.entity.PageData;


/**
 * 行业服务
 * 
 * 
 * @author guohao
 * @since 2.2.4
 */
public interface IndustryService {

    /**
     * 检索行业列表
     * 
     * @param pd
     * @return
     */
    public PageData queryIndustryList(PageData pd) throws Exception;

    /**
     * 获取一级行业列表
     * 
     * @param pd
     * @return
     */
    public PageData getFirstLevelIndustrys() throws Exception;

    /**
     * 获取行业信息
     * 
     * @param pd
     * @return
     */
    public PageData getIndustryInfo(PageData pd) throws Exception;

    /**
     * 添加行业
     * 
     * @param pd
     */
    public void addIndustry(PageData pd) throws Exception;

    /**
     * 编辑行业
     * 
     * @param pd
     */
    public void editIndustry(PageData pd) throws Exception;
}
