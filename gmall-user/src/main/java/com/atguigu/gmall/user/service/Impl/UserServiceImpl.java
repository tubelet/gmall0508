package com.atguigu.gmall.user.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.bean.UserInfo;
import com.atguigu.gmall.service.UserService;
import com.atguigu.gmall.user.mapper.UserAddressMapper;
import com.atguigu.gmall.user.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    UserAddressMapper userAddressMapper;

    //重写UserService的接口方法
    @Override
    public List<UserInfo> getUserInfo() {
        //调用通用mapper的selectAll()方法
        List<UserInfo> userInfo = userInfoMapper.selectAll();
        return userInfo;
    }

    @Override
    public List<UserAddress> getUserAddress() {
        List<UserAddress> userAddresses = userAddressMapper.selectAll();
        return userAddresses;
    }

}
