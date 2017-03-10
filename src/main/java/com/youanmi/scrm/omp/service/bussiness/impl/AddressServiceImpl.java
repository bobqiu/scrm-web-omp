/*
 * 文件名：AddressServiceImpl.java
 * 版权：深圳柚安米科技有限公司版权所有
 * 修改人：吴尚高
 * 修改时间：2016年12月6日
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
import com.youanmi.scrm.omp.service.bussiness.IAddressService;


/**
 * 区域实现类
 * 
 * 
 * @author 吴尚高
 * @since 2.2.4
 */
@Transactional(readOnly = true)
@Service("addressService")
public class AddressServiceImpl implements IAddressService {
    /**
     * 调测日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Resource(name = "BaseDao")
    private BaseDAO dao;


    @Override
    public List<PageData> selectProvinceList() throws Exception {
        List<PageData> list = dao.findForList("AddressMapper.selectProvinceList", null);
        return list;
    }


    @Override
    public List<PageData> selectCityList(String provinceId) throws Exception {
        List<PageData> list = dao.findForList("AddressMapper.selectCityList", new PageData() {
            {
                put("provinceId", provinceId);
            }
        });
        return list;
    }


    @Override
    public List<PageData> selectAreaList(String cityId) throws Exception {

        List<PageData> list = dao.findForList("AddressMapper.selectAreaList", new PageData() {
            {
                put("cityId", cityId);
            }
        });
        return list;
    }


    @Override
    public PageData cityListPage(PageData pd) throws Exception {
        int totalCount = (int) dao.findForObject("AddressMapper.cityListCount", pd);
        pd.put("from", pd.getInteger("offset"));
        pd.put("size", pd.getInteger("limit"));
        // 城市列表
        List<PageData> list = dao.findForList("AddressMapper.cityListPage", pd);
        for (PageData city : list) {
            // 该城市的区
            List<PageData> areaList = this.selectAreaList(city.getString("id"));
            city.put("areaList", areaList);
        }
        PageData result = new PageData();
        result.put("total", totalCount);
        result.put("rows", list);
        return result;
    }


    @Override
    public PageData getProvinceDataById(Long provinceId) {
        if (provinceId == null || provinceId == 0) {
            return new PageData();
        }
        try {
            return dao.findForObject("AddressMapper.getProvinceDataById", new PageData() {
                {
                    put("provinceId", provinceId);
                }
            });
        }
        catch (Exception e) {
            LOG.error("Failed to getProvinceDataById", e);
            return new PageData();
        }
    }


    @Override
    public PageData getCityDataById(Long cityId) {
        if (cityId == null || cityId == 0) {
            return new PageData();
        }
        try {
            return dao.findForObject("AddressMapper.getCityDataById", new PageData() {
                {
                    put("cityId", cityId);
                }
            });
        }
        catch (Exception e) {
            LOG.error("Failed to getCityDataById", e);
            return new PageData();
        }
    }


    @Override
    public PageData getAreaDataById(Long areaId) {
        if (areaId == null || areaId == 0) {
            return new PageData();
        }
        try {
            return dao.findForObject("AddressMapper.getAreaDataById", new PageData() {
                {
                    put("areaId", areaId);
                }
            });
        }
        catch (Exception e) {
            LOG.error("Failed to getAreaDataById", e);
            return new PageData();
        }
    }
}
