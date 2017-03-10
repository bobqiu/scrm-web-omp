package com.youanmi.scrm.omp.controller.bussiness;

import com.youanmi.scrm.api.crm.dto.member.AttentionOrgListDto;
import com.youanmi.scrm.api.crm.dto.member.MemberRelationListDto;
import com.youanmi.scrm.api.crm.service.IOrgCustomerRelationService;
import com.youanmi.scrm.api.data.dto.industry.IndustryDto;
import com.youanmi.scrm.api.data.service.industry.IIndustryService;
import com.youanmi.scrm.omp.constants.ResultConstants;
import com.youanmi.scrm.omp.service.bussiness.WechatUsersService;
import net.wildpig.base.common.entity.PageData;
import net.wildpig.base.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 微信用户 
 * Created by laishaoqiang on 2017/3/8.
 */
@RequestMapping(value = "/wx")
@Controller
public class WechatUsersController extends BaseController {
    
    private static Logger LOG = LoggerFactory.getLogger(WechatUsersController.class);
    
    @Autowired
    private WechatUsersService wechatUsersService;

    @Autowired
    private IOrgCustomerRelationService orgCustomerRelationService;
    
    @Autowired
    private IIndustryService industryService;
    
    /**
     * 列表页面
     * @return
     */
    @RequestMapping(value = "/wxUser")
    public ModelAndView getWechatUserPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("wechat/list");
        return mv;
    }

    /**
     * 微信用户列表
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/wxUser/list")
    @ResponseBody
    public PageData wxUserList() throws ParseException {
        PageData pd = super.getPageData();
        LOG.info("微信用户列表参数：" + pd.toJSONString());
        PageData result = new PageData();

        result = wechatUsersService.listAllWechatUsers(pd);
        return result;
    }

    /**
     * 会员详情
     * @param userId
     * @return
     */
    @RequestMapping(value = "/wxUser/detail")
    public ModelAndView getDesignateUsers() {
        PageData pd = super.getPageData();
        Long id = pd.getLong("id");
        LOG.info("会员详情，参数为：" + pd.toJSONString());
        
        ModelAndView mv = new ModelAndView();
        
        //获取会员信息
        MemberRelationListDto dto = orgCustomerRelationService.getMemberInfoById(id);
        
        mv.addObject("dto", dto);
        mv.setViewName("wechat/userDetail");
        return mv;
    }
    
    /**
     * 会员详情列表
     * 
     * @return
     * @throws Exception 
     */
    @RequestMapping("/wxUser/detailList")
    @ResponseBody
    public PageData detailList() throws Exception {
        PageData pd = super.getPageData();
        Long id = pd.getLong("id");
        LOG.info("获取会员详情列表，参数为：" + pd.toJSONString());
        
        PageData result = new PageData();
        
        //获取列表信息
        List<AttentionOrgListDto> list = orgCustomerRelationService.getAttentionOrgList(id);
        
        List<Long> totalIndustryList = new ArrayList<Long>();
        for(AttentionOrgListDto dto : list) {
            Long firstIndustryId = dto.getFirstIndustryId();
            Long secondIndustryId = dto.getSecondIndustryId();
            if(firstIndustryId != null) {
                totalIndustryList.add(firstIndustryId);
            }
            if(secondIndustryId != null) {
                totalIndustryList.add(secondIndustryId);
            }
        }
        
        //设置行业
        if(totalIndustryList != null && totalIndustryList.size() > 0) {
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("ids", totalIndustryList);
            List<IndustryDto> industryDtoList = industryService.getIndustrysByIds(map1);
            
            Map<Long, String> targetMap = listToMap(industryDtoList);
            for (AttentionOrgListDto dto : list) {
                Long firstIndustryId = dto.getFirstIndustryId();
                Long secondIndustryId =dto.getSecondIndustryId();
                
                String firstIndustryName = targetMap.get(firstIndustryId) != null ? targetMap.get(firstIndustryId) : "";
                String secondIndustryName = targetMap.get(secondIndustryId) != null ? targetMap.get(secondIndustryId) : "";
                
                String industryStr = firstIndustryName + "-" + secondIndustryName;
                dto.setIndustryStr(industryStr);
            }
        }
        
        result.put("status", ResultConstants.SUCCESS);
        result.put("total", list.size());
        result.put("rows", list);
        return result;
    }

    private Map<Long, String> listToMap(List<IndustryDto> industryDtoList) {
        Map<Long, String> map = new HashMap<Long, String>();
        
        for(IndustryDto dto : industryDtoList) {
            map.put(dto.getId(), dto.getName());
        }
        
        return map;
        
    }
    
    

}
