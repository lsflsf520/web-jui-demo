package com.samsung.xiaoi.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class GlobalController {
	
	@RequestMapping("/index")
	public String index(){
		
		return "index";
	}
	
	@RequestMapping("/main.do")
	public String main(){
		return "main";
	}

}
