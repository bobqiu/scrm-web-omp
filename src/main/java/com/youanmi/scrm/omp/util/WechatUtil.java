package com.youanmi.scrm.omp.util;

import java.util.Map;

import net.sf.json.JSONObject;
import net.wildpig.base.common.entity.PageData;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.youanmi.scrm.commons.util.json.JsonUtils;



public class WechatUtil {

	private static final String APP_ID = "wx0fac78f604b522b8";
	private static final String APP_SECRET= "c77c2177ea76a6f3afb350c3129aacd2";
	private static final long TOKEN_TIMEOUT = 3600l;
	private static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
	private static final String TOKEN_PARAM = "grant_type=client_credential&appid="+APP_ID+"&secret="+APP_SECRET;
	//用于记录上次请求的时间
    private static Long lastRequestTime = 0L;
    //缓存token
    private static String token = "";
	
    /**
     * 获取access_token
     * @param flag 取最新的token
     * @return
     */
	public static String getAccessToken(boolean flag) {
		//记录本次请求的时间，用于与下次比较
		Long timeGap = System.currentTimeMillis() - lastRequestTime;
		if (timeGap > TOKEN_TIMEOUT * 1000) {
			token = getToken();
		}
		if (flag) {
			token = getToken();
		}
		return token;
	}
	
	public static JSONObject getWeChatNewsList(PageData params, String accessURL) throws Exception {
		JSONObject json = new JSONObject(); 
		DefaultHttpClient httpClient = new DefaultHttpClient(); 
		HttpPost method = new HttpPost(accessURL); 
		StringEntity entity = new StringEntity(params.toString(),"UTF-8");//解决中文乱码问题 
        entity.setContentEncoding("UTF-8");    
        entity.setContentType("application/json");
        method.setEntity(entity); 
        HttpResponse result = httpClient.execute(method); 
        // 请求结束，返回结果  
        String resData = EntityUtils.toString(result.getEntity(), "UTF-8");  
        JSONObject resJson = JSONObject.fromObject(resData);
        return resJson;
	}
	
	private static String getToken() {
		String result = HttpUtils.doGetSimple(TOKEN_URL, TOKEN_PARAM);
		Map<String, String> map = JsonUtils.toObj(result,Map.class);
		lastRequestTime = System.currentTimeMillis();
		return map.get("access_token");
	}
	
	public static void main(String[] args) throws Exception {
		PageData params = new PageData();
		params.put("type", "news");
		params.put("offset", 0);
		params.put("count", 3);
		System.out.println(getWeChatNewsList(params, "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token="+getAccessToken(false)));;
	}
}
