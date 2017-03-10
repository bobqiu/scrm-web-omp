

package net.wildpig.base.common.entity.sys;

import java.util.List;


/**
 * @FileName Role.java
 * @Description: 
 *
 * @Date Feb 11, 2016 
 * @author YangShengJun
 * @version 1.0
 * 
 */
public class Role implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer roleId;
	private String roleName;
	private Integer removable;
	private Integer allocatable;
	private String description;
	private Integer status;
	
	private List<Menu> menus;
	private List<Button> buttons;
	
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getRemovable() {
		return removable;
	}
	public void setRemovable(Integer removable) {
		this.removable = removable;
	}
	public Integer getAllocatable() {
		return allocatable;
	}
	public void setAllocatable(Integer allocatable) {
		this.allocatable = allocatable;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<Menu> getMenus() {
		return menus;
	}
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	public List<Button> getButtons() {
		return buttons;
	}
	public void setButtons(List<Button> buttons) {
		this.buttons = buttons;
	}
	
}
