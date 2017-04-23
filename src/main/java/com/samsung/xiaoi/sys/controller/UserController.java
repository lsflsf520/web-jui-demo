package com.samsung.xiaoi.sys.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.samsung.xiaoi.common.bean.DwzAjaxModel;
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
		
		ModelAndView mav = new ModelAndView("sys/user_list", "dataList", userList);
		mav.addObject("queryData", user);
		return mav;
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
	@ResponseBody
	public DwzAjaxModel doSave(TestUser user){
		try{
			String nick = user.getNick();
			for(int i = 27; i < 200; i++){
				user.setSid(null);
				user.setNick(nick + "_" + i);
				testUserService.doSave(user);
			}
		} catch (Exception e){
			e.printStackTrace();
			return DwzAjaxModel.failure();
		}
		
		return DwzAjaxModel.successNavTab("user-mgr");
//		return DwzAjaxModel.successRel("user-mgr");
	}
	
	@RequestMapping("softdel")
	@ResponseBody
	public DwzAjaxModel softdel(Integer pk){
		testUserService.softDel(pk);
		
		return DwzAjaxModel.succesForward("/sys/user/list.do");
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public DwzAjaxModel delete(Integer pk){
		testUserService.batchDel(pk);
		
		return DwzAjaxModel.successNavTab("user-mgr");
	}
	
}
