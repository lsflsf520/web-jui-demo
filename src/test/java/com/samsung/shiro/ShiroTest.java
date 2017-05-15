package com.samsung.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class ShiroTest {

	
	public static void main(String[] args) {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		
		SecurityManager securityManger = factory.getInstance();
		
		SecurityUtils.setSecurityManager(securityManger);
		
		Subject currUser = SecurityUtils.getSubject();
		
//		Session session = currUser.getSession();
//		session.setAttribute("name", "lsflsf520");
		if(!currUser.isAuthenticated()){
			UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
			
			token.setRememberMe(true);
			currUser.login(token);
		}
		
		if(currUser.hasRole("schwartz")){
			System.out.println("curr user has schwartz role");
		}else{
			System.out.println("curr user has no schwartz role");
		}
		
		if(currUser.isPermitted("lightsaber:weild")){
			System.out.println("You can use lightsaber ring.");
		}else{
			System.out.println("Sorry, you have not lightsaber ring privilege.");
		}
		
		if(currUser.isPermitted("winnabago:drive:eagle5")){
			System.out.println("You permitted to winnabago:drive:eagle5");
		}else{
			System.out.println("Sorry, you not permitted to winnabago:drive:eagle5");
		}
		
		currUser.logout();
	}
	
}
