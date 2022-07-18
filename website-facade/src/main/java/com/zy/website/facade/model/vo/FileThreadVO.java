package com.zy.website.facade.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.InputStream;
import java.util.List;

/**
 * @author zhangyu
 * @since 2022-02-26
 * description:多线程读取文件实体类
 */
@Data
@Accessors(chain = true)
public class FileThreadVO<T> {
    private InputStream is;
    private Integer start_line;
    private Integer end_line;
    private List<T> result;
}