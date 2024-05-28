package com.java3y.hades.advice;


import com.java3y.hades.annotation.HadesResult;
import com.java3y.hades.vo.basic.BasicResultVO;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * @author kl
 * @version 1.0.0
 * @description 统一返回结构
 * @date 2023/2/9 19:00
 */
@ControllerAdvice(basePackages = "com.java3y.hades.controller")
public class HadesResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private static final String RETURN_CLASS = "BasicResultVO";

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return methodParameter.getContainingClass().isAnnotationPresent(HadesResult.class) || methodParameter.hasMethodAnnotation(HadesResult.class);
    }

    @Override
    public Object beforeBodyWrite(Object data, MethodParameter methodParameter, MediaType mediaType, Class aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (Objects.nonNull(data) && Objects.nonNull(data.getClass())) {
            String simpleName = data.getClass().getSimpleName();
            if (RETURN_CLASS.equalsIgnoreCase(simpleName)) {
                return data;
            }
        }
        return BasicResultVO.success(data);
    }
}
