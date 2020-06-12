package com.szz.shirospringboot.config;

import com.szz.shirospringboot.pojo.User;
import com.szz.shirospringboot.service.UserService;
import com.szz.shirospringboot.util.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

//自定义的 UserRealm extends AuthorizingRealm
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了=》授权doGetAuthorizationInfo");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //info.addStringPermission("user:add");
        //拿到当前登录的这个对象
        Subject currentSubject = SecurityUtils.getSubject();
        User currentUser = (User) currentSubject.getPrincipal();//拿到user对象
        //设置当前用户的权限(如果有多个权限，这里info.addStringPermission循环)
        info.addStringPermission(currentUser.getPerms());

        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了=》认证doGetAuthenticationInfo");

        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        //连接数据库
        User user = userService.queryUserByName(userToken.getUsername());

        if (StringUtils.isEmptyOrNull(user)){//没有这个人
            return null;//自动抛出UnknownAccountException异常
        }

        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        session.setAttribute("loginUser",user);
        //密码认证，shiro自己做
        //可以MD5加密，MD5盐值加密：加密后加上账号（让加密更加难破解）
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }
}
