
package net.wildpig.base.service;

import java.util.List;

import javax.annotation.Resource;

import net.wildpig.base.common.entity.PageData;
import net.wildpig.base.common.util.Const;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youanmi.commons.base.dao.BaseDAO;



/**
 * @FileName ButtonService.java
 * @Description: 
 *
 * @Date Feb 11, 2016 
 * @author YangShengJun
 * @version 1.0
 * 
 */
@Transactional(readOnly=true)
@Service("buttonService")
public class ButtonService {

	@Resource(name = "BaseDao")
	private BaseDAO dao;

	public PageData list(PageData pd) throws Exception {
		int totalCount = (int) dao.findForObject("ButtonMapper.count", pd);
		pd.put("from", pd.getInteger("offset"));
		pd.put("size", pd.getInteger("limit"));
		List<PageData> list = dao.findForList("ButtonMapper.list", pd);
		//-AppUtil.setMutiLang(list, "buttonName");
		PageData result = new PageData();
		result.put("total", totalCount);
		result.put("rows", list);
		return result;
	}

	@Transactional(rollbackFor={Throwable.class}, readOnly=false)
	public void add(PageData pd) throws Exception {
		dao.save("ButtonMapper.add", pd);
	}
	
	public PageData getById(Integer buttonId) throws Exception {
		return dao.findForObject("ButtonMapper.getById", buttonId);
	}

	@Transactional(rollbackFor={Throwable.class}, readOnly=false)
	public void edit(PageData pd) throws Exception {
		dao.update("ButtonMapper.edit", pd);
	}

	@Transactional(rollbackFor={Throwable.class}, readOnly=false)
	public PageData delete(Integer buttonId) throws Exception {
		PageData result = new PageData();
		PageData button = getById(buttonId);
		if (button != null) {
			List<String> roleNames = dao.findForList("ButtonMapper.listRoleNameByButtonId", buttonId);
			if (roleNames == null || roleNames.size() == 0) {
				dao.delete("ButtonMapper.delete", buttonId);
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
		List<Integer> buttonIds = net.wildpig.base.common.util.StringUtils.split(ids, Const.COMMA);
		if (buttonIds != null && buttonIds.size() > 0) {
			List<String> roleNames = dao.findForList("ButtonMapper.listRoleNameByButtonIds", buttonIds);
			if (roleNames == null || roleNames.size() == 0) {
				dao.delete("ButtonMapper.batchDelete", buttonIds);
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
