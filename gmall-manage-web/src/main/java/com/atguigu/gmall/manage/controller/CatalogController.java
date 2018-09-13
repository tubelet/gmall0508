package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.BaseCatalog1;
import com.atguigu.gmall.service.CatalogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CatalogController {

    @Reference
    CatalogService catalogService;

    @RequestMapping("/getCatalog1")
    public List<BaseCatalog1> getCatalog1(){
        List<BaseCatalog1> infoList = catalogService.getCatalog();
        return infoList;
    }

    @RequestMapping("/getCatalog2")
    public String getCatalog2(){

        return "attrListPage";
    }

    @RequestMapping("/getCatalog3")
    public String getCatalog3(){

        return "attrListPage";
    }

}
