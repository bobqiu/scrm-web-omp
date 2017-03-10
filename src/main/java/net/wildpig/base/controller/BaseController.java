package net.wildpig.base.controller;

import com.alibaba.fastjson.JSONObject;
import com.youanmi.scrm.omp.constants.ResultConstants;
import net.wildpig.base.common.entity.PageData;
import net.wildpig.base.common.entity.sys.User;
import net.wildpig.base.common.util.Const;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * @FileName BaseController.java
 * @Description: Controller基类
 *
 * @Date Apr 17, 2015
 * @author YangShengJun
 * @version 1.0
 * 
 */
public class BaseController {

	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	public HttpSession getSession() {
		return getRequest().getSession();
	}
	
	public PageData getPageData() {
		return new PageData(getRequest());
	}
	
	public Map<String, Object> getParams(HttpServletRequest request){
		Map<String, Object> params = new HashMap<>();
		Map<?, ?> parameterMap = request.getParameterMap();
		for (Object key : parameterMap.keySet()) {
			String name = (String) key;
			Object valueObj = parameterMap.get(key);
			if (valueObj != null && valueObj instanceof String[]) {
				String[] valueArr = (String[]) valueObj;
				if (valueArr.length == 1) {
					valueObj = valueArr[0];
				}
			}
			params.put(name, valueObj);
		}
		return params;
	}
	
	public Object getCurrentUser() {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		return session.getAttribute(Const.SESSION_USER);
	}
	
	public void write(HttpServletResponse response, String str) {
		try {
			response.setContentType("text/plain; charset=utf-8");
			response.getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeJson(HttpServletResponse response, Object obj) {
		write(response, JSONObject.toJSONString(obj));
	}

	/**
	 * Created by xiao8 on 2016/12/6.
	 * @return userId
	 */
	protected Long getSessionUserId(){
		User user = (User)getCurrentUser();
		if(user==null || user.getUserId()==null){
			return null;
		}
		return user.getUserId().longValue();
	}
	/**
	 * 分页的数据
	 * @param list 集合
	 * @return  分页pagedata
	 */
	public static PageData paging(List list, Integer total){
		return new PageData(){
			{
				put("rows",list);
				put("total",total);
			}
		};
	}

	/**
	 * 返回成功
	 * @return 分页pagedata
	 */
	public PageData success(){
		return new PageData(){
			{
				put("status", ResultConstants.SUCCESS);
			}
		};
	}
	/**
	 * 返回失败
	 * @return 分页pagedata
	 */
	public PageData fail(String msg){
		return new PageData(){
			{
				put("status",ResultConstants.FAIL);
				put("msg",msg);
			}
		};
	}
}
