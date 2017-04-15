package com.samsung.xiaoi.sys.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.samsung.xiaoi.sys.entity.TestUser;
import com.samsung.xiaoi.sys.service.TestUserService;

@Controller
@RequestMapping("/sys/user")
public class UserController {
	
	@Resource
    private TestUserService testUserService;
	
	@RequestMapping("list")
	public ModelAndView list(TestUser user){
		List<TestUser> userList = testUserService.findByPage(user);
		
		return new ModelAndView("sys/user_list", "dataList", userList);
	}
	
	@RequestMapping("toedit")
	public ModelAndView toEdit(Integer pk){
		ModelAndView mav = new ModelAndView("sys/user_edit");
		if(pk != null){
			TestUser testUser = testUserService.findById(pk);
			
			mav.addObject("data", testUser);
		}
		
		return mav;
	}
	
	@RequestMapping("dosave")
	public String doSave(TestUser user){
		testUserService.doSave(user);
		
		return "redirect:list.do";
	}
	
	@RequestMapping("softdel")
	public String softdel(Integer pk){
		testUserService.softDel(pk);
		
		return "redirect:list.do";
	}
	
	@RequestMapping("delete")
	public String delete(Integer pk){
		testUserService.batchDel(pk);
		
		return "redirect:list.do";
	}
	
}
