/*
 * 文件名：WorkingMachineController.java
 * 版权：深圳柚安米科技有限公司版权所有
 * 修改人：guohao
 * 修改时间：2016年12月6日
 * 修改内容：新增
 */
package com.youanmi.scrm.omp.controller.bussiness;

import net.wildpig.base.common.entity.PageData;
import net.wildpig.base.controller.BaseController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.youanmi.scrm.omp.constants.ResultConstants;
import com.youanmi.scrm.omp.service.bussiness.WorkingMachineService;


/**
 * Created by guohao on 2016/12/6.
 */
@Controller
@RequestMapping("/deviceManage/devices")
public class WorkingMachineController extends BaseController {
    /**
     * 调测日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(WorkingMachineController.class);

    @Autowired
    private WorkingMachineService woringkMachineService;

    @RequestMapping
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("devicesManage/devices/list");
        return mv;
    }

    /**
     * 工作机列表
     * 
     * @return
     * @throws Exception 
     */
    @RequestMapping("/list")
    @ResponseBody
    public PageData listData() throws Exception {
        PageData pd = super.getPageData();
        LOG.info("获取行业列表，参数为：" + pd.toJSONString());
        PageData result = new PageData();
        result = woringkMachineService.queryDeviceList(pd);
        result.put("status", ResultConstants.SUCCESS);
        return result;
    }

}
