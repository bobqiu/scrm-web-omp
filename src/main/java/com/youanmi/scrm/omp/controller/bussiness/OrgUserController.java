package com.youanmi.scrm.omp.controller.bussiness;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.youanmi.scrm.api.account.dto.org.OrgInfoDto;
import com.youanmi.scrm.api.account.dto.org.OrgPostDto;
import com.youanmi.scrm.api.account.dto.org.OrgStaffDto;
import com.youanmi.scrm.api.account.dto.user.UserInfoDto;
import com.youanmi.scrm.api.account.service.org.IOrgInfoService;
import com.youanmi.scrm.api.account.service.org.IOrgPostService;
import com.youanmi.scrm.api.account.service.org.IOrgStaffService;
import com.youanmi.scrm.omp.service.common.OmpSmsService;
import com.youanmi.scrm.omp.service.user.OrgUserService;

import net.wildpig.base.common.entity.PageData;
import net.wildpig.base.controller.BaseController;

/**
 * 企业用户页面
 * @author zhangteng on 2017-02-15
 */

@Controller
@RequestMapping(value="/company/")
public class OrgUserController extends BaseController{
	private static Logger LOG = LoggerFactory.getLogger(OrgUserController.class);

	@Autowired
	private IOrgInfoService orgInfoService;

	@Autowired
	private IOrgStaffService orgStaffService;
	@Autowired
	private IOrgPostService orgPostService;

	@Resource
	private OrgUserService orgUserService;


	/**
	 *
	 * @return
	 */
	@RequestMapping(value="orgUserPage")
	public ModelAndView orgUserPage() {
		ModelAndView mv = new ModelAndView();	
	    mv.setViewName("orgUsers/list");
	    return mv;
	}
	
	/**
	 * 展示全部企业用户的页面
	 * @return PageData
	 */
	@RequestMapping(value="totalUsers")
	@ResponseBody
	public PageData orgUserList() throws Exception {
		PageData pageData = super.getPageData();
		LOG.info("查询用户参数所获得的参数：" + pageData.toJSONString());
		PageData result = orgUserService.getUserList(pageData);
		return result;
	}
	
	/**
	 * 依据user_id来获取当前用户的详细信息
	 * @param userId 用户id
	 * @return 
	 */
	@RequestMapping(value="userDetail")
	public ModelAndView getCompanyDetail(@RequestParam("userId")Long userId) {
		ModelAndView mv = new ModelAndView();
		LOG.info("当前查询的用户编号：" + userId);
        mv.setViewName("orgUsers/userDetail");
        mv.addObject("dto", orgUserService.getUserDetail(userId));
		return mv;
	}

}
