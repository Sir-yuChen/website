package com.zy.website;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zy.website.code.ApiReturnCode;
import com.zy.website.response.FrontResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 统一返回实体类
 */
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL) //为null的字段不返回
public class ApiReturn<T> extends FrontResponse {
    @JsonIgnore
    private String resultCode;
    @JsonIgnore
    private String resultMsg;

    /**
     * 返回的数据
     */
    private T data;

    /**
     * 返回的状态码 200:成功
     */
    private String code;

    /**
     * 返回的提示信息
     */
    private String msg;

    /**
     * 返回服务器当前时间戳（毫秒）;
     */
    public Long getStime() {
        return System.currentTimeMillis();
    }

    public ApiReturn() {
        this.setApiReturnCode(ApiReturnCode.SUCCESSFUL);
    }

    public ApiReturn(ApiReturnCode apiReturnCode) {
        this.setApiReturnCode(apiReturnCode);
    }

    public ApiReturn(T data) {
        this.setApiReturnCode(ApiReturnCode.SUCCESSFUL);
        this.data = data;
    }

    /**
     * 设置返回的状态码及提示信息
     *
     * @param apiReturnCode
     *            前台返回值的枚举类
     */
    public void setApiReturnCode(ApiReturnCode apiReturnCode) {
        this.code = apiReturnCode.getCode();
        this.msg = apiReturnCode.getMessage();
    }


}
