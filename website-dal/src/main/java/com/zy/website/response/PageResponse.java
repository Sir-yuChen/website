package com.zy.website.response;

import lombok.Data;

/**
 *  分页公共参数
 * @author zhangyu
 * @description   分页公共参数
 * @date 2022/2/28 16:04
 */
@Data
public class PageResponse  extends FrontResponse{

    /***
     * 页码
     * @mock 1
     * @since
     */
    private long pageNum;
    /***
     * 每页显示数量
     * @mock 10
     * @since
     */
    private long pageSize;
    /***
     * 总页数
     * @mock 100
     * @since
     */
    private long total;

}
