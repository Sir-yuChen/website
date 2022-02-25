package com.zy.website.request;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;


public abstract class FrontRequest implements Serializable {
    private static final long serialVersionUID = -7250473166523181107L;
    /***
      * 页码
      * @mock 1
      * @since
      */
    private int pageNum = 1;
    /***
      * 每页显示数量
      * @mock
      * @since
      */
    private int pageSize = 10;

    public FrontRequest() {
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String toString() {
        try {
            return ReflectionToStringBuilder.toString(this);
        } catch (Exception var2) {
            var2.printStackTrace();
            return super.toString();
        }
    }
}

