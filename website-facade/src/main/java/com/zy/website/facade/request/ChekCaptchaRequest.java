package com.zy.website.facade.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @param $
 * @author zhangyu
 * @description $
 * @date $ $
 * @return $
 * @since
 **/
@Data
public class ChekCaptchaRequest  implements Serializable {

    /***
     *  验证码
     * @mock 123456
     * @since
     */
    private String verifyCode;

    /***
     *  用户ID
     * @mock zhangyu123
     * @since
     */
    private String userUid;
    /***
     *  验证类型
     * @mock  verifcation
     * @since
     */
    private String type;

}
