package com.samsung.xiaoi.common.shiro.aop;

import com.ujigu.secure.common.bean.ResultModel;
import com.ujigu.secure.common.utils.JsonUtil;
import com.ujigu.secure.common.utils.LogUtils;
import com.ujigu.secure.web.util.WebUtils;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author ouyangan
 * @Date 2016/11/1/19:35
 * @Description 权限过滤器 未启用
 */
public class ShiroAuthorizationFilter extends AuthorizationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
    	HttpServletRequest httpRequest = (HttpServletRequest) request;
        Subject subject = getSubject(request, response);
        if (subject.getPrincipal() == null) {
            //未登录跳转至登陆页面
            saveRequest(request);
            if (httpRequest.getHeader("Accept").contains("application/json") || WebUtils.isAjax(httpRequest)) {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=UTF-8");
                
                ResultModel resultModel = new ResultModel("UNAUTHENTICATED", "用户尚未登录");
    			response.getWriter().append(JsonUtil.create().toJson(resultModel));
                response.getWriter().flush();
                response.getWriter().close();
            } else {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
//                ((HttpServletResponse) response).sendRedirect(this.getLoginUrl());
                redirectToLogin(httpRequest, response);
            }
        } else {
            //已登录未授权
            if (httpRequest.getHeader("Accept").contains("application/json")  || WebUtils.isAjax(httpRequest)) {
                LogUtils.debug("授权认证:未通过:ajax, %s", httpRequest.getRequestURL());
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=UTF-8");
                
                ResultModel resultModel = new ResultModel("UNAUTHORIZED", "对不起，您无此操作权限");
    			response.getWriter().append(JsonUtil.create().toJson(resultModel));
                response.getWriter().flush();
                response.getWriter().close();
            } else {
            	LogUtils.debug("授权认证:未通过:http, %s", httpRequest.getRequestURL());
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                
                ((HttpServletResponse) response).sendRedirect(this.getUnauthorizedUrl());
            }
        }
        return false;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        String[] perms = (String[]) mappedValue;
        boolean isPermitted = true;
        if (perms != null && perms.length > 0) {
            if (perms.length == 1) {
                if (!subject.isPermitted(perms[0])) {
                	LogUtils.debug("授权认证:未通过");
                    isPermitted = false;
                }
            } else {
                if (!subject.isPermittedAll(perms)) {
                	LogUtils.debug("授权认证:未通过");
                    isPermitted = false;
                }
            }
        }
        return isPermitted;
    }
}
