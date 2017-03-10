
package net.wildpig.base.common.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.youanmi.scrm.omp.constants.ResultConstants;

/**
 * @FileName MyExceptionResolver.java
 * @Description:
 *
 * @Date Apr 17, 2015
 * @author YangShengJun
 * @version 1.0
 * 
 */
public class MyExceptionResolver implements HandlerExceptionResolver {

    private static Logger logger = LoggerFactory.getLogger(MyExceptionResolver.class);
            
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		System.out.println("==============异常开始=============");// TODO: 切换到logger
		if(logger.isErrorEnabled()) {
		    logger.error(ex.getMessage(), ex);
		}
		System.out.println("==============异常结束=============");
		if (isNotAjax(request)) {
		    ModelAndView mv = new ModelAndView("error");
		    mv.addObject("exception", ex.toString().replaceAll("\n", "<br/>"));// TODO:release的时候，注释掉（不用暴露具体错误信息给用户）
		    return mv;
		}
		return new ModelAndView(new MappingJackson2JsonView(), getResultMap(ResultConstants.FAIL, "操作失败"));
	}
	
    @SuppressWarnings("serial")
    private Map<String, String> getResultMap(final Integer status, final String msg) {
        return new HashMap<String, String>() {
            {
                put("status", status.toString());
                put("msg", msg);
            }
        };
    }
    
    /**
     * 
     * 判断请求是否为ajax请求。
     * 
     * @param httpServletRequest
     * @return true为非ajax请求，false则代表为ajax请求。
     */
    private boolean isNotAjax(HttpServletRequest httpServletRequest) {
        if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With"))
                || httpServletRequest.getParameter("ajax") == null) {// 不是ajax请求
            return true;
        }
        return false;
    }

}
