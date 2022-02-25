package com.zy.website.exception;

/**
 *
 * @ClassName ServiceException
 * @Description  service 运行时异常
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -8634700792767837033L;

	private String code;

	public ErrorCode errorCode;

	public ServiceException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public ServiceException() {

	}

	public ServiceException(String code, String message) {
		super(message);
		this.code = code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	/**
	 *	示例
	 *	log.error("[{}]加密参数不可为空", xxxx);
	 *  throw new ServiceException(ApiReturnCode.PARAM_ERROR.getCode(), "加密参数不可为空");
	 *
	 */



}
