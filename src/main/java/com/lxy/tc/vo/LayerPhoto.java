package com.lxy.tc.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LayerPhoto {


    private String title;
    private int id;

    private int start;
    private List<LayerPhotoData> data;

}
