package com.atguigu.gmall.manage.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.BaseSaleAttr;
import com.atguigu.gmall.bean.SpuImage;
import com.atguigu.gmall.bean.SpuInfo;
import com.atguigu.gmall.manage.mapper.SpuImageMapper;
import com.atguigu.gmall.manage.mapper.SpuInfoMapper;
import com.atguigu.gmall.manage.mapper.SpuSaleAttrMapper;
import com.atguigu.gmall.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    SpuInfoMapper spuInfoMapper;

    @Autowired
    SpuImageMapper spuImageMapper;

    @Autowired
    SpuSaleAttrMapper spuSaleAttrMapper;

    @Override
    public List<SpuInfo> getSpuInfoList(String id) {
        Example example = new Example(SpuInfo.class);
        example.createCriteria().andEqualTo("catalog3Id",id);
        List<SpuInfo> spuInfoList = spuInfoMapper.selectByExample(example);
        return  spuInfoList;
    }

    @Override
    public List<SpuImage> getSpuImageList(String id) {
        Example example = new Example(SpuImage.class);
        example.createCriteria().andEqualTo("spuId",id);
        List<SpuImage> spuImages = spuImageMapper.selectByExample(example);
        return spuImages;
    }

    @Override
    public List<BaseSaleAttr> baseSaleAttrList() {
        List<BaseSaleAttr> baseSaleAttrs = spuSaleAttrMapper.selectAll();
        return baseSaleAttrs;
    }

    @Override
    public void delSpuInfo(String spuInfoId) {
        spuInfoMapper.deleteByPrimaryKey(spuInfoId);
    }
}
