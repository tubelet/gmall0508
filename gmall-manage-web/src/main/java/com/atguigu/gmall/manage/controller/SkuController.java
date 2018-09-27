package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.SkuAttrValue;
import com.atguigu.gmall.bean.SkuImage;
import com.atguigu.gmall.bean.SkuInfo;
import com.atguigu.gmall.bean.SkuSaleAttrValue;
import com.atguigu.gmall.service.SkuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SkuController {

    @Reference
    SkuService skuService;

    @RequestMapping("/saveSku")
    public String saveSku(SkuInfo skuInfo){
        skuService.saveSku(skuInfo);
        return "success";

    }

    @RequestMapping("/skuInfoListBySpu")
    public List<SkuInfo> skuInfoListBySpu(String spuId){
        List<SkuInfo> skuInfos = skuService.skuInfoListBySpu(spuId);
        for (SkuInfo skuInfo : skuInfos) {
            String skuId = skuInfo.getId();
            List<SkuImage> skuImages = skuService.getSkuImageList(skuId);
            skuInfo.setSkuImageList(skuImages);
            List<SkuAttrValue> skuAttrValueList = skuService.getSkuAttrValueList(skuId);
            skuInfo.setSkuAttrValueList(skuAttrValueList);
            List<SkuSaleAttrValue> skuSaleAttrValueList = skuService.getSkuSaleAttrValueList(skuId);
            skuInfo.setSkuSaleAttrValueList(skuSaleAttrValueList);
        }
        return skuInfos;
    }

    @RequestMapping("/delSkuInfo")
    public String delSkuInfo(String skuInfoId){
        skuService.delSkuInfo(skuInfoId);
        return "success";
    }

}
