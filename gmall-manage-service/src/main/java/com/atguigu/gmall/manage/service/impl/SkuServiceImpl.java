package com.atguigu.gmall.manage.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.manage.mapper.SkuAttrValueMapper;
import com.atguigu.gmall.manage.mapper.SkuImageMapper;
import com.atguigu.gmall.manage.mapper.SkuInfoMapper;
import com.atguigu.gmall.manage.mapper.SkuSaleAttrValueMapper;
import com.atguigu.gmall.service.SkuService;
import com.atguigu.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
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

    @Autowired
    RedisUtil redisUtil;

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

    @Override
    public SkuInfo getSkuById(String skuId) {
        String custom = Thread.currentThread().getName();

        System.err.println(custom+"线程进入sku查询方法");

        SkuInfo skuInfo = null;
        String skuKey = "sku:"+skuId+":info";

        // 缓存redis查询
        Jedis jedis = redisUtil.getJedis();
        String s = jedis.get(skuKey);

        if(StringUtils.isNotBlank(s)&&s.equals("empty")){
            System.err.println(custom+"线程进发现数据库中没有数据，返回");
            return null;
        }

        if(StringUtils.isNotBlank(s)&&!"empty".equals(s)){
            System.err.println(custom+"线程能够从redis中获取数据");
            skuInfo = JSON.parseObject(s, SkuInfo.class);

        }else{
            System.err.println(custom+"线程没有从redis中取出数据库，申请访问数据库的分布式锁");
            // db查询(限制db的访问量)
            String OK = jedis.set("sku:" + skuId + ":lock", "1", "nx", "px", 10000);
            if(StringUtils.isNotBlank(OK)){
                System.err.println(custom+"线程得到分布式锁，开始访问数据库");
                skuInfo = getSkuByIdFromDB(skuId);
                if(null!=skuInfo){
                    System.err.println(custom+"线程成功访问数据库，删除分布式锁");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    jedis.del("sku:" + skuId + ":lock");
                }
            }else{
                System.err.println(custom+"线程需要访问数据库，但是未得到分布式锁，开始自旋");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 自旋
                getSkuById(skuId);
            }

            if(null==skuInfo){
                System.err.println(custom+"线程访问数据库后，发现数据库为空，将空值同步redis");
                jedis.set(skuKey,"empty");
            }

            // 同步redis
            System.err.println(custom+"线程将数据库中获取数据同步redis");
            if(null!=skuInfo&&!"empty".equals(s)){
                jedis.set(skuKey,JSON.toJSONString(skuInfo));
            }

        }
        System.err.println(custom+"线程结束访问返回");

        jedis.close();
        return skuInfo;
    }

    public SkuInfo getSkuByIdFromDB(String skuId) {

        Example example = new Example(SkuInfo.class);
        example.createCriteria().andEqualTo("id",skuId);
        SkuInfo skuInfo = skuInfoMapper.selectOneByExample(example);

        Example skuImageExample = new Example(SkuImage.class);
        skuImageExample.createCriteria().andEqualTo("skuId",skuId);
        List<SkuImage> skuImages = skuImageMapper.selectByExample(skuImageExample);
        skuInfo.setSkuImageList(skuImages);

        Example skuAttrValueExample = new Example(SkuAttrValue.class);
        skuAttrValueExample.createCriteria().andEqualTo("skuId",skuId);
        List<SkuAttrValue> skuAttrValues = skuAttrValueMapper.selectByExample(skuAttrValueExample);
        skuInfo.setSkuAttrValueList(skuAttrValues);

        Example skuSaleAttrValueExample = new Example(SkuSaleAttrValue.class);
        skuSaleAttrValueExample.createCriteria().andEqualTo("skuId",skuId);
        List<SkuSaleAttrValue> skuSaleAttrValues = skuSaleAttrValueMapper.selectByExample(skuSaleAttrValueExample);
        skuInfo.setSkuSaleAttrValueList(skuSaleAttrValues);

        return skuInfo;
    }

    @Override
    public List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(String spuId,String skuId) {
        List<SpuSaleAttr> spuSaleAttrList = skuSaleAttrValueMapper.selectSpuSaleAttrListCheckBySku(spuId,skuId);
        return spuSaleAttrList;
    }

    @Override
    public List<SkuInfo> getSkuSaleAttrValueListBySpu(String spuId) {

        List<SkuInfo> skuInfos = skuSaleAttrValueMapper.selectSkuSaleAttrValueListBySpu(Integer.parseInt(spuId));

        return skuInfos;
    }

}
