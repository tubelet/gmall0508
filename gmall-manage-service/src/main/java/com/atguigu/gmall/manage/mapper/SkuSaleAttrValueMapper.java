package com.atguigu.gmall.manage.mapper;

import com.atguigu.gmall.bean.SkuInfo;
import com.atguigu.gmall.bean.SkuSaleAttrValue;
import com.atguigu.gmall.bean.SpuSaleAttr;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SkuSaleAttrValueMapper extends Mapper<SkuSaleAttrValue> {
    List<SpuSaleAttr> selectSpuSaleAttrListCheckBySku(@Param("spuId") String spuId,@Param("skuId") String skuId);

    List<SkuInfo> selectSkuSaleAttrValueListBySpu(@Param("spuId") Integer spuId);
}
