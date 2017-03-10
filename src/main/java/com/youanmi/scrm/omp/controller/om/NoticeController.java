package com.youanmi.scrm.omp.controller.om;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.wildpig.base.common.entity.PageData;
import net.wildpig.base.common.entity.sys.User;
import net.wildpig.base.controller.BaseController;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.youanmi.scrm.omp.dto.om.NoticeDto;
import com.youanmi.scrm.omp.service.bussiness.IAddressService;
import com.youanmi.scrm.omp.service.om.NoticeService;
import com.youanmi.scrm.omp.service.system.OperateLogService;
import com.youanmi.scrm.omp.util.ImageUploadUtils;
import com.youanmi.scrm.omp.util.ImageUrlDTO;


/**
 * 公告 Created by xiao8 on 2016/12/3.
 */
@Controller
@RequestMapping("/om/notice")
public class NoticeController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(NoticeController.class);

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private IAddressService addressService;

    @Autowired
    private OperateLogService operateLogService;


    @RequestMapping
    public ModelAndView page() throws Exception {
        logger.info("系统公告列表页");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("om/notice/list");
        return modelAndView;
    }


    @RequestMapping("/list")
    @ResponseBody
    public PageData listData() throws Exception {
        PageData pd = super.getPageData();
        // PageData pageData = noticeService.queryNoticeList(pd);
        return noticeService.queryNoticeList(pd);
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "om/notice/add";
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public PageData doAdd(NoticeDto noticeDto) {
        // 验证
        PageData pageData = checkNotice(noticeDto);

        if (pageData != null) {
            return pageData;
        }
        else {
            pageData = new PageData();
        }

        PageData pd = super.getPageData();
        noticeDto.setSendTime(strDateToLongDate(pd.getString("sendTimeStr")));
        noticeDto.setCreateId(super.getSessionUserId());
        noticeDto.setSendChannel("omp");
        noticeDto.setSendUserId(super.getSessionUserId());
        noticeDto.setSendUsername(((User) super.getCurrentUser()).getLoginName());

        noticeService.addNotice(noticeDto);

        pageData.put("status", 1);
        operateLogService.saveOperateLog("添加公告“" + noticeDto.getTitle() + "”");
        // pageData.put("msg","操作成功");

        return pageData;
    }


    @RequestMapping(value = "/uploadImage")
    @ResponseBody
    public PageData uploadImage(HttpServletRequest request) {
        logger.info("upload Image");
        PageData result = new PageData();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("uploadImage");
        if (multipartFile != null) {
            try {
                ImageUrlDTO urlDTO = ImageUploadUtils.uploadImage(multipartFile);
                result.put("file_path", urlDTO.getOriginImageUrl());
                result.put("success", true);
                // result.put("msg", "操作成功");
            }
            catch (Exception e) {
                e.printStackTrace();
                result.put("status", 0);
                result.put("msg", "图片上传失败");
            }
        }
        return result;
    }


    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Model model) {
        PageData pageData = super.getPageData();
        Long id = pageData.getLong("id");

        if (id == null) {
            return null;
        }
        NoticeDto noticeDto = noticeService.getNoticeById(id);
        model.addAttribute("notice", noticeDto);
        try {
            model.addAttribute("provinceList", addressService.selectProvinceList());
            if (noticeDto.getProvince() != null && noticeDto.getProvince() != 0) {
                List cityList = addressService.selectCityList(noticeDto.getProvince() + "");
                model.addAttribute("cityList", cityList);
            }
            if (noticeDto.getCity() != null && noticeDto.getCity() != 0) {
                List countyList = addressService.selectAreaList(noticeDto.getCity() + "");
                model.addAttribute("countyList", countyList);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            return "om/notice/edit";
        }
        return "om/notice/edit";
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public PageData doEdit(NoticeDto noticeDto) {
        // 验证
        PageData pageData = checkNotice(noticeDto);

        if (pageData != null) {
            return pageData;
        }
        else {
            pageData = new PageData();
        }

        PageData pd = super.getPageData();
        String sendTimeStr = pd.getString("sendTimeStr");
        if (!StringUtils.isEmpty(sendTimeStr)) {
            noticeDto.setSendTime(strDateToLongDate(pd.getString("sendTimeStr")));
        }
        noticeService.editNotice(noticeDto);

        pageData.put("status", 1);
        operateLogService.saveOperateLog("修改公告“" + noticeDto.getTitle() + "”");
        // pageData.put("msg","操作成功");
        return pageData;
    }


    @RequestMapping(value = "/del")
    @ResponseBody
    public PageData del() {
        PageData pd = super.getPageData();
        Long id = pd.getLong("id");
        PageData pageData = new PageData();
        if (id == null || id == 0L) {
            pageData.put("status", 0);
            pageData.put("msg", "未找到删除的对象");
            return pageData;
        }
        NoticeDto oldNoticeDto = noticeService.getNoticeById(id);
        if (oldNoticeDto == null) {
            pageData.put("status", 0);
            pageData.put("msg", "未找到删除的对象");
            return pageData;
        }
        if (!(oldNoticeDto.getSendStatus() == 3 || oldNoticeDto.getSendStatus() == 2)) {
            pageData.put("status", 0);
            pageData.put("msg", "数据已被修改，请重新加载");
            return pageData;
        }
        noticeService.delNotice(id, super.getSessionUserId());
        pageData.put("status", 1);
        // pageData.put("msg", "操作成功");
        operateLogService.saveOperateLog("删除公告“" + oldNoticeDto.getTitle() + "”");
        return pageData;
    }


    @RequestMapping(value = "/cancel")
    @ResponseBody
    public PageData cancel() {
        PageData pd = super.getPageData();
        PageData pageData = new PageData();
        Long id = pd.getLong("id");
        if (id == null || id == 0L) {
            pageData.put("status", 0);
            pageData.put("msg", "未找到删除的对象");
            return pageData;
        }
        NoticeDto oldNoticeDto = noticeService.getNoticeById(id);
        if (oldNoticeDto == null) {
            pageData.put("status", 0);
            pageData.put("msg", "未找到删除的对象");
            return pageData;
        }
        if (oldNoticeDto.getSendStatus() != 4) {
            pageData.put("status", 0);
            pageData.put("msg", "数据已被修改，请重新加载");
            return pageData;
        }
        noticeService.cancelSend(id, super.getSessionUserId());

        pageData.put("status", 1);
        // pageData.put("msg", "操作成功");
        operateLogService.saveOperateLog("取消发送公告“" + oldNoticeDto.getTitle() + "”");
        return pageData;
    }


    @RequestMapping(value = "/desc")
    private String desc(Model model) {
        PageData pd = super.getPageData();
        Long id = pd.getLong("id");
        NoticeDto noticeDto = noticeService.getNoticeById(id);
        model.addAttribute("notice", noticeDto);
        return "om/notice/desc";
    }


    private Long strDateToLongDate(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (StringUtils.isEmpty(strDate)) {
            return System.currentTimeMillis();
        }
        try {
            return sdf.parse(strDate).getTime();
        }
        catch (ParseException e) {
            e.printStackTrace();
            return System.currentTimeMillis();
        }

        // if(!StringUtils.isEmpty(strDate)){
        // String date[] = strDate.split(" ");
        // Integer y=0;
        // Integer m=0;
        // Integer day=0;
        // Integer h=0;
        // Integer mi=0;
        // for (int i = 0, dateLength = date.length; i < dateLength; i++) {
        // String d = date[i];
        // if(d!=null && i==0){
        // y=Integer.parseInt(d.split("-")[0]);
        // m=Integer.parseInt(d.split("-")[1]);
        // day=Integer.parseInt(d.split("-")[2]);
        // }else if(d!=null && i==1){
        // h = Integer.parseInt(d.split(":")[0]);
        // mi = Integer.parseInt(d.split(":")[1]);
        // }
        //
        // }
        // Calendar calendar = Calendar.getInstance();
        // calendar.set(y,m,day,h,mi);
        // return calendar.getTime().getTime();
        // // return new Date(y-1900,m-1,day,h,mi,0).getTime();
        // }
        //
        // return System.currentTimeMillis();
    }


    /**
     * 验证
     * 
     * @param noticeDto
     *            验证对象
     * @return 为null为验证通过
     */
    private PageData checkNotice(NoticeDto noticeDto) {
        PageData pageData = new PageData();
        if (noticeDto == null) {
            pageData.put("status", 0);
            pageData.put("msg", "非法操作，请按正常流程操作！");
            return pageData;
        }
        else if (StringUtils.isEmpty(noticeDto.getTitle())) {
            pageData.put("status", 0);
            pageData.put("msg", "标题不能为空！");
            return pageData;
        }
        else if (StringUtils.isEmpty(noticeDto.getContent())) {
            pageData.put("status", 0);
            pageData.put("msg", "内容不能为空！");
            return pageData;
        }
        else if (noticeDto.getSendObject() == null || noticeDto.getSendObject() != 1) {
            pageData.put("status", 0);
            pageData.put("msg", "非法的发送对象");
            return pageData;
        }
        else if (!(noticeDto.getDeviceType() != null || noticeDto.getDeviceType() == 0
                || noticeDto.getDeviceType() == 1 || noticeDto.getDeviceType() == 2)) {
            pageData.put("status", 0);
            pageData.put("msg", "非法的发送设备");
            return pageData;
        }
        else if (noticeDto.getId() != null && noticeDto.getId() != 0L) {
            NoticeDto oldNoticeDto = noticeService.getNoticeById(noticeDto.getId());
            if (oldNoticeDto.getSendStatus() != 3) {
                pageData.put("status", 0);
                pageData.put("msg", "数据已被修改，请重新加载");
                return pageData;
            }
        }
        return null;
    }

}
