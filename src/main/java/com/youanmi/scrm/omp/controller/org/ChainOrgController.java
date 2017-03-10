package com.youanmi.scrm.omp.controller.org;

import java.util.List;
import java.util.Map;

import com.youanmi.scrm.api.account.constants.AccountTableConstants;
import net.wildpig.base.common.entity.PageData;
import net.wildpig.base.controller.BaseController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.youanmi.commons.base.vo.PageBean;
import com.youanmi.scrm.api.account.dto.org.AddTopOrgDto;
import com.youanmi.scrm.api.account.dto.org.OrgInfoDto;
import com.youanmi.scrm.api.crm.dto.workmachine.OrgWorkMachineDto;
import com.youanmi.scrm.api.crm.dto.workmachine.SelectWorkMachineDto;
import com.youanmi.scrm.api.crm.service.IWorkMachineService;
import com.youanmi.scrm.api.data.service.industry.IIndustryService;
import com.youanmi.scrm.omp.constants.ResultConstants;
import com.youanmi.scrm.omp.service.bussiness.IAddressService;
import com.youanmi.scrm.omp.service.org.ChainOrgService;


/**
 * 连锁机构列表服务 Created by laishaoqiang on 2017/2/21.
 */
@Controller
@RequestMapping("/org")
public class ChainOrgController extends BaseController {
    /**
     * 调测日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(ChainOrgController.class);

    @Autowired
    private IAddressService addressService;

    @Autowired
    private IIndustryService industryService;

    @Autowired
    private ChainOrgService chainOrgService;

    @Autowired
    private IWorkMachineService workMachineService;

    /**
     * 机构列表页面
     *
     * @return
     */
    @RequestMapping("/chainOrg")
    public ModelAndView chainOrg() throws Exception {
        ModelAndView mv = new ModelAndView();
        // 全国省份
        List<PageData> provinceList = addressService.selectProvinceList();
        // 一级行业
        Map<String, Object> industryMap = industryService.getFirstLevelIndustrys();

        mv.addObject("provinceList", provinceList);
        mv.addObject("industrys", industryMap);
        mv.setViewName("org/chainOrg_list");
        return mv;
    }

    /**
     * 连锁机构列表
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/chainOrg/list")
    @ResponseBody
    public PageData list() throws Exception {
        PageData pd = super.getPageData();
        LOG.info("获取连锁机构列表，参数为：" + pd.toJSONString());
        PageData result = new PageData();

        Integer offset = pd.getInteger("offset");
        Integer limit = pd.getInteger("limit");
        Integer pageIndex = 0;

        // 页数计算
        if (offset != null && limit != null) {
            pageIndex = pd.getInteger("offset") / pd.getInteger("limit") + 1;
        }
        if (limit == null) {
            limit = 10;
        }

        PageBean<OrgInfoDto> list =
                chainOrgService.selectChainOrgListByCondition(pageIndex, limit, pd.getInteger("inputType"),
                    pd.getString("inputString"), pd.getLong("firstIndustryId"),
                    pd.getLong("secondIndustryId"), pd.getInteger("expireType"),
                    pd.getString("startCreateTimeStr"), pd.getString("endCreateTimeStr"),
                    pd.getLong("provinceId"), pd.getLong("cityId"), pd.getLong("areaId"));

        result.put("total", list.getAllRecord());
        result.put("rows", list.getData());
        result.put("status", ResultConstants.SUCCESS);

        return result;
    }

    /**
     * 删除连锁机构
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/chainOrg/delete")
    @ResponseBody
    public PageData deleteChainOrg() {

        PageData pd = super.getPageData();

        LOG.info("删除连锁机构，参数为：" + pd.toJSONString());

        // 删除连锁机构
        PageData result = chainOrgService.deleteChainOrg(pd.getLong("id"));

        return result;
    }

    /**
     * 连锁机构账号重置密码
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/chainOrg/resetPwd")
    @ResponseBody
    public PageData resetPwd() {

        PageData pd = super.getPageData();

        LOG.info("连锁机构账号重置密码，参数为：" + pd.toJSONString());

        // 重置密码
        PageData result = chainOrgService.resetPwd(pd.getLong("id"));

        return result;
    }

    /**
     * 详情页面
     * 
     * @return
     */
    @RequestMapping("/chainOrg/detail")
    public ModelAndView branchOrgDetailPage() throws Exception {
        PageData pd = super.getPageData();
        Long orgId = pd.getLong("id");

        ModelAndView mv = new ModelAndView();
        // 连锁体系详情
        OrgInfoDto dto = chainOrgService.orgDetail(orgId);

        // 查询工作机数量
        SelectWorkMachineDto selectWorkMachineDto = new SelectWorkMachineDto();
        selectWorkMachineDto.setOrgId(orgId);
        selectWorkMachineDto.setStartIndex(1);
        selectWorkMachineDto.setPageSize(10);
        Long workTotalCount = workMachineService.workMachineCount(selectWorkMachineDto);
        
        mv.addObject("workTotalCount", workTotalCount);
        mv.addObject("dto", dto);
        mv.addObject("id", orgId);
        mv.addObject("currentTime", System.currentTimeMillis());
        mv.setViewName("org/chain_detail");
        return mv;
    }

    /**
     * 工作机列表页面
     * 
     * @return
     */
    @RequestMapping("/chainOrg/detail/list")
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
        mv.setViewName("org/chainWorkmachine_list");
        return mv;
    }

    /**
     * 工作机列表
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/chainOrg/detail/form")
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


    /**
     * 机构列表页面
     * @return
     */
    @RequestMapping("/chainOrgAddPage")
    public ModelAndView addPage() throws Exception {
        ModelAndView mv = new ModelAndView("org/chainOrgAddPage");
        //一级行业
        Map<String, Object> industryMap = industryService.getFirstLevelIndustrys();
        mv.addObject("industries", industryMap.get("industrys"));
        return mv;
    }

    @RequestMapping("/chainOrgAdd")
    @ResponseBody
    public PageData addOrg(@RequestBody AddTopOrgDto dto) {
        dto.setOrgType(AccountTableConstants.Org.ORG_TYPE_CHAIN_DEPART);
        chainOrgService.addTopOrg(dto);
        return super.getPageData();
    }

}
