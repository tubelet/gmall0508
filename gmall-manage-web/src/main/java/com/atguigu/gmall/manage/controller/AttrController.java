package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.BaseAttrInfo;
import com.atguigu.gmall.bean.BaseAttrValue;
import com.atguigu.gmall.service.AttrService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AttrController {

    @Reference
    AttrService attrService;

    @RequestMapping("/getAttrListByCtg3")
    public List<BaseAttrInfo> getAttrListByCtg3(@RequestParam("id")String id){
        List<BaseAttrInfo> attrListByCtg3 = attrService.selectAttrInfoList(Long.parseLong(id));
        return attrListByCtg3;
    }

    @RequestMapping("/getAttrValue")
    public List<BaseAttrValue> getAttrValue(@RequestParam("id")Integer id){
        List<BaseAttrValue> attrValue = attrService.getAttrValue(id);
        return attrValue;
    }

    @RequestMapping("/saveAttr")
    public String saveAttr(BaseAttrInfo baseAttrInfo){
        attrService.saveAttr(baseAttrInfo);
        return "success";
    }

    @DeleteMapping("/delAttr")
    public String delAttr(@RequestParam("id")String delId){
        attrService.delAttr(delId);
        return "success";
    }

}
