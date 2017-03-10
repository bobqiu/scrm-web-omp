package com.youanmi.scrm.omp.controller.org;

import java.util.List;
import java.util.Map;

import net.wildpig.base.common.entity.PageData;
import net.wildpig.base.controller.BaseController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.youanmi.scrm.api.account.dto.org.BranchOrgDetailDto;
import com.youanmi.scrm.api.crm.dto.workmachine.OrgWorkMachineDto;
import com.youanmi.scrm.api.crm.dto.workmachine.SelectWorkMachineDto;
import com.youanmi.scrm.api.crm.service.IWorkMachineService;
import com.youanmi.scrm.api.data.service.industry.IIndustryService;
import com.youanmi.scrm.omp.constants.ResultConstants;
import com.youanmi.scrm.omp.service.bussiness.IAddressService;
import com.youanmi.scrm.omp.service.org.BranchOrgService;


/**
 * 连锁分店服务
 * Created by laishaoqiang on 2017/2/21.
 */
@Controller
@RequestMapping("/org")
public class BranchOrgController extends BaseController {
    /**
     * 调测日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(BranchOrgController.class);

    @Autowired
    private IAddressService addressService;
    
    @Autowired
    private IIndustryService industryService;
    
    @Autowired
    private BranchOrgService branchOrgService;
    
    @Autowired
    private IWorkMachineService workMachineService;
    
    /**
     * 列表页面
     * @return
     */
    @RequestMapping("/branchOrg")
    public ModelAndView list() throws Exception {
        ModelAndView mv = new ModelAndView();
        // 全国省份
        List<PageData> provinceList = addressService.selectProvinceList();
        //一级行业
        Map<String, Object> industryMap = industryService.getFirstLevelIndustrys();
        
        mv.addObject("provinceList", provinceList);
        mv.addObject("industrys", industryMap);
        mv.setViewName("org/branch_list");
        return mv;
    }

    /**
     * 连锁分店列表
     * 
     * @return
     * @throws Exception 
     */
    @RequestMapping("/branchOrg/list")
    @ResponseBody
    public PageData branchOrgList() throws Exception {
        PageData pd = super.getPageData();
        LOG.info("获取连锁分店列表，参数为：" + pd.toJSONString());
        PageData result = new PageData();
        result = branchOrgService.branchOrgList(pd);
        result.put("status", ResultConstants.SUCCESS);
        return result;
    }
    
    
    /**
     * 详情页面
     * @return
     */
    @RequestMapping("/branchOrg/branchOrgDetailPage")
    public ModelAndView branchOrgDetailPage() throws Exception {
        PageData pd = super.getPageData();
        Long orgId = pd.getLong("id");
        
        ModelAndView mv = new ModelAndView();
        //连锁分店详情
        BranchOrgDetailDto dto = branchOrgService.getBranchOrgDetail(orgId);
        // 查询工作机数量
        SelectWorkMachineDto selectWorkMachineDto = new SelectWorkMachineDto();
        selectWorkMachineDto.setOrgId(orgId);
        selectWorkMachineDto.setStartIndex(1);
        selectWorkMachineDto.setPageSize(10);
        Long workTotalCount = workMachineService.workMachineCount(selectWorkMachineDto);
        
        mv.addObject("workTotalCount", workTotalCount);
        mv.addObject("dto", dto);
        mv.addObject("id", orgId);
        mv.setViewName("org/branch_detail");
        return mv;
    }
    
    /**
     * 工作机列表页面
     * @return
     */
    @RequestMapping("/branchOrg/workMachinePage")
    public ModelAndView workMachinePage() throws Exception {
        PageData pd = super.getPageData();
        Long orgId = pd.getLong("id");
        
        ModelAndView mv = new ModelAndView();
        // 查询工作机数量
        SelectWorkMachineDto selectWorkMachineDto = new SelectWorkMachineDto();
        selectWorkMachineDto.setOrgId(orgId);
        selectWorkMachineDto.setStartIndex(1);
        selectWorkMachineDto.setPageSize(10);
        Long workTotalCount = workMachineService.workMachineCount(selectWorkMachineDto);
        
        mv.addObject("workTotalCount", workTotalCount);
        mv.addObject("id", orgId);
        mv.setViewName("org/workmachine_list");
        return mv;
    }
    
    /**
     * 工作机列表
     * 
     * @return
     * @throws Exception 
     */
    @RequestMapping("/branchOrg/workMachineList")
    @ResponseBody
    public PageData workMachineList() throws Exception {
        PageData pd = super.getPageData();
        Long orgId = pd.getLong("id");
        LOG.info("工作机列表，参数为：" + pd.toJSONString());
        PageData result = new PageData();
        
        SelectWorkMachineDto dto = new SelectWorkMachineDto();
        dto.setOrgId(orgId);
        dto.setStartIndex(pd.getInteger("offset"));
        dto.setPageSize(pd.getInteger("limit"));
        List<OrgWorkMachineDto> list = workMachineService.getWorkMachineListByOrgId(dto);
        Long totalCount = workMachineService.workMachineCount(dto);
        result.put("rows", list);
        result.put("total", totalCount);
        result.put("status", ResultConstants.SUCCESS);
        result.put("id", orgId);
        return result;
    }
    
    
}
