package com.zy.website.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class FrontResponse  implements Serializable {
    private static final long serialVersionUID = -6080858828270195838L;
    private String resultCode;
    private String resultMsg;
//    private String traceNo; 请求业务线唯一id

    public FrontResponse() {
    }

    public String getResultCode() {
        return this.resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return this.resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

//    public String getTraceNo() {
//        return this.traceNo;
//    }
//
//    public void setTraceNo(String traceNo) {
//        this.traceNo = traceNo;
//    }

    public String toString() {
        try {
            return ReflectionToStringBuilder.toString(this);
        } catch (Exception var2) {
            var2.printStackTrace();
            return super.toString();
        }
    }
}
