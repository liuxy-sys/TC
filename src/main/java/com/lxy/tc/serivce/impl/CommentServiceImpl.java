package com.lxy.tc.serivce.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxy.tc.mapper.CommentMapper;
import com.lxy.tc.pojo.Comment;
import com.lxy.tc.serivce.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
