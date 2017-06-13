package com.samsung.xiaoi.common.aop;


import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyShiroFilterFactoryBean extends ShiroFilterFactoryBean{
	Logger log=LoggerFactory.getLogger(getClass());
	private Map<String, String> filterMap; 
	public MyShiroFilterFactoryBean() {
		this.filterMap = new LinkedHashMap<String, String>(); //order matters!
		initPerms();
	}
	
	void initPerms(){
		log.info("从数据库查询权限出来，初始化权限");
		filterMap.put("/", "anon");
		filterMap.put("/login", "anon");
		filterMap.put("/main**", "authc");
		filterMap.put("/user/info**", "authc");
		filterMap.put("/admin/listUser**", "authc,perms[roleId]");
		setFilterChainDefinitionMap(filterMap);
	}

}
