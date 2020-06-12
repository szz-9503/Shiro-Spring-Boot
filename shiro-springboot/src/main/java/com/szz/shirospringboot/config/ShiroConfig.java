package com.szz.shirospringboot.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //ShiroFilterFactoryBean:3
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDefaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);

        //添加shiro的内置过滤器
        /*
            anon:无需认证就可以访问
            authc:必须认证了才能访问
            user: 必须拥有  记住我 功能才能用
            perms: 拥有某个资源的权限才能访问；
            role: 拥有某个角色权限才能访问
         */
        //拦截
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        //授权,正常情况下，没有授权回跳到未授权页面
        //可以使用通配符   "/user/*"
        filterChainDefinitionMap.put("/user/add","perms[user:add]");
        filterChainDefinitionMap.put("/user/update","perms[user:update]");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        //设置登录的请求
        bean.setLoginUrl("/toLogin");
        //设置未授权的请求
        bean.setUnauthorizedUrl("/nuauth");
        return  bean;
    }

    //DefaultWebSecurityManager:2
    @Bean(name = "getDefaultWebSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //创建 realm  对象,需要自定义类:1
    @Bean(name = "userRealm")
    public UserRealm userRealm(){
        return new UserRealm();
    }

    //整合ShiroDialect:用来整合shiro thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
