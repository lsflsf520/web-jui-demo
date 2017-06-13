package com.samsung.xiaoi.common.aop;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
public class ShiroSSOFilter extends PermissionsAuthorizationFilter {

    public Logger logger = LoggerFactory.getLogger(getClass());

//    @Resource
//    private CacheManager shiroCacheManager;

    @Override
    public boolean isAccessAllowed(ServletRequest request,
                                   ServletResponse response, Object mappedValue) throws IOException {
        //upm with shiro subject/principal
       /* Subject user = SecurityUtils.getSubject();
        ShiroUser shiroUser = (ShiroUser) user.getPrincipal();

        //get sso session
        Session session = user.getSession(false);
        Cache<Object, Object> cache = shiroCacheManager.getCache(Constants.SSO_CACHE);
        Object cachedSession = cache.get(Constants.SSO_CACHE + "-" + shiroUser.getAccount());
        if(cachedSession == null){
            user.logout();
            return false;
        }
        String cachedSessionId =cachedSession.toString();
        String sessionId = (String) session.getId();
        if (!sessionId.equals(cachedSessionId)) {
            user.logout();
        }*/

        HttpServletRequest req = (HttpServletRequest) request;
        //get shiro upm
        Subject subject = getSubject(request, response);
        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();

        int i = uri.indexOf(contextPath);
        if (i > -1) {
            uri = uri.substring(i + contextPath.length());
        }
        if (StringUtils.isBlank(uri)) {
            uri = "/";
        }

        boolean permitted = false;
        if ("/".equals(uri)) {
            permitted = true;
        } else {
            //check has right using shiro
            permitted = subject.isPermitted(uri);
        }

        return permitted;

    }
}