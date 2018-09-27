package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.SkuAttrValue;
import com.atguigu.gmall.bean.SkuImage;
import com.atguigu.gmall.bean.SkuInfo;
import com.atguigu.gmall.bean.SkuSaleAttrValue;

import java.util.List;

public interface SkuService {

    List<SkuInfo> skuInfoListBySpu(String spuId);

    void delSkuInfo(String skuInfoId);

    List<SkuImage> getSkuImageList(String skuId);

    List<SkuAttrValue> getSkuAttrValueList(String skuId);

    List<SkuSaleAttrValue> getSkuSaleAttrValueList(String skuId);

    void saveSku(SkuInfo skuInfo);
}
