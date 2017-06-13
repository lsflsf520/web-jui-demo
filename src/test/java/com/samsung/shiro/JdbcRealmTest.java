package com.samsung.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JdbcRealmTest {
	@Before  
    public void setUp() throws Exception {  
    }  
  
    @After  
    public void tearDown() throws Exception {  
    }  
  
    @Test  
    public void testAuth() {  
        // 1.获取SecurityManager工厂，此处使用ini配置文件初始化SecurityManager  
        Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro_mysql.ini");  
        // 2.获取SecurityManager实例，并绑定到SecurityUtils  
        org.apache.shiro.mgt.SecurityManager sm = factory.getInstance();  
        SecurityUtils.setSecurityManager(sm);  
  
        // 3.得到Subject  
        Subject subject = SecurityUtils.getSubject();  
        // 4.创建用户登录凭证  
//        UsernamePasswordToken token = new UsernamePasswordToken("chris.mao@emerson.com", "12345");  
        TenantPasswdToken token = new TenantPasswdToken(1012, "chris.mao@emerson.com", "12345");
        // 5.登录，如果登录失败会抛出不同的异常，根据异常输出失败原因  
        try {  
            subject.login(token);  
            // 6.判断是否成功登录  
            Assert.assertEquals(true, subject.isAuthenticated());  
            System.out.println("登录成功！！");  
            // 7.注销用户  
            subject.logout();  
        } catch (IncorrectCredentialsException e) {  
            System.out.println("登录密码错误. Password for account " + token.getPrincipal() + " was incorrect.");  
        } catch (ExcessiveAttemptsException e) {  
            System.out.println("登录失败次数过多");  
        } catch (LockedAccountException e) {  
            System.out.println("帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.");  
        } catch (DisabledAccountException e) {  
            System.out.println("帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.");  
        } catch (ExpiredCredentialsException e) {  
            System.out.println("帐号已过期. the account for username " + token.getPrincipal() + "  was expired.");  
        } catch (UnknownAccountException e) {  
            System.out.println("帐号不存在. There is no user with username of " + token.getPrincipal());  
        }  
    }  
  
    
    @Test  
    public void testPermission() {  
        // 1.获取SecurityManager工厂，此处使用ini配置文件初始化SecurityManager  
        Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro_mysql.ini");  
        // 2.获取SecurityManager实例，并绑定到SecurityUtils  
        org.apache.shiro.mgt.SecurityManager sm = factory.getInstance();  
        SecurityUtils.setSecurityManager(sm);  
        // 3.得到Subject  
        Subject subject = SecurityUtils.getSubject();  
        // 4.创建用户登录凭证  
//        UsernamePasswordToken token = new UsernamePasswordToken("chris.mao@emerson.com", "12345");  
        TenantPasswdToken token = new TenantPasswdToken(1012, "chris.mao@emerson.com", "12345");
        token.setRememberMe(true);  
        // 5.登录，如果登录失败会抛出不同的异常，根据异常输出失败原因  
        try {  
            subject.login(token);  
            // 6.判断是否成功登录  
            Assert.assertEquals(true, subject.isAuthenticated());  
            System.out.println("登录成功！！");  
            // 判断用户是否拥有某个角色  
            Assert.assertEquals(true, subject.hasRole("admin"));  
            System.out.println("有admin角色！！");  
            // 使用Shiro自带的断言判断用户是否有被授权  
            subject.checkRole("manager");  
            System.out.println("有manager角色！！");  
            subject.checkPermission("user:delete");  
            System.out.println("有create_user1权限！！");  
            // 7.注销用户  
            subject.logout();  
        } catch (IncorrectCredentialsException e) {  
            System.out.println("登录密码错误. Password for account " + token.getPrincipal() + " was incorrect.");  
        } catch (ExcessiveAttemptsException e) {  
            System.out.println("登录失败次数过多");  
        } catch (LockedAccountException e) {  
            System.out.println("帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.");  
        } catch (DisabledAccountException e) {  
            System.out.println("帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.");  
        } catch (ExpiredCredentialsException e) {  
            System.out.println("帐号已过期. the account for username " + token.getPrincipal() + "  was expired.");  
        } catch (UnknownAccountException e) {  
            System.out.println("帐号不存在. There is no user with username of " + token.getPrincipal());  
        } catch (UnauthorizedException e) {  
            System.out.println("您没有得到相应的授权！" + e.getMessage());  
        }  
    } 
    
    @Test
    public void testEncrypt(){
    	SimpleHash hash = new SimpleHash("MD5", "12345", new SimpleByteSource("{!}" + 1012), 2);
    	String hex = hash.toHex();
    	String base64 = hash.toBase64();
    	
    	System.out.println("hex:" + hex + ",base64:" + base64);
    }
    
}
