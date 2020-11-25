package com.lxy.tc.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LayerPhotoData {

    private String alt;

    private int pid;

    private String src;

    private String thumb;

}
