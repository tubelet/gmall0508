package com.atguigu.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.BaseCatalog1;
import com.atguigu.gmall.bean.BaseCatalog2;
import com.atguigu.gmall.bean.BaseCatalog3;
import com.atguigu.gmall.manage.mapper.BaseCatalog1Mapper;
import com.atguigu.gmall.manage.mapper.BaseCatalog2Mapper;
import com.atguigu.gmall.manage.mapper.BaseCatalog3Mapper;
import com.atguigu.gmall.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    BaseCatalog1Mapper baseCatalog1Mapper;

    @Autowired
    BaseCatalog2Mapper baseCatalog2Mapper;

    @Autowired
    BaseCatalog3Mapper baseCatalog3Mapper;

    @Override
    public List<BaseCatalog1> getCatalog1() {
        List<BaseCatalog1> baseCatalog1s = baseCatalog1Mapper.selectAll();
        return baseCatalog1s;
    }

    @Override
    public List<BaseCatalog2> getCatalog2ById(Integer id) {
        Example example = new Example(BaseCatalog2.class);
        example.createCriteria().andCondition("catalog1_id=", id);
        List<BaseCatalog2> baseCatalog2s = baseCatalog2Mapper.selectByExample(example);
        return baseCatalog2s;
    }

    @Override
    public List<BaseCatalog3> getCatalog3ById(Integer id) {
        Example example = new Example(BaseCatalog3.class);
        example.createCriteria().andCondition("catalog2_id=", id);
        List<BaseCatalog3> baseCatalog3s = baseCatalog3Mapper.selectByExample(example);
        return baseCatalog3s;
    }

}
