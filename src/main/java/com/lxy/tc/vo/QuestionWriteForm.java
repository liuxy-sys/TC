package com.lxy.tc.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class QuestionWriteForm {

    private String title;
    private String content;

    private Integer categoryId;

    private String authorId;
    private String authorName;
    private String authorAvatar;

}
