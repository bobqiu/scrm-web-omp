package net.wildpig.base.controller.sys;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.wildpig.base.common.entity.PageData;
import net.wildpig.base.common.entity.sys.User;
import net.wildpig.base.common.util.Const;
import net.wildpig.base.controller.BaseController;
import net.wildpig.base.service.RoleService;
import net.wildpig.base.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.youanmi.scrm.omp.service.system.OperateLogService;


/**
 * @FileName UserController.java
 * @Description:
 *
 * @Date Feb 11, 2016
 * @author YangShengJun
 * @version 1.0
 * 
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "roleService")
    private RoleService roleService;

    @Resource(name = "operateLogService")
    private OperateLogService operateLogService;


    @RequestMapping(value = "/editPwd", method = RequestMethod.GET)
    public ModelAndView toEditPassword(@RequestParam Integer userId) {
        logger.info("to edit password: userId=" + userId);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/pwd_edit");
        mv.addObject("userId", userId);
        return mv;
    }


    @RequestMapping(value = "/editPwd", method = RequestMethod.POST)
    @ResponseBody
    public PageData editPassword() {
        logger.info("edit password");
        PageData result = null;
        try {
            PageData pd = super.getPageData();
            result = userService.editPassword(pd);

            // 记日志
            Integer userId = pd.getInteger("userId");
            PageData user = userService.getById(userId);
            String loginName = user.getString("loginName");
            operateLogService.saveOperateLog("修改用户“" + loginName + "”的密码");
        }
        catch (Exception e) {
            logger.error("edit password error", e);
            result = new PageData();
            result.put("status", 0);
            result.put("msg", "新增失败");
        }
        return result;
    }


    @RequestMapping
    public ModelAndView page() throws Exception {
        logger.info("user list page");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/user/user_list");
        mv.addObject("roleList", roleService.getListByStatus(1));
        mv.addObject("userId",
            ((User) SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_USER)).getUserId());
        PageData pd = super.getPageData();
        String roleId = pd.getString("roleId");
        if (!"".equals(roleId)) {
            mv.addObject("roleId", roleId);
        }
        return mv;
    }


    @RequestMapping(value = "/list")
    @ResponseBody
    public PageData list() {
        logger.info("user list data");
        PageData result = null;
        try {
            PageData pd = super.getPageData();
            result = userService.list(pd);
        }
        catch (Exception e) {
            logger.error("list user error", e);
            result = new PageData();
        }
        return result;
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView toAdd() throws Exception {
        logger.info("to add user");
        ModelAndView mv = new ModelAndView();
        mv.addObject("roleList", roleService.getListByStatus(1));
        mv.setViewName("sys/user/user_add");
        return mv;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public PageData add() {
        logger.info("add user");
        PageData result = new PageData();
        try {
            PageData pd = super.getPageData();
            String loginName = pd.getString("loginName");

            if (userService.getByLoginName(pd.getString("loginName")) != null) {
                result.put("status", 0);
                result.put("msg", "该用户名已存在");
                return result;
            }

            String password = pd.getString("password");
            password = new SimpleHash("SHA-1", loginName, password).toString();
            pd.put("password", password);
            pd.put("status", 1);

            userService.add(pd);
            result.put("status", 1);
            operateLogService.saveOperateLog("新增用户“" + pd.getString("loginName") + "”");
        }
        catch (Exception e) {
            logger.error("add user error", e);
            result.put("status", 0);
            result.put("msg", "新增失败");
        }
        return result;
    }


    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView toEdit(@RequestParam Integer userId) throws Exception {
        logger.info("to edit user: userId=" + userId);
        PageData pd = null;
        try {
            pd = userService.getById(userId);
        }
        catch (Exception e) {
            logger.error("get user error", e);
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("roleList", roleService.getListByStatus(1));
        Integer loginUserId =
                ((User) SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_USER)).getUserId();
        mv.addObject("isDisabled", loginUserId == 1 && userId == 1);
        mv.addObject("pd", pd);
        mv.setViewName("sys/user/user_edit");
        return mv;
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public PageData edit() {
        logger.info("edit user");
        PageData result = new PageData();
        try {
            PageData pd = super.getPageData();
            String password = pd.getString("password");
            Integer userId = pd.getInteger("userId");
            PageData user = userService.getById(userId);
            if (StringUtils.isNotBlank(password)) {
                password = password.replace(" ", "");
                String loginName = user.getString("loginName");
                password = new SimpleHash("SHA-1", loginName, password).toString();
                pd.put("password", password);
            }

            userService.edit(pd);
            result.put("status", 1);

            operateLogService.saveOperateLog("修改用户“" + user.getString("loginName") + "”信息");
        }
        catch (Exception e) {
            logger.error("edit user error", e);
            result.put("status", 0);
            result.put("msg", "更新失败");
        }
        return result;
    }


    @RequestMapping(value = "/delete")
    @ResponseBody
    public PageData delete(@RequestParam Integer userId) {
        logger.info("delete user: userId=" + userId);
        PageData result = new PageData();
        try {
            if (userId == 1) {
                result.put("status", 0);
                result.put("msg", "删除成功");
                return result;
            }
            PageData user = userService.getById(userId);
            if(user==null){
                result.put("status", 0);
                result.put("msg", "用户不存在");
                return result;
            }
            userService.delete(userId);
            result.put("status", 1);

            operateLogService.saveOperateLog("删除用户“" + user.getString("loginName") + "”");
        }
        catch (Exception e) {
            logger.error("delete user error", e);
            result.put("status", 0);
            result.put("msg", "删除失败");
        }
        return result;
    }


    @RequestMapping(value = "/batchDelete")
    @ResponseBody
    public PageData batchDelete(@RequestParam String ids) {
        logger.info("batch delete user: ids=" + ids);
        PageData result = new PageData();
        try {
            userService.batchDelete(ids);
            result.put("status", 1);
        }
        catch (Exception e) {
            logger.error("batch delete user error", e);
            result.put("status", 0);
            result.put("msg", "批量删除失败");
        }
        return result;
    }


    @RequestMapping(value = "/editRole", method = RequestMethod.GET)
    public ModelAndView toEditRole(@RequestParam Integer userId) {
        logger.info("to edit role: userId=" + userId);
        List<PageData> roles = null;
        try {
            roles = userService.getRoles(userId);
        }
        catch (Exception e) {
            logger.error("to edit role error", e);
            roles = new ArrayList<PageData>();
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("userId", userId);
        mv.addObject("roles", roles);
        mv.setViewName("sys/user/user_role_edit");
        return mv;
    }


    @RequestMapping(value = "/editRole", method = RequestMethod.POST)
    @ResponseBody
    public PageData editRole() {
        logger.info("edit role");
        PageData result = new PageData();
        try {
            PageData pd = super.getPageData();
            userService.editRole(pd);
            result.put("status", 1);
        }
        catch (Exception e) {
            logger.error("edit role error", e);
            result.put("status", 0);
            result.put("msg", "授权失败");
        }
        return result;
    }

}
