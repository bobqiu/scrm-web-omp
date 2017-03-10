package com.youanmi.scrm.omp.controller.common;

import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youanmi.scrm.commons.sms.conf.SmsConfBuilder;
import com.youanmi.scrm.commons.sms.constants.SmsConstants;
import com.youanmi.scrm.omp.constants.ResultConstants;
import com.youanmi.scrm.omp.service.common.OmpSmsService;

import net.wildpig.base.common.entity.PageData;
import net.wildpig.base.controller.BaseController;

import javax.servlet.http.HttpServletResponse;

@Controller
public class SmsController extends BaseController {
	private static Logger LOG = LoggerFactory.getLogger(SmsController.class);
	
	@Autowired
	private OmpSmsService ompSmsService;


    /**
     * 绑定手机
     * @param
     */
    @RequestMapping("/sms/sendSms")
    @ResponseBody
    public PageData bindPhone() {

    	PageData pageData = super.getPageData();
        String phone = (String) pageData.get("mobilePhone");
        LOG.info("bindPhone:{}", phone);
        //获取模板
        System.out.println("param=====" + SmsConstants.Template.BIND_PHONE);
        String template = SmsConfBuilder.getProperties(SmsConstants.Template.BIND_PHONE);
        System.out.println("value=====" + template);
        ompSmsService.sendVerifyCode(phone, template);
        PageData result = new PageData();
        result.put("status", ResultConstants.SUCCESS);
        return result;
    }
}
