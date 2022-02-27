package com.zy.website.dto;


import lombok.Data;

import java.util.List;

@Data
public class NoticeDTO extends BaseDTO {

    /**
     * 榜单名称
     */
    private String noticeName;

    /**
     * 榜单唯一标识
     */
    private String noticeMark;

    /**
     * 榜单顺序
     */
    private Integer noticeOrder;

    /**
     * 榜单跳转连接[更多]
     */
    private String noticeUrl;

    /**
     * 榜单数据[20]
     */
    private List noticeData;

}
