package com.youanmi.scrm.omp.service.user;

import com.youanmi.scrm.api.account.dto.user.UserInfoDto;
import com.youanmi.scrm.omp.dto.om.OrgUserDto;
import net.wildpig.base.common.entity.PageData;

import java.text.ParseException;
import java.util.Map;

/**
 * 添加类的一句话简单描述。
 * <p>
 * 详细描述
 *
 * @author liubing on 2017/3/7
 */
public interface OrgUserService {

    /**
     * 获取企业用户列表
     * @param pd 分页查询参数
     * @return
     */
    PageData getUserList(PageData pd) throws ParseException;

    /**
     * 获取用于详细信息
     * @param id 用户id
     * @return
     */
    OrgUserDto getUserDetail(Long id);

}
