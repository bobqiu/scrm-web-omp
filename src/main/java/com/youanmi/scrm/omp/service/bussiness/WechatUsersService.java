package com.youanmi.scrm.omp.service.bussiness;

import java.text.ParseException;

import net.wildpig.base.common.entity.PageData;

/**
 * 微信用户
 * @author laishaoqiang
 */
public interface WechatUsersService {


    public PageData listAllWechatUsers(PageData pd) throws ParseException;

    
}
