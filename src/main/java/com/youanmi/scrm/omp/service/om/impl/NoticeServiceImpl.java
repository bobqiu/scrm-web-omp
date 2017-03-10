package com.youanmi.scrm.omp.service.om.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.wildpig.base.common.entity.PageData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youanmi.commons.base.dao.BaseDAO;
import com.youanmi.scrm.omp.dto.om.NoticeDto;
import com.youanmi.scrm.omp.dto.om.NoticeOrgDto;
import com.youanmi.scrm.omp.dto.om.NoticeUserDto;
import com.youanmi.scrm.omp.service.bussiness.IAddressService;
import com.youanmi.scrm.omp.service.om.NoticeService;
import com.youanmi.sky.api.account.dto.OrgDto;
import com.youanmi.sky.api.account.dto.UserInfoDto;
import com.youanmi.sky.api.account.service.IOrgService;
import com.youanmi.sky.api.account.service.IUserInfoService;
import com.youanmi.sky.api.nps.service.NewGeTuiPushService;
import com.youanmi.sky.api.nps.service.PushByUserCallback;
import com.youanmi.sky.api.nps.service.PushStatisticsService;
import com.youanmi.sky.api.nps.service.dto.PushStatisticsDataDTO;
import com.youanmi.sky.api.nps.service.dto.PushUserResult;


/**
 * 公告实现
 * <p>
 * 详细描述
 *
 * @author xiao8 on 2016/12/3
 * @since ${version}
 */
