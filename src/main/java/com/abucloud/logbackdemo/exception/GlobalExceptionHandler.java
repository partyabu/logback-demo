package com.abucloud.logbackdemo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 业务异常管理
     *
     * @param businessException
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public Resp<Object> bizExceptionHandler(BusinessException businessException) {
        businessException.printStackTrace();
        log.error("发生业务异常！原因是：{}", businessException.getErrorMsg());
        return Resp.fail(businessException.getErrorCode(), businessException.getErrorMsg());
    }

    /**
     * hibernate-validator校验管理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Resp<Object> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        StringBuilder errorInfo = new StringBuilder();
        BindingResult bindingResult = ex.getBindingResult();
        int size = bindingResult.getFieldErrors().size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                errorInfo.append(",");
            }
            FieldError fieldError = bindingResult.getFieldErrors().get(i);
            errorInfo.append(fieldError.getField()).append(" :").append(fieldError.getDefaultMessage());
        }
        log.error("请求参数有误！原因是：", ex.fillInStackTrace());
        return Resp.fail(400, errorInfo.toString());
    }


    /**
     * 全局异常管理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Resp<Object> exceptionHandler(Exception e) {
        log.error("未知异常！原因是:{}", e.getMessage());
        e.printStackTrace();
        return Resp.fail(500, "系统开小差了");
    }

    /**
     * GET/POST请求方法错误的拦截器
     * 因为开发时可能比较常见,而且发生在进入controller之前,上面的拦截器拦截不到这个错误
     * 所以定义了这个拦截器
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Resp httpRequestMethodHandler(HttpRequestMethodNotSupportedException ex) {
        log.error("未知异常！原因是:{}", ex.getMessage());
        ex.printStackTrace();
        return Resp.fail(405, "请求方式有误");
    }

}
