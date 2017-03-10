package com.youanmi.scrm.omp.dto.om;

import java.io.Serializable;

/**
 * 公告门店实体
 * <p>
 * 详细描述
 *
 * @author xiao8 on 2016/12/7
 * @since ${version}
 */
public class NoticeOrgDto implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 公告id
     */
    private Long noticeId;
    /**
     * 门店id
     */
    private Long orgId;
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
