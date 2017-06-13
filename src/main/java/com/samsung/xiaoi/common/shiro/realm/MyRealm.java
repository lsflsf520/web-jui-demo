package com.samsung.xiaoi.common.shiro.realm;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyRealm extends AuthorizingRealm {
    Logger log= LoggerFactory.getLogger(getClass());
    /**
     * 为当前登录的Subject授予角色和权限
     *    经测试:本例中该方法的调用时机为需授权资源被访问时
     *    经测试:并且每次访问需授权资源时都会执行该方法中的逻辑,这表明本例中默认并未启用AuthorizationCache

     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){
        //获取当前登录的用户名,等价于(String)principals.fromRealm(this.getName()).iterator().next()
        String currentUsername = (String)super.getAvailablePrincipal(principals);
        log.info("用户[{}]进行授权",currentUsername);
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        //实际中可能会像上面注释的那样从数据库取得
        if(null!=currentUsername && "mike".equals(currentUsername)){
            //添加一个角色,不是配置意义上的添加,而是证明该用户拥有admin角色
            simpleAuthorInfo.addRole("admin");
            //添加权限
            simpleAuthorInfo.addStringPermission("roleId");
            log.info("已为用户[mike]赋予了[admin]角色和[admin:manage]权限");
            return simpleAuthorInfo;
        }
        log.info("用户[{}]进行授权失败",currentUsername);
        //若该方法什么都不做直接返回null的话,就会导致任何用户访问/admin/listUser.jsp时都会自动跳转到unauthorizedUrl指定的地址
        //详见applicationContext.xml中的<bean id="shiroFilter">的配置
        return null;
    }


    /**
     * 验证当前登录的Subject
     *  经测试:本例中该方法的调用时机为LoginController.login()方法中执行Subject.login()时
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        //获取基于用户名和密码的令牌
        //实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
        //两个token的引用都是一样的
        UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
        log.info("验证当前Subject时获取到token为" + token);

        if("mike".equals(token.getUsername())){
        	log.info("username=[{}]，password=[{}]登陆成功",token.getPrincipal(),token.getCredentials());
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(token.getPrincipal(), token.getCredentials(), this.getName());
            this.setSession("userVo", token.getPrincipal());//从数据库查询出的user对象
            return authcInfo;
        }
        //没有返回登录用户名对应的SimpleAuthenticationInfo对象时,就会在LoginController中抛出UnknownAccountException异常
        return null;
    }


    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     *    比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
     */
    private void setSession(Object key, Object value){
        Subject currentUser = SecurityUtils.getSubject();
        if(null != currentUser){
            Session session = currentUser.getSession();
            log.info("key=["+key+"],value=["+value+"]插入session["+session.getId()+"],host=["+session.getHost()+"]");
            if(null != session){
                session.setAttribute(key, value);
            }
        }
    }
}