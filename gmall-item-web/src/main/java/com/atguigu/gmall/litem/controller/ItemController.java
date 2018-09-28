package com.atguigu.gmall.litem.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.service.SkuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {

    @Reference
    SkuService skuService;

    @RequestMapping("/{skuId}.html")
    public String index(@PathVariable String skuId){

        return "item";
    }
}
