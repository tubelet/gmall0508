package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.BaseSaleAttr;
import com.atguigu.gmall.bean.SpuImage;
import com.atguigu.gmall.bean.SpuInfo;

import java.util.List;

public interface SpuService {

    List<SpuInfo> getSpuInfoList(String id);

    List<SpuImage> getSpuImageList(String id);

    List<BaseSaleAttr> baseSaleAttrList();

    void delSpuInfo(String spuInfoId);
}
