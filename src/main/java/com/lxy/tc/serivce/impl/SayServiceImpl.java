package com.lxy.tc.serivce.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxy.tc.mapper.SayMapper;
import com.lxy.tc.pojo.Say;
import com.lxy.tc.serivce.SayService;
import org.springframework.stereotype.Service;

@Service
public class SayServiceImpl extends ServiceImpl<SayMapper, Say> implements SayService {

}
