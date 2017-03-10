
package net.wildpig.base.service;

import java.util.List;

import javax.annotation.Resource;

import net.wildpig.base.common.entity.PageData;
import net.wildpig.base.common.entity.sys.Menu;
import net.wildpig.base.common.util.Const;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youanmi.commons.base.dao.BaseDAO;


/**
 * @FileName MenuService.java
 * @Description: 
 *
 * @Date Feb 11, 2016 
 * @author YangShengJun
 * @version 1.0
 * 
 */
@Transactional(readOnly=true)
@Service("menuService")
public class MenuService {

	@Resource(name = "BaseDao")
	private BaseDAO dao;
	
	public PageData list(PageData pd) throws Exception {
		int totalCount = (int) dao.findForObject("MenuMapper.count", pd);
		pd.put("from", pd.getInteger("offset"));
		pd.put("size", pd.getInteger("limit"));
		List<PageData> list = dao.findForList("MenuMapper.list", pd);
		//AppUtil.setMutiLang(list, "menuName");
		PageData result = new PageData();
		result.put("total", totalCount);
		result.put("rows", list);
		return result;
	}
	
//	@SuppressWarnings("unchecked")
//	public List<Menu> list(PageData pd) throws Exception {
//		pd.put("menuType", 1);
//		List<Menu> menus = (List<Menu>) dao.findForList("MenuMapper.list", pd);
//		
//		pd.put("menuType", 2);
//		for (Menu menu : menus) {
//			pd.put("parentId", menu.getMenuId());
//			List<Menu> subMenus = (List<Menu>) dao.findForList("MenuMapper.list", pd);
//			menu.setSubMenu(subMenus);
//		}
//		return menus;
//	}
	
	public List<Menu> listMenu(PageData pd) throws Exception {
		pd.put("menuType", 2);
		List<Menu> subMenus = dao.findForList("MenuMapper.list", pd);
		return subMenus;
	}

	@Transactional(rollbackFor={Throwable.class}, readOnly=false)
	public void add(PageData pd) throws Exception {
		dao.save("MenuMapper.add", pd);
	}
	
	public PageData getById(Integer menuId) throws Exception {
		return dao.findForObject("MenuMapper.getById", menuId);
	}

	@Transactional(rollbackFor={Throwable.class}, readOnly=false)
	public void edit(PageData pd) throws Exception {
		dao.update("MenuMapper.edit", pd);
	}

	@Transactional(rollbackFor={Throwable.class}, readOnly=false)
	public PageData delete(Integer menuId) throws Exception {
		PageData result = new PageData();
		PageData menu = getById(menuId);
		if (menu != null) {
			List<String> roleNames = dao.findForList("MenuMapper.listRoleNameByMenuId", menuId);
			if (roleNames == null || roleNames.size() == 0) {
				dao.delete("MenuMapper.delete", menuId);
				if (menu.getInteger("menuType") == 1) {
					dao.delete("MenuMapper.deleteByParentId", menuId);
					dao.delete("MenuMapper.deleteButtonByParentId", menuId);
				} else {
					dao.delete("MenuMapper.deleteButtonByMenuId", menuId);
				}
				result.put("status", 1);
			} else {
				String names = "";
				for (int i = 0; i < roleNames.size(); i++) {
					names += "," + roleNames.get(i);
					if (i >= 2 && roleNames.size() > 3) {
						names += "...";
						break;
					}
				}
				if (names.length() > 0) {
					names = names.substring(1);
				}
				result.put("status", 0);
				result.put("msg", "该资源已被角色(" + names + ")使用，不能删除");
			}
		} else {
			result.put("status", 1);
		}
		return result;
	}

	@Transactional(rollbackFor={Throwable.class}, readOnly=false)
	public PageData batchDelete(String ids) throws Exception {
		PageData result = new PageData();
		List<Integer> menuIds = net.wildpig.base.common.util.StringUtils.split(ids, Const.COMMA);
		if (menuIds != null && menuIds.size() > 0) {
			List<String> roleNames = dao.findForList("MenuMapper.listRoleNameByMenuIds", menuIds);
			if (roleNames == null || roleNames.size() == 0) {
				PageData menu = getById(menuIds.get(0));
				dao.delete("MenuMapper.batchDelete", menuIds);
				if (menu.getInteger("menuType") == 1) {
					dao.delete("MenuMapper.batchDeleteByParentId", menuIds);
					dao.delete("MenuMapper.batchDeleteButtonByParentId", menuIds);
				} else {
					dao.delete("MenuMapper.batchDeleteButtonByMenuId", menuIds);
				}
				result.put("status", 1);
			} else {
				String names = "";
				for (int i = 0; i < roleNames.size(); i++) {
					names += "," + roleNames.get(i);
					if (i >= 2 && roleNames.size() > 3) {
						names += "...";
						break;
					}
				}
				if (names.length() > 0) {
					names = names.substring(1);
				}
				result.put("status", 0);
				result.put("msg", "选中的资源有被角色(" + names + ")使用，不能批量删除");
			}
		} else {
			result.put("status", 1);
		}
		return result;
	}

}
