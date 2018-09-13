package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.bean.UserInfo;

import java.util.List;

public interface UserService {

    //创建一个获取userList的接口
    public List<UserInfo> getUserInfo();

    public List<UserAddress> getUserAddress();
}