@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {

    private static Logger logger = LoggerFactory.getLogger(NoticeServiceImpl.class);

    @Resource(name="BaseDao")
    private BaseDAO dao;

//    @Autowired
//    private IOrgService orgServiceConsumer;

//    @Autowired
//    private IUserInfoService userInfoService;

    @Autowired
    private NewGeTuiPushService geTuiPushService;

    @Autowired
    private PushStatisticsService pushStatisticsService;

    @Autowired
    private IAddressService addressService;

    //@Autowired
    //private IOrgService orgService;

    //@Autowired
    //private IUserInfoService userInfoService;

    @Override
    public PageData queryNoticeList(PageData pageData) throws  Exception{

        logger.debug("begin queryNoticeList ");
        List<NoticeDto> list = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startSendTimeStr = pageData.getString("startSendTimeStr");
        String endSendTimeStr = pageData.getString("endSendTimeStr");
        if (startSendTimeStr != null && !startSendTimeStr.isEmpty()) {
            pageData.put("startSendTime", sdf.parse(startSendTimeStr).getTime());
        }
        if (endSendTimeStr != null && !endSendTimeStr.isEmpty()) {
            pageData.put("endSendTime", sdf.parse(endSendTimeStr).getTime()+(1000*60*60*24));//加一天
        }

        Integer total = 0;
        try {
            list = dao.findForList("NoticeMapper.queryList",pageData);
            if(list!=null && !list.isEmpty()) for (NoticeDto noticeDto : list) {
                setNoticeArea(noticeDto);
                setGetuiSum(noticeDto);
            }
            total = dao.findForObject("NoticeMapper.queryListTotal",pageData);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("queryNoticeList error ",e);
        }
        logger.debug("end queryNoticeList ");

        pageData.put("rows",list);
        pageData.put("total",total);
        return pageData;
    }

    @Override
    public NoticeDto getNoticeById(Long id) {
        logger.debug("begin getNoticeById ");
        NoticeDto noticeDto = null;
        try {
            noticeDto = dao.findForObject("NoticeMapper.findById",id);
            setNoticeArea(noticeDto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getNoticeById error ",e);
        }
        logger.debug("end getNoticeById ");

        return noticeDto;
    }

    @Override
    @Transactional
    public NoticeDto addNotice(NoticeDto noticeDto) {
        logger.debug("begin addNotice ");
        noticeDto.setCreateTime(System.currentTimeMillis());
        if(noticeDto.getSendTime()!=null && noticeDto.getSendTime() != 0 && noticeDto.getSendTime()>System.currentTimeMillis()){
            // 定时发送
            noticeDto.setSendStatus(4);
        }else{
            // 立即发送
            noticeDto.setSendStatus(2);
        }

        try {
            dao.save("NoticeMapper.save",noticeDto);
            logger.debug("addNotice save end");
            //如果是立即发送 保存后调用发送逻辑
            if(noticeDto.getSendStatus()==2){
                logger.debug("addNotice begin sendNotice");
                sendNotice(noticeDto);//TODO 接口调通后开放
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("addNotice error ",e);
        }
        logger.debug("end addNotice ");

        return noticeDto;
    }

    @Override
    @Transactional
    public void editNotice(NoticeDto noticeDto) {
        logger.debug("begin editNotice ");
        NoticeDto updateDto = new NoticeDto();
        // 主键
        updateDto.setId(noticeDto.getId());
        //标题
        updateDto.setTitle(noticeDto.getTitle());
        //内容
        updateDto.setContent(noticeDto.getContent());
        //区域
        updateDto.setProvince(noticeDto.getProvince());
        updateDto.setCity(noticeDto.getCity());
        updateDto.setCounty(noticeDto.getCounty());
        updateDto.setOrgId(noticeDto.getOrgId());
        //设备类型
        updateDto.setDeviceType(noticeDto.getDeviceType());
        //渠道
        updateDto.setSendChannel(noticeDto.getSendChannel());
        //对象
        updateDto.setSendObject(noticeDto.getSendObject());
        //发送时间
        updateDto.setSendTime(noticeDto.getSendTime());
        if(noticeDto.getSendTime()!=null && noticeDto.getSendTime() != 0 && noticeDto.getSendTime()>System.currentTimeMillis()){
            // 定时发送
            updateDto.setSendStatus(4);
        }else{
            // 立即发送
            updateDto.setSendStatus(2);
        }
        //修改时间
        updateDto.setUpdateId(noticeDto.getUpdateId());
        updateDto.setUpdateTime(System.currentTimeMillis());

        try {
            dao.update("NoticeMapper.update",updateDto);
            logger.debug("editNotice end ");
            //如果是立即发送 修改后调用发送逻辑
            if(noticeDto.getSendStatus()==2){
                logger.debug("editNotice begin sendNotice");
                sendNotice(noticeDto);//TODO 接口调通后开放
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("editNotice error ",e);
        }
        logger.debug("end editNotice ");

    }

    @Override
    @Transactional
    public void cancelSend(Long id,Long updateId) {
        logger.debug("begin cancelSend ");
        updateNoticeStatus(id,updateId,3);
//        NoticeDto updateDto = new NoticeDto();
//        // 主键
//        updateDto.setId(id);
//        // 状态
//        updateDto.setSendStatus(3);
//        //修改时间
//        updateDto.setUpdateId(updateId);
//        updateDto.setUpdateTime(System.currentTimeMillis());
//        try {
//            dao.update("NoticeMapper.update",updateDto);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("cancelSend error ",e);
//        }
        logger.debug("end cancelSend ");
    }

    @Override
    @Transactional
    public void delNotice(Long id,Long updateId) {
        logger.debug("begin delNotice ");

        NoticeDto updateDto = new NoticeDto();
        // 主键
        updateDto.setId(id);
        // 状态
        updateDto.setIsDelete(2);
        //修改时间
        updateDto.setUpdateId(updateId);
        updateDto.setUpdateTime(System.currentTimeMillis());
        try {
            dao.update("NoticeMapper.update",updateDto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("delNotice error ",e);
        }
        logger.debug("end delNotice ");
    }

    /**
     * 设置区域名称
     * @param noticeDto 需要设置的对象
     * @return 返回设置后的对象
     */
    @Async
    private NoticeDto setNoticeArea(NoticeDto noticeDto) {
        // 接口容错处理
        try {
            String addressStr = "";
            PageData provinceData = addressService.getProvinceDataById(noticeDto.getProvince());
            if (provinceData != null && !provinceData.isEmpty()) {
                addressStr += provinceData.get("name");
            }
            PageData cityData = addressService.getCityDataById(noticeDto.getCity());
            if (cityData != null && !cityData.isEmpty()) {
                addressStr += cityData.get("name");
            }
            PageData areaData = addressService.getAreaDataById(noticeDto.getCounty());
            if (areaData != null && !areaData.isEmpty()) {
                addressStr += areaData.get("name");
            }
            noticeDto.setSendArea(addressStr);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("公告地址获取出错",e);
        }
        return noticeDto;
    }

    /**
     * 设置个推统计数据
     * @param noticeDto 需要设置的对象
     * @return 返回设置后的对象
     */
    private NoticeDto setGetuiSum(NoticeDto noticeDto){
        //设置个推统计数据
        //目前业务一条公告只推送一次
        PushStatisticsDataDTO pushStatisticsDataDTO = null;
        try {
            pushStatisticsDataDTO = pushStatisticsService.getLastPushStatisticsByBusId(noticeDto.getId());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("个推接口调用出错",e);
        }
        if(pushStatisticsDataDTO!=null){
            noticeDto.setClickNum(pushStatisticsDataDTO.getClickNum());
            noticeDto.setMsgProcess(pushStatisticsDataDTO.getMsgProcess());
            noticeDto.setSendTotal(pushStatisticsDataDTO.getSendTotal());
            noticeDto.setPushNum(pushStatisticsDataDTO.getPushNum());
        }
        return noticeDto;
    }

    /**
     * 定时发送逻辑
     */
    @Override
    public void sendNoticeTask(){

        try {
            // 查询发送数据
            PageData pageData = new PageData();
            // 状态是定时发送
            pageData.put("sendStatus", 4);
            // 时间取当前时间前5分钟数据
            pageData.put("startSendTime", System.currentTimeMillis() - (5 * 60 * 1000));
            pageData.put("endSendTime", System.currentTimeMillis());
            //查询数据总数，分批处理
            Integer total = dao.findForObject("NoticeMapper.queryListTotal",pageData);
            //有数据才进入处理
            if(total>0){
                //分批100条
                for (Integer i = 0; i < total; ) {
                    pageData.put("limit",100);
                    pageData.put("offset",i);
                    List<NoticeDto> list = dao.findForList("NoticeMapper.queryList",pageData);
                    // 给每一条数据调用发送逻辑
                    list.forEach(this::sendNotice);
                    i+=100;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送公告
     * @param noticeDto 公告对象
     */
    private void sendNotice(NoticeDto noticeDto){
        logger.debug("begin sendNotice");
        if(noticeDto==null){
            return;
        }
        // 修改状态 先把状态改为已发送
        updateNoticeStatus(noticeDto.getId(),0L,2);

        //按省市区门店查用户
        // 获取推送用户，并生成或更新发送用户和发送门店到数据库
        logger.debug("sendNotice getPushUserByArea ");
        List<NoticeUserDto> userList = getPushUserByArea(noticeDto);
        // 推送的用户列表
        List<Long> pushUserList = new ArrayList<>();
        userList.forEach(user -> pushUserList.add(user.getUserId()));
        logger.debug("sendNotice getPushUserByArea ");

        logger.debug("begin sendNotice geTuiPushService.pushByUser");
        try {
            geTuiPushService.pushByUser(pushUserList, noticeDto.getTitle(), noticeDto.getContent(), "", 400, 4, noticeDto.getDeviceType(), noticeDto.getId(), new PushByUserCallback() {
                private static final long serialVersionUID = 7718765563994885223L;

                @Override
                public void callback(List<PushUserResult> resultList) {
                    //保存推送状态
                    logger.debug("begin sendNotice updatePushNoticeUserStatus");
                    resultList.forEach(item -> updatePushNoticeUserStatus(item.getUserId(),noticeDto.getId(),item.getStatus()));
                    logger.debug("end sendNotice updatePushNoticeUserStatus");

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("个推接口调用出错",e);
        }
        logger.debug("end sendNotice geTuiPushService.pushByUser");
        logger.debug("end sendNotice");
    }


    /**
     * 修改公告状态
     * @param id 公告id
     * @param updateId 修改人
     * @param status 状态值 2-已发送 3-已取消 4-定时发送
     */
    private void updateNoticeStatus(Long id,Long updateId,Integer status) {
        logger.debug("begin updateNoticeStatus ");

        NoticeDto updateDto = new NoticeDto();
        // 主键
        updateDto.setId(id);
        // 状态
        updateDto.setSendStatus(status);
        //修改时间
        updateDto.setUpdateId(updateId);
        updateDto.setUpdateTime(System.currentTimeMillis());
        try {
            dao.update("NoticeMapper.update",updateDto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updateNoticeStatus error ",e);
        }
        logger.debug("end updateNoticeStatus ");
    }

    /**
     * 按区域获取推送用户
     * @param noticeDto 推送公告对象
     * @return 返回推送用户列表
     */
    private List<NoticeUserDto> getPushUserByArea(NoticeDto noticeDto){
        Long orgId =noticeDto.getOrgId();
        List<NoticeUserDto> result = new ArrayList<>();
        List<Long> orgList = new ArrayList<>();
        // 如果有门店id 直接按门店id查询
        if (orgId != null && !orgId.equals(0L)) {
            orgList.add(orgId);
        }else{ // 否则按省市区 查门店 再门店查用户
            // 查询省市区下的门店
            orgList = getOrgIdByArea(noticeDto.getProvince(),noticeDto.getCity(),noticeDto.getCounty());

        }
        // 分别查询每个门店下的用户
        orgList.forEach(_orgId -> {
            List<Long> userList = getUserIdByOrg(_orgId);
            userList.forEach(userId -> {
                NoticeUserDto userDto = new NoticeUserDto();
                userDto.setCreateTime(System.currentTimeMillis());
                userDto.setNoticeId(noticeDto.getId());
                userDto.setCreateId(0L);
                userDto.setOrgId(_orgId);
//                userDto.setImei(); //imei号无法获取
                userDto.setIsDelete(1);
                userDto.setSendStatus(2);
                userDto.setSendTime(System.currentTimeMillis());
                userDto.setTitle(noticeDto.getTitle());
                userDto.setUserId(userId);
                result.add(userDto);
            });
        });

        // 保存门店
        savePushOrg(orgList,noticeDto.getId());
        // 保存推送用户
        savePushUser(result,noticeDto);
        return result;
    }

    /**
     * 获取门店下的所有店员用户id
     * @param orgId 门店id
     * @return 店员用户id列表
     */
    private List<Long> getUserIdByOrg(Long orgId){
        logger.debug("begin getUserIdByOrg");
        List<Long> userIds = new ArrayList<>();
        boolean isAllOrg = false;
        int pageNo = 1;
        while (!isAllOrg){
            logger.debug("getUserIdByOrg pageNo:"+pageNo);
            List<UserInfoDto> userList;
            try {
                userList = new ArrayList<>();
                //userList = userInfoService.getAllOrgClerkByOrgId(orgId,100,pageNo);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("账户接口调用出错",e);
                userList = new ArrayList<>();
            }
            userList.forEach(org -> userIds.add(org.getId()));
            if(userList.size()<100){
                isAllOrg=true;
            }
            pageNo++;
        }
        logger.debug("end getUserIdByOrg");
        return userIds;
    }

    /**
     * 按省市区获取门店id
     * @param provinceId 省id
     * @param cityId 市id
     * @param countyId 区id
     * @return 区域的门店id列表
     */
    private List<Long> getOrgIdByArea(Long provinceId,Long cityId,Long countyId){
        List<Long> orgList = new ArrayList<>();
        boolean isAllOrg = false;
        int pageNo = 1;
        while (!isAllOrg){
            List<OrgDto> orgDtos;
            try {
                orgDtos = new ArrayList<>();
                //orgDtos = orgService.getOrgByAddress(provinceId,cityId,countyId,100,pageNo);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("账户接口调用出错",e);
                orgDtos = new ArrayList<>();
            }
            orgDtos.forEach(org -> orgList.add(org.getId()));
            if(orgDtos.size()<100){
                isAllOrg=true;
            }
            pageNo++;
        }
        return orgList;
    }

    /**
     * 保存推送门店
     * @param orgList 门店列表
     * @param noticeId 公告id
     */
    private void savePushOrg(List<Long> orgList,Long noticeId){
        logger.debug("begin savePushOrg");
        if(orgList==null||orgList.isEmpty()||noticeId==null){
            logger.debug("savePushOrg orgList==null||orgList.isEmpty()||noticeId==null");
            return;
        }

        List<NoticeOrgDto> noticeOrgList = new ArrayList<>();
        for (Long orgId : orgList) {
            NoticeOrgDto orgDto = new NoticeOrgDto();
            orgDto.setCreateId(0L);
            orgDto.setCreateTime(System.currentTimeMillis());
            orgDto.setOrgId(orgId);
            orgDto.setNoticeId(noticeId);
            noticeOrgList.add(orgDto);
            //每一100条写一次数据库
            if(noticeOrgList.size()==100){
                try {
                    dao.save("NoticeMapper.savePushNoticeOrg",noticeOrgList);
                    noticeOrgList = new ArrayList<>();
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("savePushOrg error", e);
                }
            }
        }
        // 如果noticeOrgList的数据不够100 也需要保存
        if(!noticeOrgList.isEmpty()){
            try {
                dao.save("NoticeMapper.savePushNoticeOrg",noticeOrgList);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("savePushOrg error", e);
            }
        }
        logger.debug("end savePushOrg");
    }

    /**
     * 保存推送用户信息，异步保存
     * @param userList 推送用户列表
     * @param noticeDto 推送的公告
     */
    @Async
    private void savePushUser(List<NoticeUserDto> userList,NoticeDto noticeDto){
        logger.debug("begin savePushUser");
        if (userList == null || userList.isEmpty() || noticeDto == null) {
            return;
        }
        try {
            dao.save("NoticeMapper.savePushNoticeUser",userList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("savePushOrg error", e);
        }

        logger.debug("end savePushUser");
    }

    /**
     * 修改推送状态
     * @param userId 用户id
     * @param noticeId 公告id
     * @param status 推送状态
     */
    private void updatePushNoticeUserStatus(Long userId,Long noticeId,Integer status){
        NoticeUserDto noticeUserDto = new NoticeUserDto();
        noticeUserDto.setUserId(userId);
        noticeUserDto.setNoticeId(noticeId);
        noticeUserDto.setUpdateId(0L);
        noticeUserDto.setSendStatus(status);
        noticeUserDto.setUpdateTime(System.currentTimeMillis());
        logger.debug("begin updatePushNoticeUserStatus");
        try {
            dao.update("NoticeMapper.updatePushNoticeUserStatus",noticeUserDto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updatePushNoticeUserStatus error", e);
        }
        logger.debug("end updatePushNoticeUserStatus");
    }


}
