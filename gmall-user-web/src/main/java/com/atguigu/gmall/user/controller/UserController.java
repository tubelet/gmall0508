package com.atguigu.gmall.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.bean.UserInfo;
import com.atguigu.gmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//相当于@ResponseBody和@Controller合在一起的作用
@RestController
public class UserController {

    //自动注入bean
    @Reference
    UserService userService;

    //制定springMVC的请求与处理之间的映射关系，/userlist获取getUserList信息
    @RequestMapping("/userinfo")
    public List<UserInfo> getUserInfo(){
        List<UserInfo> userList = userService.getUserInfo();
        //根据请求返回一个值或者对象
        return userList;
    }

    //制定springMVC的请求与处理之间的映射关系，/userlist获取getUserAddress信息
    @RequestMapping("/useraddress")
    public List<UserAddress> getUserAddress(){
        List<UserAddress> addressList = userService.getUserAddress();
        //根据请求返回一个值或者对象
        return addressList;
    }
}
