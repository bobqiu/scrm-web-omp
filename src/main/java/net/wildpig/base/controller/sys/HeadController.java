
package net.wildpig.base.controller.sys;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.wildpig.base.common.entity.PageData;
import net.wildpig.base.common.entity.sys.User;
import net.wildpig.base.common.util.AppUtil;
import net.wildpig.base.common.util.Const;
import net.wildpig.base.common.util.Tools;
import net.wildpig.base.controller.BaseController;
import net.wildpig.base.service.UserService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @FileName HeadController.java
 * @Description:
 *
 * @Date Apr 17, 2015
 * @author YangShengJun
 * @version 1.0
 * 
 */
@Controller
@RequestMapping(value = "/head")
public class HeadController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(HeadController.class);
	@Resource(name = "userService")
	private UserService userService;

	/**
	 * 获取头部信息
	 */
	@RequestMapping(value = "/getUname")
	@ResponseBody
	public Object getList() {
		PageData pd = super.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
//			List<PageData> pdList = new ArrayList<PageData>();

			// shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();
//			Session session = currentUser.getSession();
//
//			PageData pds = new PageData();
//			pds = (PageData) session.getAttribute("userpds");
//
//			if (null == pds) {
//				String loginName = session.getAttribute(Const.SESSION_USERNAME).toString(); // 获取当前登录者loginname
//				pd.put("loginName", loginName);
//				pds = userService.findByUId(pd);
//				session.setAttribute("userpds", pds);
//			}
//
//			pdList.add(pds);
//			map.put("list", pdList);
			map.put("userName", ((User)currentUser.getPrincipal()).getName());
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} 
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 保存皮肤
	 */
	@RequestMapping(value = "/setSKIN")
	public void setSKIN(PrintWriter out) {
		try {
			PageData pd = super.getPageData();

			// shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();

			String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString();// 获取当前登录者loginname
			pd.put("USERNAME", USERNAME);
			userService.setSKIN(pd);
			session.removeAttribute(Const.SESSION_userpds);
			session.removeAttribute(Const.SESSION_USERROL);
			out.write("success");
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}

	}

	/**
	 * 去系统设置页面
	 */
	@RequestMapping(value = "/system/form")
	public ModelAndView goEditEmail() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = super.getPageData();
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
		pd.put("COUNTPAGE", Tools.readTxtFile(Const.PAGE)); // 读取每页条数

		mv.setViewName("system/head/sys_edit");
		mv.addObject("pd", pd);

		return mv;
	}

	/**
	 * 保存系统设置
	 */
	@RequestMapping(value = "/saveSys")
	public ModelAndView saveU(PrintWriter out) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = super.getPageData();
		Tools.writeFile(Const.SYSNAME, pd.getString("YSYNAME")); // 写入系统名称
		Tools.writeFile(Const.PAGE, pd.getString("COUNTPAGE")); // 写入每页条数
		mv.addObject("msg", "OK");
		mv.setViewName("save_result");
		return mv;
	}

}
