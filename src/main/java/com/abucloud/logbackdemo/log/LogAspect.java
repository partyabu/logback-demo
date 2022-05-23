package com.abucloud.logbackdemo.log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

/**
 * @Description:
 * @Author party-abu
 * @Date 2022/5/22 14:59
 */
@Slf4j
@Component
@Aspect
public class LogAspect {


    @Pointcut("execution(* com.abucloud.logbackdemo.controller.*.*(..))")
    public void logPoint() {
    }

    @Pointcut("execution(* com.abucloud.logbackdemo.exception.GlobalExceptionHandler.*(..))")
    public void exceptionHandlerPoint() {
    }

    /**
     * 请求前
     *
     * @param joinPoint
     */
    @Before("logPoint()")
    public void preLog(JoinPoint joinPoint) {

        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }

        HttpServletRequest request = requestAttributes.getRequest();
        Signature signature = joinPoint.getSignature();
        log.info("请求地址：{}，进入方法：{}",
                request.getRequestURL(),
                signature.getDeclaringTypeName() + ":" + signature.getName());

        JSONObject jsonObject = this.getRequestInfo(request);
        log.info("请求参数：{}", JSONObject.parseObject(jsonObject.toString()));

        MDC.put("beginRequestTime", String.valueOf(System.currentTimeMillis()));
    }


    /**
     * 请求后处理
     *
     * @param returnValue
     */
    @AfterReturning(value = "logPoint() || exceptionHandlerPoint()", returning = "returnValue")
    public void preLog(Object returnValue) {

        Map<String, String> mdcMap = MDC.getCopyOfContextMap();
        String beginRequestTime = mdcMap.getOrDefault("beginRequestTime", "");
        long requestTime = System.currentTimeMillis() - Long.parseLong(beginRequestTime);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("requestTime", requestTime + "毫秒");
        jsonObject.put("result", returnValue);
        log.info("响应结果：{}", JSON.parseObject(jsonObject.toString()));

    }

    /**
     * 获取请求参数
     *
     * @param request
     * @return
     */
    private JSONObject getRequestInfo(HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();
        try {

            // 查询字符串
            String queryString = request.getQueryString();
            if (StringUtils.hasLength(queryString)) {
                String requestParams = URLDecoder.decode(queryString, "utf-8");
                jsonObject.put("queryString", requestParams);
            }

            // 获取请求体
            if (request instanceof ContentCachingRequestWrapper) {
                ContentCachingRequestWrapper requestWrapper = (ContentCachingRequestWrapper) request;
                String requestBody = new String(requestWrapper.getContentAsByteArray());
                if (StringUtils.hasLength(requestBody)) {
                    jsonObject.put("requestBody", JSON.parseObject(requestBody));
                }
            }

            // 获取ip
            String remoteAddr = request.getRemoteAddr();
            jsonObject.put("IP", remoteAddr);

        } catch (IOException e) {
            log.error("解析请求失败", e);
            jsonObject.put("parseRequestError", e.getMessage());
        }

        return jsonObject;

    }
}
