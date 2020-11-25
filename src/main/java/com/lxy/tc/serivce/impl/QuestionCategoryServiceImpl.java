package com.lxy.tc.serivce.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxy.tc.mapper.QuestionCategoryMapper;
import com.lxy.tc.pojo.QuestionCategory;
import com.lxy.tc.serivce.QuestionCategoryService;
import org.springframework.stereotype.Service;

@Service
public class QuestionCategoryServiceImpl extends ServiceImpl<QuestionCategoryMapper, QuestionCategory> implements QuestionCategoryService {

}
