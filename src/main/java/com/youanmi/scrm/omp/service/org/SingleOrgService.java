/*
 * 文件名：SingleOrgService.java
 * 版权：深圳柚安米科技有限公司版权所有
 * 修改人：tanguojun
 * 修改时间：2017年3月7日
 * 修改内容：新增
 */
package com.youanmi.scrm.omp.service.org;

import net.wildpig.base.common.entity.PageData;

import com.youanmi.commons.base.vo.PageBean;
import com.youanmi.scrm.api.account.dto.org.OrgInfoDto;


/**
 * 单店service
 * 
 * @author     tanguojun 2017年3月7日
 * @version    1.0.0
 */
public interface SingleOrgService {
    
    /**
     * 分页条件查询单店列表
     *
     * @param pageIndex
     * @param pageSize
     * @param inputType
     * @param inputString
     * @param firstIndustryId
     * @param secondIndustryId
     * @param expireType
     * @param startCreateTimeStr
     * @param endCreateTimeStr
     * @param provinceId
     * @param cityId
     * @param areaId
     * @return
     * @author tanguojun on 2017年3月2日
     */
    PageBean<OrgInfoDto> selectSingleOrgListByCondition(int pageIndex, int pageSize, Integer inputType,
            String inputString, Long firstIndustryId, Long secondIndustryId, Integer expireType,
            String startCreateTimeStr, String endCreateTimeStr, Long provinceId, Long cityId, Long areaId);
    
    /**
     * 删除单店
     *
     * @param id
     * @author tanguojun on 2017年3月6日
     */
    PageData deleteSingleOrg(Long id);
    
    /**
     * 重置密码
     *
     * @param id
     * @return
     * @author tanguojun on 2017年3月6日
     */
    PageData resetPwd(Long id);
    
    /**
     * 分店详情 
     *
     * @param id
     * @return
     * @author tanguojun on 2017年3月7日
     */
    OrgInfoDto orgDetail(Long id);
}
