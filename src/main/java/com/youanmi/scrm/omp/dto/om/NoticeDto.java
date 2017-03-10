package com.youanmi.scrm.omp.dto.om;

import java.io.Serializable;

/**
 * 公告实体
 * <p>
 * 详细描述
 *
 * @author xiao8 on 2016/12/3
 * @since ${version}
 */
public class NoticeDto implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 公告标题
     */
    private String title;
    /**
     * 公告内容
     */
    private String content;
    /**
     * 发送对象（1-商户）
     */
    private Integer sendObject;
    /**
     * 设备类型（1-安卓，2-苹果）
     */
    private Integer deviceType;
    /**
     * 区域省
     */
    private Long province;
    /**
     * 区域市
     */
    private Long city;
    /**
     * 区域区
     */
    private Long county;

    private String sendArea;
    /**
     * 门店id
     */
    private Long orgId;
    /**
     * 发布时间（定时发送是未来时间）
     */
    private Long sendTime;
    /**
     * 发送状态（1-草搞，2-已发送，3-取消发送，4-定时发送）
     */
    private Integer sendStatus;
    /**
     * 发送渠道（omp-运营后台，smp-商户后台）
     */
    private String sendChannel;
    /**
     * 发送用户id
     */
    private Long sendUserId;
    /**
     * 发送用户名
     */
    private String sendUsername;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 创建人
     */
    private Long createId;
    /**
     * 更新人
     */
    private Long updateId;
    /**
     * 更新时间
     */
    private Long updateTime;
    /**
     * 是否删除(1:未删除 2:删除)
     */
    private Integer isDelete;
    /**
     * 推送总数
     */
    private Integer sendTotal;
    /**
     * 已发送
     */
    private Integer pushNum;

    /**
     * 已到达
     */
    private Integer msgProcess;

    /**
     * 点击数
     */
    private Integer clickNum;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSendObject() {
        return sendObject;
    }

    public void setSendObject(Integer sendObject) {
        this.sendObject = sendObject;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Long getProvince() {
        return province;
    }

    public void setProvince(Long province) {
        this.province = province;
    }

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }

    public Long getCounty() {
        return county;
    }

    public void setCounty(Long county) {
        this.county = county;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getSendChannel() {
        return sendChannel;
    }

    public void setSendChannel(String sendChannel) {
        this.sendChannel = sendChannel;
    }

    public Long getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Long sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getSendUsername() {
        return sendUsername;
    }

    public void setSendUsername(String sendUsername) {
        this.sendUsername = sendUsername;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getSendTotal() {
        return sendTotal;
    }

    public void setSendTotal(Integer sendTotal) {
        this.sendTotal = sendTotal;
    }

    public Integer getPushNum() {
        return pushNum;
    }

    public void setPushNum(Integer pushNum) {
        this.pushNum = pushNum;
    }

    public Integer getMsgProcess() {
        return msgProcess;
    }

    public void setMsgProcess(Integer msgProcess) {
        this.msgProcess = msgProcess;
    }

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }

    public String getSendArea() {
        return sendArea;
    }

    public void setSendArea(String sendArea) {
        this.sendArea = sendArea;
    }
}
