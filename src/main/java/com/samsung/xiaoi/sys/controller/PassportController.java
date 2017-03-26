package com.samsung.xiaoi.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sys/port")
public class PassportController {
	
	@RequestMapping("/loginpage")
	public String loginpage(){
		
		return "sys/login";
	}
	
	@RequestMapping("/login_dialog")
	public String logindialog(){
		
		return "sys/login_dialog";
	}
	
	

}
