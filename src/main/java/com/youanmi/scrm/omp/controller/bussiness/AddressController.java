/*
 * 文件名：AddressController.java
 * 版权：深圳柚安米科技有限公司版权所有
 * 修改人：吴尚高
 * 修改时间：2016年12月6日
 * 修改内容：新增
 */
package com.youanmi.scrm.omp.controller.bussiness;

import java.util.List;

import net.wildpig.base.common.entity.PageData;
import net.wildpig.base.controller.BaseController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.youanmi.scrm.omp.service.bussiness.IAddressService;


/**
 * 区域信息
 * 
 * 
 * @author 吴尚高
 * @since 2.2.4
 */
@Controller
@RequestMapping("/bussiness/address")
public class AddressController extends BaseController {
    /**
     * 调测日志记录器。
     */
    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private IAddressService addressService;


    @RequestMapping
    public ModelAndView page() throws Exception {
        logger.info("address list page");
        ModelAndView mv = new ModelAndView();
        // 全国省份
        List<PageData> provinceList = addressService.selectProvinceList();
        mv.setViewName("bussiness/address/address_list");
        mv.addObject("provinceId", "12");// 默认广东省
        mv.addObject("provinceList", provinceList);
        return mv;
    }


    @RequestMapping(value = "/list")
    @ResponseBody
    public PageData list() {
        logger.info("address list data");
        PageData result = null;
        try {
            PageData pd = super.getPageData();
            String provinceId = pd.getString("provinceId");
            if (String.valueOf(provinceId) == null) {
                provinceId = "12";// 默认广东省
                pd.put("provinceId", provinceId);
            }
            // 城市列表
            result = addressService.cityListPage(pd);
        }
        catch (Exception e) {
            logger.error("list address error", e);
            result = new PageData();
        }
        return result;
    }


    /**
     * 
     * 全国省列表
     * 
     * @return
     */
    @RequestMapping(value = "/getProvinces")
    @ResponseBody
    public PageData provinceList() {
        logger.info("get province data");
        PageData result = new PageData();
        try {
            // 全国省份
            List<PageData> provinceList = addressService.selectProvinceList();
            result.put("provinces", provinceList);
        }
        catch (Exception e) {
            logger.error("get province data error", e);
            result = new PageData();
        }
        return result;
    }


    /**
     * 
     * 根据省得到该省的城市
     * 
     * @param provinceId
     * @return
     */
    @RequestMapping(value = "/getCitys")
    @ResponseBody
    public PageData cityList(@RequestParam String provinceId) {
        logger.info("get city data : provinceId=" + provinceId);
        PageData result = new PageData();
        try {
            // 城市列表
            List<PageData> cityList = addressService.selectCityList(provinceId);
            result.put("citys", cityList);
        }
        catch (Exception e) {
            logger.error("get city data error", e);
            result = new PageData();
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
    @RequestMapping(value = "/getAreas")
    @ResponseBody
    public PageData areaList(@RequestParam String cityId) {
        logger.info("get area data : cityId=" + cityId);
        PageData result = new PageData();
        try {
            // 地区列表
            List<PageData> areasList = addressService.selectAreaList(cityId);
            result.put("areas", areasList);
        }
        catch (Exception e) {
            logger.error("get area data error", e);
            result = new PageData();
        }
        return result;
    }
}
