package net.wildpig.base.common.entity;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;


/**
 * @FileName PageData.java
 * @Description: 
 *
 * @Date Feb 11, 2016 
 * @author YangShengJun
 * @version 1.0
 * 
 */
public class PageData extends JSONObject {

	private static final long serialVersionUID = 6578073611533457992L;
	
	public PageData() {
		super();
	}
	
	public PageData(Map<String, Object> map) {
		super(map);
	}
	
	public PageData(HttpServletRequest request){
		super();
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
			super.put(name, valueObj);
		}
	}

}
