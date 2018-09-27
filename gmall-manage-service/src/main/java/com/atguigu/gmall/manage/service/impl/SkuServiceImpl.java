package com.atguigu.gmall.manage.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.manage.mapper.SkuAttrValueMapper;
import com.atguigu.gmall.manage.mapper.SkuImageMapper;
import com.atguigu.gmall.manage.mapper.SkuInfoMapper;
import com.atguigu.gmall.manage.mapper.SkuSaleAttrValueMapper;
import com.atguigu.gmall.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    SkuInfoMapper skuInfoMapper;

    @Autowired
    SkuImageMapper skuImageMapper;

    @Autowired
    SkuAttrValueMapper skuAttrValueMapper;

    @Autowired
    SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Override
    public List<SkuInfo> skuInfoListBySpu(String spuId) {
        Example example = new Example(SkuInfo.class);
        example.createCriteria().andEqualTo("spuId",spuId);
        List<SkuInfo> skuInfos = skuInfoMapper.selectByExample(example);
        return skuInfos;
    }

    @Override
    public void delSkuInfo(String skuInfoId) {
        skuInfoMapper.deleteByPrimaryKey(skuInfoId);
    }

    @Override
    public List<SkuImage> getSkuImageList(String skuId) {
        Example example = new Example(SkuImage.class);
        example.createCriteria().andEqualTo("skuId",skuId);
        List<SkuImage> skuImages = skuImageMapper.selectByExample(example);
        return skuImages;
    }

    @Override
    public List<SkuAttrValue> getSkuAttrValueList(String skuId) {
        Example example = new Example(SkuAttrValue.class);
        example.createCriteria().andEqualTo("skuId",skuId);
        List<SkuAttrValue> skuAttrValues = skuAttrValueMapper.selectByExample(example);
        return skuAttrValues;
    }

    @Override
    public List<SkuSaleAttrValue> getSkuSaleAttrValueList(String skuId) {
        Example example = new Example(SkuSaleAttrValue.class);
        example.createCriteria().andEqualTo("skuId",skuId);
        List<SkuSaleAttrValue> skuSaleAttrValues = skuSaleAttrValueMapper.selectByExample(example);
        return skuSaleAttrValues;
    }

    @Override
    public void saveSku(SkuInfo skuInfo) {
        String skuId = skuInfo.getId();
        // 保存sku信息
        //skuInfoMapper.insertSelective(skuInfo);
        //保存主表 通过主键存在判断是修改 还是新增
        if(skuInfo.getId()==null||skuInfo.getId().length()==0){
            skuInfo.setId(null);
            skuInfoMapper.insertSelective(skuInfo);
        }else{
            skuInfoMapper.updateByPrimaryKey(skuInfo);
        }

        // 保存平台属性关联信息
        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
        for (SkuAttrValue skuAttrValue : skuAttrValueList) {
            skuAttrValue.setSkuId(skuId);
            skuAttrValueMapper.insert(skuAttrValue);
        }

        // 保存销售属性关联信息
        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
        for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
            skuSaleAttrValue.setSkuId(skuId);
            skuSaleAttrValueMapper.insert(skuSaleAttrValue);
        }


        // 保存sku图片信息
        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        for (SkuImage skuImage : skuImageList) {
            skuImage.setSkuId(skuId);
            skuImageMapper.insert(skuImage);
        }
    }
}
