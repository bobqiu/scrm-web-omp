package com.youanmi.scrm.omp.controller.system;

import com.youanmi.scrm.omp.service.system.OperateLogService;
import net.wildpig.base.common.entity.PageData;
import net.wildpig.base.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2016/12/6.
 */
@Controller
@RequestMapping("/operateLog")
public class OperateLogController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(OperateLogController.class);

    @Autowired
    private OperateLogService operateLogService;

    /**
     *       操作日志列表页
     * @return
     */
    @RequestMapping
    public ModelAndView page() throws  Exception{
        logger.info("操作日志列表页");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sys/operateLog/list");
        return modelAndView;
    }

    @RequestMapping(value="/list")
    @ResponseBody
    public PageData list() throws  Exception{
        logger.info("操作日志列表页加载数据");
        PageData paramData = super.getPageData();
        PageData  resultData =  operateLogService.operateLogPage(paramData);
        return resultData;
    }
}
