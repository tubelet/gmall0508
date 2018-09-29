package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.*;

import java.util.List;

public interface SkuService {

    List<SkuInfo> skuInfoListBySpu(String spuId);

    void delSkuInfo(String skuInfoId);

    List<SkuImage> getSkuImageList(String skuId);

    List<SkuAttrValue> getSkuAttrValueList(String skuId);

    List<SkuSaleAttrValue> getSkuSaleAttrValueList(String skuId);

    void saveSku(SkuInfo skuInfo);

    SkuInfo getSkuById(String skuId);

    List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(String spuId,String skuId);

    List<SkuInfo> getSkuSaleAttrValueListBySpu(String spuId);
}
