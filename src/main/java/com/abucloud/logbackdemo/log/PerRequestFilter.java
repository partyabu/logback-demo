package com.abucloud.logbackdemo.log;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @Description: 先于拦截器进入，后于拦截器结束
 * @Author party-abu
 * @Date 2022/5/22 10:02
 */
@Component
public class PerRequestFilter extends OncePerRequestFilter {

    private static final String TRACE_ID = "traceId";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {
        try {
            MDC.put(TRACE_ID, UUID.randomUUID().toString().replace("-", ""));
            // 解决多次获取inputStream流拿不到数据的问题，使用缓存进行存储
            ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
            filterChain.doFilter(requestWrapper, response);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        } finally {
            MDC.clear();
        }
    }
}
