package com.atguigu.gmall.manage.mapper;

import com.atguigu.gmall.bean.BaseAttrInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

//继承Mapper接口，注意是tk的通用mapper
public interface BaseAttrInfoMapper extends Mapper<BaseAttrInfo> {

    List<BaseAttrInfo> selectAttrInfoList(long catalog3Id);
}

