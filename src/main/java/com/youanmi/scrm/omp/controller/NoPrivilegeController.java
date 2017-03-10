package com.youanmi.scrm.omp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/noPrivilege")
public class NoPrivilegeController {

    @RequestMapping()
    public ModelAndView page() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("noPrivilege");
        return modelAndView;
    }
}
