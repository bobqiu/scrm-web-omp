package net.wildpig.base.controller.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import net.wildpig.base.common.entity.PageData;
import net.wildpig.base.common.entity.sys.User;
import net.wildpig.base.controller.BaseController;
import net.wildpig.base.service.RoleService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.youanmi.scrm.omp.constants.ResultConstants;
import com.youanmi.scrm.omp.service.system.OperateLogService;


//import com.alibaba.fastjson.JSONObject;

/**
 * @FileName RoleController.java
 * @Description:
 *
 * @Date Feb 11, 2016
 * @author YangShengJun
 * @version 1.0
 * 
 */
@Controller
@RequestMapping(value = "/role")
public class RoleController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Resource(name = "roleService")
    private RoleService roleService;

    @Autowired
    private OperateLogService operateLogService;


    @RequestMapping
    public ModelAndView page() {
        logger.info("role list page");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/role/role_list");
        return mv;
    }


    @RequestMapping(value = "/list")
    @ResponseBody
    public PageData list() {
        logger.info("role list data");
        PageData result = null;
        try {
            PageData pd = super.getPageData();
            result = roleService.list(pd);
        }
        catch (Exception e) {
            logger.error("list role error", e);
            result = new PageData();
        }
        return result;
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView toAdd() {
        logger.info("to add role");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/role/role_add");
        return mv;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public PageData add() throws Exception {
        logger.info("add role");
        PageData result = new PageData();
        PageData pd = super.getPageData();
        // 参数校验
        PageData param = validateParam(pd, "1");
        if ("0".equals(param.getString("status"))) {
            result.put("status", ResultConstants.FAIL);
            result.put("msg", param.getString("msg"));
            return result;
        }
        pd.put("createTime", System.currentTimeMillis());
        User user = (User) this.getCurrentUser();
        pd.put("createId", user.getUserId());
        pd.put("status", 1);
        pd.put("updateId", user.getUserId());
        pd.put("updateTime", System.currentTimeMillis());
        roleService.add(pd);
        result.put("status", ResultConstants.SUCCESS);

        operateLogService.saveOperateLog("新增角色“" + pd.get("roleName") + "”");
        return result;
    }


    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView toEdit(@RequestParam Integer roleId) {
        logger.info("to edit role: roleId=" + roleId);
        PageData pd = null;
        try {
            pd = roleService.getById(roleId);
        }
        catch (Exception e) {
            logger.error("get role error", e);
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("pd", pd);
        mv.setViewName("sys/role/role_edit");
        return mv;
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public PageData edit() throws Exception {
        logger.info("edit role");
        PageData result = new PageData();
        PageData pd = super.getPageData();
        // 参数校验
        PageData param = validateParam(pd, "2");
        if ("0".equals(param.getString("status"))) {
            result.put("status", ResultConstants.FAIL);
            result.put("msg", param.getString("msg"));
            return result;
        }
        PageData old = roleService.getById(pd.getInteger("roleId"));
        if (old == null) {
            result.put("status", ResultConstants.FAIL);
            result.put("msg", "修改角色失败，角色不存在");
            return result;
        }

        User user = (User) this.getCurrentUser();
        pd.put("updateId", user.getUserId());
        pd.put("updateTime", System.currentTimeMillis());
        roleService.edit(pd);
        result.put("status", ResultConstants.SUCCESS);
        operateLogService.saveOperateLog("修改角色“" + pd.get("roleName") + "”");

        return result;
    }


    @RequestMapping(value = "/delete")
    @ResponseBody
    public PageData delete(@RequestParam Integer roleId) {
        logger.info("delete role: roleId=" + roleId);
        PageData result = null;
        try {
            PageData pd = roleService.getById(roleId);
            if (pd == null) {
                logger.error("删除角色失败，角色不存在");
                result = new PageData();
                result.put("status", 0);
                result.put("msg", "被删除的信息不存在");
                return result;
            }
            result = roleService.delete(roleId);
            // 删除成功记日志
            if ("1".equals(result.getString("status"))) {
                operateLogService.saveOperateLog("删除角色“" + pd.get("roleName") + "”");
            }
        }
        catch (Exception e) {
            logger.error("delete role error", e);
            result = new PageData();
            result.put("status", 0);
            result.put("msg", "删除失败");
        }
        return result;
    }


    @RequestMapping(value = "/batchDelete")
    @ResponseBody
    public PageData batchDelete(@RequestParam String ids) {
        PageData result = null;
        try {
            result = roleService.batchDelete(ids);
        }
        catch (Exception e) {
            logger.error("batch delete role error", e);
            result = new PageData();
            result.put("status", 0);
            result.put("msg", "批量删除失败");
        }
        return result;
    }


    @RequestMapping(value = "/editRight", method = RequestMethod.GET)
    public ModelAndView toEditRight(@RequestParam Integer roleId) {
        logger.info("to edit right: roleId=" + roleId);
        PageData pd = null;
        try {
            pd = roleService.getById(roleId);
        }
        catch (Exception e) {
            logger.error("to edit right error", e);
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("pd", pd);
        mv.setViewName("sys/role/role_right_edit");
        return mv;
    }


    @RequestMapping(value = "/resNodes")
    @ResponseBody
    public List<PageData> resNodes(@RequestParam Integer roleId) {
        logger.info("edit res nodes: roleId=" + roleId);
        List<PageData> treeData = null;
        try {
            treeData = roleService.listTreeData(roleId);
        }
        catch (Exception e) {
            logger.error("get res nodes error", e);
            treeData = new ArrayList<PageData>();
        }
        return treeData;
    }


    @RequestMapping(value = "/editRight", method = RequestMethod.POST)
    @ResponseBody
    public PageData editRight() {
        logger.info("edit right");
        PageData result = new PageData();
        try {
            PageData pd = super.getPageData();
            roleService.editRight(pd);
            result.put("status", 1);
            // 日志
            Integer roleId = pd.getInteger("roleId");
            PageData role = roleService.getById(roleId);
            if (role != null) {
                operateLogService.saveOperateLog("修改了“" + role.get("roleName") + "”的权限");
            }
        }
        catch (Exception e) {
            logger.error("edit right error", e);
            result.put("status", 0);
            result.put("msg", "编辑权限失败");
        }
        return result;
    }


    @RequestMapping(value = "/checkRoleName", method = RequestMethod.POST)
    @ResponseBody
    public PageData checkRoleName() {
        logger.info("check role name");
        PageData result = new PageData();
        try {
            PageData pd = super.getPageData();
            int status = 1;
            int count = roleService.checkRoleName(pd);
            if (count != 0) {
                result.put("msg", "该角色名已存在");
                status = 0;
            }
            result.put("status", status);
        }
        catch (Exception e) {
            logger.error("check role name error", e);
            result.put("status", 0);
            result.put("msg", "检查角色重名失败");
        }
        return result;
    }


    /**
     * 
     * 参数校验
     * 
     * @param pd
     * @param opt
     * @return
     */
    private PageData validateParam(PageData pd, String opt) {
        logger.info("validate param");
        // 去空格
        pd.put("roleName", pd.getString("roleName").trim());
        pd.put("description", pd.getString("description").trim());
        PageData result = new PageData();
        // ////////////////////////////////////////////////
        // 角色名字校验
        String roleName = pd.getString("roleName");
        if (!StringUtils.isNotBlank(roleName)) {
            result.put("status", 0);
            result.put("msg", "角色名不能为空");
            return result;
        }
        // 只支持输入汉字和数字
        String regex = "^[\u4e00-\u9fa50-9A-Za-z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(roleName);
        boolean b = match.matches();
        if (!b) {
            result.put("status", 0);
            result.put("msg", "角色名只支持输入英文、数字、汉字及组合");
            return result;
        }
        // 长度校验
        if (roleName.length() > 8) {
            result.put("status", 0);
            result.put("msg", "角色名长度不能大于8");
            return result;
        }
        // 重名校验
        try {
            int status = 1;
            int count = roleService.checkRoleName(pd);
            if (count != 0) {
                result.put("msg", "该角色名已存在");
                status = 0;
                result.put("status", status);
                return result;
            }
            result.put("status", status);
        }
        catch (Exception e) {
            logger.error("check role name error", e);
            result.put("status", 0);
            result.put("msg", "检查角色重名失败");
            return result;
        }
        // //////////////////////////////////////////////
        // 角色描述校验
        String description = pd.getString("description");
        if (StringUtils.isBlank(description)) {
            result.put("status", 0);
            result.put("msg", "角色描述不能为空");
            return result;
        }
        // 角色描述长度校验
        if (description.length() > 50) {
            result.put("status", 0);
            result.put("msg", "角色描述长度不能大于50");
            return result;
        }
        return result;
    }
}
