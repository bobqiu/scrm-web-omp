/*
 * 文件名：SingleOrgServiceImpl.java
 * 版权：深圳柚安米科技有限公司版权所有
 * 修改人：tanguojun
 * 修改时间：2017年3月7日
 * 修改内容：新增
 */
package com.youanmi.scrm.omp.service.org.impl;

import net.wildpig.base.common.entity.PageData;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youanmi.commons.base.vo.PageBean;
import com.youanmi.scrm.api.account.constants.AccountTableConstants;
import com.youanmi.scrm.api.account.dto.org.OrgDetailInfoDto;
import com.youanmi.scrm.api.account.dto.org.OrgExpireTimeDto;
import com.youanmi.scrm.api.account.dto.org.OrgInfoDto;
import com.youanmi.scrm.api.account.service.org.IOrgDetailInfoService;
import com.youanmi.scrm.api.account.service.org.IOrgExpireTimeService;
import com.youanmi.scrm.api.account.service.org.IOrgInfoService;
import com.youanmi.scrm.api.account.service.org.IOrgStaffService;
import com.youanmi.scrm.api.data.dto.industry.IndustryDto;
import com.youanmi.scrm.api.data.service.industry.IIndustryService;
import com.youanmi.scrm.commons.constants.DateConstants;
import com.youanmi.scrm.commons.util.date.DateUtils;
import com.youanmi.scrm.commons.util.exception.ExceptionUtils;
import com.youanmi.scrm.omp.constants.ResultConstants;
import com.youanmi.scrm.omp.service.org.SingleOrgService;
import com.youanmi.scrm.omp.service.system.OperateLogService;


/**
 * 单店service实现
 * 
 * @author tanguojun 2017年3月7日
 * @version 1.0.0
 */
