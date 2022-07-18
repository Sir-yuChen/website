package com.zy.website.facade.model.dto;


import lombok.Data;

@Data
public class IpJsonDTO {
    /***
     * 状态
     * @mock success/fail
     * @since
     */
    private String status;
    /***
     * 异常信息 【 私有范围、保留范围、无效查询】
     * @mock private range, reserved range, invalid query
     * @since
     */
    private String message;
    /***
     *  国家
     * @mock 中国
     * @since
     */
    private String country;
    private String countryCode;
    private String region;
    /***
     * 地区/州
     * @mock California
     * @since
     */
    private String regionName;
    /***
     * 城市
     * @mock 北京
     * @since
     */
    private String city;
    /***
     * 邮政编码
     * @mock 94043
     * @since
     */
    private String zip;
    /***
     *  纬度
     * @mock 37.4192
     * @since
     */
    private float lat;
    /***
     *  经度
     * @mock -122.0574
     * @since
     */
    private float lon;
    /***
      * 时区 (tz)
      * @mock
      * @since
      */
    private String timezone;
    /***
      * ISP 名称
      * @mock Google
      * @since
      */
    private String isp;
    /***
      * 机构名称
      * @mock   Google
      * @since
      */
    private String org;
    private String query;
}
