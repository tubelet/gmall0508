package com.atguigu.gmall.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//相当于@ResponseBody和@Controller合在一起的作用
@RestController
public class IndexController {

    //制定springMVC的请求与处理之间的映射关系，
    @RequestMapping("/index")
    public String index(){
        //根据请求返回一个值或者对象
        return "Hello!";
    }
}
