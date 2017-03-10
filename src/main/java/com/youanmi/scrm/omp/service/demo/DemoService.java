package com.youanmi.scrm.omp.service.demo;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youanmi.commons.base.dao.BaseDAO;

/**
 * Created by Administrator on 2016/11/28.
 */
@Transactional(readOnly=true)
@Service("demoService")
public class DemoService {

    @Resource(name="BaseDao")
    private BaseDAO dao;


}
