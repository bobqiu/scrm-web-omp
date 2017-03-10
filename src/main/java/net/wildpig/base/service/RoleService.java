package net.wildpig.base.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.wildpig.base.common.entity.PageData;
import net.wildpig.base.common.util.Const;
import net.wildpig.base.common.util.DateUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youanmi.commons.base.dao.BaseDAO;


/**
 * @FileName RoleService.java
 * @Description:
 *
 * @Date Feb 11, 2016
 * @author YangShengJun
 * @version 1.0
 * 
 */
@Transactional(readOnly = true)
@Service("roleService")
public class RoleService {

    @Resource(name = "BaseDao")
    private BaseDAO dao;


    /**
     * 根具状态获取List
     * 
     * @param status
     *            1未删除,-1删除
     * @return
     */
    public List<PageData> getListByStatus(int status) throws Exception {
        List<PageData> list = dao.findForList("RoleMapper.getListByStatus", new PageData() {
            {
                put("status", status);
            }
        });
        return list;
    }


    public PageData list(PageData pd) throws Exception {
        int totalCount = (int) dao.findForObject("RoleMapper.count", pd);
        pd.put("from", pd.getInteger("offset"));
        pd.put("size", pd.getInteger("limit"));
        List<PageData> list = dao.findForList("RoleMapper.list", pd);
        for (PageData key : list) {
            if (key.get("update_time") != null) {
                key.put("update_time", DateUtil.parseNumberToDate((Long) key.get("update_time")));
            }
            if (key.get("description") == null || "".equals(key.get("description"))) {
                key.put("description", "--");
            }

        }
        // AppUtil.setMutiLang(list, "roleName");
        PageData result = new PageData();
        result.put("total", totalCount);
        result.put("rows", list);
        return result;
    }


    @Transactional(rollbackFor = {Throwable.class}, readOnly = false)
    public void add(PageData pd) throws Exception {
        dao.save("RoleMapper.add", pd);

        // 新增一个角色时默认授权首页
        pd.put("menuName", "首页");
        PageData muen = dao.findForObject("MenuMapper.getByName", pd);
        if (muen != null) {
            pd.put("roleId", pd.getString("role_id"));
            pd.put("resourceId", muen.getString("menuId"));
            pd.put("resourceType", muen.getString("menuType"));
            dao.save("RoleMapper.saveRoleRes", pd);
        }
    }


    public PageData getById(Integer roleId) throws Exception {
        return dao.findForObject("RoleMapper.getById", roleId);
    }


    @Transactional(rollbackFor = {Throwable.class}, readOnly = false)
    public void edit(PageData pd) throws Exception {
        dao.update("RoleMapper.edit", pd);
    }


    @Transactional(rollbackFor = {Throwable.class}, readOnly = false)
    public PageData delete(Integer roleId) throws Exception {
        PageData result = new PageData();
        PageData role = getById(roleId);
        if (role != null) {
            List<String> usernames = dao.findForList("RoleMapper.listUsernameByRoleId", roleId);
            if (usernames == null || usernames.size() == 0) {
                dao.delete("RoleMapper.delete", roleId);
                // 解除角色与资源的关系
                dao.delete("RoleMapper.deleteRoleRes", roleId);
                result.put("status", 1);
            }
            else {
                result.put("status", 0);
                result.put("msg", "该角色下已有对应的工作人员，请对人员进行调整后在删除");
            }
        }
        else {
            result.put("status", 1);
        }
        return result;
    }


