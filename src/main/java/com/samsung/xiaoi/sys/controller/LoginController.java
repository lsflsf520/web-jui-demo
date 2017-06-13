package com.samsung.xiaoi.sys.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2014/12/18.
 */
@Controller
public class LoginController {

    @RequestMapping("doshirologin")
    public String login(HttpServletRequest request,@RequestParam("username") String username,@RequestParam("password") String password){
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //token.setRememberMe(true);
        try {
//        	currentUser.getSession().setAttribute("msg",username);
            currentUser.login(token);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg",e.getClass()+",用户名或者密码错误");
            return "sys/shiro_login_ok";
        }
        if(currentUser.isAuthenticated()){
            request.setAttribute("msg", "用户名密码正确");
            return "sys/shiro_login_ok";
        }else{
        	request.setAttribute("msg", "认证失败");
        	return "sys/shiro_login_ok";
        }
    }
    
    @RequestMapping("tologin")
    public String tologin(){
    	return "sys/shiro_login";
    }
}
