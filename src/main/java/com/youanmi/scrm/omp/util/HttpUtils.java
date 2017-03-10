/*
 * File Name：HttpUtils.java
 * Description： HttpUtils.java
 * Modify By：res-tinglong
 * Modify Date：2014年11月19日
 * Modify Type：Add
 */
package com.youanmi.scrm.omp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * http 工具类
 * 
 * <pre>
 * </pre>
 * 
 */
public class HttpUtils {
    /**
     * 字符集，UTF8
     */
    public static final String CHARSET = "UTF-8";

    private static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);


    /**
     * 
     * 判断请求是否是ajax
     * 
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {
        if (request != null && "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
            return true;
        }
        return false;
    }


    /**
     * 执行一个HTTP GET请求，返回请求响应的HTML
     * 
     * @param url
     *            请求的URL地址
     * @param queryString
     *            请求的查询参数,可以为null
     * @return 返回请求响应的HTML
     */
    public static String doGetSimple(String url, String queryString) {
        return doGet(url, queryString, CHARSET, false);
    }


    /**
     * 执行一个HTTP GET请求，返回请求响应的HTML
     * 
     * @param url
     *            请求的URL地址
     * @param queryString
     *            请求的查询参数,可以为null
     * @return 返回请求响应的HTML
     */
    public static String doGetSimple(String url, String queryString, String charSet) {
        return doGet(url, queryString, charSet, false);
    }


    /**
     * 执行一个HTTP GET请求，返回请求响应的HTML
     * 
     * @param url
     *            请求的URL地址
     * @param queryString
     *            请求的查询参数,可以为null
     * @param charset
     *            字符集
     * @param pretty
     *            是否美化
     * @return 返回请求响应的HTML
     */
    public static String doGet(String url, String queryString, String charset, boolean pretty) {
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        try {
            if (StringUtils.isNotBlank(queryString)) {
                // 对get请求参数做了http请求默认编码，好像没有任何问题，汉字编码后，就成为%式样的字符串
                method.setQueryString(URIUtil.encodeQuery(queryString));
            }
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (pretty) {
                        response.append(line).append(System.getProperty("line.separator"));
                    }
                    else {
                        response.append(line);
                    }
                }
                reader.close();
            }
        }
        catch (URIException e) {
            LOG.error("执行HTTP Get请求时，编码查询字符串“" + queryString + "”发生异常！", e);
            response.append("{\"error\":\"").append("执行HTTP Get请求时，编码查询字符串“" + queryString + "”发生异常！")
                .append("\"}");
            return response.toString();
        }
        catch (IOException e) {
            LOG.error("执行HTTP Get请求" + url + "时，发生异常！", e);
            response.append("{\"error\":\"").append("执行HTTP Get请求" + url + "时,发生异常").append("\"}");
            return response.toString();

        }
        finally {
            method.releaseConnection();
        }
        return response.toString();
    }


    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     * 
     * @param url
     *            请求的URL地址
     * @param params
     *            请求的查询参数,可以为null
     * @return 返回请求响应的HTML
     */
    public static String doPostSimple(String url, Map<String, String> params) {
        return doPost(url, params, CHARSET, false);
    }


    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     * 
     * @param url
     *            请求的URL地址
     * @param params
     *            请求的查询参数,可以为null
     * @param charset
     *            字符集
     * @param pretty
     *            是否美化
     * @return 返回请求响应的HTML
     */
    public static String doPost(String url, Map<String, String> params, String charset, boolean pretty) {
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        HttpMethod method = new PostMethod(url);
        // 设置Http Post数据
        if (params != null) {
            HttpMethodParams p = new HttpMethodParams();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                p.setParameter(entry.getKey(), entry.getValue());
            }
            method.setParams(p);
        }
        try {
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (pretty) {
                        response.append(line).append(System.getProperty("line.separator"));
                    }
                    else {
                        response.append(line);
                    }
                }
                reader.close();
            }
        }
        catch (IOException e) {
            LOG.error("执行HTTP Post请求" + url + "时，发生异常！", e);
        }
        finally {
            method.releaseConnection();
        }
        return response.toString();
    }


    /**
     * 
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
     * 
     * @param request
     *            req
     * @return
     * @throws IOException
     *             ex
     */
    public static String getIpAddr(HttpServletRequest request) throws IOException {
        String ip = request.getHeader("X-Forwarded-For");
        if (LOG.isInfoEnabled()) {
            LOG.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
                if (LOG.isInfoEnabled()) {
                    LOG.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                if (LOG.isInfoEnabled()) {
                    LOG.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
                if (LOG.isInfoEnabled()) {
                    LOG.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                if (LOG.isInfoEnabled()) {
                    LOG.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                if (LOG.isInfoEnabled()) {
                    LOG.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
                }
            }
        }
        else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }


    /**
     * 执行一个HTTP GET请求，返回请求响应的HTML
     * 
     * @param url
     *            请求的URL地址
     * @param queryString
     *            请求的查询参数,可以为null
     * @param charset
     *            字符集
     * @param pretty
     *            是否美化
     * @return 返回请求响应的HTML
     */
    public static String doGetEncode(String url, String queryString, String charset, boolean pretty) {
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        try {
            if (StringUtils.isNotBlank(queryString)) {
                // 对get请求参数做了http请求默认编码，好像没有任何问题，汉字编码后，就成为%式样的字符串
                method.setQueryString(queryString);
            }
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (pretty) {
                        response.append(line).append(System.getProperty("line.separator"));
                    }
                    else {
                        response.append(line);
                    }
                }
                reader.close();
            }
        }
        catch (URIException e) {
            LOG.error("执行HTTP Get请求时，编码查询字符串“" + queryString + "”发生异常！", e);
        }
        catch (IOException e) {
            LOG.error("执行HTTP Get请求" + url + "时，发生异常！", e);
        }
        finally {
            method.releaseConnection();
        }
        return response.toString();
    }


    public static String getStringByHttps(String url, String queryString) {
        String result = "";
        Protocol https = new Protocol("https", new HTTPSSecureProtocolSocketFactory(), 443);
        Protocol.registerProtocol("https", https);
        GetMethod get = new GetMethod(url);
        HttpClient client = new HttpClient();
        try {
            if (AssertUtils.notNull(queryString)) {
                get.setQueryString(URIUtil.encodeQuery(queryString));
            }
            client.executeMethod(get);
            result = get.getResponseBodyAsString();
            // result = new String(result.getBytes("ISO-8859-1"), "UTF-8");
            Protocol.unregisterProtocol("https");
            return result;
        }
        catch (HttpException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return "error";
    }
}
