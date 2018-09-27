package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.*;

import java.util.List;

public interface SpuService {

    List<SpuInfo> getSpuInfoList(String id);

    List<SpuImage> getSpuImageList(String spuId);

    List<BaseSaleAttr> baseSaleAttrList();

    void delSpuInfo(String spuInfoId);

    void saveSpuInfo(SpuInfo spuInfo);

    List<SpuSaleAttrValue> spuSaleAttrValueList(String spuId,String saleAttrId);

    List<SpuSaleAttr> getSpuSaleAttrList(String spuId);

}
