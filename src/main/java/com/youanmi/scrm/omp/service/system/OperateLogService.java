package com.youanmi.scrm.omp.service.system;

import java.text.SimpleDateFormat;
import java.util.List;

import net.wildpig.base.common.entity.PageData;
import net.wildpig.base.common.entity.sys.User;
import net.wildpig.base.common.util.Const;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.youanmi.commons.base.dao.BaseDAO;

/**
 * Created by Administrator on 2016/12/6.
 */
@Service("operateLogService")
public class OperateLogService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BaseDAO baseDao;

    /**
     * 保存操作日志
     * @param description 描述
     */
    @Async
    public void saveOperateLog(String description){
        try {
            saveOperateLogData(description+"");
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     *
     * @param description 日志描述
     */
    @Async
    public void saveOperateLogData(String description) throws Exception {
        logger.info("service>>操作日志>>>记录操作日志>>参数信息:"+description);
        /*HttpSession session =  request.getSession();*/
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User  user = (User) session.getAttribute(Const.SESSION_USER);
        if(user==null){
            logger.info("service>>操作日志>>>记录操作日志>>当前没有登陆的用户信息");
            return;
        }
        logger.info("service>>操作日志>>>记录操作日志>>当前登陆户的用户信息:"+ JSONObject.toJSONString(user));
        PageData savaData = new PageData();
        savaData.put("name",user.getName());
        savaData.put("userName",user.getLoginName());
        PageData roleData  =   baseDao.findForObject("RoleMapper.findRoleNameByUserId",user.getUserId());
        if(roleData!=null){
            logger.info("service>>操作日志>>>记录操作日志>>当前用户角色信息:"+ roleData.toString());
            savaData.put("roleName",roleData.get("roleName"));
        }
        savaData.put("description",description);
        savaData.put("userId",user.getUserId());
        savaData.put("createId",user.getUserId());
        savaData.put("createTime",System.currentTimeMillis());
        int rows = baseDao.save("OperateLogMapper.saveOperateLog",savaData);
        if(rows > 0){
            logger.info("service>>操作日志>>>记录操作日志>>记录成功");
            return;
        }
    }


    /**
     *   分页查询操作日志
     * @param pd
     * @return
     */
    public PageData operateLogPage(PageData pd) throws  Exception{
        logger.info("service>>操作日志>>>分页查询操作日志>>参数信息"+ pd.toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startTime = pd.getString("startTime");
        String endTime = pd.getString("endTime");
        if (startTime != null && !startTime.isEmpty()) {
            pd.put("startTime", sdf.parse(startTime).getTime());
        }
        if (endTime != null && !endTime.isEmpty()) {
            pd.put("endTime", (sdf.parse(endTime).getTime())+86399000);
        }
        int totalCount =  baseDao.findForObject("OperateLogMapper.findOperateLogByPageDataCount", pd);
        logger.info("service>>操作日志>>>分页查询操作日志>>当前查询总记录数:"+totalCount);
        List<PageData> list = baseDao.findForList("OperateLogMapper.findOperateLogByPageData", pd);
        logger.info("service>>操作日志>>>分页查询操作日志>>当前查询结果:"+JSONObject.toJSONString(list));
        PageData result = new PageData();
        result.put("total", totalCount);
        result.put("rows", list);
        return result;
    }
}
