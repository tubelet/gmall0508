package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.BaseSaleAttr;
import com.atguigu.gmall.bean.SpuImage;
import com.atguigu.gmall.bean.SpuInfo;
import com.atguigu.gmall.manage.util.FileUploadUtil;
import com.atguigu.gmall.service.SpuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class SpuController {

    @Reference
    SpuService spuService;

    @RequestMapping("/spuList")
    public List<SpuInfo> getSpuInfoList(@RequestParam("catalog3Id") String id){
        List<SpuInfo> spuInfoList = spuService.getSpuInfoList(id);
        return spuInfoList;
    }

    @RequestMapping("/spuImageList")
    public List<SpuImage> getSpuImageList(@RequestParam("spuId") String id){
        List<SpuImage> spuImageList = spuService.getSpuImageList(id);
        return spuImageList;
    }

    @RequestMapping("/fileUpload")
    public String fileUpload(@RequestParam("file") MultipartFile file){
        String imgUrl = FileUploadUtil.uploadImage(file);
        return imgUrl;
    }

    @RequestMapping("/baseSaleAttrList")
    public List<BaseSaleAttr> baseSaleAttrList(){
        List<BaseSaleAttr> baseSaleAttrs =  spuService.baseSaleAttrList();
        return baseSaleAttrs;
    }

    @RequestMapping("/delSpuInfo")
    public String delSpuInfo(@RequestParam("spuInfoId") String spuInfoId){
        spuService.delSpuInfo(spuInfoId);
        return "sucess";
    }

}
