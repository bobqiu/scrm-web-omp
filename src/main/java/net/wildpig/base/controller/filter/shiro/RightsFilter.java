package net.wildpig.base.controller.filter.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.wildpig.base.common.entity.PageData;
import net.wildpig.base.common.entity.sys.User;
import net.wildpig.base.common.util.Const;
import net.wildpig.base.service.UserService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @FileName RightsFilter.java
 * @Description:
 *
 * @Date May 6, 2015
 * @author YangShengJun
 * @version 1.0
 * 
 */
public class RightsFilter extends AccessControlFilter {

    // public static final String DEFAULT_UNAUTH_URL = "/unAuth.jsp";
    // f
    // private String unAuthUrl = DEFAULT_UNAUTH_URL;

    @Autowired
    private UserService userService;


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {

        if (isLoginRequest(request, response)) {
            return true;
        }
        else {
            HttpServletResponse res = (HttpServletResponse) response;
            Subject subject = SecurityUtils.getSubject();
            Object obj = subject.getSession().getAttribute(Const.SESSION_USER);
            if (obj == null) {
                return false;
            }
            User user = (User) obj;
            PageData userPageData = userService.getById(user.getUserId());
            if (userPageData == null) {
                subject.getSession().removeAttribute(Const.SESSION_USER);
                return false;
            }

            HttpServletRequest req = (HttpServletRequest) request;
            String url = req.getServletPath();

            // if(url.equals("main/index")) return true;
            if (subject.getPrincipal() != null) {
                // 判断该请求是否包含在要求过滤的列表中，如果是则判断访问的用户是否有权限访问资源。

                System.out.println("=============access url(origianl)==" + url);
                if (url.startsWith("/"))
                    url = url.substring(1);
                if (url.endsWith("/list"))
                    url = url.substring(0, url.lastIndexOf("/list"));
                if (url.endsWith("/form"))
                    url = url.substring(0, url.lastIndexOf("/form"));
                boolean hasPermition = subject.isPermitted(url);
                boolean isAdmin = subject.hasRole(Const.ADMIN_ROLE);
                // isAdmin=false;
                System.out.println("=============isAdmin==" + isAdmin);
                System.out.println("=============access url==" + url);
                System.out.println("=============access has permision==" + hasPermition);
                return (hasPermition || isAdmin);
            }
            return false;
        }
    }


    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        Object obj = subject.getSession().getAttribute(Const.SESSION_USER);
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        String headerStr = req.getHeader("Accept");
        if (!subject.isAuthenticated()) {
            if(headerStr.contains("application/json")){
                if (obj == null) {
                    res.getWriter().write("{\"isLogin\":\"0\"}");
                }else{
                    res.getWriter().write("{\"isLogin\":\"1\"}");
                }
            }else{
                redirectToLogin(request, response);
            }
            return false;
            // isAccessAllowed 有一些本不应该拦截的url, 若理顺后，改方法可以直接返回false
            // 将上面url答应出来的放在shiro的匿名访问列表中就可以了， 例如url:/tab.do； main/index
        }

        String url = req.getServletPath();
        System.out.println("url===============" + url);
        System.out.println("===============" + subject.isPermitted(url));
        if (!subject.isPermitted(url)) {


            if(headerStr.contains("application/json")){
                if (obj == null) {
                    res.getWriter().write("{\"isLogin\":\"0\"}");
                }else{
                    res.getWriter().write("{\"isLogin\":\"1\"}");
                }
            }else{
                if (obj == null) {
                    redirectToLogin(request, response);
                }else{
                    WebUtils.issueRedirect(request, response, "/noPrivilege");
                }
            }
        }
        return false;
    }

    // protected void redirectToAuthInfo(ServletRequest request, ServletResponse
    // response) throws IOException {
    // String url = getUnAuthUrl();
    // WebUtils.issueRedirect(request, response, url);
    // }
    //
    // public String getUnAuthUrl() {
    // return unAuthUrl;
    // }
    //
    // public void setUnAuthUrl(String unAuthUrl) {
    // this.unAuthUrl = unAuthUrl;
    // }

}