package com.samsung.xiaoi.common.shiro.aop;

import com.ujigu.secure.common.bean.ResultModel;
import com.ujigu.secure.common.utils.JsonUtil;
import com.ujigu.secure.web.util.WebUtils;

import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author ouyangan
 * @Date 2016/11/1/19:32
 * @Description 登录过滤器
 */
public class ShiroAuthenticationFilter extends PassThruAuthenticationFilter {

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		if (isLoginRequest(request, response)) {
			return true;
		}
		
		saveRequest(request);
		HttpServletRequest httpRequest = (HttpServletRequest) request;
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

			 redirectToLogin(httpRequest, response);
		}
		return false;
	}
}
