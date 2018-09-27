package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.manage.util.FileUploadUtil;
import com.atguigu.gmall.service.SpuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<SpuImage> getSpuImageList(@RequestParam("spuId") String spuId){
        List<SpuImage> spuImageList = spuService.getSpuImageList(spuId);
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

    @RequestMapping("/saveSpuInfo")
    public String saveSpuInfo(SpuInfo spuInfo){
        spuService.saveSpuInfo(spuInfo);
        return "success";
    }

    @RequestMapping("/spuSaleAttrValueList")
    public List<SpuSaleAttrValue> spuSaleAttrValueList(@RequestParam("spuId") String spuId,@RequestParam("saleAttrId") String saleAttrId){
        List<SpuSaleAttrValue> spuSaleAttrValues = spuService.spuSaleAttrValueList(spuId,saleAttrId);
        return spuSaleAttrValues;
    }

    @RequestMapping("/spuSaleAttrList")
    public List<SpuSaleAttr> getSpuSaleAttrList(@RequestParam("spuId") String spuId){
        List<SpuSaleAttr> spuSaleAttrList = spuService.getSpuSaleAttrList(spuId);

        for (SpuSaleAttr spuSaleAttr : spuSaleAttrList) {
            List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
            Map map=new HashMap();
            map.put("total",spuSaleAttrValueList.size());
            map.put("rows",spuSaleAttrValueList);
            // String spuSaleAttrValueJson = JSON.toJSONString(map);
            spuSaleAttr.setSpuSaleAttrValueJson(map);
        }

        return spuSaleAttrList;
    }

}
