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
public class CaptchaRequest implements Serializable {

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
