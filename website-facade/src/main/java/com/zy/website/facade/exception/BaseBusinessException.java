package com.zy.website.facade.exception;

/**
 * @Author zhangyu
 * @Date 11:26 2022/2/25
 **/
public class BaseBusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String msg;
    private String code;
    private Throwable innerException;
    private String jsonContent;
    protected boolean monitored = false;
    protected boolean loggable = true;

    public BaseBusinessException(String msg, String code, Throwable ex, String jsonContent) {
        super(msg);
        this.msg = msg;
        this.code = code;
        this.innerException = ex;
        this.jsonContent = jsonContent;
    }

    public BaseBusinessException(String msg, String code, String jsonContent) {
        super(msg);
        this.msg = msg;
        this.code = code;
        this.jsonContent = jsonContent;
    }

    public BaseBusinessException(String msg, String code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Throwable getInnerException() {
        return this.innerException;
    }

    public void setInnerException(Throwable innerException) {
        this.innerException = innerException;
    }

    public String getJsonContent() {
        return this.jsonContent;
    }

    public void setJsonContent(String jsonContent) {
        this.jsonContent = jsonContent;
    }

    public boolean isMonitored() {
        return this.monitored;
    }

    public void setMonitored(boolean monitored) {
        this.monitored = monitored;
    }

    public boolean isLoggable() {
        return this.loggable;
    }

    public void setLoggable(boolean loggable) {
        this.loggable = loggable;
    }
}