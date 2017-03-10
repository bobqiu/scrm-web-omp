package com.youanmi.scrm.omp.service.bussiness.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.wildpig.base.common.entity.PageData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.youanmi.scrm.api.crm.dto.member.MemberRelationListDto;
import com.youanmi.scrm.api.crm.param.MemberRelationParam;
import com.youanmi.scrm.api.crm.service.IOrgCustomerRelationService;
import com.youanmi.scrm.omp.service.bussiness.WechatUsersService;

/**
 * 微信用户
 * @author laishaoqiang
 * 
 */
@Transactional(readOnly = true)
@Service("wechatUsersService")
public class WechatUsersServiceImpl implements WechatUsersService {

    @Autowired
    private IOrgCustomerRelationService orgCustomerRelationService;
    
    
    /**
     * 调测日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(WechatUsersServiceImpl.class);

    /**
     * 微信用户列表
     * @throws ParseException 
     */
    @Override
    public PageData listAllWechatUsers(PageData pd) throws ParseException {
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
        
        MemberRelationParam param = new MemberRelationParam();
        param.setStartIndex(pd.getInteger("offset"));
        param.setPageSize(pd.getInteger("limit"));
        param.setSelectType(pd.getInteger("selectType"));
        param.setKey(pd.getString("key"));
        param.setStartTimeStamp(pd.getLong("startTimeStamp"));
        param.setEndTimeStamp(pd.getLong("endTimeStamp"));
        
        //查询dubbo服务接口
        List<MemberRelationListDto> list = orgCustomerRelationService.getMemberRelationList(param);
        Long totalCount = orgCustomerRelationService.memberRelationCount(param);
        
        //把加入时间转化为年月日格式
        for (MemberRelationListDto dto: list) {
            if(dto.getCreateTime() != null) {
                
                String createTimeStr = sdf.format(new Date(dto.getCreateTime()));
                dto.setCreateTimeStr(createTimeStr);
            }
        }
        
        PageData result = new PageData();
        result.put("rows", list);
        result.put("total", totalCount);
        return result;
    }

    
    
    
    
}
