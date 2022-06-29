package com.zy.website.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class CaptchaVO implements Serializable {

    /**
     * 验证码
     */
    private String captchaCode;

}
