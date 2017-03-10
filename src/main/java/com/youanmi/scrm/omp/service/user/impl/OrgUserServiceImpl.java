package com.youanmi.scrm.omp.service.user.impl;

import com.youanmi.scrm.api.account.dto.org.OrgInfoDto;
import com.youanmi.scrm.api.account.dto.org.OrgPostDto;
import com.youanmi.scrm.api.account.dto.org.OrgStaffDto;
import com.youanmi.scrm.api.account.dto.user.UserInfoDto;
import com.youanmi.scrm.api.account.service.org.IOrgInfoService;
import com.youanmi.scrm.api.account.service.org.IOrgPostService;
import com.youanmi.scrm.api.account.service.org.IOrgStaffService;
import com.youanmi.scrm.api.account.service.user.IUserInfoService;
import com.youanmi.scrm.commons.util.date.DateUtils;
import com.youanmi.scrm.commons.util.json.JsonUtils;
import com.youanmi.scrm.commons.util.string.AssertUtils;
import com.youanmi.scrm.omp.dto.om.OrgUserDto;
import com.youanmi.scrm.omp.service.user.OrgUserService;
import net.wildpig.base.common.entity.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 添加类的一句话简单描述。
 * <p>
 * 详细描述
 *
 * @author Administrator on 2017/3/7
 * @since ${version}
 */
@Service("orgUserService")
public class OrgUserServiceImpl implements OrgUserService {


    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IOrgStaffService orgStaffService;
    @Autowired
    private IOrgInfoService orgInfoService;
    @Autowired
    private IOrgPostService orgPostService;

    //日志记录器
    private static Logger LOG = LoggerFactory.getLogger(OrgUserServiceImpl.class);

    /*
    查询类型
     */
    private static final Byte USER_NAME = 1;
    private static final Byte NAME = 2;
    private static final Byte PHONE = 3;

    @Override
    public PageData getUserList(PageData pd) throws ParseException {

        LOG.info("getUserList:{}", JsonUtils.toJSON(pd));

        Map<String, Object> map = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //分页参数
        map.put("startIndex", pd.getInteger("offset"));
        map.put("pageSize", pd.getInteger("limit"));
        //查询时间起止
        if (AssertUtils.notNull(pd.getString("startSendTimeStr"))) {
            map.put("startTime", sdf.parse(pd.getString("startSendTimeStr")).getTime());
        }
        if (AssertUtils.notNull(pd.getString("endSendTimeStr"))) {
            map.put("endTime", sdf.parse(pd.getString("endSendTimeStr")).getTime() + (1000*60*60*24));
        }
        //查询类型
        Byte queryType = pd.getByte("searchCondition");
        String content = pd.getString("content");
        if (USER_NAME.equals(queryType)) {
            map.put("userName", content);
        } else if (NAME.equals(queryType)) {
            map.put("name", queryType);
        } else if (PHONE.equals(queryType)){
            map.put("mobilePhone", queryType);
        }
        map.put("isForbid", pd.getByte("isForbid"));
        map.put("isActive", pd.getByte("isActive"));

        PageData result = new PageData();
        result.put("rows", userInfoService.getOrgUserListByConditions(map));
        result.put("total", userInfoService.getOrgUserCount(map));
        return result;
    }

    @Override
    public OrgUserDto getUserDetail(Long id) {
        //获取用户信息
        UserInfoDto userInfo = userInfoService.getUserById(id);
        OrgStaffDto staffInfo = orgStaffService.getStaffByUserId(id);
        Long orgId = staffInfo.getOrgId();
        Long topOrgId = staffInfo.getTopOrgId();
        String topOrgName = null;
        String secondOrgName = null;
        if (topOrgId != null) {
            OrgInfoDto topDto = orgInfoService.getOrgById(topOrgId);
            topOrgName = topDto.getOrgName();
        }

        if (orgId != null) {
            OrgInfoDto secondDto = orgInfoService.getOrgById(orgId);
            secondOrgName = secondDto.getOrgName();
        }

        String postName = null;
        if (staffInfo.getPostId() != null) {
            OrgPostDto orgPostDto = orgPostService.getOrgPost(staffInfo.getPostId());
            postName = orgPostDto.getPostName();
        }
        OrgUserDto orgUserDto = new OrgUserDto();
        orgUserDto.setUserName(userInfo.getUserName());
        orgUserDto.setName(userInfo.getName());
        orgUserDto.setPhone(userInfo.getMobilePhone());
        orgUserDto.setGender(userInfo.getGender());
        orgUserDto.setBirthday(DateUtils.parseNumberToDate(userInfo.getBirthday()));
        orgUserDto.setCreateTime(DateUtils.parseNumberToDate(userInfo.getCreateTime()));
        orgUserDto.setTopOrgName(topOrgName);
        orgUserDto.setSecondOrgName(secondOrgName);
        orgUserDto.setPostName(postName);
        orgUserDto.setIsActive(userInfo.getIsActive());
        return orgUserDto;
    }

}
