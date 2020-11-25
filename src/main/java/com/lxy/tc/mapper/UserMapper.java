package com.lxy.tc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lxy.tc.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author tudte
 * @Date 2020/11/6 22:53
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
