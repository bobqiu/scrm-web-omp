package com.youanmi.scrm.omp.service.org.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.wildpig.base.common.entity.PageData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youanmi.commons.base.dao.BaseDAO;
import com.youanmi.scrm.api.account.dto.org.BranchOrgDetailDto;
import com.youanmi.scrm.api.account.dto.org.BranchOrgDto;
import com.youanmi.scrm.api.account.dto.org.SelectBranchOrgDto;
import com.youanmi.scrm.api.account.service.org.IOrgInfoService;
import com.youanmi.scrm.api.data.dto.industry.IndustryDto;
import com.youanmi.scrm.api.data.service.industry.IIndustryService;
import com.youanmi.scrm.omp.service.bussiness.IndustryService;
import com.youanmi.scrm.omp.service.org.BranchOrgService;


/**
 * 连锁分店实现类
 * @author laishaoqiang
 * 
 */
@Transactional(readOnly = true)
@Service("branchOrgService")
public class BranchOrgServiceImpl implements BranchOrgService {

    @Resource(name = "BaseDao")
    private BaseDAO dao;
    
    @Autowired
    private IOrgInfoService orgInfoService;
    
    @Autowired
    private IIndustryService industryService;
    
    /**
     * 调测日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(BranchOrgServiceImpl.class);

    @Override
    public PageData branchOrgList(PageData pd) throws Exception {
        pd.put("from", pd.getInteger("offset"));
        pd.put("size", pd.getInteger("limit"));
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startTimeStr = pd.getString("startTimeStr");
        String endTimeStr = pd.getString("endTimeStr");
        if (startTimeStr != null && !startTimeStr.isEmpty()) {
            pd.put("startTimeStamp", sdf.parse(startTimeStr).getTime());
        }
        if (endTimeStr != null && !endTimeStr.isEmpty()) {
            pd.put("endTimeStamp", sdf.parse(endTimeStr).getTime()+(1000*60*60*24));//加一天
        }
        
        SelectBranchOrgDto dto = new SelectBranchOrgDto();
        dto.setStartIndex(pd.getInteger("offset"));
        dto.setPageSize(pd.getInteger("limit"));
        dto.setAccountOrName(pd.getInteger("accountOrName"));
        dto.setKey(pd.getString("key"));
        dto.setProvinceId(pd.getLong("provinceId"));
        dto.setCityId(pd.getLong("cityId"));
        dto.setAreaId(pd.getLong("areaId"));
        dto.setTopOrgName(pd.getString("topOrgName"));
        
        dto.setStartTimeStr(pd.getString("startTimeStr"));
        dto.setEndTimeStr(pd.getString("endTimeStr"));
        dto.setStartTimeStamp(pd.getLong("startTimeStamp"));
        dto.setEndTimeStamp(pd.getLong("endTimeStamp"));
        
        dto.setFirstIndustryId(pd.getLong("firstIndustryId"));
        dto.setSecondIndustryId(pd.getLong("secondIndustryId"));
        
        //查询dubbo服务接口
        List<BranchOrgDto> list = orgInfoService.branchOrgList(dto);
        //List<PageData> lists = dao.findForList("BranchOrgMapper.branchOrgList", pd);
        Long totalCount = orgInfoService.branchOrgCount(dto);
        
        //把加入时间转化为年月日格式
        List<Long> totalIndustryList = new ArrayList<Long>();
        for (BranchOrgDto branchOrgDto : list) {
            if(branchOrgDto.getCreateTime() != null) {
                String createTimeStr = sdf.format(new Date(branchOrgDto.getCreateTime()));
                branchOrgDto.setCreateTimeStr(createTimeStr);
            }
            
            Long firstIndustryId = branchOrgDto.getFirstIndustryId();
            Long secondIndustryId = branchOrgDto.getSecondIndustryId();
            if(firstIndustryId != null) {
                totalIndustryList.add(firstIndustryId);
            }
            if(secondIndustryId != null) {
                totalIndustryList.add(secondIndustryId);
            }
            
        }
        
        //设置行业
        if(totalIndustryList != null && totalIndustryList.size() > 0) {
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("ids", totalIndustryList);
            List<IndustryDto> industryDtoList = industryService.getIndustrysByIds(map1);
            
            Map<Long, String> targetMap = listToMap(industryDtoList);
            for (BranchOrgDto branchOrgDto : list) {
                Long firstIndustryId = branchOrgDto.getFirstIndustryId();
                Long secondIndustryId =branchOrgDto.getSecondIndustryId();
                
                String firstIndustryName = targetMap.get(firstIndustryId) != null ? targetMap.get(firstIndustryId) : "";
                String secondIndustryName = targetMap.get(secondIndustryId) != null ? targetMap.get(secondIndustryId) : "";
                
                String industryStr = firstIndustryName + "-" + secondIndustryName;
                branchOrgDto.setIndustryStr(industryStr);
            }
        }
        
        PageData result = new PageData();
        result.put("total", totalCount);
        result.put("rows", list);
        return result;
    }

    private Map<Long, String> listToMap(List<IndustryDto> industryDtoList) {
        Map<Long, String> map = new HashMap<Long, String>();
        
        for(IndustryDto dto : industryDtoList) {
            map.put(dto.getId(), dto.getName());
        }
        
        return map;
        
    }

    /**
     * 
     * 连锁分店详情
     */
    @Override
    public BranchOrgDetailDto getBranchOrgDetail(Long orgId) {
        BranchOrgDetailDto dto = orgInfoService.getBranchOrgDetail(orgId);
        
        if(dto != null) {
            //设置行业
            Long firstIndustryId = dto.getFirstIndustryId();
            Long secondIndustryId =dto.getSecondIndustryId();
            
            IndustryDto firstIndustryDto = industryService.getIndustryById(firstIndustryId);
            IndustryDto secondIndustryDto = industryService.getIndustryById(secondIndustryId);
            
            String firstIndustryName = "";
            if(firstIndustryDto != null) {
                firstIndustryName = firstIndustryDto.getName();
            }
            String secondIndustryName = "";
            if(secondIndustryDto != null) {
                secondIndustryName = secondIndustryDto.getName();
            }
            
            String industryStr = firstIndustryName + "-" + secondIndustryName;
            dto.setIndustryStr(industryStr);
            
            //设置创建时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(dto.getCreateTime());
            String createTimeStr = sdf.format(date);
            dto.setCreateTimeStr(createTimeStr);
        }
        
        return dto;
    }
}
