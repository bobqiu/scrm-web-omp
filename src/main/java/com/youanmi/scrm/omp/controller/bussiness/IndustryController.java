/*
 * 文件名：IndustryController.java
 * 版权：深圳柚安米科技有限公司版权所有
 * 修改人：guohao
 * 修改时间：2016年12月5日
 * 修改内容：新增
 */
package com.youanmi.scrm.omp.controller.bussiness;

import java.util.List;
import java.util.Map;

import net.wildpig.base.common.entity.PageData;
import net.wildpig.base.controller.BaseController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.youanmi.scrm.api.data.dto.industry.IndustryDto;
import com.youanmi.scrm.api.data.service.industry.IIndustryService;
import com.youanmi.scrm.omp.constants.ResultConstants;
import com.youanmi.scrm.omp.service.system.OperateLogService;
import com.youanmi.scrm.omp.util.AssertUtils;


/**
 * Created by guohao on 2016/12/5.
 */
@Controller
@RequestMapping("/shopBasic/industry")
public class IndustryController extends BaseController {
    /**
     * 调测日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(IndustryController.class);

    @Autowired
    private IIndustryService industryService;

    @Autowired
    private OperateLogService operateLogService;


    @RequestMapping
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("shopBasic/industry/list");
        try {
            mv.addObject("industrys", industryService.getFirstLevelIndustrys());
        }
        catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("获取一级行业失败", e);
            }
        }
        return mv;
    }


    /**
     * 行业列表
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    @ResponseBody
    public PageData listData() throws Exception {
        PageData pd = super.getPageData();
        LOG.info("获取行业列表，参数为：" + pd.toJSONString());
        Map<String, Object> map= industryService.queryIndustryList(getParams(getRequest()));
        PageData result = new PageData(map);
        result.put("status", ResultConstants.SUCCESS);
        return result;
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView toAdd() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("shopBasic/industry/add");
        mv.addObject("industrys", industryService.getFirstLevelIndustrys());
        return mv;
    }


    /**
     * 添加行业
     * 
     * @param request
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public PageData addIndustry() throws Exception {
        PageData pd = super.getPageData();
        LOG.info("开始添加行业，参数为：" + pd.toJSONString());
        PageData result = new PageData();
        String industryName = pd.getString("name");
        String level = pd.getString("level");
        if (AssertUtils.isNull(industryName) || AssertUtils.isNull(level)) {
            result.put("status", ResultConstants.FAIL);
            result.put("msg", "参数为空！");
            return result;
        }
        Long userId = getSessionUserId();
        long time = System.currentTimeMillis();
        if (industryService.getIndustryInfo(industryName) != null) {
            result.put("status", ResultConstants.FAIL);
            result.put("msg", "该行业名称已存在");
        }
        else {
        	
        	//获取参数map
        	Map<String, Object> params = getParams(getRequest());
        	params.put("userId", userId);
        	params.put("time", time);
            industryService.addIndustry(params);
            result.put("status", ResultConstants.SUCCESS);
            operateLogService.saveOperateLog("新增行业“" + industryName + "”");
        }
        return result;
    }


    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView toEdit(@RequestParam Map<String, String> params) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("shopBasic/industry/edit");
        mv.addObject("industryId", params.get("id"));
        mv.addObject("industryName", params.get("name"));
        return mv;
    }


    /**
     * 编辑行业
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public PageData editIndustry() throws Exception {
        PageData pd = super.getPageData();
        LOG.info("开始编辑行业，参数为：" + pd.toJSONString());
        PageData result = new PageData();
        String newIndustryName = pd.getString("name");
        String oldIndustryName = pd.getString("oldIndustryName");
        if (AssertUtils.isNull(newIndustryName)) {
            result.put("status", ResultConstants.FAIL);
            result.put("msg", "参数为空！");
            return result;
        }
        Long userId = getSessionUserId();
        long time = System.currentTimeMillis();
        if (industryService.getIndustryInfo(newIndustryName) != null && !oldIndustryName.equals(newIndustryName)) {
            result.put("status", ResultConstants.FAIL);
            result.put("msg", "该行业名称已存在");
        }
        else {
        	//获取参数map
        	Map<String, Object> params = getParams(getRequest());
        	params.put("userId", userId);
        	params.put("time", time);
            industryService.editIndustry(params);
            result.put("status", ResultConstants.SUCCESS);
            operateLogService.saveOperateLog("修改行业" + pd.getString("id") + ":“" + oldIndustryName + "”改为“"
                    + newIndustryName + "”");
        }
        return result;
    }
    
    /**
     * 
     * 根据城市得到该省的城市下的区域
     * 
     * @param cityId
     * @return
     */
    @RequestMapping(value = "/getIndustryByParentId")
    @ResponseBody
    public PageData getIndustryByParentId(@RequestParam Long parentId) {
        LOG.info("getIndustryByParentId data : parentId=" + parentId);
        PageData result = new PageData();
        try {
            List<IndustryDto> industryList = industryService.getIndustryByParentId(parentId);
            result.put("industryList", industryList);
        }
        catch (Exception e) {
            LOG.error("getIndustryByParentId data error", e);
            result = new PageData();
        }
        return result;
    }
}
