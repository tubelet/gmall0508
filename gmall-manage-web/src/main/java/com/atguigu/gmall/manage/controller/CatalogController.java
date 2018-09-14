package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.BaseCatalog1;
import com.atguigu.gmall.bean.BaseCatalog2;
import com.atguigu.gmall.bean.BaseCatalog3;
import com.atguigu.gmall.service.CatalogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CatalogController {

    @Reference
    CatalogService catalogService;

    @RequestMapping("/getCatalog1")
    public List<BaseCatalog1> getCatalog1(){
        List<BaseCatalog1> infoList = catalogService.getCatalog1();
        return infoList;
    }

    @RequestMapping("/getCatalog2")
    public List<BaseCatalog2> getCatalog2(@RequestParam("id")Integer id){
        List<BaseCatalog2> infoList = catalogService.getCatalog2ById(id);
        return infoList;
    }

    @RequestMapping("/getCatalog3")
    public List<BaseCatalog3> getCatalog3(@RequestParam("id")Integer id){
        List<BaseCatalog3> infoList = catalogService.getCatalog3ById(id);
        return infoList;
    }

}
