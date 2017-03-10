package com.youanmi.scrm.omp.dto.om;

import java.io.Serializable;

/**
 * 公告用户实体
 * <p>
 * 详细描述
 *
 * @author xiao8 on 2016/12/7
 * @since ${version}
 */
public class NoticeUserDto implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 公告标题
     */
    private String title;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户imei
     */
    private String imei;
    /**
     * 公告id
     */
    private Long noticeId;
    /**
     * 门店id
     */
    private Long orgId;
    /**
     * 发送时间
     */
    private Long sendTime;
    /**
     * 发送状态(1-已发送，2-待发送，3-取消发送)
     */
    private Integer sendStatus;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 创建人
     */
    private Long createId;
    /**
     * 最后更新时间
     */
    private Long updateTime;
    /**
     * 最后更新人
     */
    private Long updateId;
    /**
     * 是否删除(1:未删除 2:删除)
     */
    private Integer isDelete;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
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

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
