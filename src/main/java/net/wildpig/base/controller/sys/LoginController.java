
package net.wildpig.base.controller.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import net.wildpig.base.common.entity.PageData;
import net.wildpig.base.common.entity.sys.Button;
import net.wildpig.base.common.entity.sys.Menu;
import net.wildpig.base.common.entity.sys.User;
import net.wildpig.base.common.util.AppUtil;
import net.wildpig.base.common.util.Const;
import net.wildpig.base.common.util.DateUtil;
import net.wildpig.base.common.util.Tools;
import net.wildpig.base.controller.BaseController;
import net.wildpig.base.service.LoginService;
import net.wildpig.base.service.UserService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

//import com.alibaba.fastjson.JSONObject;






/**
 * @FileName LoginController.java
 * @Description:
 *
 * @Date Apr 17, 2015
 * @author YangShengJun
 * @version 1.0
 * 
 */
@Controller
public class LoginController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "loginService")
	private LoginService loginService;

	/**
	 * 获取登录用户的IP
	 * 
	 * @throws Exception
	 */
	public void getRemoteIP(String loginName) throws Exception {
		PageData pd = new PageData();
		HttpServletRequest request = this.getRequest();
		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {
			ip = request.getRemoteAddr();
		} else {
			ip = request.getHeader("x-forwarded-for");
		}
		pd.put("loginName", loginName);
		pd.put("ip", ip);
		userService.saveIP(pd);
	}

	/**
	 * 访问登录页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login_toLogin")
	public ModelAndView toLogin() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = super.getPageData();
//		pd.put("sysname", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
		mv.setViewName("sys/login");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 请求登录，验证用户
	 */
	@RequestMapping(value = "/login_login", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object login() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = super.getPageData();
		String errInfo = "";
		String keyData = pd.getString("keyData");
		keyData = keyData.replaceAll("ksbadmtn1f2izwqy", "");
		keyData = keyData.replaceAll("ipvb5cxat0zn9eg7", "");
		String keyDatas[] = keyData.split(",00,");

		if (null != keyDatas && keyDatas.length == 3) {
			// shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			String sessionCode = (String) session.getAttribute(Const.SESSION_SECURITY_CODE); // 获取session中的验证码

			String code = keyDatas[2];
			boolean useValiCode = true;// 关闭验证码
			if (useValiCode && (null == code || "".equals(code))) {
				errInfo = "nullcode"; // 验证码为空
			} else {
				String loginName = keyDatas[0];
				String password = keyDatas[1];
				pd.put("loginName", loginName);
				if (!useValiCode || (Tools.notEmpty(sessionCode) && sessionCode.equalsIgnoreCase(code))) {
					String passwd = new SimpleHash("SHA-1", loginName, password).toString(); // 密码加密
					pd.put("password", passwd);
					pd = userService.getUserByNameAndPwd(pd);// TODO
																// 用于验证用户名和密码，改方法名需要改良
					if (pd != null) {
						pd.put("lastLogin", DateUtil.getTime().toString());
						userService.updateLastLogin(pd);
						User user = new User();// TODO
												// 改成直接从mybatis中返回的user,不需要下面逐行注入
						user.setUserId(pd.getInteger("userId"));
						user.setLoginName(pd.getString("loginName"));
						user.setPassword(pd.getString("password"));
						user.setName(pd.getString("name"));
						user.setLastLogin(pd.getString("lastLogin"));
						user.setIp(pd.getString("ip"));
						user.setStatus(pd.getInteger("status"));
						session.setAttribute(Const.SESSION_USER, user);// TODO ?
						session.removeAttribute(Const.SESSION_SECURITY_CODE); // TODO
																				// ?

						// shiro加入身份验证
						Subject subject = SecurityUtils.getSubject();
						UsernamePasswordToken token = new UsernamePasswordToken(loginName, password);
						try {
							subject.login(token);
						} catch (AuthenticationException e) {
							errInfo = "身份验证失败！";
						}

					} else {
						errInfo = "usererror"; // 用户名或密码有误
					}
				} else {
					errInfo = "codeerror"; // 验证码输入有误
				}
				if (Tools.isEmpty(errInfo)) {
					errInfo = "success"; // 验证成功
				}
			}
		} else {
			errInfo = "error"; // 缺少参数
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 访问系统首页
	 */
	@RequestMapping(value = "/sys/index")
	public ModelAndView login_index(HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();

		PageData pd = super.getPageData();
		pd.put("changeMenu", "yes");// 加载所有菜单
		try {

			// shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();

			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (user != null) {

				initRights(user, session);

				session.setAttribute(Const.SESSION_USERNAME, user.getLoginName()); // 放入用户名

				List<Button> allButtonList = new ArrayList<Button>();

				mv.setViewName("sys/main");
				mv.addObject("user", user);
//				mv.addObject("menuList", session.getAttribute(Const.SESSION_ALLMENULIST));
				mv.addObject("buttonList", allButtonList);

				mv.addObject("menuLists", JSON.toJSONString(session.getAttribute(Const.SESSION_ALLMENULIST)));

			} else {
				mv.setViewName("sys/login");// session失效后跳转登录页面
			}

		} catch (Exception e) {
			mv.setViewName("sys/login");
			logger.error(e.getMessage(), e);
		}
//		pd.put("sysname", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
		mv.addObject("pd", pd);
		return mv;
	}

	private void initRights(User sysUser, Session session) {

		try {

			// 当前用户下的所有menu
			// List<Menu> menuList=new ArrayList<>();
			// 当前用户下的所有button
			List<Button> buttonList = new ArrayList<>();

			List<String> allRightsUrls = new ArrayList<>();

			List<String> roles = loginService.getRightsRolesId(sysUser.getUserId());
			session.setAttribute(Const.SESSION_ROLES_NAME, roles);
			// info.addRoles(roles);// TODO change to roleCode

			// 添加菜单权限信息（含分类菜单）
			Map<String, Integer> params = new HashMap<>();
			params.put("userId", sysUser.getUserId());
			params.put("parentId", 0);
			List<Menu> menus = loginService.getRightsParentMenus(params);
			// menuList.addAll(menus);
			for (Menu menu : menus) {
				allRightsUrls.add(menu.getMenuUrl());

				params.put("parentId", menu.getMenuId());
				List<Menu> subMenus = loginService.getRightsParentMenus(params);
				menu.setSubMenu(subMenus);
				for (Menu subMenu : subMenus) {
					allRightsUrls.add(subMenu.getMenuUrl());
					params.put("parentId",subMenu.getMenuId());
					List<Menu> childMenus = loginService.getRightsParentMenus(params);
					subMenu.setSubMenu(childMenus);
					for (Menu childMenu :childMenus){
						allRightsUrls.add(childMenu.getMenuUrl());
					}
				}

			}

			// 添加按钮权限信息
			List<Button> buttons = loginService.getRightsButtons(sysUser.getUserId());
			buttonList.addAll(buttons);
			for (Button button : buttons) {
				allRightsUrls.add(button.getButtonUrl());
			}

			// shiro用到权限列表
			session.setAttribute(Const.SESSION_ALL_RIGHTS_URL, allRightsUrls);

			// 菜单和按钮数据，用于登陆成功后生成菜单树
			session.setAttribute(Const.SESSION_ALLMENULIST, menus);
			session.setAttribute(Const.SESSION_ALLBUTTONLIST, buttonList);

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * 用户注销
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public ModelAndView logout() {
		ModelAndView mv = new ModelAndView();
		PageData pd = super.getPageData();

		// shiro销毁登录，logout的时候shiro会删掉所有session
		Subject subject = SecurityUtils.getSubject();
		/*
		String lang = (String) subject.getSession().getAttribute("lang");
		if (lang == null) {
			lang = "";
		}*/
		subject.logout();

		String msg = pd.getString("msg");
		pd.put("msg", msg);
//		pd.put("sysname", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
		
		//mv.setViewName("redirect:?locale=" + lang);
		mv.setViewName("sys/login");
		mv.addObject("pd", pd);
		return mv;
	}

}
