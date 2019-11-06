package com.robingong;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Robin
 * @date 2019/11/6
 */
@Slf4j
@Component
public class MyAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        log.info(method.getName());
        log.info("Prams: {}", params);
        log.error("Exception caught", ex);
    }
}
