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



}
