package com.lxy.tc.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("question")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String qid;

    private String title;

    private String content;

    private Integer status;

    private Integer sort;

    private Integer views;

    private String authorId;

    private String authorName;

    private String authorAvatar;

    private Integer categoryId;

    private String categoryName;

    private Date gmtCreate;

    private Date gmtUpdate;


}
