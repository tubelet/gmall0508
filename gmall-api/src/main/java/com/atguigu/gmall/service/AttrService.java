package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.BaseAttrInfo;
import com.atguigu.gmall.bean.BaseAttrValue;

import java.util.List;

public interface AttrService {

    List<BaseAttrInfo> getAttrListByCtg3(Integer id);

    List<BaseAttrValue> getAttrValue(Integer id);

    void saveAttr(BaseAttrInfo baseAttrInfo);

    void delAttr(String delId);

    List<BaseAttrInfo> selectAttrInfoList(long catalog3Id);
}
