package com.youanmi.scrm.omp.controller;

import net.wildpig.base.common.entity.sys.User;
import net.wildpig.base.common.util.Const;
import net.wildpig.base.controller.BaseController;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2016/12/6.
 */
@Controller
@RequestMapping("/home/page")
public class HomePageController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(HomePageController.class);

    /**
     *       首页
     * @return
     */
    @RequestMapping()
    public ModelAndView page() throws  Exception{
        ModelAndView modelAndView = new ModelAndView();
        if(getCurrentUser() == null){
            modelAndView.setViewName("sys/login");
            return modelAndView;
        }
        modelAndView.setViewName("homePage");
        modelAndView.addObject("loginName",((User) SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_USER)).getLoginName());
        return modelAndView;
    }
}
