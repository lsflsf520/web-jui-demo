package com.samsung.xiaoi.sys.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2014/12/18.
 */
@Controller
@RequestMapping("shiro")
public class LoginController {

    @RequestMapping("dologon")
    public String login(HttpServletRequest request,@RequestParam("username") String username,@RequestParam("password") String password, RedirectAttributes attr){
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //token.setRememberMe(true);
        try {
//        	currentUser.getSession().setAttribute("msg",username);
            currentUser.login(token);
        } catch (Exception e) {
            e.printStackTrace();
            attr.addAttribute("msg",e.getClass()+",用户名或者密码错误");
            return "redirect:tologin.do";
        }
        if(currentUser.isAuthenticated()){
        	attr.addAttribute("msg", "用户名密码正确");
            return "redirect:loginok.do";
        }else{
        	attr.addAttribute("msg", "用户名密码正确");
            return "redirect:tologin.do";
        }
    }
    
    @RequestMapping("tologin")
    public ModelAndView tologin(String msg){
    	return new ModelAndView("sys/shiro_login", "msg", msg);
    }
    
    @RequestMapping("loginok")
    public ModelAndView loginok(){
    	return new ModelAndView("sys/shiro_login_ok", "msg", "恭喜您，登录成功");
    }
    
    @RequestMapping("403")
    public ModelAndView to403(){
    	return new ModelAndView("sys/403", "msg", "您没有权限");
    }
}
