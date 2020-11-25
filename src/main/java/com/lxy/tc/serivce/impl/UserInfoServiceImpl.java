package com.lxy.tc.serivce.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxy.tc.mapper.UserInfoMapper;
import com.lxy.tc.pojo.UserInfo;
import com.lxy.tc.serivce.UserInfoService;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
