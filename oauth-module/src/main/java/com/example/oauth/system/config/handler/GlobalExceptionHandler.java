package com.example.oauth.system.config.handler;

import com.example.oauth.comm.rest.RestResult;
import com.example.oauth.comm.rest.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>
 *      全局异常拦截器，本拦截器，主要拦截运行时异常
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@Slf4j
@Configuration
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public RestResult runtimeExceptionHandler (RuntimeException ex) {
        ex.printStackTrace();
        return ResultUtils.failure(ex.getMessage());
    }

}
