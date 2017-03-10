/*
 * 文件名：IAddressService.java
 * 版权：深圳柚安米科技有限公司版权所有
 * 修改人：吴尚高
 * 修改时间：2016年12月6日
 * 修改内容：新增
 */
package com.youanmi.scrm.omp.service.bussiness;

import java.util.List;

import net.wildpig.base.common.entity.PageData;


/**
 * 区域接口
 * 
 * 
 * @author 吴尚高
 * @since 2.2.4
 */
public interface IAddressService {

    /**
     * 
     * 查询省份list
     * 
     * @return
     */
    public List<PageData> selectProvinceList() throws Exception;


    /**
     * 根据省份ID查询城市列表
     * 
     * @param provinceId
     * @return
     */
    public List<PageData> selectCityList(String provinceId) throws Exception;


    /**
     * 根据城市ID查询地区列表
     * 
     * @param cityId
     * @return
     */
    public List<PageData> selectAreaList(String cityId) throws Exception;


    /**
     * 
     * 根据省份ID查询城市列表分页
     * 
     * @param pd
     * @return
     * @throws Exception
     */
    public PageData cityListPage(PageData pd) throws Exception;


    /**
     * 根据省ID查询省信息
     * 
     * @param provinceId
     * @return
     */
    public PageData getProvinceDataById(Long provinceId);


    /**
     * 根据城市ID查询城市信息
     * 
     * @param cityId
     * @return
     */
    public PageData getCityDataById(Long cityId);


    /**
     * 根据地区ID查询地区信息
     * 
     * @param areaId
     * @return
     */
    public PageData getAreaDataById(Long areaId);
}
