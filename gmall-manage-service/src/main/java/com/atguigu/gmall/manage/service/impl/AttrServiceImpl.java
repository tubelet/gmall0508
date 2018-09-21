package com.atguigu.gmall.manage.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.BaseAttrInfo;
import com.atguigu.gmall.bean.BaseAttrValue;
import com.atguigu.gmall.manage.mapper.BaseAttrInfoMapper;
import com.atguigu.gmall.manage.mapper.BaseAttrValueMapper;
import com.atguigu.gmall.service.AttrService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class AttrServiceImpl implements AttrService {

    @Autowired
    BaseAttrInfoMapper baseAttrInfoMapper;

    @Autowired
    BaseAttrValueMapper baseAttrValueMapper;

    @Override
    public List<BaseAttrInfo> getAttrListByCtg3(Integer id) {
        Example example = new Example(BaseAttrInfo.class);
        example.createCriteria().andEqualTo("catalog3Id",id);
        List<BaseAttrInfo> baseAttrInfos = baseAttrInfoMapper.selectByExample(example);
        return baseAttrInfos;
    }

    @Override
    public List<BaseAttrValue> getAttrValue(Integer id) {
        Example example = new Example(BaseAttrValue.class);
        example.createCriteria().andCondition("attr_id=",id);
        List<BaseAttrValue> baseAttrValues = baseAttrValueMapper.selectByExample(example);
        return baseAttrValues;
    }

    @Override
    public void saveAttr(BaseAttrInfo baseAttrInfo) {
        String id = baseAttrInfo.getId();

        if(StringUtils.isBlank(id)){//id为空时是保存
            // 保存属性信息
            baseAttrInfoMapper.insertSelective(baseAttrInfo);
            String attrId = baseAttrInfo.getId();
            /*Example example = new Example(BaseAttrInfo.class);
            example.createCriteria().andEqualTo(baseAttrInfo.getAttrName());
            List<BaseAttrInfo> baseAttrInfos = baseAttrInfoMapper.selectByExample(example);
            id = baseAttrInfos.get(0).getId();*/
            List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();

            for (BaseAttrValue baseAttrValue : attrValueList) {
                baseAttrValue.setAttrId(attrId);
                baseAttrValueMapper.insert(baseAttrValue);
            }

        }else{//id不为空时是更新
            baseAttrInfoMapper.updateByPrimaryKeySelective(baseAttrInfo);
            Example example = new Example(BaseAttrValue.class);
            example.createCriteria().andEqualTo("attrId",baseAttrInfo.getId());
            baseAttrValueMapper.deleteByExample(example);

            List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();

            for (BaseAttrValue baseAttrValue : attrValueList) {
                baseAttrValue.setAttrId(id);
                baseAttrValueMapper.insert(baseAttrValue);
            }
        }

    }

    @Override
    public void delAttr(String delId) {
        //删除info信息
        Example example = new Example(BaseAttrInfo.class);
        example.createCriteria().andEqualTo("id",delId);
        baseAttrInfoMapper.deleteByExample(example);
        //删除相应的属性值
        Example example1 = new Example(BaseAttrValue.class);
        example1.createCriteria().andEqualTo("attrId",delId);
        baseAttrValueMapper.deleteByExample(example1);
    }


}
