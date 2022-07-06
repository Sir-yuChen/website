package com.zy.website.facade.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 * @date 2022/7/2 23:15
 **/
@Data
public class UserLoginRequest implements Serializable {

    /***
     * 用户名
     * @mock zhangyu01
     * @since
     */
    private String userName;

    /***
     * 密码
     * @mock 5f968bfaee3680299115bb97
     * @since
     */
    private String pwd;
    /***
     * 验证码
     * @mock 15286
     * @since
     */
    private String verifyCode;
    /***
     * 验证类型
     * @mock verifcation
     * @since
     */
    private String type;
    /***
     * IP
     * @mock  127.0.0.1
     * @since
     */
    private String ip;



}
