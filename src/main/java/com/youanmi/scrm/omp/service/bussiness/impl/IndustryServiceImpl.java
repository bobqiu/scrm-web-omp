/*
 * 文件名：IndustryServiceImpl.java
 * 版权：深圳柚安米科技有限公司版权所有
 * 修改人：guohao
 * 修改时间：2016年12月5日
 * 修改内容：新增
 */
package com.youanmi.scrm.omp.service.bussiness.impl;

import java.util.List;

import javax.annotation.Resource;

import net.wildpig.base.common.entity.PageData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youanmi.commons.base.dao.BaseDAO;
import com.youanmi.scrm.omp.service.bussiness.IndustryService;


/**
 * 行业服务实现类
 * 
 * 
 * @author guohao
 * @since 2.2.4
 */
@Transactional(readOnly = true)
//@Service("industryService")
@Service
public class IndustryServiceImpl implements IndustryService {

    @Resource(name = "BaseDao")
    private BaseDAO dao;

    /**
     * 调测日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(IndustryServiceImpl.class);

    @Override
    public PageData queryIndustryList(PageData pd) throws Exception {
        int totalCount = Integer.parseInt(dao.findForObject("IndustryMapper.count", pd) + "");
        pd.put("from", pd.getInteger("offset"));
        pd.put("size", pd.getInteger("limit"));
        List<PageData> list = dao.findForList("IndustryMapper.getIndustrys", pd);
        PageData result = new PageData();
        result.put("total", totalCount);
        result.put("rows", list);
        return result;
    }

    @Override
    public PageData getFirstLevelIndustrys() throws Exception {
        List<PageData> datas = dao.findForList("IndustryMapper.getFirstLevelIndustrys", null);
        PageData result = new PageData();
        result.put("industrys", datas);
        return result;
    }

    @Transactional(rollbackFor = {Throwable.class}, readOnly = false)
    @Override
    public void addIndustry(PageData pd) throws Exception {
        dao.save("IndustryMapper.addIndustry", pd);
    }

    @Transactional(rollbackFor = {Throwable.class}, readOnly = false)
    @Override
    public void editIndustry(PageData pd) throws Exception {
        dao.save("IndustryMapper.editIndustry", pd);
    }

    @Override
    public PageData getIndustryInfo(PageData pd) throws Exception {
        return dao.findForObject("IndustryMapper.getIndustryInfo", pd);
    }
}