    @Transactional(rollbackFor = {Throwable.class}, readOnly = false)
    public PageData batchDelete(String ids) throws Exception {
        PageData result = new PageData();
        List<Integer> roleIds = net.wildpig.base.common.util.StringUtils.split(ids, Const.COMMA);
        if (roleIds != null && roleIds.size() > 0) {
            if (roleIds.contains(1)) {
                result.put("status", 0);
                result.put("msg", "不能删除超级管理员");
                return result;
            }
            List<String> usernames = dao.findForList("RoleMapper.listUsernameByRoleIds", roleIds);
            if (usernames == null || usernames.size() == 0) {
                dao.delete("RoleMapper.batchDelete", roleIds);
                // 解除角色与资源的关系
                dao.delete("RoleMapper.batchDeleteRoleRes", roleIds);
                result.put("status", 1);
            }
            else {
                String names = "";
                for (int i = 0; i < usernames.size(); i++) {
                    names += "," + usernames.get(i);
                    if (i >= 2 && usernames.size() > 3) {
                        names += "...";
                        break;
                    }
                }
                if (names.length() > 0) {
                    names = names.substring(1);
                }
                result.put("status", 0);
                result.put("msg", "选中的角色有被用户(" + names + ")使用，不能批量删除");
            }
        }
        else {
            result.put("status", 1);
        }
        return result;
    }


    public List<PageData> listTreeData(Integer roleId) throws Exception {
        List<PageData> result = new ArrayList<PageData>();

        PageData pd = new PageData();
        pd.put("parentId", 0);
        // 组装权限数结构的数据
        treeData(pd, result);
        // 设置已选中的权限
        List<PageData> roleResList = dao.findForList("RoleMapper.listResByRoleId", roleId);
        for (PageData roleRes : roleResList) {
            String resFlag = roleRes.getInteger("resourceId") + "_" + roleRes.getInteger("resourceType");
            for (PageData p : result) {
                if (resFlag.equals(p.getString("resFlag"))) {
                    p.put("checked", true);
                    break;
                }
            }
        }

        return result;
    }


    /**
     * 
     * 组装权限树结构的数据
     * 
     * @param pd
     * @param result
     * @throws Exception
     */
    private void treeData(PageData pd, List<PageData> result) throws Exception {
        // 菜单或分类
        List<PageData> menuList = dao.findForList("MenuMapper.listBy", pd);
        for (PageData menu : menuList) {
            PageData p1 = new PageData();
            p1.put("id", menu.getString("menuId"));
            p1.put("pId", menu.getString("parentId"));
            p1.put("name", menu.getString("menuName"));
            if ("首页".equals(menu.getString("menuName"))) {
                p1.put("checked", true);
            }
            p1.put("open", "true");
            p1.put("resFlag", menu.getString("menuId") + "_" + menu.getString("menuType"));
            result.add(p1);
            pd.put("parentId", menu.getInteger("menuId"));
            // 按钮
            List<PageData> buttonList =
                    dao.findForList("ButtonMapper.listByMenuId", menu.getInteger("menuId"));
            for (PageData button : buttonList) {
                PageData p4 = new PageData();
                p4.put("id", button.getString("menuId") + "_" + button.getString("buttonId"));
                p4.put("pId", button.getString("menuId"));
                p4.put("name", button.getString("buttonName"));
                p4.put("open", "true");
                p4.put("resFlag", button.getString("buttonId") + "_" + 3);
                result.add(p4);
            }
            // 递归
            treeData(pd, result);
        }
    }


    @Transactional(rollbackFor = {Throwable.class}, readOnly = false)
    public void editRight(PageData pd) throws Exception {
        Integer roleId = pd.getInteger("roleId");
        String selRes = pd.getString("selRes");
        if (StringUtils.isNotBlank(selRes)) {
            List<PageData> list = new ArrayList<PageData>();
            String[] resFlags = selRes.split(",");
            for (String resFlag : resFlags) {
                String[] resArr = resFlag.split("_");
                PageData p = new PageData();
                p.put("roleId", roleId);
                p.put("resourceId", Integer.valueOf(resArr[0]));
                p.put("resourceType", Integer.valueOf(resArr[1]));
                list.add(p);
            }
            dao.delete("RoleMapper.deleteResByRoleId", roleId);
            dao.batchSave("RoleMapper.saveRes", list);
        }
        else {
            dao.delete("RoleMapper.deleteResByRoleId", roleId);
        }
    }


    /**
     * 检查角色重名
     * 
     * @param name
     * @return
     * @throws Exception
     */
    public int checkRoleName(PageData pd) throws Exception {
        return dao.findForObject("RoleMapper.checkRoleName", pd);
    }
}
