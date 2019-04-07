package cn.lucasldl.nearby_refectory.util;

import lombok.Data;

import java.util.List;

@Data
public class Pagination<T> {
    private Integer pageSize;

    private Integer pageNum;

    private Long total;

    private Integer pages;

    private List<T> list;
}
