package com.abucloud.logbackdemo.exception;

/**
 * @author abu
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -6627343597988887982L;
    /**
     * 错误码
     */
    protected Integer errorCode;
    /**
     * 错误信息
     */
    protected String errorMsg;


    /**
     * 禁止无用且昂贵的堆栈日志追踪，提高效率
     *
     * @return
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public BusinessException() {
    }

    public BusinessException(String errorMsg) {
        this.errorCode = -1;
        this.errorMsg = errorMsg;
    }

    public BusinessException(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public BusinessException(Integer errorCode, String errorMsg) {
        super(errorCode.toString());
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }

}
