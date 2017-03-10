/*
 * 文件名：AddressController.java
 * 版权：深圳柚安米科技有限公司版权所有
 * 修改人：吴尚高
 * 修改时间：2016年12月6日
 * 修改内容：新增
 */
package com.youanmi.scrm.omp.controller.ics;

import com.youanmi.scrm.api.ics.constants.IcsDataBaseConstants;
import com.youanmi.scrm.api.ics.dto.AutoReplyDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.youanmi.commons.base.vo.PageBean;
import com.youanmi.scrm.api.ics.dto.AutoReplyTempletDto;
import com.youanmi.scrm.api.ics.service.IAutoReplyTempletService;
import com.youanmi.scrm.commons.util.object.BeanCopyUtils;
import com.youanmi.scrm.omp.vo.ics.AutoReplyTempletVo;

import net.wildpig.base.common.entity.PageData;
import net.wildpig.base.controller.BaseController;


/**
 * 区域信息
 * 
 * 
 * @author 吴尚高
 * @since 2.2.4
 */
@Controller
@RequestMapping("/ics/autoreply")
public class AutoReplyController extends BaseController {
    /**
     * 调测日志记录器。
     */
    private static final Logger logger = LoggerFactory.getLogger(AutoReplyController.class);

    @Autowired
    private IAutoReplyTempletService autoReplyTempletService;

    @RequestMapping
    public ModelAndView page() throws Exception {
        logger.info("address list page");
        ModelAndView mv = new ModelAndView("/ics/autoreply/list");
        return mv;
    }

    @RequestMapping(value = "/toEdite")
    public ModelAndView toEdite(@RequestParam("id") Integer id) throws Exception {
        ModelAndView mv = new ModelAndView("/ics/autoreply/edite");
        AutoReplyTempletDto autoReplyDto = autoReplyTempletService.getById(id);
        if (autoReplyDto.getType() == IcsDataBaseConstants.AutoReplyType.MEMBER_BIRTHDYA) {
            String content = autoReplyDto.getContent().replace("：", ":");
            int index = content.indexOf(':');
            if (index > 0){
                String birthdayHeader = content.substring(0, index+1);
                autoReplyDto.setContent(content.substring(index+1,content.length()));
                mv.addObject("birthdayHeader", birthdayHeader);
            }
        }
        mv.addObject("data", autoReplyDto);
        return mv;
    }

    /**
     * 获list
     * 
     * @return list
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageData list() {
        PageData param = super.getPageData();
        Integer pageSize = param.getInteger("pageSize");
        Integer pageIndex = param.getInteger("pageIndex");
        pageSize = pageSize == null ? 20 : pageSize;
        pageIndex = pageIndex == null ? 1 : pageIndex;
        PageBean pageBean = autoReplyTempletService.getAllList(pageIndex, pageSize);
        return paging(pageBean.getData(), pageBean.getAllRecord());
    }

    /**
     * 获list
     * 
     * @return pagedata
     */
    @RequestMapping(value = "/edite")
    @ResponseBody
    public PageData edite(AutoReplyTempletVo autoReplyTemplet) {
        if (autoReplyTemplet.getType() == IcsDataBaseConstants.AutoReplyType.MEMBER_BIRTHDYA) {
            autoReplyTemplet.setContent(autoReplyTemplet.getBirthdayHeader() + autoReplyTemplet.getContent());
        }
        return autoReplyTempletService
            .update(BeanCopyUtils.map(autoReplyTemplet, AutoReplyTempletDto.class)) > 0 ? success()
                    : fail("编辑失败");
    }

    /**
     * 获list
     * 
     * @return pagedata
     */
    @RequestMapping(value = "/disable")
    @ResponseBody
    public PageData disable(@RequestParam("id") Integer id, @RequestParam("isDisable") Byte isDisable) {
        AutoReplyTempletDto autoReplyTempletDto = new AutoReplyTempletDto();
        autoReplyTempletDto.setId(id);
        autoReplyTempletDto.setIsDisable(isDisable);
        return autoReplyTempletService.update(autoReplyTempletDto) > 0 ? success() : fail("修改状态失败");
    }
}
