package com.zy.website.response;

import com.zy.website.dto.NoticeDTO;
import lombok.Data;

import java.util.List;

/*
 * 菜单 response
 * @author zhangyu
 * @date 2022/2/26 17:16
 * @return
 */
@Data
public class NoticeResponse extends FrontResponse{

    /**
     * 榜单集合
     * @mock
     * @since v1.0
     */
    private List<NoticeDTO> noticeList;

}
