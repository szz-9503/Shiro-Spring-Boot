package com.szz.shirospringboot.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {
    @RequestMapping("index")
    public String toIndex(Model model){
        model.addAttribute("msg","hello,shiro");
        return "index";
    }

    @RequestMapping("user/add")
    public String add(){
        return "user/add";
    }

    @RequestMapping("user/update")
    public String update(){
        return "user/update";
    }

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/login")
    public String login(String username,String password,Model model){
        //获取当前的用户
        Subject currentUser = SecurityUtils.getSubject();
        //封装的用户的登录数据(加密)
        UsernamePasswordToken token =  new UsernamePasswordToken(username,password);
        try{
            currentUser.login(token);//执行登录方法，如果没有异常就ok了
            return "index";
        }catch (UnknownAccountException e){//用户名不存在
            model.addAttribute("msg","用户名错误");
            return "login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "login";
        }
    }

    @RequestMapping("/nuauth")
    @ResponseBody
    public String unauthorized(){
        return "未经授权无法访问此页面";
    }
}
