package com.zy.website.exception;


import com.zy.website.code.ApiReturnCode;

/**
 * 仅供参考（业务异常一定要自定义异常类，并继承BaseBusinessException）
 */
public class WebsiteBusinessException extends BaseBusinessException {

	private static final long serialVersionUID = 1L;

	public WebsiteBusinessException(String msg, String code, Exception ex, String jsonContent) {
		super(msg, code, ex, jsonContent);
	}

	public WebsiteBusinessException(String msg, String code, String jsonContent) {
		super(msg, code, jsonContent);
	}

	public WebsiteBusinessException(String msg, String code) {
		super(msg, code);
	}

	public WebsiteBusinessException(ApiReturnCode apiReturnCode) {
		this(apiReturnCode.getMessage(), apiReturnCode.getCode());
	}

}
