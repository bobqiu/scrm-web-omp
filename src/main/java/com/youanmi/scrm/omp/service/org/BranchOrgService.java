package com.youanmi.scrm.omp.service.org;

import com.youanmi.scrm.api.account.dto.org.BranchOrgDetailDto;

import net.wildpig.base.common.entity.PageData;



/**
 * 连锁分店服务
 * 
 * @author laishaoqiang
 */
public interface BranchOrgService {


    /**
     * 连锁分店列表
     * 
     * @param pd
     * @return
     */
    public PageData branchOrgList(PageData pd) throws Exception;

    /**
     * 连锁分店详情
     * @param orgId
     * @return
     */
    public BranchOrgDetailDto getBranchOrgDetail(Long orgId);

    
}
