/*
 * 文件名：IndustryServiceImpl.java
 * 版权：深圳柚安米科技有限公司版权所有
 * 修改人：guohao
 * 修改时间：2016年12月5日
 * 修改内容：新增
 */
package com.youanmi.scrm.omp.service.bussiness.impl;

import com.youanmi.commons.base.dao.BaseDAO;
import com.youanmi.scrm.omp.service.bussiness.WorkingMachineService;
import net.wildpig.base.common.entity.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 工作机服务实现类
 * 
 * 
 * @author guohao
 * @since 2.2.4
 */
@Transactional(readOnly = true)
@Service
public class WorkingMachineServiceImpl implements WorkingMachineService {

    @Resource(name = "BaseDao")
    private BaseDAO dao;

    /**
     * 调测日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(WorkingMachineServiceImpl.class);
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public PageData queryDeviceList(PageData pd) throws Exception {
        int totalCount = Integer.parseInt(dao.findForObject("WorkingMachineMapper.count", pd) + "");
        pd.put("from", pd.getInteger("offset"));
        pd.put("size", pd.getInteger("limit"));
        List<PageData> lists = dao.findForList("WorkingMachineMapper.getDevices", pd);
        for (PageData data : lists) {
            if (data.get("activeTime") != null) {
                data.put("activeTime", sdf.format(new Date(Long.parseLong(data.getString("activeTime")))));
            }
        }
        PageData result = new PageData();
        result.put("total", totalCount);
        result.put("rows", lists);
        return result;
    }

}
