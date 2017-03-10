
package net.wildpig.base.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.wildpig.base.common.entity.sys.Button;
import net.wildpig.base.common.entity.sys.Menu;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youanmi.commons.base.dao.BaseDAO;

/**
 * @FileName LoginService.java
 * @Description: 
 *
 * @Date May 6, 2015 
 * @author YangShengJun
 * @version 1.0
 * 
 */
@Transactional(readOnly=true)
@Service("loginService")
public class LoginService {
	
	@Resource(name = "BaseDao")
	private BaseDAO dao;
	

	public List<String> getRightsRolesId(Integer userId) throws Exception {
		return dao.findForList("RoleMapper.findRolesIdByUserId", userId);
	}
	
	public List<Menu> getRightsParentMenus(Map<?, ?> parames) throws Exception {
		return dao.findForList("MenuMapper.findParentMenusByUserId", parames);
	}
/*
	public List<Menu> getSecondlevelMenus(Map<?, ?> parames) throws Exception {
		return dao.findForList("MenuMapper.findMenusByUserIdAndTwoParent", parames);
	}
	public List<Menu> getThreelevelMenus(Map<?, ?> parames) throws Exception {
		return dao.findForList("MenuMapper.findMenusByUserIdAndThreeParent", parames);
	}*/

	public List<Button> getRightsButtons(Integer userId) throws Exception {
		return dao.findForList("ButtonMapper.findButtonsByUserId", userId);
	}
}
