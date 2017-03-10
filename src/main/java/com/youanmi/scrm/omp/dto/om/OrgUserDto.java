package com.youanmi.scrm.omp.dto.om;

/**
 * 添加类的一句话简单描述。
 * <p>
 * 详细描述
 *
 * @author 刘兵 on 2017/3/7
 */
public class OrgUserDto {

    private String userName;

    private String name;

    private String phone;

    private Byte gender;

    private String birthday;

    private String createTime;

    private String topOrgName;

    private String secondOrgName;

    private String postName;

    private Byte isActive;

    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTopOrgName() {
        return topOrgName;
    }

    public void setTopOrgName(String topOrgName) {
        this.topOrgName = topOrgName;
    }

    public String getSecondOrgName() {
        return secondOrgName;
    }

    public void setSecondOrgName(String secondOrgName) {
        this.secondOrgName = secondOrgName;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }
}
