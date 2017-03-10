package com.youanmi.scrm.omp.vo.ics;

/**
 * 添加类的一句话简单描述。
 * <p>
 * 详细描述
 *
 * @author liulj on 2017/2/14
 * @since ${version}
 */

public class AutoReplyTempletVo {
    private Integer id;

    private String keywords;

    private String content;

    private Byte type;

    private Byte isDisable;

    private Byte isSupportCustom;

    private Long createBy;

    private Long createTime;

    private Long updateBy;

    private Long updateTime;

    private String birthdayHeader;

    private Byte isDelete;

    public Integer getId() {
        return id;
    }

    public String getBirthdayHeader() {
        return birthdayHeader;
    }

    public void setBirthdayHeader(String birthdayHeader) {
        this.birthdayHeader = birthdayHeader;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(Byte isDisable) {
        this.isDisable = isDisable;
    }

    public Byte getIsSupportCustom() {
        return isSupportCustom;
    }

    public void setIsSupportCustom(Byte isSupportCustom) {
        this.isSupportCustom = isSupportCustom;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }
}
