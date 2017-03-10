package com.youanmi.scrm.omp.controller;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.wildpig.base.common.entity.sys.User;
import net.wildpig.base.common.util.Const;
import net.wildpig.base.controller.BaseController;

/**
 * Created by Administrator on 2016/12/6.
 */
@Controller
@RequestMapping("/notOpen")
public class NotOpenController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(NotOpenController.class);

    /**
     *   未开放
     * @return
     */
    @RequestMapping()
    public ModelAndView page() throws  Exception{
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("notOpen");
        return modelAndView;
    }
}
