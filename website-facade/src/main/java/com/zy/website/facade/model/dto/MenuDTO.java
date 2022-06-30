package com.zy.website.facade.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/*
 * 菜单
 * @author zhangyu
 * @date 2022/2/26 17:14
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL) //为null的字段不返回
public class MenuDTO implements Serializable {

    private Integer id;
    /**
     * 菜单父ID 顶级0
     */
    private Integer parentId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 唯一标识
     */
    private String menuMark;

    /**
     * 状态 Y正常 N暂停 D删除
     */
    private String menuStatus;

    /**
     * 排序
     */
    private Integer menuSequence;

    /**
     * 图标地址
     */
    private String menuIconUrl;

    /**
     * 跳转地址
     */
    private String menuUrl;

    /**
     * 创建时间
     */
    private String creactTime;

    /**
     * 菜单类型[菜单位置]
     */
    private String menuType;
    /**
     * 是否有子类 true/false
     */
    private Boolean menuIsChild;

    /**
     * 子菜单集合
     */
    private List childList;
}
