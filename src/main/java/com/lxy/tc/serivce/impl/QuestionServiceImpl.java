package com.lxy.tc.serivce.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxy.tc.mapper.QuestionMapper;
import com.lxy.tc.pojo.Question;
import com.lxy.tc.serivce.QuestionService;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

}
