package com.samsung.xiaoi.common.shiro.realm;

import java.util.Arrays;
import java.util.HashSet;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import com.samsung.xiaoi.sys.entity.TestUser;
import com.samsung.xiaoi.sys.service.TestUserService;

@Component
public class CommonAuthRealm extends AuthorizingRealm {
	
	@Resource
	private TestUserService testUserService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//		TestUser user = (TestUser)principals.getPrimaryPrincipal();
		
		SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo(new HashSet<>(Arrays.asList("admin")));
		authInfo.addStringPermissions(Arrays.asList("user:create", "user:update", "user:delete", "document:read"));
		
		return authInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken uptoken = (UsernamePasswordToken)token;
		String username = (String)uptoken.getPrincipal();
		
		TestUser query = new TestUser();
		query.setNick(username);
		TestUser dbData = testUserService.findOne(query);
		if(dbData != null){
			SimpleAuthenticationInfo authInfo = new SimpleAuthenticationInfo(dbData, "123456", this.getName());
			
			return authInfo;
		}
		
		return null;
	}

}
