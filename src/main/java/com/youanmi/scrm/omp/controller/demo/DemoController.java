package com.youanmi.scrm.omp.controller.demo;

import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2016/11/28.
 */
public class DemoController {


    /**
     *      测试分页
     * @return
     */
    public ModelAndView pageInfo(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("demo/list");
        return mav;
    }


}
