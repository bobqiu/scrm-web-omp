
package net.wildpig.base.common.entity.sys;

import java.util.List;


/**
 * @FileName User.java
 * @Description: 
 *
 * @Date Feb 11, 2016 
 * @author YangShengJun
 * @version 1.0
 * 
 */
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 1;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getSkin() {
		return skin;
	}
	public void setSkin(Integer skin) {
		this.skin = skin;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	private Integer userId; // 用户id
	private String loginName; // 用户名
	private String password; // 密码
	private String name; // 姓名
	private String lastLogin; // 最后登录时间
	private String ip; // 用户登录ip地址
	private Integer status; // 状态
	private String description; // 用户登录ip地址
	private String email; // 用户登录ip地址
	private String phone; // 用户登录ip地址
	private Integer skin; // 皮肤
	private List<Role> roles;
	

}