@Service("singleOrgService")
public class SingleOrgServiceImpl implements SingleOrgService {
    /**
     * 调测日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(SingleOrgServiceImpl.class);

    @Autowired
    private IOrgInfoService orgInfoService;

    @Autowired
    private IOrgStaffService orgStaffService;

    @Autowired
    private IIndustryService industryService;

    @Autowired
    private OperateLogService operateLogService;

    @Autowired
    private IOrgDetailInfoService orgDetailInfoService;

    @Autowired
    private IOrgExpireTimeService orgExpireTimeService;

    @Override
    public PageBean<OrgInfoDto> selectSingleOrgListByCondition(int pageIndex, int pageSize,
            Integer inputType, String inputString, Long firstIndustryId, Long secondIndustryId,
            Integer expireType, String startCreateTimeStr, String endCreateTimeStr, Long provinceId,
            Long cityId, Long areaId) {

        // 单店账号
        String orgAccount = null;
        // 单店全称
        String orgFullName = null;

        // inputType=1则为单店全称查询
        if (inputType != null && inputType == 1 && StringUtils.isNotBlank(inputString)) {
            orgFullName = inputString;
        }
        // inputType=2则为单店账号查询
        else if (inputType != null && inputType == 2 && StringUtils.isNotBlank(inputString)) {
            orgAccount = inputString;
        }

        Long currentTime = DateUtils.getCurrentDay();
        // 开始过期时间
        Long startExpireTime = null;
        // 结束过期时间
        Long endExpireTime = null;
        // 3天内
        if (expireType != null && expireType == 1) {
            // 现在加3天
            endExpireTime = currentTime + (3 * 24 * 60 * 60 * 1000L);
            startExpireTime = currentTime;
        }
        // 一周内
        else if (expireType != null && expireType == 2) {
            // 现在加7天
            endExpireTime = currentTime + (7 * 24 * 60 * 60 * 1000L);
            startExpireTime = currentTime;
        }
        // 一个月内
        else if (expireType != null && expireType == 3) {
            // 现在加30天
            endExpireTime = currentTime + (30 * 24 * 60 * 60 * 1000L);
            startExpireTime = currentTime;
        }

        Long startCreateTime = null;
        Long endCreateTime = null;
        // 开始加入时间
        if (StringUtils.isNotBlank(startCreateTimeStr)) {
            startCreateTime = DateUtils.parseDateToNumber(startCreateTimeStr, DateConstants.YYYY_MM_dd);
        }
        // 结束加入时间
        if (StringUtils.isNotBlank(endCreateTimeStr)) {
            endCreateTime = DateUtils.parseDateToNumber(endCreateTimeStr, DateConstants.YYYY_MM_dd);
        }

        // 查询列表
        PageBean<OrgInfoDto> list =
                orgInfoService.selectOrgListByCondition(pageIndex, pageSize,
                    AccountTableConstants.Org.ORG_TYPE_SINGLE_SHOP, orgFullName, orgAccount, firstIndustryId,
                    secondIndustryId, startExpireTime, endExpireTime, startCreateTime, endCreateTime,
                    provinceId, cityId, areaId);

        // 结果不为空
        if (list.getData() != null && !list.getData().isEmpty()) {

            // 遍历
            for (OrgInfoDto org : list.getData()) {

                // 查询单店行业名称
                if (org.getFirstIndustryId() != null) {
                    IndustryDto firstIndustryNameDto =
                            industryService.getIndustryById(org.getFirstIndustryId());
                    org.setFirstIndustryName(firstIndustryNameDto.getName());
                }
                if (org.getSecondIndustryId() != null) {
                    IndustryDto secondIndustryNameDto =
                            industryService.getIndustryById(org.getSecondIndustryId());
                    org.setSecondIndustryName(secondIndustryNameDto.getName());
                }
            }
        }

        return list;
    }

    @Override
    public PageData deleteSingleOrg(Long id) {

        PageData result = new PageData();

        if (id == null) {
            result.put("msg", "单店不存在");
            result.put("status", ResultConstants.FAIL);
            return result;
        }

        // 查询单店下否有员工&店员
        Long staffCount = orgStaffService.getCountStaffByTopOrg(id);

        // 存在员工,返回错误信息
        if (staffCount > 0) {
            result.put("msg", "该单店下存在员工，无法删除！");
            result.put("status", ResultConstants.FAIL);
            return result;
        }

        boolean isDelete = false;
        try {
            // 删除单店
            isDelete = orgInfoService.deleteOrgsByTopOrgId(id);

            //TODO 删除门店账号
            //TODO 删除员工
            //TODO 删除岗位
            //TODO 删除超管角色
        }
        catch (Exception e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
        }

        // 删除成功
        if (isDelete) {
            // 返回成功
            result.put("status", ResultConstants.SUCCESS);

            // 操作日志记录
            operateLogService.saveOperateLog("删除单店,“" + id + "”");
        }
        // 删除失败,返回错误信息
        else {
            result.put("msg", "删除失败！");
            result.put("status", ResultConstants.FAIL);
        }
        return result;
    }

    @Override
    public PageData resetPwd(Long id) {

        PageData result = new PageData();

        try {
            // 重置密码
            orgInfoService.resetOrgManagerPwd(id, AccountTableConstants.Org.ORG_TYPE_SINGLE_SHOP);
            // 操作日志记录
            operateLogService.saveOperateLog("单店账号重置密码,“" + id + "”");

            // 返回成功
            result.put("status", ResultConstants.SUCCESS);
        }
        catch (Exception e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
            result.put("msg", "重置密码失败！");
            result.put("status", ResultConstants.FAIL);
        }

        return result;
    }

    @Override
    public OrgInfoDto orgDetail(Long id) {

        // 判断id是否为空
        if (id == null) {
            throw new RuntimeException("单店不存在");
        }

        // 查询单店信息
        OrgInfoDto orgInfo = orgInfoService.getOrgById(id);

        if (orgInfo == null) {
            throw new RuntimeException("单店不存在");
        }
        // 查询单店行业名称
        if (orgInfo.getFirstIndustryId() != null) {
            IndustryDto firstIndustryNameDto = industryService.getIndustryById(orgInfo.getFirstIndustryId());
            orgInfo.setFirstIndustryName(firstIndustryNameDto.getName());
        }
        if (orgInfo.getSecondIndustryId() != null) {
            IndustryDto secondIndustryNameDto =
                    industryService.getIndustryById(orgInfo.getSecondIndustryId());
            orgInfo.setSecondIndustryName(secondIndustryNameDto.getName());
        }

        // 查询单店详情
        OrgDetailInfoDto orgDetail = orgDetailInfoService.getOrgDetailByOrgId(id);
        // 设置详情
        orgInfo.setOrgDetail(orgDetail);

        // 查询单店到期信息
        OrgExpireTimeDto orgExpireTime = orgExpireTimeService.getOrgExpireTimePoByOrgId(id);
        // 设置单店到期信息
        orgInfo.setOrgExpireTime(orgExpireTime);

        return orgInfo;
    }

}
