
package net.wildpig.base.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.wildpig.base.common.entity.PageData;
import net.wildpig.base.common.util.Const;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youanmi.commons.base.dao.BaseDAO;



/**
 * @FileName UserService.java
 * @Description: 
 *
 * @Date Feb 11, 2016 
 * @author YangShengJun
 * @version 1.0
 * 
 */
@Transactional(readOnly=true)
@Service("userService")
public class UserService {

	@Resource(name = "BaseDao")
	private BaseDAO dao;
	
	/***********************************/
	@Transactional(rollbackFor={Throwable.class}, readOnly=false)
	public void setSKIN(PageData pd) throws Exception {
		dao.update("UserXMapper.setSKIN", pd);
	}
	
	@Transactional(rollbackFor={Throwable.class}, readOnly=false)
	public void saveIP(PageData pd) throws Exception {
		dao.update("UserXMapper.saveIP", pd);
	}
	
	public PageData getUserByNameAndPwd(PageData pd) throws Exception {
		return dao.findForObject("UserMapper.getUserInfo", pd);
	}
	
	@Transactional(rollbackFor={Throwable.class}, readOnly=false)
	public void updateLastLogin(PageData pd) throws Exception {
		dao.update("UserXMapper.updateLastLogin", pd);
	}
	/***********************************/
	
	@Transactional(rollbackFor={Throwable.class}, readOnly=false)
	public PageData editPassword(PageData pd) throws Exception {
		PageData result = new PageData();
		Integer userId = pd.getInteger("userId");
		String oldPassword = pd.getString("oldpassword");
		String password = pd.getString("password");
		PageData user = dao.findForObject("UserMapper.getByUserId", userId);
		String loginName = user.getString("loginName");
		String oldPwd = new SimpleHash("SHA-1", loginName, oldPassword).toString();
		if (user.getString("password").equals(oldPwd)) {
			String newPwd = new SimpleHash("SHA-1", loginName, password).toString();
			pd.put("newPwd", newPwd);
			dao.update("UserMapper.updatePassword", pd);
			result.put("status", 1);
		} else {
			result.put("status", 0);
			result.put("msg", "原密码错误");
		}
		return result;
	}

	public PageData list(PageData pd) throws Exception {
		int totalCount = (int) dao.findForObject("UserMapper.count", pd);
		pd.put("from", pd.getInteger("offset"));
		pd.put("size", pd.getInteger("limit"));
		List<PageData> list = dao.findForList("UserMapper.list", pd);
		PageData result = new PageData();
		result.put("total", totalCount);
		result.put("rows", list);
		return result;
	}

	@Transactional(rollbackFor={Throwable.class}, readOnly=false)
	public void add(PageData pd) throws Exception {
		dao.save("UserMapper.add", pd);
		dao.save("UserMapper.saveSingleUserRole",pd);
	}
	
	public PageData getById(Integer userId) throws Exception {
		return dao.findForObject("UserMapper.getById", userId);
	}

	@Transactional(rollbackFor={Throwable.class}, readOnly=false)
	public void edit(PageData pd) throws Exception {
		dao.update("UserMapper.edit", pd);
		String roleId = (String) pd.get("roleId");
		if (StringUtils.isNotBlank(roleId)) {
			//解除用户与角色的关系
			dao.delete("UserMapper.deleteUserRole", pd.get("userId"));
			dao.save("UserMapper.saveSingleUserRole",pd);
		}
	}

	@Transactional(rollbackFor={Throwable.class}, readOnly=false)
	public void delete(Integer userId) throws Exception {
		dao.delete("UserMapper.delete", userId);
		//解除用户与角色的关系
		dao.delete("UserMapper.deleteUserRole", userId);
	}
	
	@Transactional(rollbackFor={Throwable.class}, readOnly=false)
	public void batchDelete(String ids) throws Exception {
		List<Integer> idList = net.wildpig.base.common.util.StringUtils.split(ids, Const.COMMA);
		if (idList != null && idList.size() > 0) {
			if (idList.contains(1)) {
				throw new Exception("不能删除超级管理员");
			}
			dao.delete("UserMapper.batchDelete", idList);
			//解除用户与角色的关系
			dao.delete("UserMapper.batchDeleteUserRole", idList);
		}
	}

	public List<PageData> getRoles(Integer userId) throws Exception {
		List<PageData> roles = dao.findForList("RoleMapper.listAllRoles", null);
		List<PageData> userRoles = dao.findForList("UserMapper.listUserRoleByUserId", userId);
		for (PageData role : roles) {
			Integer roleId = role.getInteger("roleId");
			for (PageData userRole : userRoles) {
				if (roleId.equals(userRole.getInteger("roleId"))) {
					role.put("checked", true);
					break;
				}
			}
		}
		return roles;
	}

	@Transactional(rollbackFor={Throwable.class}, readOnly=false)
	public void editRole(PageData pd) throws Exception {
		Integer userId = pd.getInteger("userId");
		Object roleIds = pd.get("roleIds");
		if (roleIds!=null  ) {
			List<PageData> list = new ArrayList<PageData>();
//			String[] roleIdArr = roleIds.split(",");
			String[] roleIdArr;
			if(roleIds instanceof String[]){
				roleIdArr = (String[])roleIds;
			}else{
				roleIdArr = new String[]{roleIds.toString()};
			}
			for (String roleId : roleIdArr) {
				PageData p = new PageData();
				p.put("userId", userId);
				p.put("roleId", Integer.valueOf(roleId));
				list.add(p);
			}
			dao.delete("UserMapper.deleteUserRole", userId);
			dao.batchSave("UserMapper.saveUserRole", list);
		} else {
			dao.delete("UserMapper.deleteUserRole", userId);
		}
	}

	public PageData getByLoginName(String loginName) throws Exception {
		return dao.findForObject("UserMapper.getByLoginName", loginName);
	}
}
