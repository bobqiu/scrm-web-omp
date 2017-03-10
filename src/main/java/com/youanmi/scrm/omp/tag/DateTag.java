/*
 * 文件名：DateTag.java
 * 版权：Copyright 2014 youanmi Tech. Co. Ltd. All Rights Reserved. 
 * 描述： DateTag.java
 * 修改人：刘红艳
 * 修改时间：2014年12月29日
 * 修改内容：新增
 */
package com.youanmi.scrm.omp.tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * 日期标签。
 * <p>
 * 
 * <p>
 * 
 * 
 * <pre>
 * </pre>
 * 
 * @author 刘红艳
 * @version YouAnMi-OTO 2014年12月29日
 * @since YouAnMi-OTO
 */
public class DateTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    /**
     * 调测日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(DateTag.class);

    // 日期毫秒值
    private Long value;

    // 格式
    private String pattern;


    @Override
    public int doStartTag() throws JspException {
        String s = null;
        if (value == null || value.longValue() == 0) {
            s = "";
        }
        else {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(value);
            SimpleDateFormat dateformat = new SimpleDateFormat(pattern);
            s = dateformat.format(c.getTime());
        }
        try {
            pageContext.getOut().write(s);
        }
        catch (IOException e) {
            LOG.error("Date tag error.", e);
        }
        return super.doStartTag();
    }


    public void setValue(Long value) {
        this.value = value;
    }


    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

}
